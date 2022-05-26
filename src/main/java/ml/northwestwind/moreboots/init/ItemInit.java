package ml.northwestwind.moreboots.init;

import ml.northwestwind.moreboots.MoreBoots;
import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.init.item.TooltipItem;
import ml.northwestwind.moreboots.init.item.boots.*;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ItemInit {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MODID);
    private static final DeferredRegister<Item> MINECRAFT_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");
    
    public static final RegistryObject<Item> SPIDER_BOOTS = ITEMS.register("spider_boots", SpiderBootsItem::new);
    public static final RegistryObject<Item> QUARTZ_BOOTS = ITEMS.register("quartz_boots", QuartzBootsItem::new);
    public static final RegistryObject<Item> SOCKS_BOOTS = ITEMS.register("socks_boots", SocksBootsItem::new);
    public static final RegistryObject<Item> RAINBOW_SOCKS_BOOTS = ITEMS.register("rainbow_socks_boots", RainbowSocksBootItem::new);
    public static final RegistryObject<Item> MINER_BOOTS = ITEMS.register("miner_boots", MinerBootsItem::new);
    public static final RegistryObject<Item> LAPIS_BOOTS = ITEMS.register("lapis_boots", LapisBootsItem::new);
    public static final RegistryObject<Item> GLOWSTONE_BOOTS = ITEMS.register("glowstone_boots", GlowstoneBootsItem::new);
    public static final RegistryObject<Item> WATER_BOOTS = ITEMS.register("water_boots", WaterBootsItem::new);
    public static final RegistryObject<Item> LAVA_BOOTS = ITEMS.register("lava_boots", LavaBootsItem::new);
    public static final RegistryObject<Item> OBSIDIAN_BOOTS = ITEMS.register("obsidian_boots", ObsidianBootsItem::new);
    public static final RegistryObject<Item> ICE_BOOTS = ITEMS.register("ice_boots", IceBootsItem::new);
    public static final RegistryObject<Item> VANISHING_BOOTS = ITEMS.register("vanishing_boots", VanishingBootsItem::new);
    public static final RegistryObject<Item> MILK_BOOTS = ITEMS.register("milk_boots", MilkBootsItem::new);
    public static final RegistryObject<Item> REDSTONE_BOOTS = ITEMS.register("redstone_boots", RedstoneBootsItem::new);
    public static final RegistryObject<Item> PLUMBER_BOOTS = ITEMS.register("plumber_boots", PlumberBootsItem::new);
    public static final RegistryObject<Item> METAL_BOOTS = ITEMS.register("metal_boots", MetalBootsItem::new);
    public static final RegistryObject<Item> UPWARP_BOOTS = ITEMS.register("upwarp_boots", UpwarpBootsItem::new);
    public static final RegistryObject<Item> DOWNWARP_BOOTS = ITEMS.register("downwarp_boots", DownwarpBootsItem::new);
    public static final RegistryObject<Item> ENDER_BOOTS = ITEMS.register("ender_boots", EnderBootsItem::new);
    public static final RegistryObject<Item> BONE_BOOTS = ITEMS.register("bone_boots", BoneBootsItem::new);
    public static final RegistryObject<Item> MUSHROOM_BOOTS = ITEMS.register("mushroom_boots", MushroomBootsItem::new);
    public static final RegistryObject<Item> SLIME_BOOTS = ITEMS.register("slime_boots", SlimeBootsItem::new);
    public static final RegistryObject<Item> BLAZE_BOOTS = ITEMS.register("blaze_boots", BlazeBootsItem::new);
    public static final RegistryObject<Item> CACTUS_BOOTS = ITEMS.register("cactus_boots", CactusBootsItem::new);
    public static final RegistryObject<Item> EXPLOSIVE_BOOTS = ITEMS.register("explosive_boots", ExplosiveBootsItem::new);
    public static final RegistryObject<Item> WINDY_BOOTS = ITEMS.register("windy_boots", WindyBootsItem::new);
    public static final RegistryObject<Item> SKATER = ITEMS.register("skating_boots", SkatingBootsItem::new);
    public static final RegistryObject<Item> PRISMARINE_BOOTS = ITEMS.register("prismarine_boots", PrismarineBootsItem::new);
    public static final RegistryObject<Item> BAT_BOOTS = ITEMS.register("bat_boots", BatBootsItem::new);
    public static final RegistryObject<Item> KA_BOOTS = ITEMS.register("ka_boots", KABootsItem::new);
    public static final RegistryObject<Item> GLASS_BOOTS = ITEMS.register("glass_boots", GlassBootsItem::new);
    public static final RegistryObject<Item> GLASS_BOOTS_EMPTY = ITEMS.register("glass_boots_empty", EmptyGlassBootsItem::new);
    public static final RegistryObject<Item> FLOATIE_BOOTS = ITEMS.register("floatie_boots", FloatieBootsItem::new);
    public static final RegistryObject<Item> STRIDER_BOOTS = ITEMS.register("strider_boots", StriderBootsItem::new);
    public static final RegistryObject<Item> SANDALS = ITEMS.register("sand_boots", SandBootsItem::new);
    public static final RegistryObject<Item> MUSIC_BOOTS = ITEMS.register("music_boots", MusicBootsItem::new);
    public static final RegistryObject<Item> HOPPER_BOOTS = ITEMS.register("hopper_boots", HopperBootsItem::new);
    public static final RegistryObject<Item> LOKI_BOOTS = ITEMS.register("loki_boots", LokiBootsItem::new);
    public static final RegistryObject<Item> STORAGE_BOOTS = ITEMS.register("storage_boots", StorageBootsItem::new);
    public static final RegistryObject<Item> GLIDER = ITEMS.register("gliding_boots", GlidingBootsItem::new);
    public static final RegistryObject<Item> SPONGE_BOOTS = ITEMS.register("sponge_boots", () -> new SpongeBootsItem(ModArmorMaterial.SPONGE, "sponge_boots", FluidTags.WATER, false));
    public static final RegistryObject<Item> LAVA_SPONGE_BOOTS = ITEMS.register("lava_sponge_boots", () -> new SpongeBootsItem(ModArmorMaterial.LAVA_SPONGE, "lava_sponge_boots", FluidTags.LAVA, true));
    public static final RegistryObject<Item> LIGHTNING_BOOTS = ITEMS.register("lightning_boots", LightningBootsItem::new);
    public static final RegistryObject<Item> MAGMA_BOOTS = ITEMS.register("magma_boots", MagmaBootsItem::new);
    public static final RegistryObject<Item> ENDER_DRAGON_BOOTS = ITEMS.register("ender_dragon_boots", EnderDragonBootsItem::new);
    public static final RegistryObject<Item> WITHER_BOOTS = ITEMS.register("wither_boots", WitherBootsItem::new);
    public static final RegistryObject<Item> MACHINE_BOW_BOOTS = ITEMS.register("machine_bow_boots", MachineBowBoots::new);
    public static final RegistryObject<Item> SLIPPERY_BOOTS = ITEMS.register("slippery_boots", SlipperyBootsItem::new);
    public static final RegistryObject<Item> FLYING_BOOTS = ITEMS.register("flying_boots", FlyingBootsItem::new);
    public static final RegistryObject<Item> VISCOUS_BOOTS = ITEMS.register("viscous_boots", ViscousBootsItem::new);
    public static final RegistryObject<Item> AVIAN_FEET = ITEMS.register("avian_feet", AvianFeetItem::new);
    public static final RegistryObject<Item> SUPER_AVIAN_FEET = ITEMS.register("super_avian_feet", SuperAvianFeetItem::new);
    public static final RegistryObject<Item> TANOOKI_FEET = ITEMS.register("tanooki_feet", TanookiFeetItem::new);
    public static final RegistryObject<Item> TURTLE_BOOTS = ITEMS.register("turtle_boots", TurtleBootsItem::new);

    public static final RegistryObject<Item> QUARTZ_INGOT = ITEMS.register("quartz_ingot", () -> new TooltipItem("quartz_ingot"));
    public static final RegistryObject<Item> METAL_MIX = ITEMS.register("metal_mix", () -> new TooltipItem("metal_mix"));
    public static final RegistryObject<Item> BAT_HIDE = ITEMS.register("bat_hide", () -> new TooltipItem("bat_hide"));
    public static final RegistryObject<Item> STRIDER_FOOT = ITEMS.register("strider_foot", () -> new TooltipItem("strider_foot"));
    public static final RegistryObject<Item> FLOATING_CORE = ITEMS.register("floating_core", () -> new TooltipItem("floating_core"));
    public static final RegistryObject<Item> GOLDEN_FEATHER = ITEMS.register("golden_feather", () -> new TooltipItem("golden_feather"));
    public static final RegistryObject<Item> VISCOUS_GOO = ITEMS.register("viscous_goo", () -> new ItemNameBlockItem(BlockInit.VISCOUS_GOO.get(), new Item.Properties().tab(MoreBoots.MoreBootsItemGroup.INSTANCE)));
    public static final RegistryObject<Item> SMOOTH_FABRIC = ITEMS.register("smooth_fabric", () -> new TooltipItem("smooth_fabric"));

    // Heroic update items
    public static final RegistryObject<Item> SUPER_FEET = ITEMS.register("super_feet", SuperFeetItem::new);
    public static final RegistryObject<Item> WARRIOR_QUEEN_FEET = ITEMS.register("warrior_queen_feet", WarriorQueenFeet::new);
    public static final RegistryObject<Item> CINDERING_FEET = ITEMS.register("cindering_feet", CinderingFeetItem::new);
    public static final RegistryObject<Item> INCINERATING_FEET = ITEMS.register("incinerating_feet", IncineratingFeetItem::new);
    public static final RegistryObject<Item> MIGHTY_FEET = ITEMS.register("mighty_feet", MightyFeetItem::new);
    public static final RegistryObject<Item> POWER_FEET = ITEMS.register("power_feet", PowerFeetItem::new);
    public static final RegistryObject<Item> JOLT_FEET = ITEMS.register("jolt_feet", JoltFeetItem::new);
    public static final RegistryObject<Item> WATER_NINJA_FEET = ITEMS.register("water_ninja_feet", WaterNinjaFeet::new);
    public static final RegistryObject<Item> ELECTRICITY_FEET = ITEMS.register("electricity_feet", ElectricityFeetItem::new);
    public static final RegistryObject<Item> BAHAMUTS_FEET = ITEMS.register("bahamuts_feet", BahamutsFeetItem::new);
    public static final RegistryObject<Item> AWAKENED_BAHAMUTS_FEET = ITEMS.register("awakened_bahamuts_feet", AwakenedBahamutsFeet::new);
    public static final RegistryObject<Item> SPEEDSTER_FEET = ITEMS.register("speedster_feet", SpeedsterFeetItem::new);
    public static final RegistryObject<Item> SUPER_BOUNCE_BOOTS = ITEMS.register("super_bounce_boots", SuperBounceBootsItem::new);
    public static final RegistryObject<Item> SOUL_BOOTS = ITEMS.register("soul_boots", SoulBootsItem::new);
    public static final RegistryObject<Item> BOMBERFEET = ITEMS.register("bomber_feet", BomberFeetItem::new);
    public static final RegistryObject<Item> COSMIC_FIRE_FEET = ITEMS.register("cosmic_fire_feet", CosmicFireFeetItem::new);
    public static final RegistryObject<Item> LIGHTUP_FEET = ITEMS.register("lightup_feet", LightupFeetItem::new);
    public static final RegistryObject<Item> ODD1SOUT_FEET = ITEMS.register("odd1sout_feet", Odd1sOutFeetItem::new);
    public static final RegistryObject<Item> SUPER_SOCKS = ITEMS.register("super_socks", SuperSocksItem::new);
    public static final RegistryObject<Item> RUNNER_FEET = ITEMS.register("runner_feet", RunnerFeetItem::new);
    public static final RegistryObject<Item> NATURE_FAIRY_FEET = ITEMS.register("nature_fairy_feet", NatureFairyFeetItem::new);
    public static final RegistryObject<Item> WOODPECKER_FEET = ITEMS.register("woodpecker_feet", WoodpeckerFeetItem::new);
    public static final RegistryObject<Item> BOLTER_FEET = ITEMS.register("bolter_feet", BolterFeetItem::new);
    public static final RegistryObject<Item> FLUTTERING_FEET = ITEMS.register("fluttering_feet", FlutteringFeetItem::new);
    public static final RegistryObject<Item> BUG_FEET = ITEMS.register("bug_feet", BugFeetItem::new);
    public static final RegistryObject<Item> SALAMANDER_FEET = ITEMS.register("salamander_feet", SalamanderFeetItem::new);
    public static final RegistryObject<Item> MEERKAT_FEET = ITEMS.register("meerkat_feet", MeerkatFeetItem::new);
    public static final RegistryObject<Item> BIONIC_BEETLE_FEET = ITEMS.register("bionic_beetle_feet", BionicBeetleFeetItem::new);

    public static final RegistryObject<Item> HEROIC_CORE = ITEMS.register("heroic_core", () -> new TooltipItem("heroic_core"));
    public static final RegistryObject<Item> TITANIUM_DUST = ITEMS.register("titanium_dust", () -> new TooltipItem("titanium_dust"));
    public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot", () -> new TooltipItem("titanium_ingot"));
    public static final RegistryObject<Item> BIONIC_INGOT = ITEMS.register("bionic_ingot", () -> new TooltipItem("bionic_ingot"));
    public static final RegistryObject<Item> SOUL_STONE = ITEMS.register("soul_stone", () -> new TooltipItem("soul_stone"));
    public static final RegistryObject<Item> ELECTRIC_STONE = ITEMS.register("electric_stone", () -> new TooltipItem("electric_stone"));
    public static final RegistryObject<Item> WATER_STONE = ITEMS.register("water_stone", () -> new TooltipItem("water_stone"));
    public static final RegistryObject<Item> FIRE_STONE = ITEMS.register("fire_stone", () -> new TooltipItem("fire_stone"));
    public static final RegistryObject<Item> METAL_STONE = ITEMS.register("metal_stone", () -> new TooltipItem("metal_stone"));
    public static final RegistryObject<Item> PURENESS_STONE = ITEMS.register("pureness_stone", () -> new TooltipItem("pureness_stone"));
    public static final RegistryObject<Item> WAR_STONE = ITEMS.register("war_stone", () -> new TooltipItem("war_stone"));
    public static final RegistryObject<Item> NATURE_STONE = ITEMS.register("nature_stone", () -> new TooltipItem("nature_stone"));
    public static final RegistryObject<Item> BIONIC_CORE = ITEMS.register("bionic_core", () -> new TooltipItem("bionic_core"));
    public static final RegistryObject<Item> SUPERPOWERED_FEET = ITEMS.register("superpowered_feet", () -> new TooltipItem("superpowered_feet"));

    public static void registerItems() {
        ITEMS.register("rainbow_wool", () -> new BlockItem(BlockInit.RAINBOW_WOOL.get(), new Item.Properties().stacksTo(64).tab(MoreBoots.MoreBootsItemGroup.INSTANCE)));
        ITEMS.register("cobblestone_8", () -> new BlockItem(BlockInit.COBBLESTONE_8.get(), new Item.Properties().stacksTo(64).tab(MoreBoots.MoreBootsItemGroup.INSTANCE)));
        ITEMS.register("cobblestone_64", () -> new BlockItem(BlockInit.COBBLESTONE_64.get(), new Item.Properties().stacksTo(64).tab(MoreBoots.MoreBootsItemGroup.INSTANCE)));
        ITEMS.register("cobblestone_512", () -> new BlockItem(BlockInit.COBBLESTONE_512.get(), new Item.Properties().stacksTo(64).tab(MoreBoots.MoreBootsItemGroup.INSTANCE)));
        MINECRAFT_ITEMS.register("glowstone_dust", () -> new BlockItem(BlockInit.GLOWSTONE_DUST.get(), new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS)));
        ITEMS.register("boot_recycler", () -> new BlockItem(BlockInit.BOOT_RECYCLER.get(), new Item.Properties().stacksTo(64).tab(MoreBoots.MoreBootsItemGroup.INSTANCE)));

        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public enum ModArmorMaterial implements ArmorMaterial {
        SPIDER(Reference.MODID + ":spider", 12, 1, 10, SoundEvents.SPIDER_AMBIENT, 1.0F, 0.0F, 10000, () -> {
            return Ingredient.of(Items.COBWEB);
        }),
        QUARTZ(Reference.MODID + ":quartz", 150, 6, 120, SoundEvents.ARMOR_EQUIP_GOLD, 6.0f, 0.0F, 50000, () -> {
            return Ingredient.of(Items.QUARTZ_BLOCK);
        }),
        SOCKS(Reference.MODID + ":socks", 20, 1, 20, SoundEvents.ARMOR_EQUIP_LEATHER, 0.5f, 0.0F, 20000, () -> {
            return Ingredient.of(Items.WHITE_WOOL);
        }),
        RAINBOW_SOCKS(Reference.MODID + ":rainbow_socks", 50, 5, 42, SoundEvents.ARMOR_EQUIP_LEATHER, 2f, 0F, 150000, () -> {
            return Ingredient.of(BlockInit.RAINBOW_WOOL.get());
        }),
        MINER(Reference.MODID + ":miner", 16, 2, 15, SoundEvents.BEACON_ACTIVATE, 1.5F, 0.0F, 100000, () -> {
            return Ingredient.of(Blocks.IRON_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.GOLD_ORE);
        }),
        LAPIS(Reference.MODID + ":lapis", 64, 2, 690, SoundEvents.PLAYER_LEVELUP, 2.0F, 0.0F, 60000, () -> {
            return Ingredient.of(Items.LAPIS_LAZULI);
        }),
        GLOWSTONE(Reference.MODID + ":glowstone", 24, 2, 10, SoundEvents.ARMOR_EQUIP_GOLD, 1.0F, 0.0F, 30000, () -> {
            return Ingredient.of(Blocks.GLOWSTONE);
        }),
        WATER(Reference.MODID + ":water", 24, 1, 12, SoundEvents.AMBIENT_UNDERWATER_ENTER, 1.0f, 0.0F, 20000, () -> Ingredient.EMPTY),
        LAVA(Reference.MODID + ":lava", 24, 1, 12, SoundEvents.LAVA_POP, 1.0f, 0.0F, 40000, () -> Ingredient.EMPTY),
        OBSIDIAN(Reference.MODID + ":obsidian", 200, 4, 20, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, 90000, () -> {
            return Ingredient.of(Blocks.OBSIDIAN);
        }),
        ICE(Reference.MODID + ":ice", 20, 1, 5, SoundEvents.GLASS_BREAK, 1.0f, 0.0f, 15000, () -> {
            return Ingredient.of(Blocks.BLUE_ICE);
        }),
        VANISHING(Reference.MODID + ":vanishing", 32, 2, 8, SoundEvents.ILLUSIONER_PREPARE_MIRROR, 0.0f, 0.0f, 100000, () -> Ingredient.EMPTY),
        MILK(Reference.MODID + ":milk", 24, 1, 10, SoundEvents.COW_MILK, 0.0f, 0.0f, 20000, () -> Ingredient.EMPTY),
        REDSTONE(Reference.MODID + ":redstone", 10, 2, 12, SoundEvents.REDSTONE_TORCH_BURNOUT, 1.0f, 0.0f, 80000, () -> {
            return Ingredient.of(Items.REDSTONE_BLOCK);
        }),
        PLUMBER(Reference.MODID + ":plumber", 12, 1, 4, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 50000, () -> {
            return Ingredient.of(Items.LEATHER);
        }),
        METAL(Reference.MODID + ":metal", 6, 5, 12, SoundEvents.ARMOR_EQUIP_IRON, 3.0f, 1.0f, 120000, () -> {
            return Ingredient.of(ItemInit.METAL_MIX.get());
        }),
        UPWARP(Reference.MODID + ":upwarp", 10, 2, 8, SoundEvents.ENDERMAN_TELEPORT, 0.0f, 0.0f, 75000, () -> {
            return Ingredient.of(Items.ENDER_PEARL);
        }),
        DOWNWARP(Reference.MODID + ":downwarp", 10, 2, 8, SoundEvents.ENDERMAN_TELEPORT, 0.0f, 0.0f, 75000, () -> {
            return Ingredient.of(Items.ENDER_PEARL);
        }),
        ENDER(Reference.MODID + ":ender", 20, 1, 4, SoundEvents.ENDERMAN_TELEPORT, 0.0f, 0.0f, 70000, () -> {
            return Ingredient.of(Items.ENDER_PEARL);
        }),
        BONE(Reference.MODID + ":bone", 12, 1, 8, SoundEvents.CROP_PLANTED, 0.0f, 0.0f, 25000, () -> Ingredient.of(Items.BONE)),
        MUSHROOM(Reference.MODID + ":mushroom", 12, 1, 8, SoundEvents.CROP_PLANTED, 0.0f, 0.0f, 25000, () -> Ingredient.of(Items.BROWN_MUSHROOM_BLOCK, Items.RED_MUSHROOM_BLOCK)),
        SLIME(Reference.MODID + ":slime", 20, 2, 16, SoundEvents.SLIME_BLOCK_HIT, 0.0f, 0.0f, 50000, () -> Ingredient.of(Items.SLIME_BALL)),
        BLAZE(Reference.MODID + ":blaze", 20, 5, 20, SoundEvents.BLAZE_AMBIENT, 1.0f, 0.0f, 100000, () -> Ingredient.of(Items.BLAZE_ROD)),
        CACTUS(Reference.MODID + ":cactus", 20, 1, 12, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 30000, () -> Ingredient.of(Items.CACTUS)),
        EXPLOSIVE(Reference.MODID + ":explosive", 0.1875f, 1, 16, SoundEvents.TNT_PRIMED, 0.0f, 0.0f, 80000, () -> Ingredient.EMPTY),
        WINDY(Reference.MODID + ":windy", 20, 1, 12, SoundEvents.GLASS_BREAK, 0.0f, 0.0f, 125000, () -> Ingredient.of(Blocks.BLUE_ICE)),
        SKATER(Reference.MODID + ":skating", 32, 1, 8, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 60000, () -> Ingredient.of(Items.SHEARS)),
        PRISMARINE(Reference.MODID + ":prismarine", 28, 2, 10, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 75000, () -> Ingredient.of(Items.PRISMARINE_SHARD)),
        BAT(Reference.MODID + ":bat", 24, 1, 12, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 50000, () -> Ingredient.of(ItemInit.BAT_HIDE.get())),
        KA(Reference.MODID + ":ka", 16, 2, 8, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 120000, () -> Ingredient.EMPTY),
        GLASS_EMPTY(Reference.MODID + ":glass", 10, 1, 20, SoundEvents.BOTTLE_FILL, 0.0f, 0.0f, () -> Ingredient.of(Items.GLASS)),
        GLASS(Reference.MODID + ":glass", 10, 1, 20, SoundEvents.GLASS_BREAK, 0.0f, 0.0f, () -> Ingredient.of(Items.GLASS)),
        FLOATIE(Reference.MODID + ":floatie", 40, 4, 15, SoundEvents.ARMOR_EQUIP_DIAMOND, 1.0f, 0.0f, 160000, () -> Ingredient.of(Items.OAK_PLANKS)),
        STRIDER(Reference.MODID + ":strider", 40, 4, 15, SoundEvents.ARMOR_EQUIP_DIAMOND, 1.0f, 0.0f, 160000, () -> Ingredient.of(ItemInit.STRIDER_FOOT.get())),
        SAND(Reference.MODID + ":sand", 18, 1, 8, SoundEvents.SAND_PLACE, 0.0f, 0.0f, 40000, () -> Ingredient.of(Items.SAND)),
        MUSIC(Reference.MODID + ":music", 20, 1, 10, SoundEvents.NOTE_BLOCK_BANJO, 0.0f, 0.0f, 60000, () -> Ingredient.of(Items.NOTE_BLOCK)),
        ENERGY(Reference.MODID + ":energy", 1, 4, 4, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.0f, 0.0f, 75000, () -> Ingredient.EMPTY),
        HOPPER(Reference.MODID + ":hopper", 8, 2, 8, SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f, 90000, () -> Ingredient.of(Items.HOPPER)),
        LOKI(Reference.MODID + ":loki", 20, 1, 12, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.0f, 0.0f, 150000, () -> Ingredient.EMPTY),
        STORAGE(Reference.MODID + ":storage", 10, 1, 8, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 45000, () -> Ingredient.of(Items.CHEST)),
        GLIDER(Reference.MODID + ":gliding", 20, 1, 10, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 80000, () -> Ingredient.of(Items.PHANTOM_MEMBRANE)),
        SPONGE(Reference.MODID + ":sponge", 10, 1, 6, SoundEvents.GRASS_BREAK, 0.0f, 0.0f, 60000, () -> Ingredient.of(Items.SPONGE, Items.WET_SPONGE)),
        LAVA_SPONGE(Reference.MODID + ":lava_sponge", 10, 1, 6, SoundEvents.GRASS_BREAK, 0.0f, 0.0f, 65000, () -> Ingredient.of(Items.SPONGE)),
        LIGHTNING(Reference.MODID + ":lightning", 12, 4, 8, SoundEvents.LIGHTNING_BOLT_THUNDER, 0f, 0f, 120000, () -> Ingredient.EMPTY),
        MAGMA(Reference.MODID + ":magma", 16, 2, 8, SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_INSIDE, 0f, 0f, 100000, () -> Ingredient.of(Items.MAGMA_CREAM)),
        DRAGON(Reference.MODID + ":ender_dragon", 40, 4, 20, SoundEvents.ENDER_DRAGON_AMBIENT, 1.0f, 1.0f, 240000, () -> Ingredient.EMPTY),
        WITHER(Reference.MODID + ":wither", 40, 5, 24, SoundEvents.WITHER_AMBIENT, 1.0f, 1.0f, 240000, () -> Ingredient.EMPTY),
        MACHINE_BOW(Reference.MODID + ":machine_bow", 20, 1, 10, SoundEvents.ARROW_SHOOT, 0f, 0f, 90000, () -> Ingredient.EMPTY),
        SLIPPERY(Reference.MODID + ":slippery", 20, 1, 8, SoundEvents.GLASS_BREAK, 0f, 0f, 20000, () -> Ingredient.of(Items.BLUE_ICE)),
        FLYING(Reference.MODID + ":flying", 5, 2, 20, SoundEvents.FIREWORK_ROCKET_LAUNCH, 0f, 0f, 180000, () -> Ingredient.EMPTY),
        VISCOUS(Reference.MODID + ":viscous", 15, 3, 12, SoundEvents.SPIDER_AMBIENT, 0f, 0f, 20000, () -> Ingredient.of(VISCOUS_GOO.get())),
        AVIAN(Reference.MODID + ":avian", 22, 2, 12, SoundEvents.GLASS_BREAK, 0f, 0f, 150000, () -> Ingredient.of(Items.PHANTOM_MEMBRANE)),
        SUPER_AVIAN(Reference.MODID + ":super_avian", 120, 5, 42, SoundEvents.GLASS_BREAK, 4f, 0f, 160000, () -> Ingredient.of(BlockInit.RAINBOW_WOOL.get())),
        TANOOKI(Reference.MODID + ":tanooki", 20, 1, 10, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 80000, () -> Ingredient.of(Items.PHANTOM_MEMBRANE)),
        TURTLE(Reference.MODID + ":turtle", 25, 2, 10, SoundEvents.ARMOR_EQUIP_TURTLE, 0f, 0f, () -> Ingredient.of(Items.SCUTE)),
        // Heroic update materials
        SUPER(Reference.MODID + ":super", 16, 3, 20, SoundEvents.ARMOR_EQUIP_ELYTRA, 0f, 0f, 200000, () -> Ingredient.of(ItemInit.PURENESS_STONE.get())),
        WARRIOR_QUEEN(Reference.MODID + ":warrior_queen", 40, 6, 45, SoundEvents.ARMOR_EQUIP_DIAMOND, 2f, 0f, 160000, () -> Ingredient.of(ItemInit.WAR_STONE.get())),
        CINDERING(Reference.MODID + ":cindering", 40, 3, 30, SoundEvents.FIREWORK_ROCKET_BLAST, 0f, 0f, 150000, () -> Ingredient.of(ItemInit.FIRE_STONE.get())),
        INCINERATING(Reference.MODID + ":incinerating", 50, 4, 50, SoundEvents.FIREWORK_ROCKET_LARGE_BLAST, 0f, 0f, 200000, () -> Ingredient.of(ItemInit.FIRE_STONE.get())),
        MIGHTY(Reference.MODID + ":mighty", 18, 4, 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 0f, 0f, 50000, () -> Ingredient.of(ItemInit.WAR_STONE.get(), ItemInit.METAL_STONE.get())),
        POWER(Reference.MODID + ":power", 16, 3, 14, SoundEvents.ARMOR_EQUIP_DIAMOND, 0f, 0f, 40000, () -> Ingredient.of(Items.GLOW_INK_SAC)),
        JOLT(Reference.MODID + ":jolt", 15, 4, 10, SoundEvents.LIGHTNING_BOLT_THUNDER, 0f, 0f, 150000, () -> Ingredient.of(ItemInit.ELECTRIC_STONE.get())),
        WATER_NINJA(Reference.MODID + ":water_ninja", 15, 3, 15, SoundEvents.ARMOR_EQUIP_DIAMOND, 0f, 0f, 30000, () -> Ingredient.of(ItemInit.WATER_STONE.get())),
        ELECTRICITY(Reference.MODID + ":electricity", 15, 4, 10, SoundEvents.LIGHTNING_BOLT_IMPACT, 0f, 0f, 180000, () -> Ingredient.of(ItemInit.ELECTRIC_STONE.get())),
        BAHAMUTS(Reference.MODID + ":bahamuts", 20, 2, 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 0f, 0f, 100000, () -> Ingredient.of(ItemInit.SOUL_STONE.get(), ItemInit.WAR_STONE.get())),
        AWAKENED(Reference.MODID + ":awakened_bahamuts", 20, 2, 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 0f, 0f, 150000, () -> Ingredient.of(ItemInit.SOUL_STONE.get(), ItemInit.ELECTRIC_STONE.get())),
        SPEEDSTER(Reference.MODID + ":speedster", 20, 1, 10, SoundEvents.ARMOR_EQUIP_LEATHER, 0f, 0f, 40000, () -> Ingredient.of(ItemInit.ELECTRIC_STONE.get(), ItemInit.FIRE_STONE.get())),
        SUPER_BOUNCE(Reference.MODID + ":super_bounce", 80, 4, 45, SoundEvents.ARMOR_EQUIP_LEATHER, 1f, 0f, 150000, () -> Ingredient.of(BlockInit.RAINBOW_WOOL.get())),
        SOUL(Reference.MODID + ":soul", 25, 3, 16, SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, 0f, 0f, 30000, () -> Ingredient.of(ItemInit.SOUL_STONE.get())),
        BOMBER(Reference.MODID + ":bomber", 80, 4, 40, SoundEvents.GENERIC_EXPLODE, 1f, 0f, 150000, () -> Ingredient.EMPTY),
        COSMIC_FIRE(Reference.MODID + ":cosmic_fire", 40, 3, 20, SoundEvents.ARMOR_EQUIP_ELYTRA, 0f, 0f, 200000, () -> Ingredient.of(ItemInit.FLOATING_CORE.get())),
        LIGHTUP(Reference.MODID + ":lightup", 27, 2, 10, SoundEvents.ARMOR_EQUIP_GOLD, 1f, 0f, 40000, () -> Ingredient.of(Items.GLOWSTONE)),
        ODD1SOUT(Reference.MODID + ":odd1sout", 54, 5, 42, SoundEvents.ARMOR_EQUIP_LEATHER, 2f, 0f, 150000, () -> Ingredient.EMPTY),
        SUPER_SOCKS(Reference.MODID + ":super_socks", 55, 4, 45, SoundEvents.ARMOR_EQUIP_LEATHER, 2f, 0f, 150000, () -> Ingredient.EMPTY),
        RUNNER(Reference.MODID + ":runner", 50, 5, 45, SoundEvents.ARMOR_EQUIP_LEATHER, 2f, 0f, 150000, () -> Ingredient.EMPTY),
        NATURE_FAIRY(Reference.MODID + ":nature_fairy", 50, 5, 45, SoundEvents.ARMOR_EQUIP_LEATHER, 1f, 0f, 150000, () -> Ingredient.of(ItemInit.NATURE_STONE.get())),
        WOODPECKER(Reference.MODID + ":woodpecker", 120, 5, 42, SoundEvents.ARMOR_EQUIP_ELYTRA, 4f, 0f, 200000, () -> Ingredient.of(ItemInit.GOLDEN_FEATHER.get())),
        BOLTER(Reference.MODID + ":bolter", 20, 4, 15, SoundEvents.LIGHTNING_BOLT_THUNDER, 0f, 0f, 160000, () -> Ingredient.of(ItemInit.ELECTRIC_STONE.get())),
        FLUTTERING(Reference.MODID + ":fluttering", 5, 1, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0f, 0f, 10000, () -> Ingredient.of(Items.ORANGE_WOOL)),
        BUG(Reference.MODID + ":bug", 40, 2, 20, SoundEvents.ARMOR_EQUIP_LEATHER, 0f, 0f, 160000, () -> Ingredient.of(BlockInit.RAINBOW_WOOL.get())),
        SALAMANDER(Reference.MODID + ":salamander", 10, 1, 10, SoundEvents.ARMOR_EQUIP_LEATHER, 0f, 0f, 50000, () -> Ingredient.of(ItemInit.ELECTRIC_STONE.get())),
        MEERKAT(Reference.MODID + ":meerkat", 20, 1, 20, SoundEvents.ARMOR_EQUIP_LEATHER, 0f, 0f, 75000, () -> Ingredient.of(Items.BROWN_WOOL, Items.ORANGE_WOOL)),
        BIONIC(Reference.MODID + ":bionic", 100, 8, 60, SoundEvents.ARMOR_EQUIP_NETHERITE, 4f, 0f, 500000, () -> Ingredient.of(ItemInit.BIONIC_CORE.get()));
        private static final int[] MAX_DAMAGE_ARRAY = new int[] { 16, 16, 16, 16 };
        private final String name;
        private final float maxDamageFactor;
        private final int[] damageReductionAmountArray;
        private final int enchantability;
        private final SoundEvent soundEvent;
        private final float toughness;
        private final float knockbackResistance;
        private final LazyLoadedValue<Ingredient> repairMaterial;
        private final int energy;
        ModArmorMaterial(String name, float maxDmg, int dmgRed,
                         int enchant, SoundEvent sound, float tough, float kbRes, int energy,
                         Supplier<Ingredient> repairMat) {
            this.name = name;
            this.maxDamageFactor = maxDmg;
            this.damageReductionAmountArray = new int[] { dmgRed, 1, 1, 1 };
            this.enchantability = enchant;
            this.soundEvent = sound;
            this.toughness = tough;
            this.repairMaterial = new LazyLoadedValue<>(repairMat);
            this.knockbackResistance = kbRes;
            this.energy = energy;
        }
        ModArmorMaterial(String name, float maxDmg, int dmgRed,
                         int enchant, SoundEvent sound, float tough, float kbRes,
                         Supplier<Ingredient> repairMat) {
            this(name, maxDmg, dmgRed, enchant, sound, tough, kbRes, -1, repairMat);
        }
        @Override
        public int getDurabilityForSlot(EquipmentSlot slotIn) {
            return (int) (MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor);
        }
        @Override
        public int getDefenseForSlot(EquipmentSlot slotIn) {
            return this.damageReductionAmountArray[slotIn.getIndex()];
        }
        @Override
        public int getEnchantmentValue() {
            return this.enchantability;
        }
        @Nonnull
        @Override
        public SoundEvent getEquipSound() {
            return this.soundEvent;
        }
        @Override
        public Ingredient getRepairIngredient() {
            return this.repairMaterial.get();
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
        public float getKnockbackResistance() {
            return knockbackResistance;
        }
        public int getEnergy() {
            return energy;
        }
    }

}
