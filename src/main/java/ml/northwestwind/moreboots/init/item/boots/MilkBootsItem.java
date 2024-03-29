package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.Collection;

public class MilkBootsItem extends BootsItem {
    public MilkBootsItem() {
        super(ItemInit.ModArmorMaterial.MILK, "milk_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        Collection<MobEffectInstance> potions = entity.getActiveEffects();
        for (MobEffectInstance effect : potions)
            if (!effect.getEffect().isBeneficial()) {
                entity.removeEffect(effect.getEffect());
                break;
            }
    }
}
