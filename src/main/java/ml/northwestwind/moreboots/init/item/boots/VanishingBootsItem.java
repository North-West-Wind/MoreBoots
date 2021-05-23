package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class VanishingBootsItem extends BootsItem {
    public VanishingBootsItem() {
        super(ItemInit.ModArmorMaterial.VANISHING, "vanishing_boots");
    }

    @Override
    public void getCollisionShape(IBlockReader worldIn, BlockPos pos, ISelectionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        BlockState state = worldIn.getBlockState(pos);
        if (!state.canOcclude() || state.getMaterial().equals(Material.GLASS)) cir.setReturnValue(VoxelShapes.empty());
    }
}
