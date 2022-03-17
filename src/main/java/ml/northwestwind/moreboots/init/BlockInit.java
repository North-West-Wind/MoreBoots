package ml.northwestwind.moreboots.init;

import ml.northwestwind.moreboots.MoreBoots;
import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.init.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Reference.MODID)
public class BlockInit {

    public static final Block RAINBOW_WOOL = new RainbowWoolBlock(Block.Properties.of(Material.WOOL).strength(10.0f, 3600000f).sound(SoundType.WOOL)).setRegistryName("rainbow_wool");
    public static final Block GLOWSTONE_DUST = new GlowstoneDustBlock(Block.Properties.of(Material.AIR).noCollission().instabreak().strength(0.5f).lightLevel(value -> 15)).setRegistryName("glowstone_dust");
    public static final Block REDSTONE_DUST = new RedstoneDustBlock(Block.Properties.of(Material.AIR).noCollission().instabreak().strength(0.5f)).setRegistryName("redstone_dust");
    public static final Block VISCOUS_GOO = new ViscousLayerBlock(BlockBehaviour.Properties.of(Material.WEB).noCollission().strength(0.1f).sound(SoundType.SLIME_BLOCK).isViewBlocking((state, level, pos) -> state.getValue(SnowLayerBlock.LAYERS) >= 8)).setRegistryName("viscous_goo");
    public static final Block COBBLESTONE_8 = new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).strength(16.0f, 48.0f)).setRegistryName("cobblestone_8");
    public static final Block COBBLESTONE_64 = new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).strength(128.0f, 384.0f)).setRegistryName("cobblestone_64");
    public static final Block COBBLESTONE_512 = new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).strength(1024.0f, 3072.0f)).setRegistryName("cobblestone_512");
    public static final Block BOOT_RECYCLER = new BootRecyclerBlock().setRegistryName("boot_recycler");

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                RAINBOW_WOOL,
                GLOWSTONE_DUST,
                REDSTONE_DUST,
                VISCOUS_GOO,
                COBBLESTONE_8,
                COBBLESTONE_64,
                COBBLESTONE_512,
                BOOT_RECYCLER
        );
    }

    @SubscribeEvent
    public static void registerBlockItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new BlockItem(RAINBOW_WOOL, new Item.Properties().stacksTo(64).tab(MoreBoots.MoreBootsItemGroup.INSTANCE)).setRegistryName("rainbow_wool"),
                new BlockItem(COBBLESTONE_8, new Item.Properties().stacksTo(64).tab(MoreBoots.MoreBootsItemGroup.INSTANCE)).setRegistryName("cobblestone_8"),
                new BlockItem(COBBLESTONE_64, new Item.Properties().stacksTo(64).tab(MoreBoots.MoreBootsItemGroup.INSTANCE)).setRegistryName("cobblestone_64"),
                new BlockItem(COBBLESTONE_512, new Item.Properties().stacksTo(64).tab(MoreBoots.MoreBootsItemGroup.INSTANCE)).setRegistryName("cobblestone_512"),
                new BlockItem(GLOWSTONE_DUST, new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MATERIALS)).setRegistryName("minecraft", "glowstone_dust"),
                new BlockItem(BOOT_RECYCLER, new Item.Properties().stacksTo(64).tab(MoreBoots.MoreBootsItemGroup.INSTANCE)).setRegistryName("boot_recycler")
        );
    }
}
