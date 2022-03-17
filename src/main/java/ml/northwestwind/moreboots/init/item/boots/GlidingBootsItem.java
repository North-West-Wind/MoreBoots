package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraftforge.event.entity.living.LivingEvent;

public class GlidingBootsItem extends BootsItem {
    public GlidingBootsItem() {
        this(ItemInit.ModArmorMaterial.GLIDER, "gliding_boots");
    }

    protected GlidingBootsItem(ArmorMaterial material, String registryName) {
        super(material, registryName);
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.getDeltaMovement().x() == 0 && entity.getDeltaMovement().z() == 0) return;
        if (entity.getDeltaMovement().y() < 0.02 && !entity.isCrouching()) {
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.05, 0, 1.05).add(0, -0.02, 0));
            entity.hasImpulse = true;
            entity.fallDistance = 0;
        }
    }
}
