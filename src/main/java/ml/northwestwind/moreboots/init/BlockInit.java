package ml.northwestwind.moreboots.init;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.init.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MODID);

    public static final RegistryObject<Block> RAINBOW_WOOL = BLOCKS.register("rainbow_wool", () -> new RainbowWoolBlock(Block.Properties.of(Material.WOOL).strength(10.0f, 3600000f).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> GLOWSTONE_DUST = BLOCKS.register("glowstone_dust", () -> new GlowstoneDustBlock(Block.Properties.of(Material.AIR).noCollission().instabreak().strength(0.5f).lightLevel(value -> 15)));
    public static final RegistryObject<Block> REDSTONE_DUST = BLOCKS.register("redstone_dust", () -> new RedstoneDustBlock(Block.Properties.of(Material.AIR).noCollission().instabreak().strength(0.5f)));
    public static final RegistryObject<Block> VISCOUS_GOO = BLOCKS.register("viscous_goo", () -> new ViscousLayerBlock(BlockBehaviour.Properties.of(Material.WEB).noCollission().strength(0.1f).sound(SoundType.SLIME_BLOCK).isViewBlocking((state, level, pos) -> state.getValue(SnowLayerBlock.LAYERS) >= 8)));
    public static final RegistryObject<Block> COBBLESTONE_8 = BLOCKS.register("cobblestone_8", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).strength(16.0f, 48.0f)));
    public static final RegistryObject<Block> COBBLESTONE_64 = BLOCKS.register("cobblestone_64", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).strength(128.0f, 384.0f)));
    public static final RegistryObject<Block> COBBLESTONE_512 = BLOCKS.register("cobblestone_512", () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).strength(1024.0f, 3072.0f)));
    public static final RegistryObject<Block> BOOT_RECYCLER = BLOCKS.register("boot_recycler", BootRecyclerBlock::new);
    public static final RegistryObject<Block> TITANIUM_BLOCK = BLOCKS.register("titanium_block", () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(50.0F, 1200.0F).sound(SoundType.NETHERITE_BLOCK)));
    public static final RegistryObject<Block> VISCOUS_BLOCK = BLOCKS.register("viscous_block", () -> new ViscousBlock(BlockBehaviour.Properties.of(Material.WEB).noCollission().strength(0.1f).sound(SoundType.SLIME_BLOCK)));

    public static void registerBlocks() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
