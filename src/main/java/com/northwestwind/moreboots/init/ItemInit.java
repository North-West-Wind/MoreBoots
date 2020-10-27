package com.northwestwind.moreboots.init;

import com.northwestwind.moreboots.MoreBoots;
import com.northwestwind.moreboots.Reference;
import com.northwestwind.moreboots.init.item.ArmorItemBase;
import com.northwestwind.moreboots.init.item.ItemBase;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Reference.MODID)
public class ItemInit {
    public static final Item spider_boots = new ArmorItemBase(ModArmorMaterial.SPIDER, EquipmentSlotType.FEET, new Item.Properties().group(com.northwestwind.moreboots.MoreBoots.MoreBootsItemGroup.instance), "spider_boots").setRegistryName("spider_boots");
    public static final Item quartz_boots = new ArmorItemBase(ModArmorMaterial.QUARTZ, EquipmentSlotType.FEET, new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "quartz_boots").setRegistryName("quartz_boots");
    public static final Item socks_boots = new ArmorItemBase(ModArmorMaterial.SOCKS, EquipmentSlotType.FEET, new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "socks_boots").setRegistryName("socks_boots");
    public static final Item rainbow_socks_boots = new ArmorItemBase(ModArmorMaterial.RAINBOW_SOCKS, EquipmentSlotType.FEET, new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "rainbow_socks_boots").setRegistryName("rainbow_socks_boots");
    public static final Item miner_boots = new ArmorItemBase(ModArmorMaterial.MINER, EquipmentSlotType.FEET, new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "miner_boots").setRegistryName("miner_boots");
    public static final Item lapis_boots = new ArmorItemBase(ModArmorMaterial.LAPIS, EquipmentSlotType.FEET, new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "lapis_boots").setRegistryName("lapis_boots");
    public static final Item glowstone_boots = new ArmorItemBase(ModArmorMaterial.GLOWSTONE, EquipmentSlotType.FEET, new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "glowstone_boots").setRegistryName("glowstone_boots");
    public static final Item water_boots = new ArmorItemBase(ModArmorMaterial.WATER, EquipmentSlotType.FEET, new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "water_boots").setRegistryName("water_boots");
    public static final Item lava_boots = new ArmorItemBase(ModArmorMaterial.LAVA, EquipmentSlotType.FEET, new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "lava_boots").setRegistryName("lava_boots");
    public static final Item obsidian_boots = new ArmorItemBase(ModArmorMaterial.OBSIDIAN, EquipmentSlotType.FEET, new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "obsidian_boots").setRegistryName("obsidian_boots");
    public static final Item ice_boots = new ArmorItemBase(ModArmorMaterial.ICE, EquipmentSlotType.FEET, new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "ice_boots").setRegistryName("ice_boots");
    public static final Item vanishing_boots = new ArmorItemBase(ModArmorMaterial.VANISHING, EquipmentSlotType.FEET, new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "vanishing_boots").setRegistryName("vanishing_boots");
    public static final Item milk_boots = new ArmorItemBase(ModArmorMaterial.MILK, EquipmentSlotType.FEET, new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "milk_boots").setRegistryName("milk_boots");
    public static final Item redstone_boots = new ArmorItemBase(ModArmorMaterial.REDSTONE, EquipmentSlotType.FEET, new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "redstone_boots").setRegistryName("redstone_boots");
    public static final Item baseball_boots = new ArmorItemBase(ModArmorMaterial.BASEBALL, EquipmentSlotType.FEET, new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "baseball_boots").setRegistryName("baseball_boots");
    public static final Item plumber_boots = new ArmorItemBase(ModArmorMaterial.PLUMBER, EquipmentSlotType.FEET, new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "plumber_boots").setRegistryName("plumber_boots");
    public static final Item metal_boots = new ArmorItemBase(ModArmorMaterial.METAL, EquipmentSlotType.FEET, new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "metal_boots").setRegistryName("metal_boots");
    public static final Item upwarp_boots = new ArmorItemBase(ModArmorMaterial.UPWARP, EquipmentSlotType.FEET, new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "upwarp_boots").setRegistryName("upwarp_boots");
    public static final Item downwarp_boots = new ArmorItemBase(ModArmorMaterial.DOWNWARP, EquipmentSlotType.FEET, new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "downwarp_boots").setRegistryName("downwarp_boots");

    public static final Item quartz_ingot = new ItemBase(new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "quartz_ingot").setRegistryName("quartz_ingot");
    public static final Item metal_mix = new ItemBase(new Item.Properties().group(MoreBoots.MoreBootsItemGroup.instance), "metal_mix").setRegistryName("metal_mix");

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(spider_boots);
        event.getRegistry().register(quartz_boots);
        event.getRegistry().register(socks_boots);
        event.getRegistry().register(rainbow_socks_boots);
        event.getRegistry().register(miner_boots);
        event.getRegistry().register(lapis_boots);
        event.getRegistry().register(glowstone_boots);
        event.getRegistry().register(water_boots);
        event.getRegistry().register(lava_boots);
        event.getRegistry().register(obsidian_boots);
        event.getRegistry().register(ice_boots);
        event.getRegistry().register(vanishing_boots);
        event.getRegistry().register(milk_boots);
        event.getRegistry().register(redstone_boots);
        event.getRegistry().register(baseball_boots);
        event.getRegistry().register(plumber_boots);
        event.getRegistry().register(metal_boots);
        event.getRegistry().register(upwarp_boots);
        event.getRegistry().register(downwarp_boots);

        event.getRegistry().register(quartz_ingot);
        event.getRegistry().register(metal_mix);
    }

    public enum ModArmorMaterial implements IArmorMaterial {
        SPIDER(Reference.MODID + ":spider", 12, new int[] { 1, 4, 6, 3 }, 10, SoundEvents.ENTITY_SPIDER_AMBIENT, 1.0F, 0.0F, () -> {
            return Ingredient.fromItems(Items.COBWEB);
        }),
        QUARTZ(Reference.MODID + ":quartz", 150, new int[] { 5, 13, 16, 12 }, 120, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 6.0f, 0.0F, () -> {
            return Ingredient.fromItems(Items.QUARTZ_BLOCK);
        }),
        SOCKS(Reference.MODID + ":socks", 20, new int[] { 1, 4, 5, 3 }, 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.5f, 0.0F, () -> {
            return Ingredient.fromItems(Items.WHITE_WOOL);
        }),
        RAINBOW_SOCKS(Reference.MODID + ":rainbow_socks", 200, new int[] { 10, 15, 15, 15 }, 420, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 10.0f, 0.0F, () -> {
            return Ingredient.fromItems(BlockInit.rainbow_wool);
        }),
        MINER(Reference.MODID + ":miner", 16, new int[] { 1, 3, 3, 3 }, 15, SoundEvents.BLOCK_BEACON_ACTIVATE, 1.5F, 0.0F, () -> {
            return Ingredient.fromItems(Blocks.IRON_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.GOLD_ORE);
        }),
        LAPIS(Reference.MODID + ":lapis", 64, new int[] { 2, 4, 4, 4 }, 690, SoundEvents.ENTITY_PLAYER_LEVELUP, 2.0F, 0.0F, () -> {
            return Ingredient.fromItems(Items.LAPIS_LAZULI);
        }),
        GLOWSTONE(Reference.MODID + ":glowstone", 24, new int[] { 2, 3, 3, 3 }, 10, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 1.0F, 0.0F, () -> {
            return Ingredient.fromItems(Blocks.GLOWSTONE);
        }),
        WATER(Reference.MODID + ":water", 24, new int[] { 1, 3, 3, 3 }, 12, SoundEvents.AMBIENT_UNDERWATER_ENTER, 1.0f, 0.0F, () -> Ingredient.EMPTY),
        LAVA(Reference.MODID + ":lava", 24, new int[] { 1, 4, 4, 4 }, 12, SoundEvents.BLOCK_LAVA_POP, 1.0f, 0.0F, () -> Ingredient.EMPTY),
        OBSIDIAN(Reference.MODID + ":obsidian", 200, new int[] { 3, 3, 3, 3 }, 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, () -> {
            return Ingredient.fromItems(Blocks.OBSIDIAN);
        }),
        ICE(Reference.MODID + ":ice", 20, new int[] { 2, 2, 2, 2 }, 5, SoundEvents.BLOCK_GLASS_BREAK, 1.0f, 0.0f, () -> {
            return Ingredient.fromItems(Blocks.BLUE_ICE);
        }),
        VANISHING(Reference.MODID + ":vanishing", 32, new int[] { 2, 1, 1, 1 }, 8, SoundEvents.ENTITY_ILLUSIONER_PREPARE_MIRROR, 0.0f, 0.0f, () -> Ingredient.EMPTY),
        MILK(Reference.MODID + ":milk", 24, new int[] { 1, 1, 1, 1 }, 10, SoundEvents.ENTITY_COW_MILK, 0.0f, 0.0f, () -> Ingredient.EMPTY),
        REDSTONE(Reference.MODID + ":redstone", 24, new int[] { 2, 2, 2, 2 }, 12, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, 1.0f, 0.0f, () -> {
            return Ingredient.fromItems(Items.REDSTONE_BLOCK);
        }),
        BASEBALL(Reference.MODID + ":baseball", 12, new int[] { 1, 1, 1, 1 }, 6, SoundEvents.ENTITY_ENDER_PEARL_THROW, 0.0f, 0.0f, () -> {
            return Ingredient.fromItems(Items.BOW);
        }),
        PLUMBER(Reference.MODID + ":plumber", 6, new int[] { 1, 1, 1, 1 }, 4, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, () -> {
            return Ingredient.fromItems(Items.LEATHER);
        }),
        METAL(Reference.MODID + ":metal", 6, new int[] { 5, 1, 1, 1 }, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0f, 1.0f, () -> {
            return Ingredient.fromItems(ItemInit.metal_mix);
        }),
        UPWARP(Reference.MODID + ":upwarp", 10, new int[] { 2, 1, 1, 1 }, 8, SoundEvents.ENTITY_ENDERMAN_TELEPORT, 0.0f, 0.0f, () -> {
            return Ingredient.fromItems(Items.ENDER_PEARL);
        }),
        DOWNWARP(Reference.MODID + ":downwarp", 10, new int[] { 2, 1, 1, 1 }, 8, SoundEvents.ENTITY_ENDERMAN_TELEPORT, 0.0f, 0.0f, () -> {
            return Ingredient.fromItems(Items.ENDER_PEARL);
        });
        private static final int[] MAX_DAMAGE_ARRAY = new int[] { 16, 16, 16, 16 };
        private final String name;
        private final int maxDamageFactor;
        private final int[] damageReductionAmountArray;
        private final int enchantability;
        private final SoundEvent soundEvent;
        private final float toughness;
        private final float knockbackResistance;
        private final LazyValue<Ingredient> repairMaterial;
        ModArmorMaterial(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountIn,
                         int enchantabilityIn, SoundEvent soundEventIn, float toughnessIn, float knockbackResistanceIn,
                         Supplier<Ingredient> repairMaterialIn) {
            this.name = nameIn;
            this.maxDamageFactor = maxDamageFactorIn;
            this.damageReductionAmountArray = damageReductionAmountIn;
            this.enchantability = enchantabilityIn;
            this.soundEvent = soundEventIn;
            this.toughness = toughnessIn;
            this.repairMaterial = new LazyValue<>(repairMaterialIn);
            this.knockbackResistance = knockbackResistanceIn;
        }
        @Override
        public int getDurability(EquipmentSlotType slotIn) {
            return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
        }
        @Override
        public int getDamageReductionAmount(EquipmentSlotType slotIn) {
            return this.damageReductionAmountArray[slotIn.getIndex()];
        }
        @Override
        public int getEnchantability() {
            return this.enchantability;
        }
        @Override
        public SoundEvent getSoundEvent() {
            return this.soundEvent;
        }
        @Override
        public Ingredient getRepairMaterial() {
            return this.repairMaterial.getValue();
        }
        @OnlyIn(Dist.CLIENT)
        @Override
        public String getName() {
            return this.name;
        }
        @Override
        public float getToughness() {
            return this.toughness;
        }

        @Override
        public float func_230304_f_() {
            return knockbackResistance;
        }
    }

    private static Item register(String key, Item itemIn) {
        return register(new ResourceLocation(key), itemIn);
    }

    private static Item register(ResourceLocation key, Item itemIn) {
        if (itemIn instanceof BlockItem) {
            ((BlockItem)itemIn).addToBlockToItemMap(Item.BLOCK_TO_ITEM, itemIn);
        }

        return Registry.register(Registry.ITEM, key, itemIn);
    }
}
