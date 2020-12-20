package com.northwestwind.moreboots.init.item;

import com.northwestwind.moreboots.MoreBoots;
import com.northwestwind.moreboots.init.ItemInit;
import com.northwestwind.moreboots.init.brewing.nbt.PotionNBT;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ArmorItemBase extends ArmorItem {
    private String registryName;

    public ArmorItemBase(IArmorMaterial material, String registryName) {
        super(material, EquipmentSlotType.FEET, material.equals(ItemInit.ModArmorMaterial.REDSTONE) ? new Item.Properties().group(MoreBoots.MoreBootsItemGroup.INSTANCE).maxStackSize(64) : new Item.Properties().group(MoreBoots.MoreBootsItemGroup.INSTANCE));
        this.registryName = registryName;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!(stack.getItem() instanceof ArmorItemBase)) return;
        tooltip.add(new TranslationTextComponent("tooltip.moreboots." + registryName));
        if (stack.getItem().equals(ItemInit.GLASS_BOOTS)) {
            CompoundNBT tag = stack.getOrCreateTag();
            ListNBT effects = tag.getList("Potions", 0);
            for (int i = 0; i < effects.size(); i++) {
                PotionNBT potionNBT = new PotionNBT();
                potionNBT.deserializeNBT(effects.getCompound(i));
                Potion potion = Potion.getPotionTypeForName(potionNBT.getName());
                for (EffectInstance instance : potion.getEffects()) tooltip.add(new TranslationTextComponent("potion.withAmplifier", new TranslationTextComponent(instance.getPotion().getName()), new TranslationTextComponent("potion.potency." + potionNBT.getAmp())));
            }
        }
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
            playerIn.setHeldItem(handIn, new ItemStack(ItemInit.GLASS_BOOTS));
            playerIn.playSound(SoundEvents.ITEM_BOTTLE_FILL, 1, 1);
            return ActionResult.resultConsume(stack);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
