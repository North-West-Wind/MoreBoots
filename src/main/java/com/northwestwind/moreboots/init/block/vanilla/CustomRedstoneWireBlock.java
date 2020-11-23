package com.northwestwind.moreboots.init.block.vanilla;

import com.northwestwind.moreboots.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class CustomRedstoneWireBlock extends RedstoneWireBlock {

    public CustomRedstoneWireBlock(Block block) {
        super(Properties.from(block));
    }
    private boolean powered;
    @Override
    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return powered ? 15 : super.getWeakPower(blockState, blockAccess, pos, side);
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return powered ? 15 : super.getStrongPower(blockState, blockAccess, pos, side);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if(entityIn instanceof LivingEntity && ((LivingEntity) entityIn).getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemInit.REDSTONE_BOOTS)) powered = true;
        else powered = false;
        super.onEntityCollision(state, worldIn, pos, entityIn);
    }
}
