package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;

public class EmptyGlassBootsItem extends BootsItem {
    public EmptyGlassBootsItem() {
        super(ItemInit.ModArmorMaterial.GLASS_EMPTY, "glass_boots_empty");
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level worldIn, Player playerIn, @Nonnull InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        BlockHitResult blockRayTraceResult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.ANY);
        if (!blockRayTraceResult.getType().equals(BlockHitResult.Type.BLOCK)) return InteractionResultHolder.pass(stack);
        BlockPos pos = blockRayTraceResult.getBlockPos();
        if (!worldIn.mayInteract(playerIn, pos)) return InteractionResultHolder.pass(stack);
        if (worldIn.getFluidState(pos).is(FluidTags.WATER)) {
            ItemStack newStack = new ItemStack(ItemInit.GLASS_BOOTS, 1);
            PotionUtils.setPotion(newStack, Potions.WATER);
            playerIn.setItemInHand(handIn, newStack);
            return InteractionResultHolder.consume(newStack);
        }
        return InteractionResultHolder.pass(stack);
    }
}
