package ml.northwestwind.moreboots.init.block;

import com.google.common.collect.ImmutableList;
import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ViscousLayerBlock extends Block {
    public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS;
    public static final BooleanProperty LONG_LASTING = BooleanProperty.create("long_lasting");
    protected static final VoxelShape[] SHAPE_BY_LAYER = new VoxelShape[]{Shapes.empty(), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    private static final List<RegistryObject<Item>> EXCLUSIONS = ImmutableList.of(ItemInit.SUPER_AVIAN_FEET, ItemInit.BIONIC_BEETLE_FEET);

    public ViscousLayerBlock(Properties p_49795_) {
        super(p_49795_);
    }

    public boolean isPathfindable(BlockState p_56592_, BlockGetter p_56593_, BlockPos p_56594_, PathComputationType type) {
        if (type.equals(PathComputationType.LAND)) return p_56592_.getValue(LAYERS) < 5;
        else return false;
    }

    public VoxelShape getShape(BlockState p_56620_, BlockGetter p_56621_, BlockPos p_56622_, CollisionContext p_56623_) {
        return SHAPE_BY_LAYER[p_56620_.getValue(LAYERS)];
    }

    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) entity.makeStuckInBlock(state, new Vec3(0.8F, 0.75D, 0.8F));
        else {
            Item boots = livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem();
            for (RegistryObject<Item> item : EXCLUSIONS) {
                if (boots.equals(item.get())) return;
            }
            entity.makeStuckInBlock(state, new Vec3(0.8F, 0.75D, 0.8F));
        }
    }

    public boolean useShapeForLightOcclusion(BlockState p_56630_) {
        return true;
    }

    public boolean canSurvive(BlockState p_56602_, LevelReader p_56603_, BlockPos p_56604_) {
        BlockState blockstate = p_56603_.getBlockState(p_56604_.below());
        if (!blockstate.is(Blocks.ICE) && !blockstate.is(Blocks.PACKED_ICE) && !blockstate.is(Blocks.BARRIER)) {
            if (!blockstate.is(Blocks.HONEY_BLOCK) && !blockstate.is(Blocks.SOUL_SAND)) {
                return Block.isFaceFull(blockstate.getCollisionShape(p_56603_, p_56604_.below()), Direction.UP) || blockstate.is(this) && blockstate.getValue(LAYERS) == 8;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public BlockState updateShape(BlockState p_56606_, Direction p_56607_, BlockState p_56608_, LevelAccessor p_56609_, BlockPos p_56610_, BlockPos p_56611_) {
        return !p_56606_.canSurvive(p_56609_, p_56610_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_56606_, p_56607_, p_56608_, p_56609_, p_56610_, p_56611_);
    }

    public boolean canBeReplaced(BlockState p_56589_, BlockPlaceContext p_56590_) {
        int i = p_56589_.getValue(LAYERS);
        if (p_56590_.getItemInHand().is(this.asItem()) && i < 8) {
            if (p_56590_.replacingClickedOnBlock()) {
                return p_56590_.getClickedFace() == Direction.UP;
            } else {
                return true;
            }
        } else {
            return i == 1;
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_56587_) {
        BlockState blockstate = p_56587_.getLevel().getBlockState(p_56587_.getClickedPos());
        if (blockstate.is(this)) {
            int i = blockstate.getValue(LAYERS);
            return blockstate.setValue(LAYERS, Math.min(8, i + 1));
        } else {
            return super.getStateForPlacement(p_56587_);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_56613_) {
        p_56613_.add(LAYERS);
        p_56613_.add(LONG_LASTING);
    }

    @Override
    public Item asItem() {
        return ItemInit.VISCOUS_GOO.get();
    }

    @Override
    public boolean isRandomlyTicking(BlockState p_49921_) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        double chance;
        if (state.getValue(LONG_LASTING)) chance = 0.8;
        else chance = 1;
        if (random.nextDouble() < chance) {
            int layers = state.getValue(LAYERS);
            if (layers > 1) level.setBlockAndUpdate(pos, state.setValue(LAYERS, layers - 1));
            else level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        }
    }
}
