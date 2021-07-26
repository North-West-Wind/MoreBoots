package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class StriderBootsItem extends BootsItem {
    public StriderBootsItem() {
        super(ItemInit.ModArmorMaterial.STRIDER, "strider_boots", true);
    }

    @Override
    public void onLivingDamage(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        if (source.equals(DamageSource.IN_FIRE) || source.equals(DamageSource.LAVA) || source.equals(DamageSource.ON_FIRE))
            event.setCanceled(true);
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        entity.clearFire();
        if (entity.isInLava()) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.1, 0));
            entity.hasImpulse = true;
        }
    }

    @Override
    public void getCollisionShape(BlockGetter worldIn, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        BlockState state = worldIn.getBlockState(pos);
        if(state.getMaterial().equals(Material.LAVA) && context.isAbove(Shapes.block(), pos, true)) cir.setReturnValue(Shapes.block());
    }
}
