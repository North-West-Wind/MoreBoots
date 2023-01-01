package ml.northwestwind.moreboots.init.block;

import ml.northwestwind.moreboots.init.BlockEntityInit;
import ml.northwestwind.moreboots.init.tileentity.BootRecyclerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BootRecyclerBlock extends BaseEntityBlock {
    public static BooleanProperty POWERED = BooleanProperty.create("recycler_powered");

    public BootRecyclerBlock() {
        super(Properties.of(Material.METAL).strength(2, 2.4F).requiresCorrectToolForDrops());
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        BootRecyclerBlockEntity tileEntity = (BootRecyclerBlockEntity) worldIn.getBlockEntity(pos);
        if (tileEntity == null || !(player.getItemInHand(handIn).getItem() instanceof ArmorItem) || !((ArmorItem) player.getItemInHand(handIn).getItem()).getSlot().equals(EquipmentSlot.FEET)) return InteractionResult.PASS;
        IItemHandler itemHandler = tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, hit.getDirection()).resolve().get();
        if (!itemHandler.getStackInSlot(0).isEmpty()) return InteractionResult.PASS;
        if (!worldIn.isClientSide) player.setItemInHand(handIn, itemHandler.insertItem(0, player.getItemInHand(handIn), false));
        else player.playSound(SoundEvents.WOOD_PLACE, 1, 1);
        tileEntity.setChanged();
        return InteractionResult.SUCCESS;
    }

    @Override
    public void playerWillDestroy(Level worldIn, @Nonnull BlockPos pos, BlockState state, @Nonnull Player player) {
        BootRecyclerBlockEntity tileEntity = (BootRecyclerBlockEntity) worldIn.getBlockEntity(pos);
        if (tileEntity != null) worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).resolve().get().getStackInSlot(0)));
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BootRecyclerBlockEntity tileEntity = (BootRecyclerBlockEntity) context.getLevel().getBlockEntity(context.getClickedPos());
        if (tileEntity == null) return this.defaultBlockState().setValue(POWERED, false);
        return this.defaultBlockState().setValue(POWERED, !tileEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).resolve().get().getStackInSlot(0).isEmpty());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, BlockEntityInit.BOOT_RECYCLER.get(), (BlockEntityTicker<BlockEntity>) (level1, pos, state1, tile) -> ((BootRecyclerBlockEntity) tile).tick());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BootRecyclerBlockEntity(pos, state);
    }
}
