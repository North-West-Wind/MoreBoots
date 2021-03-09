package ml.northwestwind.moreboots.init;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.init.item.ArmorItemBase;
import ml.northwestwind.moreboots.init.item.ItemBase;
import ml.northwestwind.moreboots.init.item.StorageBootsItem;
import net.minecraft.block.Blocks;
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

import javax.annotation.Nonnull;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Reference.MODID)
public class ItemInit {
    public static final Item SPIDER_BOOTS = registerBoots(ModArmorMaterial.SPIDER, "spider_boots");
    public static final Item QUARTZ_BOOTS = registerBoots(ModArmorMaterial.QUARTZ, "quartz_boots");
    public static final Item SOCKS_BOOTS = registerBoots(ModArmorMaterial.SOCKS, "socks_boots");
    public static final Item RAINBOW_SOCKS_BOOTS = registerBoots(ModArmorMaterial.RAINBOW_SOCKS, "rainbow_socks_boots");
    public static final Item MINER_BOOTS = registerBoots(ModArmorMaterial.MINER, "miner_boots");
    public static final Item LAPIS_BOOTS = registerBoots(ModArmorMaterial.LAPIS, "lapis_boots");
    public static final Item GLOWSTONE_BOOTS = registerBoots(ModArmorMaterial.GLOWSTONE, "glowstone_boots");
    public static final Item WATER_BOOTS = registerBoots(ModArmorMaterial.WATER, "water_boots");
    public static final Item LAVA_BOOTS = registerBoots(ModArmorMaterial.LAVA, "lava_boots");
    public static final Item OBSIDIAN_BOOTS = registerBoots(ModArmorMaterial.OBSIDIAN, "obsidian_boots");
    public static final Item ICE_BOOTS = registerBoots(ModArmorMaterial.ICE, "ice_boots");
    public static final Item VANISHING_BOOTS = registerBoots(ModArmorMaterial.VANISHING, "vanishing_boots");
    public static final Item MILK_BOOTS = registerBoots(ModArmorMaterial.MILK, "milk_boots");
    public static final Item REDSTONE_BOOTS = registerBoots(ModArmorMaterial.REDSTONE, "redstone_boots");
    public static final Item PLUMBER_BOOTS = registerBoots(ModArmorMaterial.PLUMBER, "plumber_boots");
    public static final Item METAL_BOOTS = registerBoots(ModArmorMaterial.METAL, "metal_boots");
    public static final Item UPWARP_BOOTS = registerBoots(ModArmorMaterial.UPWARP, "upwarp_boots");
    public static final Item DOWNWARP_BOOTS = registerBoots(ModArmorMaterial.DOWNWARP, "downwarp_boots");
    public static final Item ENDER_BOOTS = registerBoots(ModArmorMaterial.ENDER, "ender_boots");
    public static final Item BONE_BOOTS = registerBoots(ModArmorMaterial.BONE, "bone_boots");
    public static final Item MUSHROOM_BOOTS = registerBoots(ModArmorMaterial.MUSHROOM, "mushroom_boots");
    public static final Item SLIME_BOOTS = registerBoots(ModArmorMaterial.SLIME, "slime_boots");
    public static final Item BLAZE_BOOTS = registerBoots(ModArmorMaterial.BLAZE, "blaze_boots");
    public static final Item CACTUS_BOOTS = registerBoots(ModArmorMaterial.CACTUS, "cactus_boots");
    public static final Item EXPLOSIVE_BOOTS = registerBoots(ModArmorMaterial.EXPLOSIVE, "explosive_boots");
    public static final Item WINDY_BOOTS = registerBoots(ModArmorMaterial.WINDY, "windy_boots");
    public static final Item SKATER = registerBoots(ModArmorMaterial.SKATER, "skating_boots");
    public static final Item PRISMARINE_BOOTS = registerBoots(ModArmorMaterial.PRISMARINE, "prismarine_boots");
    public static final Item BAT_BOOTS = registerBoots(ModArmorMaterial.BAT, "bat_boots");
    public static final Item KA_BOOTS = registerBoots(ModArmorMaterial.KA, "ka_boots");
    public static final Item GLASS_BOOTS = registerBoots(ModArmorMaterial.GLASS, "glass_boots");
    public static final Item GLASS_BOOTS_EMPTY = registerBoots(ModArmorMaterial.GLASS_EMPTY, "glass_boots_empty");
    public static final Item FLOATIE_BOOTS = registerBoots(ModArmorMaterial.FLOATIE, "floatie_boots", true);
    public static final Item STRIDER_BOOTS = registerBoots(ModArmorMaterial.STRIDER, "strider_boots", true);
    public static final Item SANDALS = registerBoots(ModArmorMaterial.SAND, "sand_boots");
    public static final Item MUSIC_BOOTS = registerBoots(ModArmorMaterial.MUSIC, "music_boots");
    public static final Item HOPPER_BOOTS = registerBoots(ModArmorMaterial.HOPPER, "hopper_boots");
    public static final Item LOKI_BOOTS = registerBoots(ModArmorMaterial.LOKI, "loki_boots");
    public static final Item STORAGE_BOOTS = new StorageBootsItem(ModArmorMaterial.STORAGE, "storage_boots", 6);
    public static final Item GLIDER = registerBoots(ModArmorMaterial.GLIDER, "gliding_boots");
    public static final Item SPONGE_BOOTS = registerBoots(ModArmorMaterial.SPONGE, "sponge_boots");
    public static final Item LAVA_SPONGE_BOOTS = registerBoots(ModArmorMaterial.LAVA_SPONGE, "lava_sponge_boots", true);

    public static final Item QUARTZ_INGOT = new ItemBase("quartz_ingot");
    public static final Item METAL_MIX = new ItemBase("metal_mix");
    public static final Item BAT_HIDE = new ItemBase("bat_hide");
    public static final Item STRIDER_FOOT = new ItemBase("strider_foot");

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(SPIDER_BOOTS);
        event.getRegistry().register(QUARTZ_BOOTS);
        event.getRegistry().register(SOCKS_BOOTS);
        event.getRegistry().register(RAINBOW_SOCKS_BOOTS);
        event.getRegistry().register(MINER_BOOTS);
        event.getRegistry().register(LAPIS_BOOTS);
        event.getRegistry().register(GLOWSTONE_BOOTS);
        event.getRegistry().register(WATER_BOOTS);
        event.getRegistry().register(LAVA_BOOTS);
        event.getRegistry().register(OBSIDIAN_BOOTS);
        event.getRegistry().register(ICE_BOOTS);
        event.getRegistry().register(VANISHING_BOOTS);
        event.getRegistry().register(MILK_BOOTS);
        event.getRegistry().register(REDSTONE_BOOTS);
        event.getRegistry().register(PLUMBER_BOOTS);
        event.getRegistry().register(METAL_BOOTS);
        event.getRegistry().register(UPWARP_BOOTS);
        event.getRegistry().register(DOWNWARP_BOOTS);
        event.getRegistry().register(ENDER_BOOTS);
        event.getRegistry().register(BONE_BOOTS);
        event.getRegistry().register(MUSHROOM_BOOTS);
        event.getRegistry().register(SLIME_BOOTS);
        event.getRegistry().register(BLAZE_BOOTS);
        event.getRegistry().register(CACTUS_BOOTS);
        event.getRegistry().register(EXPLOSIVE_BOOTS);
        event.getRegistry().register(WINDY_BOOTS);
        event.getRegistry().register(SKATER);
        event.getRegistry().register(PRISMARINE_BOOTS);
        event.getRegistry().register(BAT_BOOTS);
        event.getRegistry().register(KA_BOOTS);
        event.getRegistry().register(GLASS_BOOTS);
        event.getRegistry().register(GLASS_BOOTS_EMPTY);
        event.getRegistry().register(FLOATIE_BOOTS);
        event.getRegistry().register(STRIDER_BOOTS);
        event.getRegistry().register(SANDALS);
        event.getRegistry().register(MUSIC_BOOTS);
        event.getRegistry().register(HOPPER_BOOTS);
        //event.getRegistry().register(LOKI_BOOTS);
        event.getRegistry().register(STORAGE_BOOTS);
        event.getRegistry().register(GLIDER);
        event.getRegistry().register(SPONGE_BOOTS);
        event.getRegistry().register(LAVA_SPONGE_BOOTS);

        event.getRegistry().register(QUARTZ_INGOT);
        event.getRegistry().register(METAL_MIX);
        event.getRegistry().register(BAT_HIDE);
        event.getRegistry().register(STRIDER_FOOT);
    }

    public static Item registerBoots(IArmorMaterial material, String registryName) {
        return registerBoots(material, registryName, false);
    }

    public static Item registerBoots(IArmorMaterial material, String registryName, boolean isNetherite) {
        return new ArmorItemBase(material, registryName, isNetherite);
    }

    public enum ModArmorMaterial implements IArmorMaterial {
        SPIDER(Reference.MODID + ":spider", 12, 1, 10, SoundEvents.ENTITY_SPIDER_AMBIENT, 1.0F, 0.0F, 10000, () -> {
            return Ingredient.fromItems(Items.COBWEB);
        }),
        QUARTZ(Reference.MODID + ":quartz", 150, 6, 120, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 6.0f, 0.0F, 50000, () -> {
            return Ingredient.fromItems(Items.QUARTZ_BLOCK);
        }),
        SOCKS(Reference.MODID + ":socks", 20, 1, 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.5f, 0.0F, 20000, () -> {
            return Ingredient.fromItems(Items.WHITE_WOOL);
        }),
        RAINBOW_SOCKS(Reference.MODID + ":rainbow_socks", 200, 10, 42, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 10.0f, 0.0F, 150000, () -> {
            return Ingredient.fromItems(BlockInit.RAINBOW_WOOL);
        }),
        MINER(Reference.MODID + ":miner", 16, 2, 15, SoundEvents.BLOCK_BEACON_ACTIVATE, 1.5F, 0.0F, 100000, () -> {
            return Ingredient.fromItems(Blocks.IRON_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.GOLD_ORE);
        }),
        LAPIS(Reference.MODID + ":lapis", 64, 2, 690, SoundEvents.ENTITY_PLAYER_LEVELUP, 2.0F, 0.0F, 60000, () -> {
            return Ingredient.fromItems(Items.LAPIS_LAZULI);
        }),
        GLOWSTONE(Reference.MODID + ":glowstone", 24, 2, 10, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 1.0F, 0.0F, 30000, () -> {
            return Ingredient.fromItems(Blocks.GLOWSTONE);
        }),
        WATER(Reference.MODID + ":water", 24, 1, 12, SoundEvents.AMBIENT_UNDERWATER_ENTER, 1.0f, 0.0F, 20000, () -> Ingredient.EMPTY),
        LAVA(Reference.MODID + ":lava", 24, 1, 12, SoundEvents.BLOCK_LAVA_POP, 1.0f, 0.0F, 40000, () -> Ingredient.EMPTY),
        OBSIDIAN(Reference.MODID + ":obsidian", 200, 4, 20, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, 90000, () -> {
            return Ingredient.fromItems(Blocks.OBSIDIAN);
        }),
        ICE(Reference.MODID + ":ice", 20, 1, 5, SoundEvents.BLOCK_GLASS_BREAK, 1.0f, 0.0f, 15000, () -> {
            return Ingredient.fromItems(Blocks.BLUE_ICE);
        }),
        VANISHING(Reference.MODID + ":vanishing", 32, 2, 8, SoundEvents.ENTITY_ILLUSIONER_PREPARE_MIRROR, 0.0f, 0.0f, 100000, () -> Ingredient.EMPTY),
        MILK(Reference.MODID + ":milk", 24, 1, 10, SoundEvents.ENTITY_COW_MILK, 0.0f, 0.0f, 20000, () -> Ingredient.EMPTY),
        REDSTONE(Reference.MODID + ":redstone", 10, 2, 12, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, 1.0f, 0.0f, 80000, () -> {
            return Ingredient.fromItems(Items.REDSTONE_BLOCK);
        }),
        PLUMBER(Reference.MODID + ":plumber", 12, 1, 4, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 50000, () -> {
            return Ingredient.fromItems(Items.LEATHER);
        }),
        METAL(Reference.MODID + ":metal", 6, 5, 12, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0f, 1.0f, 120000, () -> {
            return Ingredient.fromItems(ItemInit.METAL_MIX);
        }),
        UPWARP(Reference.MODID + ":upwarp", 10, 2, 8, SoundEvents.ENTITY_ENDERMAN_TELEPORT, 0.0f, 0.0f, 75000, () -> {
            return Ingredient.fromItems(Items.ENDER_PEARL);
        }),
        DOWNWARP(Reference.MODID + ":downwarp", 10, 2, 8, SoundEvents.ENTITY_ENDERMAN_TELEPORT, 0.0f, 0.0f, 75000, () -> {
            return Ingredient.fromItems(Items.ENDER_PEARL);
        }),
        ENDER(Reference.MODID + ":ender", 20, 1, 4, SoundEvents.ENTITY_ENDERMAN_TELEPORT, 0.0f, 0.0f, 70000, () -> {
            return Ingredient.fromItems(Items.ENDER_PEARL);
        }),
        BONE(Reference.MODID + ":bone", 12, 1, 8, SoundEvents.ITEM_CROP_PLANT, 0.0f, 0.0f, 25000, () -> Ingredient.fromItems(Items.BONE)),
        MUSHROOM(Reference.MODID + ":mushroom", 12, 1, 8, SoundEvents.ITEM_CROP_PLANT, 0.0f, 0.0f, 25000, () -> Ingredient.fromItems(Items.BROWN_MUSHROOM_BLOCK, Items.RED_MUSHROOM_BLOCK)),
        SLIME(Reference.MODID + ":slime", 20, 2, 16, SoundEvents.BLOCK_SLIME_BLOCK_HIT, 0.0f, 0.0f, 50000, () -> Ingredient.fromItems(Items.SLIME_BALL)),
        BLAZE(Reference.MODID + ":blaze", 20, 5, 20, SoundEvents.ENTITY_BLAZE_AMBIENT, 1.0f, 0.0f, 100000, () -> Ingredient.fromItems(Items.BLAZE_ROD)),
        CACTUS(Reference.MODID + ":cactus", 20, 1, 12, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 30000, () -> Ingredient.fromItems(Items.CACTUS)),
        EXPLOSIVE(Reference.MODID + ":explosive", 0.1875f, 1, 16, SoundEvents.ENTITY_TNT_PRIMED, 0.0f, 0.0f, 80000, () -> Ingredient.EMPTY),
        WINDY(Reference.MODID + ":windy", 20, 1, 12, SoundEvents.BLOCK_GLASS_BREAK, 0.0f, 0.0f, 125000, () -> Ingredient.fromItems(Blocks.BLUE_ICE)),
        SKATER(Reference.MODID + ":skating", 32, 1, 8, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 60000, () -> Ingredient.fromItems(Items.SHEARS)),
        PRISMARINE(Reference.MODID + ":prismarine", 28, 2, 10, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 75000, () -> Ingredient.fromItems(Items.PRISMARINE_SHARD)),
        BAT(Reference.MODID + ":bat", 24, 1, 12, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 50000, () -> Ingredient.fromItems(ItemInit.BAT_HIDE)),
        KA(Reference.MODID + ":ka", 16, 2, 8, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 120000, () -> Ingredient.EMPTY),
        GLASS_EMPTY(Reference.MODID + ":glass", 10, 1, 20, SoundEvents.ITEM_BOTTLE_FILL, 0.0f, 0.0f, () -> Ingredient.fromItems(Items.GLASS)),
        GLASS(Reference.MODID + ":glass", 10, 1, 20, SoundEvents.BLOCK_GLASS_BREAK, 0.0f, 0.0f, () -> Ingredient.fromItems(Items.GLASS)),
        FLOATIE(Reference.MODID + ":floatie", 40, 4, 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.0f, 0.0f, 160000, () -> Ingredient.fromItems(Items.field_234759_km_)),
        STRIDER(Reference.MODID + ":strider", 40, 4, 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.0f, 0.0f, 160000, () -> Ingredient.fromItems(ItemInit.STRIDER_FOOT)),
        SAND(Reference.MODID + ":sand", 18, 1, 8, SoundEvents.BLOCK_SAND_PLACE, 0.0f, 0.0f, 40000, () -> Ingredient.fromItems(Items.SAND)),
        MUSIC(Reference.MODID + ":music", 20, 1, 10, SoundEvents.BLOCK_NOTE_BLOCK_BANJO, 0.0f, 0.0f, 60000, () -> Ingredient.fromItems(Items.NOTE_BLOCK)),
        ENERGY(Reference.MODID + ":energy", 1, 4, 4, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0f, 0.0f, 75000, () -> Ingredient.EMPTY),
        HOPPER(Reference.MODID + ":hopper", 8, 2, 8, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0f, 0.0f, () -> Ingredient.fromItems(Items.HOPPER)),
        LOKI(Reference.MODID + ":loki", 20, 1, 12, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0f, 0.0f, () -> Ingredient.EMPTY),
        STORAGE(Reference.MODID + ":storage", 10, 1, 8, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, () -> Ingredient.fromItems(Items.CHEST)),
        GLIDER(Reference.MODID + ":gliding", 20, 1, 10, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, () -> Ingredient.fromItems(Items.PHANTOM_MEMBRANE)),
        SPONGE(Reference.MODID + ":sponge", 10, 1, 6, SoundEvents.BLOCK_GRASS_BREAK, 0.0f, 0.0f, () -> Ingredient.fromItems(Items.SPONGE, Items.WET_SPONGE)),
        LAVA_SPONGE(Reference.MODID + ":sponge", 10, 1, 6, SoundEvents.BLOCK_GRASS_BREAK, 0.0f, 0.0f, () -> Ingredient.fromItems(Items.SPONGE));
        private static final int[] MAX_DAMAGE_ARRAY = new int[] { 16, 16, 16, 16 };
        private final String name;
        private final float maxDamageFactor;
        private final int[] damageReductionAmountArray;
        private final int enchantability;
        private final SoundEvent soundEvent;
        private final float toughness;
        private final float knockbackResistance;
        private final LazyValue<Ingredient> repairMaterial;
        private final int energy;
        ModArmorMaterial(String nameIn, float maxDamageFactorIn, int dmgRed,
                         int enchantabilityIn, SoundEvent soundEventIn, float toughnessIn, float knockbackResistanceIn, int energy,
                         Supplier<Ingredient> repairMaterialIn) {
            this.name = nameIn;
            this.maxDamageFactor = maxDamageFactorIn;
            this.damageReductionAmountArray = new int[] { dmgRed, 1, 1, 1 };
            this.enchantability = enchantabilityIn;
            this.soundEvent = soundEventIn;
            this.toughness = toughnessIn;
            this.repairMaterial = new LazyValue<>(repairMaterialIn);
            this.knockbackResistance = knockbackResistanceIn;
            this.energy = energy;
        }
        ModArmorMaterial(String nameIn, float maxDamageFactorIn, int damageReductionAmountIn,
                         int enchantabilityIn, SoundEvent soundEventIn, float toughnessIn, float knockbackResistanceIn,
                         Supplier<Ingredient> repairMaterialIn) {
            this(nameIn, maxDamageFactorIn, damageReductionAmountIn, enchantabilityIn, soundEventIn, toughnessIn, knockbackResistanceIn, -1, repairMaterialIn);
        }
        @Override
        public int getDurability(EquipmentSlotType slotIn) {
            return (int) (MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor);
        }
        @Override
        public int getDamageReductionAmount(EquipmentSlotType slotIn) {
            return this.damageReductionAmountArray[slotIn.getIndex()];
        }
        @Override
        public int getEnchantability() {
            return this.enchantability;
        }
        @Nonnull
        @Override
        public SoundEvent getSoundEvent() {
            return this.soundEvent;
        }
        @Override
        public Ingredient getRepairMaterial() {
            return this.repairMaterial.getValue();
        }
        @Nonnull
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
        public int getEnergy() {
            return energy;
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
