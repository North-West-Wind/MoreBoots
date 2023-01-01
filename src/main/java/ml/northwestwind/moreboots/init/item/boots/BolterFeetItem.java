package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CPlayerDashPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class BolterFeetItem extends BootsItem {
    public BolterFeetItem() {
        super(ItemInit.ModArmorMaterial.BOLTER, "bolter_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) || entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() < 1)
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 0, false, false, false));
    }

    @Override
    public void onLivingAttack(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.level);
        lightning.setPos(entity.getX(), entity.getY(), entity.getZ());
        entity.level.addFreshEntity(lightning);
    }

    @Override
    public void activateBoots() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        Vec3 lookVec = player.getLookAngle().scale(0.1);
        player.setDeltaMovement(player.getDeltaMovement().add(lookVec));
        player.hasImpulse = true;
        MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerDashPacket());
    }
}
