package com.northwestwind.moreboots.init;

import com.northwestwind.moreboots.MoreBoots;
import com.northwestwind.moreboots.Reference;
import com.northwestwind.moreboots.init.block.BootRecyclerBlock;
import com.northwestwind.moreboots.init.block.GlowstoneDustBlock;
import com.northwestwind.moreboots.init.block.RainbowWoolBlock;
import com.northwestwind.moreboots.init.block.vanilla.*;
import com.northwestwind.moreboots.init.item.GlowstoneDustItemBase;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Reference.MODID)
public class BlockInit {
    public static final Block RAINBOW_WOOL = new RainbowWoolBlock(Block.Properties.create(Material.WOOL).hardnessAndResistance(10.0f, 3600000f).sound(SoundType.CLOTH).harvestTool(ToolType.get("shears"))).setRegistryName("rainbow_wool");
    public static final GlowstoneDustBlock GLOWSTONE_DUST = (GlowstoneDustBlock) new GlowstoneDustBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().zeroHardnessAndResistance().func_235838_a_(value -> 15)).setRegistryName("glowstone_dust");
    public static final Block COBBLESTONE_8 = new Block(AbstractBlock.Properties.from(Blocks.COBBLESTONE).hardnessAndResistance(16.0f, 48.0f)).setRegistryName("cobblestone_8");
    public static final Block COBBLESTONE_64 = new Block(AbstractBlock.Properties.from(Blocks.COBBLESTONE).hardnessAndResistance(128.0f, 384.0f)).setRegistryName("cobblestone_64");
    public static final Block COBBLESTONE_512 = new Block(AbstractBlock.Properties.from(Blocks.COBBLESTONE).hardnessAndResistance(1024.0f, 3072.0f)).setRegistryName("cobblestone_512");
    public static final Block BOOT_RECYCLER = new BootRecyclerBlock().setRegistryName("boot_recycler");

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        event.getRegistry().register(RAINBOW_WOOL);
        event.getRegistry().register(GLOWSTONE_DUST);
        event.getRegistry().register(COBBLESTONE_8);
        event.getRegistry().register(COBBLESTONE_64);
        event.getRegistry().register(COBBLESTONE_512);
        event.getRegistry().register(BOOT_RECYCLER);
        Field[] fields = Blocks.class.getDeclaredFields();
        LogManager.getLogger().info("Found " + fields.length + " blocks");
        for(Field f : fields) {
            Class<?> cl = f.getType();
            if(cl.equals(Block.class) && Modifier.isStatic(f.getModifiers())) {
                try {
                    Block block = (Block) f.get(null);
                    if(block.getRegistryName() == null) continue;
                    Block newBlock = null;
                    if(block instanceof AnvilBlock) newBlock = new VanishingAnvilBlock(block);
                    //else if(block instanceof BambooSaplingBlock) newBlock = new VanishingBambooSaplingBlock(block);
                    //else if(block instanceof BambooBlock) newBlock = new VanishingBambooBlock(block);
                    //else if(block instanceof FrostedIceBlock) newBlock = new VanishingFrostedIceBlock(block);
                    else if(block instanceof StainedGlassPaneBlock) newBlock = new VanishingStainedGlassPaneBlock((StainedGlassPaneBlock) block);
                    else if(block instanceof StainedGlassBlock) newBlock = new VanishingStainedGlassBlock((StainedGlassBlock) block);
                    //else if(block instanceof GlassBlock) newBlock = new VanishingGlassBlock(block);
                    //else if(block instanceof IceBlock) newBlock = new VanishingIceBlock(block);
                    //else if(block instanceof BreakableBlock) newBlock = new VanishingBreakableBlock(block);
                    //else if(block instanceof BrewingStandBlock) newBlock = new VanishingBrewingStandBlock(block);
                    //else if(block instanceof CactusBlock) newBlock = new VanishingCactusBlock(block);
                    else if(block instanceof CakeBlock) newBlock = new VanishingCakeBlock(block);
                    //else if(block instanceof CampfireBlock && block.getRegistryName().getPath().equals("campfire")) newBlock = new VanishingCampfireBlock(true, 1, block);
                    //else if(block instanceof CampfireBlock && block.getRegistryName().getPath().equals("soul_campfire")) newBlock = new VanishingCampfireBlock(false, 2, block);
                    else if(block instanceof CarpetBlock) newBlock = new VanishingCarpetBlock((CarpetBlock) block);
                    else if(block instanceof ChainBlock) newBlock = new VanishingChainBlock(block);
                    //else if(block instanceof ChorusPlantBlock) newBlock = new VanishingChorusPlantBlock(block);
                    //else if(block instanceof CocoaBlock) newBlock = new VanishingCocoaBlock(block);
                    else if(block instanceof ComparatorBlock) newBlock = new VanishingComparatorBlock(block);
                    else if(block instanceof ConduitBlock) newBlock = new VanishingConduitBlock(block);
                    else if(block instanceof DaylightDetectorBlock) newBlock = new VanishingDaylighDetectorBlock(block);
                    else if(block instanceof DoorBlock) newBlock = new VanishingDoorBlock(block);
                    else if(block instanceof EndRodBlock) newBlock = new VanishingEndRodBlock(block);
                    else if(block instanceof FenceBlock) newBlock = new VanishingFenceBlock(block);
                    else if(block instanceof FenceGateBlock) newBlock = new VanishingFenceGateBlock(block);
                    //else if(block instanceof FlowerPotBlock) newBlock = new VanishingFlowerPotBlock((FlowerPotBlock) block);
                    else if(block instanceof LadderBlock) newBlock = new VanishingLadderBlock(block);
                    else if(block instanceof LanternBlock) newBlock = new VanishingLanternBlock(block);
                    else if(block instanceof PaneBlock) newBlock = new VanishingPaneBlock(block);
                    else if(block instanceof PressurePlateBlock && (block.getRegistryName().getPath().equals("polished_blackstone_pressure_plate") || block.getRegistryName().getPath().equals("stone_pressure_plate"))) newBlock = new VanishingPressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, block);
                    else if(block instanceof PressurePlateBlock) newBlock = new VanishingPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, block);
                    else if(block instanceof RepeaterBlock) newBlock = new VanishingRepeaterBlock(block);
                    //else if(block instanceof RedstoneWireBlock) newBlock = new CustomRedstoneWireBlock(block);
                    else if(block instanceof ScaffoldingBlock) newBlock = new VanishingScaffoldingBlock(block);
                    //else if(block instanceof WitherSkeletonSkullBlock) newBlock = new VanishingWitherSkeletonSkullBlock(block);
                    //else if(block instanceof WitherSkeletonWallSkullBlock) newBlock = new VanishingWitherSkeletonWallSkullBlock(block);
                    //else if(block instanceof SkullBlock) newBlock = new VanishingSkullBlock((SkullBlock) block);
                    else if(block instanceof SlabBlock) newBlock = new VanishingSlabBlock(block);
                    else if(block instanceof SnowBlock) newBlock = new VanishingSnowBlock(block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("oak_stairs")) newBlock = new VanishingStairsBlock(Blocks.OAK_PLANKS::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("cobblestone_stairs")) newBlock = new VanishingStairsBlock(Blocks.COBBLESTONE::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("brick_stairs")) newBlock = new VanishingStairsBlock(Blocks.BRICKS::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("stone_brick_stairs")) newBlock = new VanishingStairsBlock(Blocks.STONE_BRICKS::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("nether_brick_stairs")) newBlock = new VanishingStairsBlock(Blocks.NETHER_BRICKS::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("sandstone_stairs")) newBlock = new VanishingStairsBlock(Blocks.SANDSTONE::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("spruce_stairs")) newBlock = new VanishingStairsBlock(Blocks.SPRUCE_PLANKS::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("birch_stairs")) newBlock = new VanishingStairsBlock(Blocks.BIRCH_PLANKS::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("jungle_stairs")) newBlock = new VanishingStairsBlock(Blocks.JUNGLE_PLANKS::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("quartz_stairs")) newBlock = new VanishingStairsBlock(Blocks.QUARTZ_BLOCK::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("acacia_stairs")) newBlock = new VanishingStairsBlock(Blocks.ACACIA_PLANKS::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("dark_oak_stairs")) newBlock = new VanishingStairsBlock(Blocks.DARK_OAK_PLANKS::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("prismarine_stairs")) newBlock = new VanishingStairsBlock(Blocks.PRISMARINE::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("prismarine_brick_stairs")) newBlock = new VanishingStairsBlock(Blocks.PRISMARINE_BRICKS::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("dark_prismarine_stairs")) newBlock = new VanishingStairsBlock(Blocks.DARK_PRISMARINE::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("red_sandstone_stairs")) newBlock = new VanishingStairsBlock(Blocks.RED_SANDSTONE::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("purpur_stairs")) newBlock = new VanishingStairsBlock(Blocks.PURPUR_BLOCK::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("polished_granite_stairs")) newBlock = new VanishingStairsBlock(Blocks.POLISHED_GRANITE::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("smooth_red_sandstone_stairs")) newBlock = new VanishingStairsBlock(Blocks.SMOOTH_RED_SANDSTONE::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("mossy_stone_brick_stairs")) newBlock = new VanishingStairsBlock(Blocks.MOSSY_STONE_BRICKS::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("polished_diorite_stairs")) newBlock = new VanishingStairsBlock(Blocks.POLISHED_DIORITE::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("mossy_cobblestone_stairs")) newBlock = new VanishingStairsBlock(Blocks.MOSSY_COBBLESTONE::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("end_stone_brick_stairs")) newBlock = new VanishingStairsBlock(Blocks.END_STONE_BRICKS::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("stone_stairs")) newBlock = new VanishingStairsBlock(Blocks.STONE::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("smooth_sandstone_stairs")) newBlock = new VanishingStairsBlock(Blocks.SMOOTH_SANDSTONE::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("smooth_quartz_stairs")) newBlock = new VanishingStairsBlock(Blocks.SMOOTH_QUARTZ::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("granite_stairs")) newBlock = new VanishingStairsBlock(Blocks.GRANITE::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("andesite_stairs")) newBlock = new VanishingStairsBlock(Blocks.ANDESITE::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("red_nether_brick_stairs")) newBlock = new VanishingStairsBlock(Blocks.RED_NETHER_BRICKS::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("polished_andesite_stairs")) newBlock = new VanishingStairsBlock(Blocks.POLISHED_ANDESITE::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("diorite_stairs")) newBlock = new VanishingStairsBlock(Blocks.DIORITE::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("crimson_stairs")) newBlock = new VanishingStairsBlock(Blocks.field_235344_mC_::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("warped_stairs")) newBlock = new VanishingStairsBlock(Blocks.field_235345_mD_::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("blackstone_stairs")) newBlock = new VanishingStairsBlock(Blocks.field_235406_np_::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("polished_blackstone_brick_stairs")) newBlock = new VanishingStairsBlock(Blocks.field_235411_nu_::getDefaultState, block);
                    else if(block instanceof StairsBlock && block.getRegistryName().getPath().equals("polished_blackstone_stairs")) newBlock = new VanishingStairsBlock(Blocks.field_235410_nt_::getDefaultState, block);
                    else if(block instanceof StonecutterBlock) newBlock = new VanishingStonecutterBlock(block);
                    //else if(block instanceof SweetBerryBushBlock) newBlock = new VanishingSweetBerryBushBlock(block);
                    else if(block instanceof TrapDoorBlock) newBlock = new VanishingTrapDoorBlock(block);
                    //else if(block instanceof TwistingVinesBlock) newBlock = new VanishingTwistingVinesBlock(block);
                    //else if(block instanceof TwistingVinesTopBlock) newBlock = new VanishingTwistingVinesTopBlock(block);
                    //else if(block instanceof VineBlock) newBlock = new VanishingVineBlock(block);
                    else if(block instanceof WallBlock) newBlock = new VanishingWallBlock(block);
                    else if(block instanceof WallSkullBlock) newBlock = new VanishingWallSkullBlock((WallSkullBlock) block);
                    else if(block instanceof WebBlock) newBlock = new VanishingWebBlock(block);
                    //else if(block instanceof WeepingVinesBlock) newBlock = new VanishingWeepingVinesBlock(block);
                    //else if(block instanceof WeepingVinesTopBlock) newBlock = new VanishingWeepingVinesTopBlock(block);
                    else if(block instanceof WeightedPressurePlateBlock && block.getRegistryName().getPath().equals("light_weighted_pressure_plate")) newBlock = new VanishingWeightedPressurePlateBlock(15, block);
                    else if(block instanceof WeightedPressurePlateBlock && block.getRegistryName().getPath().equals("heavy_weighted_pressure_plate")) newBlock = new VanishingWeightedPressurePlateBlock(150, block);
                    //else if(block.getRegistryName().getPath().equals("packed_ice")) newBlock = new VanishingBlock(block);
                    if(newBlock == null) continue;
                    event.getRegistry().register(newBlock.setRegistryName(block.getRegistryName()));
                    Item.BLOCK_TO_ITEM.put(newBlock, block.asItem());
                    LogManager.getLogger().info("Registered " + block.getRegistryName());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerBlockItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new BlockItem(RAINBOW_WOOL, new Item.Properties().maxStackSize(64).group(MoreBoots.MoreBootsItemGroup.INSTANCE)).setRegistryName("rainbow_wool"));
        event.getRegistry().register(new BlockItem(COBBLESTONE_8, new Item.Properties().maxStackSize(64).group(MoreBoots.MoreBootsItemGroup.INSTANCE)).setRegistryName("cobblestone_8"));
        event.getRegistry().register(new BlockItem(COBBLESTONE_64, new Item.Properties().maxStackSize(64).group(MoreBoots.MoreBootsItemGroup.INSTANCE)).setRegistryName("cobblestone_64"));
        event.getRegistry().register(new BlockItem(COBBLESTONE_512, new Item.Properties().maxStackSize(64).group(MoreBoots.MoreBootsItemGroup.INSTANCE)).setRegistryName("cobblestone_512"));
        event.getRegistry().register(new GlowstoneDustItemBase(GLOWSTONE_DUST, new Item.Properties().maxStackSize(64).group(ItemGroup.MATERIALS)).setRegistryName("minecraft", "glowstone_dust"));
        event.getRegistry().register(new BlockItem(BOOT_RECYCLER, new Item.Properties().maxStackSize(64).group(MoreBoots.MoreBootsItemGroup.INSTANCE)).setRegistryName("boot_recycler"));
    }
}
