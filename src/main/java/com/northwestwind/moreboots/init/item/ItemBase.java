package com.northwestwind.moreboots.init.item;

import com.northwestwind.moreboots.MoreBoots;
import com.northwestwind.moreboots.Reference;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.GameData;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBase extends Item {
    private String registryName;

    public ItemBase(Properties properties, String registryName) {
        super(properties);
        this.registryName = registryName;
        setRegistryName(Reference.MODID, registryName);
    }

    public ItemBase(String registryName) {
        this(new Item.Properties().group(MoreBoots.MoreBootsItemGroup.INSTANCE), registryName);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
    }
}
