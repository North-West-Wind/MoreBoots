package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CPlayerMultiJumpPacket;
import ml.northwestwind.moreboots.handler.packet.CPlayerRandomTeleportPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.event.entity.living.LivingEvent;

public class NatureFairyFeetItem extends BootsItem {
    public NatureFairyFeetItem() {
        super(ItemInit.ModArmorMaterial.NATURE_FAIRY, "nature_fairy_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) || entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() < 2)
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 1, false, false, false));
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
    }

    @Override
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.5, 0));
        entity.hasImpulse = true;
    }

    @Override
    public void onJump() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || player.isOnGround() || player.getAbilities().flying) return;
        player.jumpFromGround();
        player.setDeltaMovement(player.getDeltaMovement().add(0, 0.25, 0));
        if (player.level.isClientSide)
            MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerMultiJumpPacket());
    }

    @Override
    public void activateBoots() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerRandomTeleportPacket());
    }
}
