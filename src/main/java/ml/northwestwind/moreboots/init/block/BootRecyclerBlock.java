package ml.northwestwind.moreboots.init.block;

import ml.northwestwind.moreboots.init.tileentity.BootRecyclerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BootRecyclerBlock extends Block {
    public static BooleanProperty POWERED = BooleanProperty.create("recycler_powered");

    public BootRecyclerBlock() {
        super(Properties.of(Material.METAL).strength(2, 2.4F).harvestTool(ToolType.PICKAXE).harvestLevel(1));
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        BootRecyclerTileEntity tileEntity = (BootRecyclerTileEntity) worldIn.getBlockEntity(pos);
        if (tileEntity == null || !(player.getItemInHand(handIn).getItem() instanceof ArmorItem) || !((ArmorItem) player.getItemInHand(handIn).getItem()).getSlot().equals(EquipmentSlotType.FEET)) return ActionResultType.PASS;
        IItemHandler itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, hit.getDirection()).resolve().get();
        if (!itemHandler.getStackInSlot(0).isEmpty()) return ActionResultType.PASS;
        if (!worldIn.isClientSide) player.setItemInHand(handIn, itemHandler.insertItem(0, player.getItemInHand(handIn), false));
        else player.playSound(SoundEvents.WOOD_PLACE, 1, 1);
        tileEntity.setChanged();
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new BootRecyclerTileEntity();
    }

    @Override
    public void playerWillDestroy(World worldIn, @Nonnull BlockPos pos, BlockState state, @Nonnull PlayerEntity player) {
        BootRecyclerTileEntity tileEntity = (BootRecyclerTileEntity) worldIn.getBlockEntity(pos);
        if (tileEntity != null) worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).resolve().get().getStackInSlot(0)));
        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BootRecyclerTileEntity tileEntity = (BootRecyclerTileEntity) context.getLevel().getBlockEntity(context.getClickedPos());
        if (tileEntity == null) return this.defaultBlockState().setValue(POWERED, false);
        return this.defaultBlockState().setValue(POWERED, !tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).resolve().get().getStackInSlot(0).isEmpty());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }
}
