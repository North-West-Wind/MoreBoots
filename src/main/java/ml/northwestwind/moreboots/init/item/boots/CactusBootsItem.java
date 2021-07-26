package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class CactusBootsItem extends BootsItem {
    public CactusBootsItem() {
        super(ItemInit.ModArmorMaterial.CACTUS, "cactus_boots");
    }

    @Override
    public void onLivingDamage(LivingDamageEvent event) {
        Entity attacker = event.getSource().getEntity();
        if (event.getSource().isProjectile()) return;
        float amount = event.getAmount();
        if (attacker instanceof LivingEntity) attacker.hurt(DamageSource.CACTUS, amount / 3.0f);
    }
}
