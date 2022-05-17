package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class CactusBootsItem extends BootsItem {
    public CactusBootsItem() {
        super(ItemInit.ModArmorMaterial.CACTUS, "cactus_boots");
    }

    @Override
    public void onLivingHurt(LivingHurtEvent event) {
        Entity attacker = event.getSource().getEntity();
        if (attacker instanceof LivingEntity) attacker.hurt(DamageSource.CACTUS, event.getAmount() / 3.0f);
    }
}
