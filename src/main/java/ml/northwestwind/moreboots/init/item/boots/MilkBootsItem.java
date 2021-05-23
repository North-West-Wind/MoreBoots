package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.Collection;

public class MilkBootsItem extends BootsItem {
    public MilkBootsItem() {
        super(ItemInit.ModArmorMaterial.MILK, "milk_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Collection<EffectInstance> potions = entity.getActiveEffects();
        for (EffectInstance effect : potions)
            if (!effect.getEffect().isBeneficial()) {
                entity.removeEffect(effect.getEffect());
                break;
            }
    }
}
