package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class SlimeBootsItem extends BootsItem {
    public SlimeBootsItem() {
        super(ItemInit.ModArmorMaterial.SLIME, "slime_boots");
    }

    @Override
    public void onLivingFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isCrouching()) return;
        float distance = event.getDistance();
        if (distance < 1.5) return;
        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.x * 1.1, Math.sqrt(distance) / 3.0, motion.z * 1.1);
        entity.hasImpulse = true;
        entity.playSound(SoundEvents.SLIME_BLOCK_HIT, 1, 1);
        event.setCanceled(true);
        if (!entity.level.isClientSide) ((ServerLevel) entity.level).getServer().getPlayerList().broadcastAll(new ClientboundSetEntityMotionPacket(entity));
    }
}
