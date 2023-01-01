package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CActivateBootsPacket;
import ml.northwestwind.moreboots.handler.packet.CPlayerMultiJumpPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class MeerkatFeetItem extends BootsItem {
    public MeerkatFeetItem() {
        super(ItemInit.ModArmorMaterial.MEERKAT, "meerkat_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) || entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() < 1)
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 0, false, false, false));
        if (!entity.isCrouching()) {
            if (entity.getDeltaMovement().y() < 0) {
                entity.setDeltaMovement(entity.getDeltaMovement().multiply(1, 0, 1).add(0, -0.05, 0));
                entity.hasImpulse = true;
                entity.fallDistance = 0;
            }
        } else if (entity.isOnGround()) {
            ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
            CompoundTag tag = boots.getOrCreateTag();
            long tickSneak = tag.getLong("tickSneak");
            tag.putLong("tickSneak", tag.getLong("tickSneak") + 1);
            if (tickSneak < 200) {
                tickSneak += 1;
                tag.putLong("tickSneak", tickSneak);
            }
            if (entity instanceof Player && !entity.level.isClientSide)
                ((Player) entity).displayClientMessage(MutableComponent.create(new TranslatableContents("message.moreboots.charging_jump", tickSneak)), true);
            boots.setTag(tag);
        }
        if (entity.isVisuallySwimming() && entity.isInWater())
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.01, 1, 1.01).add(entity.getLookAngle().scale(0.15)));
    }

    @Override
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.add(0, 0.25, 0));
        entity.hasImpulse = true;
        if (entity.isCrouching()) return;
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = boots.getOrCreateTag();
        entity.setDeltaMovement(motion.add(0, 0.005 * tag.getLong("tickSneak"), 0));
        tag.putLong("tickSneak", 0);
        boots.setTag(tag);
    }

    @Override
    public void activateBoots() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = boots.getOrCreateTag();
        boolean newState = !tag.getBoolean("Activated");
        tag.putBoolean("Activated", newState);
        boots.setTag(tag);
        MoreBootsPacketHandler.INSTANCE.sendToServer(new CActivateBootsPacket(newState));
    }

    @Override
    public void onJump() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        player.jumpFromGround();
        player.setDeltaMovement(player.getDeltaMovement().add(0, 0.25, 0));
        if (player.level.isClientSide)
            MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerMultiJumpPacket());
    }

    @Override
    public void getCollisionShape(BlockGetter worldIn, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        BlockState state = worldIn.getBlockState(pos);
        if((state.getMaterial().equals(Material.WATER) || state.getMaterial().equals(Material.POWDER_SNOW)) && context.isAbove(Shapes.block(), pos, true)) cir.setReturnValue(Shapes.block());
    }
}
