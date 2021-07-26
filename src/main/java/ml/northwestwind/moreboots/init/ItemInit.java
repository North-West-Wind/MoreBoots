package ml.northwestwind.moreboots.init;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.init.item.TooltipItem;
import ml.northwestwind.moreboots.init.item.boots.*;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.tags.FluidTags;
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
    public static final Item SPIDER_BOOTS = new SpiderBootsItem();
    public static final Item QUARTZ_BOOTS = new QuartzBootsItem();
    public static final Item SOCKS_BOOTS = new SocksBootsItem();
    public static final Item RAINBOW_SOCKS_BOOTS = new RainbowSocksBootItem();
    public static final Item MINER_BOOTS = new MinerBootsItem();
    public static final Item LAPIS_BOOTS = new LapisBootsItem();
    public static final Item GLOWSTONE_BOOTS = new GlowstoneBootsItem();
    public static final Item WATER_BOOTS = new WaterBootsItem();
    public static final Item LAVA_BOOTS = new LavaBootsItem();
    public static final Item OBSIDIAN_BOOTS = new ObsidianBootsItem();
    public static final Item ICE_BOOTS = new IceBootsItem();
    public static final Item VANISHING_BOOTS = new VanishingBootsItem();
    public static final Item MILK_BOOTS = new MilkBootsItem();
    public static final Item REDSTONE_BOOTS = new RedstoneBootsItem();
    public static final Item PLUMBER_BOOTS = new PlumberBootsItem();
    public static final Item METAL_BOOTS = new MetalBootsItem();
    public static final Item UPWARP_BOOTS = new UpwarpBootsItem();
    public static final Item DOWNWARP_BOOTS = new DownwarpBootsItem();
    public static final Item ENDER_BOOTS = new EnderBootsItem();
    public static final Item BONE_BOOTS = new BoneBootsItem();
    public static final Item MUSHROOM_BOOTS = new MushroomBootsItem();
    public static final Item SLIME_BOOTS = new SlimeBootsItem();
    public static final Item BLAZE_BOOTS = new BlazeBootsItem();
    public static final Item CACTUS_BOOTS = new CactusBootsItem();
    public static final Item EXPLOSIVE_BOOTS = new ExplosiveBootsItem();
    public static final Item WINDY_BOOTS = new WindyBootsItem();
    public static final Item SKATER = new SkatingBootsItem();
    public static final Item PRISMARINE_BOOTS = new PrismarineBootsItem();
    public static final Item BAT_BOOTS = new BatBootsItem();
    public static final Item KA_BOOTS = new KABootsItem();
    public static final Item GLASS_BOOTS = new GlassBootsItem();
    public static final Item GLASS_BOOTS_EMPTY = new EmptyGlassBootsItem();
    public static final Item FLOATIE_BOOTS = new FloatieBootsItem();
    public static final Item STRIDER_BOOTS = new StriderBootsItem();
    public static final Item SANDALS = new SandBootsItem();
    public static final Item MUSIC_BOOTS = new MusicBootsItem();
    public static final Item HOPPER_BOOTS = new HopperBootsItem();
    public static final Item LOKI_BOOTS = new LokiBootsItem();
    public static final Item STORAGE_BOOTS = new StorageBootsItem();
    public static final Item GLIDER = new GlidingBootsItem();
    public static final Item SPONGE_BOOTS = new SpongeBootsItem(ModArmorMaterial.SPONGE, "sponge_boots", FluidTags.WATER, false);
    public static final Item LAVA_SPONGE_BOOTS = new SpongeBootsItem(ModArmorMaterial.LAVA_SPONGE, "lava_sponge_boots", FluidTags.LAVA, true);
    public static final Item LIGHTNING_BOOTS = new LightningBootsItem();
    public static final Item MAGMA_BOOTS = new MagmaBootsItem();
    public static final Item ENDER_DRAGON_BOOTS = new EnderDragonBootsItem();
    public static final Item WITHER_BOOTS = new WitherBootsItem();
    public static final Item MACHINE_BOW_BOOTS = new MachineBowBoots();

    public static final Item QUARTZ_INGOT = new TooltipItem("quartz_ingot");
    public static final Item METAL_MIX = new TooltipItem("metal_mix");
    public static final Item BAT_HIDE = new TooltipItem("bat_hide");
    public static final Item STRIDER_FOOT = new TooltipItem("strider_foot");

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                SPIDER_BOOTS,
                QUARTZ_BOOTS,
                SOCKS_BOOTS,
                RAINBOW_SOCKS_BOOTS,
                MINER_BOOTS,
                LAPIS_BOOTS,
                GLOWSTONE_BOOTS,
                WATER_BOOTS,
                LAVA_BOOTS,
                OBSIDIAN_BOOTS,
                ICE_BOOTS,
                VANISHING_BOOTS,
                MILK_BOOTS,
                REDSTONE_BOOTS,
                PLUMBER_BOOTS,
                METAL_BOOTS,
                UPWARP_BOOTS,
                DOWNWARP_BOOTS,
                ENDER_BOOTS,
                BONE_BOOTS,
                MUSHROOM_BOOTS,
                SLIME_BOOTS,
                BLAZE_BOOTS,
                CACTUS_BOOTS,
                EXPLOSIVE_BOOTS,
                WINDY_BOOTS,
                SKATER,
                PRISMARINE_BOOTS,
                BAT_BOOTS,
                KA_BOOTS,
                GLASS_BOOTS,
                GLASS_BOOTS_EMPTY,
                FLOATIE_BOOTS,
                STRIDER_BOOTS,
                SANDALS,
                MUSIC_BOOTS,
                HOPPER_BOOTS,
                LOKI_BOOTS,
                STORAGE_BOOTS,
                GLIDER,
                SPONGE_BOOTS,
                LAVA_SPONGE_BOOTS,
                LIGHTNING_BOOTS,
                MAGMA_BOOTS,
                ENDER_DRAGON_BOOTS,
                WITHER_BOOTS,
                MACHINE_BOW_BOOTS
        );

        event.getRegistry().register(QUARTZ_INGOT);
        event.getRegistry().register(METAL_MIX);
        event.getRegistry().register(BAT_HIDE);
        event.getRegistry().register(STRIDER_FOOT);
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
        RAINBOW_SOCKS(Reference.MODID + ":rainbow_socks", 200, 10, 42, SoundEvents.ARMOR_EQUIP_LEATHER, 10.0f, 0.0F, 150000, () -> {
            return Ingredient.of(BlockInit.RAINBOW_WOOL);
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
            return Ingredient.of(ItemInit.METAL_MIX);
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
        BAT(Reference.MODID + ":bat", 24, 1, 12, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 50000, () -> Ingredient.of(ItemInit.BAT_HIDE)),
        KA(Reference.MODID + ":ka", 16, 2, 8, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f, 120000, () -> Ingredient.EMPTY),
        GLASS_EMPTY(Reference.MODID + ":glass", 10, 1, 20, SoundEvents.BOTTLE_FILL, 0.0f, 0.0f, () -> Ingredient.of(Items.GLASS)),
        GLASS(Reference.MODID + ":glass", 10, 1, 20, SoundEvents.GLASS_BREAK, 0.0f, 0.0f, () -> Ingredient.of(Items.GLASS)),
        FLOATIE(Reference.MODID + ":floatie", 40, 4, 15, SoundEvents.ARMOR_EQUIP_DIAMOND, 1.0f, 0.0f, 160000, () -> Ingredient.of(Items.OAK_PLANKS)),
        STRIDER(Reference.MODID + ":strider", 40, 4, 15, SoundEvents.ARMOR_EQUIP_DIAMOND, 1.0f, 0.0f, 160000, () -> Ingredient.of(ItemInit.STRIDER_FOOT)),
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
        MACHINE_BOW(Reference.MODID + ":machine_bow", 20, 1, 10, SoundEvents.ARROW_SHOOT, 0f, 0f, 90000, () -> Ingredient.EMPTY);
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
