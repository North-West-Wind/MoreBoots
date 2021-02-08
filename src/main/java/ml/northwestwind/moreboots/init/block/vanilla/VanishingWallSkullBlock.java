package ml.northwestwind.moreboots.init.block.vanilla;

import net.minecraft.block.BlockState;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.WallSkullBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class VanishingWallSkullBlock extends WallSkullBlock {
    public VanishingWallSkullBlock(WallSkullBlock block) {
        super(block.getSkullType(), Properties.from(block));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Entity entity = context.getEntity();
        if(entity instanceof LivingEntity) {
            ItemStack boots = ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.FEET);
            ResourceLocation registryName = boots.getItem().getRegistryName();
            if(registryName != null && registryName.getPath().equals("vanishing_boots")) {
                return VoxelShapes.empty();
            }
        }
        return super.getCollisionShape(state, worldIn, pos, context);
    }
}
