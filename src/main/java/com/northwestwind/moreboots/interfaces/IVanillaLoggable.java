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
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.util.Constants;
import org.apache.logging.log4j.LogManager;

public interface IVanillaLoggable extends IBucketPickupHandler, ILiquidContainer {
    default boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return (!state.get(BlockStateProperties.WATERLOGGED) && fluidIn.isIn(FluidTags.WATER)) || (!state.get(InvisibleBlock.LAVALOGGED) && fluidIn.isIn(FluidTags.LAVA));
    }

    default boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        Fluid fluid = fluidStateIn.getFluid();
        if (canContainFluid(worldIn, pos, state, fluid)) {
            if (!worldIn.isRemote()) {
                BlockState newState = state;
                if (fluidStateIn.isTagged(FluidTags.WATER)) newState = newState.with(BlockStateProperties.WATERLOGGED, true);
                else if (fluidStateIn.isTagged(FluidTags.LAVA)) newState = newState.with(InvisibleBlock.LAVALOGGED, true);
                if (!fluidStateIn.isSource()) newState = newState.with(InvisibleBlock.FLOWINGLOGGED, true);
                worldIn.setBlockState(pos, newState, Constants.BlockFlags.DEFAULT);
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
