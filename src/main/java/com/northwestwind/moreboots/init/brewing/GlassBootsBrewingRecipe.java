package com.northwestwind.moreboots.init.brewing;

import com.northwestwind.moreboots.init.ItemInit;
import com.northwestwind.moreboots.init.brewing.nbt.PotionNBT;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.*;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nonnull;

public class GlassBootsBrewingRecipe implements IBrewingRecipe {

    @Override
    public boolean isInput(ItemStack input) {
        Item item = input.getItem();
        return item.equals(ItemInit.GLASS_BOOTS);
        /*if (!item.equals(ItemInit.GLASS_BOOTS)) return false;
        if (input.getDamage() > 0) return false;
        CompoundNBT tag = input.getOrCreateTag();
        ListNBT effects = (ListNBT) tag.get("Effects");
        if (effects == null || effects.size() > 1) return false;
        INBT effect = effects.get(0);
        PotionNBT potion = new PotionNBT();
        potion.deserializeNBT((CompoundNBT) effect);
        return potion.getId() == -1;*/
    }

    @Override
    public boolean isIngredient(@Nonnull ItemStack ingredient) {
        return PotionBrewing.isReagent(ingredient);
    }

    @Nonnull
    @Override
    public ItemStack getOutput(@Nonnull ItemStack input, @Nonnull ItemStack ingredient) {
        if (ingredient.isEmpty() || !isIngredient(ingredient)) return ItemStack.EMPTY;
        LogManager.getLogger().info("Generating output");
        CompoundNBT tag = input.getOrCreateTag();
        LogManager.getLogger().info(tag);
        ListNBT effects = tag.getList("Potions", 0);
        ListNBT newList = new ListNBT();
        if (effects.size() < 1) {
            ListNBT list = waterBrewing(ingredient);
            if (list.size() < 1) return input;
            newList.add(list.getCompound(0));
        } else for (int i = 0; i < effects.size(); i++) {
            CompoundNBT nbt = effects.getCompound(i);
            PotionNBT potion = new PotionNBT();
            potion.deserializeNBT(nbt);
            Potion pot = Potion.getPotionTypeForName(potion.getName());
            LogManager.getLogger().info("Got effect");
            if (pot.equals(Potions.EMPTY)) newList.add(waterBrewing(ingredient).getCompound(0));
            else {
                LogManager.getLogger().info("Effect is not null");
                ItemStack bottle = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), pot);
                ItemStack result = PotionBrewing.doReaction(bottle, ingredient);
                Potion dummyPotion = PotionUtils.getPotionFromItem(result);
                newList.add(new PotionNBT(dummyPotion.getRegistryName(), dummyPotion.getEffects().get(0).getAmplifier()).serializeNBT());
            }
        }
        tag.put("Potions", newList);
        input.setTag(tag);
        LogManager.getLogger().info(tag);
        return input;
    }

    private static ListNBT waterBrewing(ItemStack ingredient) {
        ItemStack waterBottle = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.WATER);
        ItemStack result = PotionBrewing.doReaction(ingredient, waterBottle);
        LogManager.getLogger().info(result.getOrCreateTag());
        Potion potion = PotionUtils.getPotionFromItem(result);
        LogManager.getLogger().info(potion.getRegistryName());
        ListNBT effects = new ListNBT();
        effects.add(new PotionNBT(potion.getRegistryName(), potion.getEffects().size() > 0 ? potion.getEffects().get(0).getAmplifier() : 0).serializeNBT());
        LogManager.getLogger().info(effects);
        return effects;
    }
}
