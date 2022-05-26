package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CPlayerMultiJumpPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class SuperSocksItem extends BootsItem {
    public SuperSocksItem() {
        super(ItemInit.ModArmorMaterial.SUPER_SOCKS, "super_socks");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) || entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() < 1)
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 0, false, false, false));
        if (!entity.isCrouching()) {
            if (entity.getDeltaMovement().y() < 0) {
                entity.setDeltaMovement(entity.getDeltaMovement().multiply(1, 0, 1).add(0, -0.08, 0));
                entity.hasImpulse = true;
                entity.fallDistance = 0;
            }
        }
    }

    @Override
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.05, 0));
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
}
