package ml.northwestwind.moreboots.init.item;

import ml.northwestwind.moreboots.init.BlockInit;
import ml.northwestwind.moreboots.init.block.GlowstoneDustBlock;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class GlowstoneDustItemBase extends Item {
    public GlowstoneDustItemBase(GlowstoneDustBlock block, Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    public ActionResultType onItemUse(ItemUseContext context) {
        ActionResultType actionresulttype = this.tryPlace(new BlockItemUseContext(context));
        return !actionresulttype.isSuccessOrConsume() && this.isFood() ? this.onItemRightClick(context.getWorld(), context.getPlayer(), context.getHand()).getType() : actionresulttype;
    }

    public ActionResultType tryPlace(BlockItemUseContext context) {
        if (!context.canPlace()) {
            return ActionResultType.FAIL;
        } else {
            BlockItemUseContext blockitemusecontext = (BlockItemUseContext) context;
            if (blockitemusecontext == null) {
                return ActionResultType.FAIL;
            } else {
                BlockState blockstate = BlockInit.GLOWSTONE_DUST.getDefaultState().with(GlowstoneDustBlock.FACING, GlowstoneDustBlock.getRandomDirection());
                if (blockstate == null) {
                    return ActionResultType.FAIL;
                } else if (!context.getWorld().setBlockState(context.getPos(), blockstate, 11)) {
                    return ActionResultType.FAIL;
                } else {
                    BlockPos blockpos = blockitemusecontext.getPos();
                    World world = blockitemusecontext.getWorld();
                    PlayerEntity playerentity = blockitemusecontext.getPlayer();
                    ItemStack itemstack = blockitemusecontext.getItem();
                    BlockState blockstate1 = world.getBlockState(blockpos);
                    Block block = blockstate1.getBlock();
                    if (block == blockstate.getBlock()) {
                        blockstate1 = this.func_219985_a(blockpos, world, itemstack, blockstate1);
                        this.onBlockPlaced(blockpos, world, playerentity, itemstack, blockstate1);
                        block.onBlockPlacedBy(world, blockpos, blockstate1, playerentity, itemstack);
                        if (playerentity instanceof ServerPlayerEntity) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerentity, blockpos, itemstack);
                        }
                    }

                    SoundType soundtype = blockstate1.getSoundType(world, blockpos, context.getPlayer());
                    world.playSound(playerentity, blockpos, this.getPlaceSound(blockstate1, world, blockpos, context.getPlayer()), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    if (playerentity == null || !playerentity.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    return ActionResultType.func_233537_a_(world.isRemote);
                }
            }
        }
    }

    protected boolean onBlockPlaced(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        return BlockItem.setTileEntityNBT(worldIn, player, pos, stack);
    }

    private BlockState func_219985_a(BlockPos p_219985_1_, World p_219985_2_, ItemStack p_219985_3_, BlockState p_219985_4_) {
        BlockState blockstate = p_219985_4_;
        CompoundNBT compoundnbt = p_219985_3_.getTag();
        if (compoundnbt != null) {
            CompoundNBT compoundnbt1 = compoundnbt.getCompound("BlockStateTag");
            StateContainer<Block, BlockState> statecontainer = p_219985_4_.getBlock().getStateContainer();

            for(String s : compoundnbt1.keySet()) {
                Property<?> property = statecontainer.getProperty(s);
                if (property != null) {
                    String s1 = compoundnbt1.get(s).getString();
                    blockstate = func_219988_a(blockstate, property, s1);
                }
            }
        }

        if (blockstate != p_219985_4_) {
            p_219985_2_.setBlockState(p_219985_1_, blockstate, 2);
        }

        return blockstate;
    }

    private static <T extends Comparable<T>> BlockState func_219988_a(BlockState p_219988_0_, Property<T> p_219988_1_, String p_219988_2_) {
        return p_219988_1_.parseValue(p_219988_2_).map((p_219986_2_) -> {
            return p_219988_0_.with(p_219988_1_, p_219986_2_);
        }).orElse(p_219988_0_);
    }

    protected SoundEvent getPlaceSound(BlockState state, World world, BlockPos pos, PlayerEntity entity) {
        return state.getSoundType(world, pos, entity).getPlaceSound();
    }
}