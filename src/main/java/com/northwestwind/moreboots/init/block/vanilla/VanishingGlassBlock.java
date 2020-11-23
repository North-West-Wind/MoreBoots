package com.northwestwind.moreboots.init.block.vanilla;

import com.northwestwind.moreboots.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class VanishingGlassBlock extends GlassBlock {
    public VanishingGlassBlock(Block block) {
        super(Properties.from(block));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Entity entity = context.getEntity();
        if(!(entity instanceof LivingEntity)) return super.getCollisionShape(state, worldIn, pos, context);
        ItemStack boots = ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.FEET);
        if(!boots.getItem().equals(ItemInit.VANISHING_BOOTS)) return super.getCollisionShape(state, worldIn, pos, context);
        else return VoxelShapes.empty();
    }
}
