package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

public class MinerBootsItem extends BootsItem {
    public MinerBootsItem() {
        super(ItemInit.ModArmorMaterial.MINER, "miner_boots");
    }

    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 20, 1));
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));
    }
}
