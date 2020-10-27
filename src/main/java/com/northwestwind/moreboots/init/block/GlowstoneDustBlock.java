package com.northwestwind.moreboots.init.block;

import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.Random;
import java.util.stream.Stream;

public class GlowstoneDustBlock extends Block implements IWaterLoggable {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.makeCuboidShape(2, 0, 2, 4, 1, 4),
            Block.makeCuboidShape(3, 0, 12, 4, 1, 13),
            Block.makeCuboidShape(13, 0, 8, 14, 1, 9),
            Block.makeCuboidShape(13, 0, 1, 14, 1, 2),
            Block.makeCuboidShape(9, 0, 4, 11, 1, 6),
            Block.makeCuboidShape(7, 0, 3, 8, 1, 4),
            Block.makeCuboidShape(4, 0, 6, 5, 1, 7),
            Block.makeCuboidShape(1, 0, 9, 2, 1, 10),
            Block.makeCuboidShape(6, 0, 9, 8, 1, 11),
            Block.makeCuboidShape(11, 0, 11, 12, 1, 12),
            Block.makeCuboidShape(13, 0, 13, 15, 1, 15),
            Block.makeCuboidShape(5, 0, 14, 6, 1, 15),
            Block.makeCuboidShape(9, 0, 13, 10, 1, 14)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    private static final VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(2, 0, 12, 4, 1, 14),
            Block.makeCuboidShape(12, 0, 12, 13, 1, 13),
            Block.makeCuboidShape(8, 0, 2, 9, 1, 3),
            Block.makeCuboidShape(1, 0, 2, 2, 1, 3),
            Block.makeCuboidShape(4, 0, 5, 6, 1, 7),
            Block.makeCuboidShape(3, 0, 8, 4, 1, 9),
            Block.makeCuboidShape(6, 0, 11, 7, 1, 12),
            Block.makeCuboidShape(9, 0, 14, 10, 1, 15),
            Block.makeCuboidShape(9, 0, 8, 11, 1, 10),
            Block.makeCuboidShape(11, 0, 4, 12, 1, 5),
            Block.makeCuboidShape(13, 0, 1, 15, 1, 3),
            Block.makeCuboidShape(14, 0, 10, 15, 1, 11),
            Block.makeCuboidShape(13, 0, 6, 14, 1, 7)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    private static final VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(12, 0, 12, 14, 1, 14),
            Block.makeCuboidShape(12, 0, 3, 13, 1, 4),
            Block.makeCuboidShape(2, 0, 7, 3, 1, 8),
            Block.makeCuboidShape(2, 0, 14, 3, 1, 15),
            Block.makeCuboidShape(5, 0, 10, 7, 1, 12),
            Block.makeCuboidShape(8, 0, 12, 9, 1, 13),
            Block.makeCuboidShape(11, 0, 9, 12, 1, 10),
            Block.makeCuboidShape(14, 0, 6, 15, 1, 7),
            Block.makeCuboidShape(8, 0, 5, 10, 1, 7),
            Block.makeCuboidShape(4, 0, 4, 5, 1, 5),
            Block.makeCuboidShape(1, 0, 1, 3, 1, 3),
            Block.makeCuboidShape(10, 0, 1, 11, 1, 2),
            Block.makeCuboidShape(6, 0, 2, 7, 1, 3)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();
    private static final VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(12, 0, 2, 14, 1, 4),
            Block.makeCuboidShape(3, 0, 3, 4, 1, 4),
            Block.makeCuboidShape(7, 0, 13, 8, 1, 14),
            Block.makeCuboidShape(14, 0, 13, 15, 1, 14),
            Block.makeCuboidShape(10, 0, 9, 12, 1, 11),
            Block.makeCuboidShape(12, 0, 7, 13, 1, 8),
            Block.makeCuboidShape(9, 0, 4, 10, 1, 5),
            Block.makeCuboidShape(6, 0, 1, 7, 1, 2),
            Block.makeCuboidShape(5, 0, 6, 7, 1, 8),
            Block.makeCuboidShape(4, 0, 11, 5, 1, 12),
            Block.makeCuboidShape(1, 0, 13, 3, 1, 15),
            Block.makeCuboidShape(1, 0, 5, 2, 1, 6),
            Block.makeCuboidShape(2, 0, 9, 3, 1, 10)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    public GlowstoneDustBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }

    public static Direction getRandomDirection() {
        int random = new Random().nextInt(4);
        switch (random) {
            case 1:
                return Direction.EAST;
            case 2:
                return Direction.SOUTH;
            case 3:
                return Direction.WEST;
            default:
                return Direction.NORTH;
        }
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            case EAST:
                return SHAPE_E;
            case SOUTH:
                return SHAPE_S;
            case WEST:
                return SHAPE_W;
            case NORTH:
                return SHAPE_N;
            default:
                int random = new Random().nextInt(4);
                switch (random) {
                    case 1:
                        return SHAPE_E;
                    case 2:
                        return SHAPE_S;
                    case 3:
                        return SHAPE_W;
                    default:
                        return SHAPE_N;
                }
        }
    }

    @Override
    public boolean isValidPosition(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
        return hasEnoughSolidSide(p_196260_2_, p_196260_3_.down(), Direction.UP);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public Item asItem() {
        return Items.GLOWSTONE_DUST;
    }
}
