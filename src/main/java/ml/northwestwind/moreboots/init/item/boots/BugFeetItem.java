package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import ml.northwestwind.moreboots.mixins.MixinLivingEntityAccessor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
        if (entity instanceof Player && entity.getDeltaMovement().y() < 0.1 &&  ((MixinLivingEntityAccessor) entity).isJumping()) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.1, 0));
            entity.fallDistance = 0;
        }
    }
}
