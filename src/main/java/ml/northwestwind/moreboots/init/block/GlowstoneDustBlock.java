package ml.northwestwind.moreboots.init.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;
import java.util.stream.Stream;

public class GlowstoneDustBlock extends Block implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.box(2, 0, 2, 4, 1, 4),
            Block.box(3, 0, 12, 4, 1, 13),
            Block.box(13, 0, 8, 14, 1, 9),
            Block.box(13, 0, 1, 14, 1, 2),
            Block.box(9, 0, 4, 11, 1, 6),
            Block.box(7, 0, 3, 8, 1, 4),
            Block.box(4, 0, 6, 5, 1, 7),
            Block.box(1, 0, 9, 2, 1, 10),
            Block.box(6, 0, 9, 8, 1, 11),
            Block.box(11, 0, 11, 12, 1, 12),
            Block.box(13, 0, 13, 15, 1, 15),
            Block.box(5, 0, 14, 6, 1, 15),
            Block.box(9, 0, 13, 10, 1, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_W = Stream.of(
            Block.box(2, 0, 12, 4, 1, 14),
            Block.box(12, 0, 12, 13, 1, 13),
            Block.box(8, 0, 2, 9, 1, 3),
            Block.box(1, 0, 2, 2, 1, 3),
            Block.box(4, 0, 5, 6, 1, 7),
            Block.box(3, 0, 8, 4, 1, 9),
            Block.box(6, 0, 11, 7, 1, 12),
            Block.box(9, 0, 14, 10, 1, 15),
            Block.box(9, 0, 8, 11, 1, 10),
            Block.box(11, 0, 4, 12, 1, 5),
            Block.box(13, 0, 1, 15, 1, 3),
            Block.box(14, 0, 10, 15, 1, 11),
            Block.box(13, 0, 6, 14, 1, 7)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_S = Stream.of(
            Block.box(12, 0, 12, 14, 1, 14),
            Block.box(12, 0, 3, 13, 1, 4),
            Block.box(2, 0, 7, 3, 1, 8),
            Block.box(2, 0, 14, 3, 1, 15),
            Block.box(5, 0, 10, 7, 1, 12),
            Block.box(8, 0, 12, 9, 1, 13),
            Block.box(11, 0, 9, 12, 1, 10),
            Block.box(14, 0, 6, 15, 1, 7),
            Block.box(8, 0, 5, 10, 1, 7),
            Block.box(4, 0, 4, 5, 1, 5),
            Block.box(1, 0, 1, 3, 1, 3),
            Block.box(10, 0, 1, 11, 1, 2),
            Block.box(6, 0, 2, 7, 1, 3)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE_E = Stream.of(
            Block.box(12, 0, 2, 14, 1, 4),
            Block.box(3, 0, 3, 4, 1, 4),
            Block.box(7, 0, 13, 8, 1, 14),
            Block.box(14, 0, 13, 15, 1, 14),
            Block.box(10, 0, 9, 12, 1, 11),
            Block.box(12, 0, 7, 13, 1, 8),
            Block.box(9, 0, 4, 10, 1, 5),
            Block.box(6, 0, 1, 7, 1, 2),
            Block.box(5, 0, 6, 7, 1, 8),
            Block.box(4, 0, 11, 5, 1, 12),
            Block.box(1, 0, 13, 3, 1, 15),
            Block.box(1, 0, 5, 2, 1, 6),
            Block.box(2, 0, 9, 3, 1, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public GlowstoneDustBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
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

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
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
    public BlockState updateShape(BlockState state, Direction direction, BlockState newState, LevelAccessor world, BlockPos oldPos, BlockPos newPos) {
        return direction == Direction.DOWN && !this.canSurvive(state, world, oldPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, newState, world, oldPos, newPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return canSupportCenter(level, pos.below(), Direction.UP);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BlockStateProperties.WATERLOGGED);
    }

    @Override
    public Item asItem() {
        return Items.GLOWSTONE_DUST;
    }
}
