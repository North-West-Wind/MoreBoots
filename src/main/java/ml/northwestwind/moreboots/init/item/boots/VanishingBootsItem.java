package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class VanishingBootsItem extends BootsItem {
    public VanishingBootsItem() {
        super(ItemInit.ModArmorMaterial.VANISHING, "vanishing_boots");
    }

    @Override
    public void getCollisionShape(BlockGetter worldIn, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        BlockState state = worldIn.getBlockState(pos);
        if (!state.canOcclude() || state.getMaterial().equals(Material.GLASS)) cir.setReturnValue(Shapes.empty());
    }
}
