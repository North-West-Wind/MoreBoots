package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import ml.northwestwind.moreboots.mixins.MixinLivingEntityAccessor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class BugFeetItem extends BootsItem {
    public BugFeetItem() {
        super(ItemInit.ModArmorMaterial.BUG, "bug_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) || entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() < 1)
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 0, false, false, false));
        if (!entity.hasEffect(MobEffects.WEAKNESS) || entity.getEffect(MobEffects.WEAKNESS).getAmplifier() < 1)
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10, 0, false, false, false));
        if (!entity.isCrouching()) {
            double addToY = 0;
            if (entity.getDeltaMovement().y() < 0.05 && ((MixinLivingEntityAccessor) entity).isJumping()) addToY = 0.02;
            else if (entity.getDeltaMovement().y() < 0) addToY = -0.05;
            if (addToY != 0) {
                entity.setDeltaMovement(entity.getDeltaMovement().add(0, addToY, 0));
                entity.hasImpulse = true;
                entity.fallDistance = 0;
            }
        }
    }
}
