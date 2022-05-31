package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.handler.packet.CActivateBootsPacket;
import ml.northwestwind.moreboots.handler.packet.CPlayerMultiJumpPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class MightyFeetItem extends BootsItem {
    public MightyFeetItem() {
        super(ItemInit.ModArmorMaterial.MIGHTY, "mighty_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        BlockPos blockPos = entity.blockPosition();
        if (entity.getItemBySlot(EquipmentSlot.FEET).getOrCreateTag().getBoolean("Activated") && !entity.level.getBlockState(blockPos.below()).getCollisionShape(entity.level, blockPos.below()).equals(Shapes.empty())) {
            Vec3 motion = entity.getDeltaMovement();
            double yDiff = entity.position().y - blockPos.getY();
            if (yDiff < 0.5) {
                entity.setDeltaMovement(motion.add(0, -motion.y + 0.5, 0));
                entity.hasImpulse = true;
            } else if (yDiff < 0.6) {
                entity.setDeltaMovement(motion.add(0, -motion.y, 0));
                entity.hasImpulse = true;
                entity.fallDistance = 0;
            }
        }
        if (entity.isCrouching() && entity.isOnGround()) {
            ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
            CompoundTag tag = boots.getOrCreateTag();
            long tickSneak = tag.getLong("tickSneak");
            if (tickSneak < 600) {
                tickSneak += 1;
                tag.putLong("tickSneak", tickSneak);
            }
            if (entity instanceof Player && !entity.level.isClientSide)
                ((Player) entity).displayClientMessage(new TranslatableComponent("message.moreboots.charging_explosion", Utils.roundTo(tickSneak / 60D, 2)), true);
            boots.setTag(tag);
        }
    }

    @Override
    public void onLivingAttack(LivingHurtEvent event) {
        LivingEntity source = (LivingEntity) event.getSource().getDirectEntity();
        ItemStack boots = source.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = boots.getOrCreateTag();
        float power = tag.getLong("tickSneak") / 60f;
        Vec3 pos = source.position().add(source.getLookAngle().scale(0.1));
        source.level.explode(source, pos.x, pos.y, pos.z, power, Explosion.BlockInteraction.NONE);
        event.setAmount(event.getAmount() * power / 2);
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

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onJump() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = boots.getOrCreateTag();
        if (!tag.getBoolean("Activated")) return;
        double yDiff = player.position().y - player.blockPosition().getY();
        if (yDiff > 0 && yDiff < 0.6) {
            player.jumpFromGround();
            if (player.level.isClientSide)
                MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerMultiJumpPacket());
        }
    }
}
