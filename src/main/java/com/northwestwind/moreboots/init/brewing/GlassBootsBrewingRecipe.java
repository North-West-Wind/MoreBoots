package com.northwestwind.moreboots.init.brewing;

import com.northwestwind.moreboots.init.ItemInit;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.*;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import javax.annotation.Nonnull;

public class GlassBootsBrewingRecipe implements IBrewingRecipe {

    @Override
    public boolean isInput(ItemStack input) {
        Item item = input.getItem();
        return item.equals(ItemInit.GLASS_BOOTS);
    }

    @Override
    public boolean isIngredient(@Nonnull ItemStack ingredient) {
        return PotionBrewing.isReagent(ingredient);
    }

    @Nonnull
    @Override
    public ItemStack getOutput(@Nonnull ItemStack input, @Nonnull ItemStack ingredient) {
        if (ingredient.isEmpty() || !isIngredient(ingredient)) return ItemStack.EMPTY;
        CompoundNBT tag = input.getOrCreateTag();
        Potion potion = PotionUtils.getPotionFromItem(input);
        if (potion.equals(Potions.WATER)) {
            Potion pot = waterBrewing(ingredient);
            if (pot.equals(potion)) return ItemStack.EMPTY;
            tag.putString("Potion", pot.getRegistryName().toString());
        } else {
            ItemStack bottle = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), potion);
            ItemStack result = PotionBrewing.doReaction(bottle, ingredient);
            Potion pot = PotionUtils.getPotionFromItem(result);
            if (pot.equals(potion)) return ItemStack.EMPTY;
            tag.putString("Potion", pot.getRegistryName().toString());
        }
        input.setTag(tag);
        return input;
    }

    private static Potion waterBrewing(ItemStack ingredient) {
        ItemStack waterBottle = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER);
        ItemStack result = PotionBrewing.doReaction(ingredient, waterBottle);
        return PotionUtils.getPotionFromItem(result);
    }
}
