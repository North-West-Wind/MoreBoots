package ml.northwestwind.moreboots.init.brewing;

import ml.northwestwind.moreboots.init.ItemInit;
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
        return PotionBrewing.isIngredient(ingredient);
    }

    @Nonnull
    @Override
    public ItemStack getOutput(@Nonnull ItemStack input, @Nonnull ItemStack ingredient) {
        if (ingredient.isEmpty() || !isIngredient(ingredient)) return ItemStack.EMPTY;
        CompoundNBT tag = input.getOrCreateTag();
        Potion potion = PotionUtils.getPotion(input);
        if (potion.equals(Potions.WATER)) {
            Potion pot = waterBrewing(ingredient);
            if (pot.equals(potion)) return ItemStack.EMPTY;
            tag.putString("Potion", pot.getRegistryName().toString());
        } else {
            ItemStack bottle = PotionUtils.setPotion(new ItemStack(Items.POTION), potion);
            ItemStack result = PotionBrewing.mix(bottle, ingredient);
            Potion pot = PotionUtils.getPotion(result);
            if (pot.equals(potion)) return ItemStack.EMPTY;
            tag.putString("Potion", pot.getRegistryName().toString());
        }
        if (tag.contains("Duration") && ingredient.getItem().equals(Items.REDSTONE)) tag.putInt("Duration", 9600);
        input.setTag(tag);
        return input;
    }

    private static Potion waterBrewing(ItemStack ingredient) {
        ItemStack waterBottle = PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER);
        ItemStack result = PotionBrewing.mix(ingredient, waterBottle);
        return PotionUtils.getPotion(result);
    }
}
