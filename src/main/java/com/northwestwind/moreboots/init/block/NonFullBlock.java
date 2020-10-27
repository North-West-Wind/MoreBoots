package com.northwestwind.moreboots.init.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
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
import org.apache.logging.log4j.LogManager;

public class NonFullBlock extends Block implements IWaterLoggable {
    Block block;

    public <T extends Block> NonFullBlock(T block) {
        super(Properties.from(block));
        this.block = block;
        LogManager.getLogger().info("Registered " + block.getRegistryName());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Entity entity = context.getEntity();
        if(entity instanceof LivingEntity) {
            ItemStack boots = ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.FEET);
            ResourceLocation registryName = boots.getItem().getRegistryName();
            if(registryName != null && registryName.getPath().equals("vanishing_boots")) {
                return VoxelShapes.empty();
            }
        }
        return block.getShape(state, worldIn, pos, context);
    }
}
