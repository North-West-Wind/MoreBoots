package com.northwestwind.moreboots.init.block;

import com.northwestwind.moreboots.init.tileentity.BootRecyclerTileEntity;
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
        super(Properties.create(Material.IRON).hardnessAndResistance(2, 2.4F).harvestTool(ToolType.PICKAXE).harvestLevel(1));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        BootRecyclerTileEntity tileEntity = (BootRecyclerTileEntity) worldIn.getTileEntity(pos);
        if (tileEntity == null || !(player.getHeldItem(handIn).getItem() instanceof ArmorItem) || !((ArmorItem) player.getHeldItem(handIn).getItem()).getEquipmentSlot().equals(EquipmentSlotType.FEET)) return ActionResultType.PASS;
        IItemHandler itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, hit.getFace()).resolve().get();
        if (!itemHandler.getStackInSlot(0).isEmpty()) return ActionResultType.PASS;
        if (!worldIn.isRemote) player.setHeldItem(handIn, itemHandler.insertItem(0, player.getHeldItem(handIn), false));
        else player.playSound(SoundEvents.BLOCK_WOOL_PLACE, 1, 1);
        tileEntity.markDirty();
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
    public void onBlockHarvested(World worldIn, @Nonnull BlockPos pos, BlockState state, @Nonnull PlayerEntity player) {
        BootRecyclerTileEntity tileEntity = (BootRecyclerTileEntity) worldIn.getTileEntity(pos);
        if (tileEntity != null) worldIn.addEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).resolve().get().getStackInSlot(0)));
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BootRecyclerTileEntity tileEntity = (BootRecyclerTileEntity) context.getWorld().getTileEntity(context.getPos());
        if (tileEntity == null) return this.getDefaultState().with(POWERED, false);
        return this.getDefaultState().with(POWERED, !tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).resolve().get().getStackInSlot(0).isEmpty());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }
}
