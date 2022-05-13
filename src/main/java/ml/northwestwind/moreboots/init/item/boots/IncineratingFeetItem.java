package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class IncineratingFeetItem extends BootsItem {
    public IncineratingFeetItem() {
        super(ItemInit.ModArmorMaterial.INCINERATING, "incinerating_feet");
    }

    @Override
    public void onLivingAttack(LivingHurtEvent event) {
        Entity source = event.getSource().getEntity();
        if (source == null || source.isInWaterRainOrBubble()) return;
        LivingEntity entity = event.getEntityLiving();
        if (!entity.fireImmune()) {
            entity.setRemainingFireTicks(entity.getRemainingFireTicks() + 20);
            if (entity.getRemainingFireTicks() == 0) {
                entity.setSecondsOnFire(600);
            }
            entity.hurt(DamageSource.IN_FIRE, 2);
        }
    }
}
