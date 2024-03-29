package ml.northwestwind.moreboots.init.brewing;

import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import javax.annotation.Nonnull;

public class GlassBootsBrewingRecipe implements IBrewingRecipe {

    @Override
    public boolean isInput(ItemStack input) {
        Item item = input.getItem();
        return item.equals(ItemInit.GLASS_BOOTS.get());
    }

    @Override
    public boolean isIngredient(@Nonnull ItemStack ingredient) {
        return PotionBrewing.isIngredient(ingredient);
    }

    @Nonnull
    @Override
    public ItemStack getOutput(@Nonnull ItemStack input, @Nonnull ItemStack ingredient) {
        if (ingredient.isEmpty() || !isIngredient(ingredient)) return ItemStack.EMPTY;
        CompoundTag tag = input.getOrCreateTag();
        Potion potion = PotionUtils.getPotion(input);
        if (potion.equals(Potions.WATER)) {
            Potion pot = waterBrewing(ingredient);
            if (pot.equals(potion)) return ItemStack.EMPTY;
            tag.putString("Potion", Registry.POTION.getKey(pot).toString());
        } else {
            ItemStack bottle = PotionUtils.setPotion(new ItemStack(Items.POTION), potion);
            ItemStack result = PotionBrewing.mix(bottle, ingredient);
            Potion pot = PotionUtils.getPotion(result);
            if (pot.equals(potion)) return ItemStack.EMPTY;
            tag.putString("Potion", Registry.POTION.getKey(pot).toString());
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
