package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class LightningBootsItem extends BootsItem {
    public LightningBootsItem() {
        super(ItemInit.ModArmorMaterial.LIGHTNING, "lightning_boots");
    }

    @Override
    public void onLivingDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.level);
        lightning.setPos(entity.getX(), entity.getY(), entity.getZ());
        entity.level.addFreshEntity(lightning);
    }

    @Override
    public void onLivingAttack(LivingDamageEvent event) {
        onLivingDamage(event);
    }
}
