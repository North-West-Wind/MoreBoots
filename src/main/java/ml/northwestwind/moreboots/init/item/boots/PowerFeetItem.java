package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;

public class PowerFeetItem extends BootsItem {
    public PowerFeetItem() {
        super(ItemInit.ModArmorMaterial.POWER, "power_feet");
    }

    @Override
    public void onLivingAttack(LivingHurtEvent event) {
        event.setAmount(event.getAmount() * 1.1f);
        event.getEntity().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 255, false, false, false));
    }

    @Override
    public void onLivingKnockBack(LivingKnockBackEvent event) {
        event.setStrength(event.getStrength() * 2);
    }
}
