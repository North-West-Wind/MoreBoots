package com.northwestwind.moreboots.init.item;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.northwestwind.moreboots.MoreBoots;
import com.northwestwind.moreboots.init.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.*;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class ArmorItemBase extends ArmorItem {
    private String registryName;

    public ArmorItemBase(IArmorMaterial material, String registryName, boolean isNetherite) {
        super(material, EquipmentSlotType.FEET, isNetherite ? new Item.Properties().group(MoreBoots.MoreBootsItemGroup.INSTANCE).func_234689_a_() : new Item.Properties().group(MoreBoots.MoreBootsItemGroup.INSTANCE));
        this.registryName = registryName;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!(stack.getItem() instanceof ArmorItemBase)) return;
        tooltip.add(new TranslationTextComponent("tooltip.moreboots." + registryName));
        if (stack.getItem().equals(ItemInit.GLASS_BOOTS)) addPotionTooltip(stack, tooltip);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!(stack.getItem() instanceof ArmorItemBase)) return;
        if (registryName.equals("socks_boots")) {
            CompoundNBT tag = stack.getOrCreateTag();
            if (tag.getBoolean("wet")) tag.putLong("wetTick", tag.getLong("wetTick") + 1);
            stack.setTag(tag);
        }
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, PlayerEntity playerIn, @Nonnull Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (!stack.getItem().equals(ItemInit.GLASS_BOOTS_EMPTY)) return super.onItemRightClick(worldIn, playerIn, handIn);
        BlockRayTraceResult blockRayTraceResult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
        if (!blockRayTraceResult.getType().equals(RayTraceResult.Type.BLOCK)) return super.onItemRightClick(worldIn, playerIn, handIn);
        BlockPos pos = blockRayTraceResult.getPos();
        if (!worldIn.isBlockModifiable(playerIn, pos)) return super.onItemRightClick(worldIn, playerIn, handIn);
        if (worldIn.getFluidState(pos).isTagged(FluidTags.WATER)) {
            ItemStack newStack = new ItemStack(ItemInit.GLASS_BOOTS);
            CompoundNBT tag = newStack.getOrCreateTag();
            tag.putString("Potion", Potions.WATER.getRegistryName().toString());
            newStack.setTag(tag);
            playerIn.setHeldItem(handIn, newStack);
            return ActionResult.resultConsume(stack);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @OnlyIn(Dist.CLIENT)
    public static void addPotionTooltip(ItemStack itemIn, List<ITextComponent> lores) {
        List<EffectInstance> list = PotionUtils.getEffectsFromStack(itemIn);
        List<Pair<Attribute, AttributeModifier>> list1 = Lists.newArrayList();
        if (!list.isEmpty()) {
            for(EffectInstance effectinstance : list) {
                IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(effectinstance.getEffectName());
                Effect effect = effectinstance.getPotion();
                Map<Attribute, AttributeModifier> map = effect.getAttributeModifierMap();
                if (!map.isEmpty()) {
                    for(Map.Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
                        AttributeModifier attributemodifier = entry.getValue();
                        AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), effect.getAttributeModifierAmount(effectinstance.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                        list1.add(new Pair<>(entry.getKey(), attributemodifier1));
                    }
                }

                if (effectinstance.getAmplifier() > 0) {
                    iformattabletextcomponent = new TranslationTextComponent("potion.withAmplifier", iformattabletextcomponent, new TranslationTextComponent("potion.potency." + effectinstance.getAmplifier()));
                }

                lores.add(iformattabletextcomponent.func_240699_a_(effect.getEffectType().getColor()));
            }
        } else {
            Potion potion = PotionUtils.getPotionFromItem(itemIn);
            IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(potion.getNamePrefixed("item.minecraft.potion.effect."));
            lores.add(iformattabletextcomponent.func_240699_a_(TextFormatting.GREEN));
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return super.hasEffect(stack) || !PotionUtils.getEffectsFromStack(stack).isEmpty();
    }
}
