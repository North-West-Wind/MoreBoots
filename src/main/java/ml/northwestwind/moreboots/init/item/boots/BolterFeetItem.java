package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class BolterFeetItem extends BootsItem {
    public BolterFeetItem() {
        super(ItemInit.ModArmorMaterial.BOLTER, "bolter_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) || entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() < 1)
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 0, false, false, false));
    }

    @Override
    public void onLivingAttack(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.level);
        lightning.setPos(entity.getX(), entity.getY(), entity.getZ());
        entity.level.addFreshEntity(lightning);
    }
}
