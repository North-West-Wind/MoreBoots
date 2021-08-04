package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class FloatieBootsItem extends BootsItem {
    public FloatieBootsItem() {
        super(ItemInit.ModArmorMaterial.FLOATIE, "floatie_boots", true);
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isInWater()) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.1, 0));
            entity.hasImpulse = true;
        }
    }

    @Override
    public void getCollisionShape(BlockGetter worldIn, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        BlockState state = worldIn.getBlockState(pos);
        if(state.getMaterial().equals(Material.WATER) && context.isAbove(Shapes.block(), pos, true)) cir.setReturnValue(Shapes.block());
    }
}
