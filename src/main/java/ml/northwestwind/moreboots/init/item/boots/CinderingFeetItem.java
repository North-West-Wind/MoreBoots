package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class CinderingFeetItem extends BootsItem {
    public CinderingFeetItem() {
        super(ItemInit.ModArmorMaterial.CINDERING, "cindering_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.isInWaterRainOrBubble()) entity.hurt(Reference.WATER, 1);
    }

    @Override
    public void onLivingAttack(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (!entity.fireImmune()) {
            entity.setRemainingFireTicks(entity.getRemainingFireTicks() + 10);
            if (entity.getRemainingFireTicks() == 0) {
                entity.setSecondsOnFire(220);
            }
            entity.hurt(DamageSource.IN_FIRE, 2);
        }
    }
}
