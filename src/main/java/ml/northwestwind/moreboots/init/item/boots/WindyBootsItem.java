package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.living.LivingEvent;

public class WindyBootsItem extends BootsItem {
    public WindyBootsItem() {
        super(ItemInit.ModArmorMaterial.WINDY, "windy_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isOnGround() || entity.isCrouching() || entity.isInWater() || entity.isInLava()) return;
        Vector3d motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.multiply(1.1, 1, 1.1));
    }
}
