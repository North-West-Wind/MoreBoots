package com.northwestwind.moreboots.interfaces;

import com.northwestwind.moreboots.Reference;
import com.northwestwind.moreboots.init.block.InvisibleBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.block.ILiquidContainer;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

public interface IVanillaLoggable extends IBucketPickupHandler, ILiquidContainer {
    default boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return (!state.get(BlockStateProperties.WATERLOGGED) && (fluidIn == Fluids.WATER || fluidIn == Fluids.FLOWING_WATER)) || (!state.get(InvisibleBlock.LAVALOGGED) && (fluidIn == Fluids.LAVA || fluidIn == Fluids.FLOWING_LAVA));
    }

    default boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        Fluid fluid = fluidStateIn.getFluid();
        if (!state.get(BlockStateProperties.WATERLOGGED) && (fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER)) {
            if (!worldIn.isRemote()) {
                worldIn.setBlockState(pos, state.with(BlockStateProperties.WATERLOGGED, true), 3);
                if (fluid == Fluids.FLOWING_WATER) worldIn.setBlockState(pos, state.with(InvisibleBlock.FLOWINGLOGGED, true), 3);
                worldIn.getPendingFluidTicks().scheduleTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
            }

            return true;
        } else if (state.get(InvisibleBlock.LAVALOGGED) && (fluid == Fluids.LAVA || fluid == Fluids.FLOWING_LAVA)) {
            if (!worldIn.isRemote()) {
                worldIn.setBlockState(pos, state.with(InvisibleBlock.LAVALOGGED, true), 3);
                if (fluid == Fluids.FLOWING_LAVA) worldIn.setBlockState(pos, state.with(InvisibleBlock.FLOWINGLOGGED, true), 3);
                worldIn.getPendingFluidTicks().scheduleTick(pos, fluidStateIn.getFluid(), fluidStateIn.getFluid().getTickRate(worldIn));
            }

            return true;
        } else {
            return false;
        }
    }

    default Fluid pickupFluid(IWorld worldIn, BlockPos pos, BlockState state) {
        if (state.get(InvisibleBlock.FLOWINGLOGGED)) return Fluids.EMPTY;
        if (state.get(BlockStateProperties.WATERLOGGED)) {
            worldIn.setBlockState(pos, state.with(BlockStateProperties.WATERLOGGED, false), 3);
            return Fluids.WATER;
        } else if (state.get(InvisibleBlock.LAVALOGGED)) {
            worldIn.setBlockState(pos, state.with(InvisibleBlock.LAVALOGGED, false), 3);
            return Fluids.LAVA;
        } else return Fluids.EMPTY;
    }
}
