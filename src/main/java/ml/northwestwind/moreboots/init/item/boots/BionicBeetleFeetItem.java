package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.handler.packet.CPlayerMultiJumpPacket;
import ml.northwestwind.moreboots.init.BlockInit;
import ml.northwestwind.moreboots.init.EffectInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;

public class BionicBeetleFeetItem extends BootsItem {
    public BionicBeetleFeetItem() {
        super(ItemInit.ModArmorMaterial.BIONIC, "bionic_beetle_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = boots.getOrCreateTag();
        int viscousity = (tag.getBoolean("reversed") ? 1 : 0) - tag.getInt("viscousity");
        int bounciness = tag.getInt("bounciness");
        int ability = tag.getInt("ability");
        if (viscousity == 0) {
            boolean climable = !Utils.isSurroundedByInvalidBlocks(entity) && !entity.isInLava() && !entity.isInWater() && !entity.isSpectator() && !entity.isOnGround();
            Vec3 motion = entity.getDeltaMovement();
            motion = motion.multiply(1, 0, 1);
            boolean ascending = entity.horizontalCollision;
            boolean descending = !ascending && entity.isCrouching();
            if (climable) {
                if (!ascending && !descending) entity.setDeltaMovement(motion);
                else if (ascending) entity.setDeltaMovement(motion.add(0, 0.2, 0));
                else entity.setDeltaMovement(motion.subtract(0, 0.2, 0));
                if (entity.getRandom().nextInt(Math.max(1, boots.getMaxDamage() - boots.getDamageValue())) == 0)
                    boots.hurtAndBreak(1, entity, entity1 -> entity1.playSound(SoundEvents.ITEM_BREAK, 1, 1));
                entity.fallDistance = 0f;
            }
        } else
            entity.addEffect(new MobEffectInstance(EffectInit.SLIPPERINESS.get(), 10, 1, false, false));
        if (bounciness == 0) {
            long tickSneak = tag.getLong("tickSneak");
            tag.putLong("tickSneak", tag.getLong("tickSneak") + 1);
            if (tickSneak < 200) {
                tickSneak += 1;
                tag.putLong("tickSneak", tickSneak);
            }
            if (entity instanceof Player && !entity.level.isClientSide)
                ((Player) entity).displayClientMessage(new TranslatableComponent("message.moreboots.charging_jump", tickSneak), true);
            boots.setTag(tag);
        }
        if (ability == 0) {
            BlockState state = entity.level.getBlockState(entity.blockPosition().below());
            BlockState blockState = entity.level.getBlockState(entity.blockPosition());
            if (blockState.is(BlockInit.VISCOUS_GOO.get()) || !Block.isFaceFull(state.getCollisionShape(entity.level, entity.blockPosition()), Direction.UP) && (blockState.isAir() || !blockState.canBeReplaced(new BlockPlaceContext((Player) entity, InteractionHand.MAIN_HAND, new ItemStack(ItemInit.VISCOUS_GOO.get()), new BlockHitResult(new Vec3(0.5, 1, 0.5), Direction.UP, entity.blockPosition().below(), false))))) return;
            entity.level.setBlockAndUpdate(entity.blockPosition(), BlockInit.VISCOUS_GOO.get().defaultBlockState());
        }
    }

    @Override
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.getItemBySlot(EquipmentSlot.FEET).getOrCreateTag().getInt("bounciness") == 2) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.5, 0));
            entity.hasImpulse = true;
        }
    }

    @Override
    public void onJump() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || player.isOnGround() || player.getAbilities().flying || player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTag().getInt("bounciness") != 1) return;
        player.jumpFromGround();
        player.setDeltaMovement(player.getDeltaMovement().add(0, 0.25, 0));
        if (player.level.isClientSide)
            MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerMultiJumpPacket());
    }
}
