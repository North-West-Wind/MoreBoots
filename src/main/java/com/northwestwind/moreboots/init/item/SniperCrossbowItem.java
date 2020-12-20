package com.northwestwind.moreboots.init.item;

import com.northwestwind.moreboots.init.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class SniperCrossbowItem extends CrossbowItem {
    public SniperCrossbowItem() {
        super((new Item.Properties()).maxStackSize(1).group(ItemGroup.COMBAT).maxDamage(326));
    }

    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        ItemStack boots = playerIn.getItemStackFromSlot(EquipmentSlotType.FEET);
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!boots.getItem().equals(ItemInit.SNIPER_BOOTS)) return super.onItemRightClick(worldIn, playerIn, handIn);
        if (isCharged(itemstack)) {
            fireProjectiles(worldIn, playerIn, handIn, itemstack, 15.0f, 0.0F);
            setCharged(itemstack, false);
            return ActionResult.resultConsume(itemstack);
        } else return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
