package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.living.LivingEvent;

public class MagmaBootsItem extends BootsItem {
    public MagmaBootsItem() {
        super(ItemInit.ModArmorMaterial.MAGMA, "magma_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.level.getFluidState(entity.blockPosition()).getType() == Fluids.WATER || entity.level.getFluidState(entity.blockPosition()).getType() == Fluids.FLOWING_WATER) {
            if (entity.isSpectator() || (entity instanceof PlayerEntity && ((PlayerEntity) entity).abilities.flying))
                return;
            entity.addEffect(new EffectInstance(Effects.WATER_BREATHING, 20, 0, false, false));
            Vector3d vector3d = entity.getDeltaMovement();
            entity.setDeltaMovement(vector3d.x, Math.max(-0.6D, vector3d.y - 0.03D), vector3d.z);
            entity.fallDistance = 0.0F;
        }
    }
}
