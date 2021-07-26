package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;

public class WindyBootsItem extends BootsItem {
    public WindyBootsItem() {
        super(ItemInit.ModArmorMaterial.WINDY, "windy_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isOnGround() || entity.isCrouching() || entity.isInWater() || entity.isInLava()) return;
        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.multiply(1.1, 1, 1.1));
    }
}
