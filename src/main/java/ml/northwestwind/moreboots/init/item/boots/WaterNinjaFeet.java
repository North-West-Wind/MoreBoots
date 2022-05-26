package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class WaterNinjaFeet extends BootsItem {
    public WaterNinjaFeet() {
        super(ItemInit.ModArmorMaterial.WATER_NINJA, "water_ninja_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isUnderWater() && (!entity.hasEffect(MobEffects.INVISIBILITY) || entity.getEffect(MobEffects.INVISIBILITY).getDuration() < 10)) {
            entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 10, 0, false, false, false));
        }
        if (entity.isVisuallySwimming()) {
            Vec3 motion = entity.getDeltaMovement();
            Vec3 direction = entity.getLookAngle().scale(0.1);
            entity.setDeltaMovement(motion.multiply(1.01, 1, 1.01).add(direction));
        }
    }

    @Override
    public void onLivingAttack(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isInWater()) event.setAmount(event.getAmount() * 1.1f);
    }

    @Override
    public void getCollisionShape(BlockGetter worldIn, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        BlockState state = worldIn.getBlockState(pos);
        if((state.getMaterial().equals(Material.WATER) || state.getMaterial().equals(Material.POWDER_SNOW)) && context.isAbove(Shapes.block(), pos, true)) cir.setReturnValue(Shapes.block());
    }
}
