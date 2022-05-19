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
import net.minecraftforge.event.entity.living.LivingFallEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class Odd1sOutFeetItem extends BootsItem {
    public Odd1sOutFeetItem() {
        super(ItemInit.ModArmorMaterial.ODD1SOUT, "odd1sout_feet");
    }

    @Override
    public void onLivingFall(LivingFallEvent event) {
        event.setCanceled(true);
    }

    @Override
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.25, 0));
        entity.hasImpulse = true;
    }

    @Override
    public void getCollisionShape(BlockGetter worldIn, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        BlockState state = worldIn.getBlockState(pos);
        Material material = state.getMaterial();
        if((material.equals(Material.WATER) || material.equals(Material.LAVA) || material.equals(Material.POWDER_SNOW)) && context.isAbove(Shapes.block(), pos, true)) cir.setReturnValue(Shapes.block());
    }
}
