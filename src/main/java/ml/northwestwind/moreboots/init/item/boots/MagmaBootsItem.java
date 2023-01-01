package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;

public class MagmaBootsItem extends BootsItem {
    public MagmaBootsItem() {
        super(ItemInit.ModArmorMaterial.MAGMA, "magma_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level.getFluidState(entity.blockPosition()).getType() == Fluids.WATER || entity.level.getFluidState(entity.blockPosition()).getType() == Fluids.FLOWING_WATER) {
            if (entity.isSpectator() || (entity instanceof Player && ((Player) entity).getAbilities().flying))
                return;
            entity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 20, 0, false, false));
            Vec3 vector3d = entity.getDeltaMovement();
            entity.setDeltaMovement(vector3d.x, Math.max(-0.6D, vector3d.y - 0.03D), vector3d.z);
            entity.fallDistance = 0.0F;
        }
    }
}
