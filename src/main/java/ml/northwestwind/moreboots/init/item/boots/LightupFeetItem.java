package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class LightupFeetItem extends BootsItem {
    public LightupFeetItem() {
        super(ItemInit.ModArmorMaterial.LIGHTUP, "lightup_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (!entity.hasEffect(MobEffects.NIGHT_VISION) || entity.getEffect(MobEffects.NIGHT_VISION).getAmplifier() < 1)
            entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 200, 0, false, false, false));
    }
}
