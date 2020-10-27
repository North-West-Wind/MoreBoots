package com.northwestwind.moreboots.init.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ArmorItemBase extends ArmorItem {
    private String registryName;

    public ArmorItemBase(IArmorMaterial p_i48534_1_, EquipmentSlotType p_i48534_2_, Properties p_i48534_3_, String registryName) {
        super(p_i48534_1_, p_i48534_2_, p_i48534_3_);
        this.registryName = registryName;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        if(!(stack.getItem() instanceof  ArmorItemBase)) return;
        tooltip.add(new TranslationTextComponent("tooltip.moreboots." + registryName));
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(!(stack.getItem() instanceof ArmorItemBase)) return;
        if(registryName.equals("socks_boots")) {
            if(stack.getOrCreateTag().getBoolean("wet")) stack.getTag().putLong("wetTick", stack.getTag().getLong("wetTick") + 1);
        }
    }
}
