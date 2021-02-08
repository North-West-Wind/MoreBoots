package ml.northwestwind.moreboots.init.block.vanilla;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WebBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class VanishingWebBlock extends WebBlock {
    public VanishingWebBlock(Block block) {
        super(Properties.from(block));
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

    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if(!(entityIn instanceof LivingEntity) || ((LivingEntity) entityIn).getItemStackFromSlot(EquipmentSlotType.FEET).getItem().getRegistryName() == null || !((LivingEntity) entityIn).getItemStackFromSlot(EquipmentSlotType.FEET).getItem().getRegistryName().getPath().equals("vanishing_boots"))
        entityIn.setMotionMultiplier(state, new Vector3d(0.25D, (double)0.05F, 0.25D));
    }
}
