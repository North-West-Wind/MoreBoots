package com.northwestwind.moreboots.init.block;

import com.northwestwind.moreboots.Reference;
import com.northwestwind.moreboots.init.ItemInit;
import com.northwestwind.moreboots.init.tileentity.InvisibleTileEntity;
import com.northwestwind.moreboots.interfaces.IVanillaLoggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

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
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return TOP_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Entity entity = context.getEntity();
        if (!(entity instanceof LivingEntity)) return VoxelShapes.empty();
        if (!((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemInit.JESUS_BOOTS)) return VoxelShapes.empty();
        if (entity.getPosY() < pos.up().getY()) return VoxelShapes.empty();
        return TOP_SHAPE;
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
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
}
