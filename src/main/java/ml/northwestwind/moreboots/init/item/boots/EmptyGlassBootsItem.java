package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class EmptyGlassBootsItem extends BootsItem {
    public EmptyGlassBootsItem() {
        super(ItemInit.ModArmorMaterial.GLASS_EMPTY, "glass_boots_empty");
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(@Nonnull World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        BlockRayTraceResult blockRayTraceResult = getPlayerPOVHitResult(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
        if (!blockRayTraceResult.getType().equals(RayTraceResult.Type.BLOCK)) return ActionResult.pass(stack);
        BlockPos pos = blockRayTraceResult.getBlockPos();
        if (!worldIn.mayInteract(playerIn, pos)) return ActionResult.pass(stack);
        if (worldIn.getFluidState(pos).is(FluidTags.WATER)) {
            ItemStack newStack = new ItemStack(ItemInit.GLASS_BOOTS, 1);
            PotionUtils.setPotion(newStack, Potions.WATER);
            playerIn.setItemInHand(handIn, newStack);
            return ActionResult.consume(newStack);
        }
        return ActionResult.pass(stack);
    }
}
