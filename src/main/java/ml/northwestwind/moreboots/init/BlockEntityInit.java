package ml.northwestwind.moreboots.init;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.init.tileentity.BootRecyclerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Reference.MODID);

    public static final RegistryObject<BlockEntityType<?>> BOOT_RECYCLER = BLOCK_ENTITIES.register("boot_recycler", () -> BlockEntityType.Builder.of((BlockEntityType.BlockEntitySupplier<BlockEntity>) BootRecyclerBlockEntity::new, BlockInit.BOOT_RECYCLER.get()).build(null));

    public static void registerTileEntity() {
        BLOCK_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
