package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CPlayerMultiJumpPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import ml.northwestwind.moreboots.mixins.MixinLivingEntityAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class SuperFeetItem extends BootsItem {
    public SuperFeetItem() {
        super(ItemInit.ModArmorMaterial.SUPER, "super_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        BlockPos blockPos = entity.blockPosition();
        if (!entity.level.getBlockState(blockPos.below()).getCollisionShape(entity.level, blockPos.below()).equals(Shapes.empty())) {
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
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = boots.getOrCreateTag();
        int flutterTicks = tag.getInt("flutter_ticks");
        if (flutterTicks <= 20 && !entity.isOnGround() && entity.getDeltaMovement().y < 0.1 && ((MixinLivingEntityAccessor) entity).isJumping()) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.01 * flutterTicks, 0));
            entity.hasImpulse = true;
            tag.putInt("flutter_ticks", flutterTicks + 1);
        } else if (flutterTicks > 0 && entity.isOnGround()) tag.putInt("flutter_ticks", 0);
        boots.setTag(tag);
    }

    @Override
    public void onLivingAttack(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        DamageSource source = event.getSource();
        if (source.isProjectile()) return;
        Entity attacker = source.getDirectEntity();
        Vec3 pos = attacker.getEyePosition().add(attacker.getLookAngle().scale(0.1));
        entity.level.explode(attacker, pos.x, pos.y, pos.z, 1, Explosion.BlockInteraction.NONE);
        event.setAmount(event.getAmount() * 1.1f);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onJump() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        double yDiff = player.position().y - player.blockPosition().getY();
        if (yDiff > 0 && yDiff < 0.6) {
            player.jumpFromGround();
            if (player.level.isClientSide)
                MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerMultiJumpPacket());
        }
    }
}
