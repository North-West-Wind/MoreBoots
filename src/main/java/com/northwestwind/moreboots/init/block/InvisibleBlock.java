package com.northwestwind.moreboots.init.block;

import com.northwestwind.moreboots.init.ItemInit;
import com.northwestwind.moreboots.init.tileentity.InvisibleTileEntity;
import com.northwestwind.moreboots.interfaces.IVanillaLoggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

public class InvisibleBlock extends Block implements IVanillaLoggable {
    public static final BooleanProperty LAVALOGGED = BooleanProperty.create("lava_logged");
    public static final BooleanProperty FLOWINGLOGGED = BooleanProperty.create("flowing_logged");
    protected static final VoxelShape TOP_SHAPE = Block.makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public InvisibleBlock() {
        super(Properties.create(Material.BARRIER).noDrops().hardnessAndResistance(-1, 3600000.8F).func_235859_g_());
        this.setDefaultState(this.getDefaultState().with(LAVALOGGED, false).with(WATERLOGGED, false).with(FLOWINGLOGGED, false));
    }

    @Override
    public boolean isTransparent(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Entity entity = context.getEntity();
        if (!(entity instanceof LivingEntity)) return VoxelShapes.empty();
        ItemStack boots = ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.FEET);
        if (!boots.getItem().equals(ItemInit.STRIDER_BOOTS) && !boots.getItem().equals(ItemInit.FLOATIE_BOOTS)) return VoxelShapes.empty();
        if (entity.getPosY() < pos.up().getY()) return VoxelShapes.empty();
        return TOP_SHAPE;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(WATERLOGGED, LAVALOGGED, FLOWINGLOGGED);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new InvisibleTileEntity();
    }

    @Override
    public boolean isReplaceable(BlockState p_225541_1_, Fluid p_225541_2_) {
        return true;
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return true;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        if (fluidstate.isTagged(FluidTags.WATER)) {
            BlockState state = this.getDefaultState().with(WATERLOGGED, true);
            if (!fluidstate.isSource()) state = state.with(FLOWINGLOGGED, true);
            return state;
        } else if (fluidstate.isTagged(FluidTags.LAVA)) {
            BlockState state = this.getDefaultState().with(LAVALOGGED, true);
            if (!fluidstate.isSource()) state = state.with(FLOWINGLOGGED, true);
            return state;
        } else return this.getDefaultState();
    }

    public FluidState getFluidState(BlockState state) {
        FluidState fluidState = super.getFluidState(state);
        if (state.get(WATERLOGGED)) {
            if (state.get(FLOWINGLOGGED)) fluidState = Fluids.FLOWING_WATER.getFlowingFluidState(4, false);
            else fluidState = Fluids.WATER.getStillFluidState(false);
        } else if (state.get(LAVALOGGED)) {
            if (state.get(FLOWINGLOGGED)) fluidState = Fluids.FLOWING_LAVA.getFlowingFluidState(4, false);
            else fluidState = Fluids.LAVA.getStillFluidState(false);
        }
        return fluidState;
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            if (stateIn.get(FLOWINGLOGGED)) worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.FLOWING_WATER, Fluids.FLOWING_WATER.getTickRate(worldIn));
            else worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        } else if (stateIn.get(LAVALOGGED)) {
            if (stateIn.get(FLOWINGLOGGED)) worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.FLOWING_LAVA, Fluids.FLOWING_LAVA.getTickRate(worldIn));
            else worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.LAVA, Fluids.LAVA.getTickRate(worldIn));
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        if (type == PathType.WATER) {
            return worldIn.getFluidState(pos).isTagged(FluidTags.WATER);
        } else return worldIn.getFluidState(pos).isTagged(FluidTags.LAVA) || super.allowsMovement(state, worldIn, pos, type);
    }


}
