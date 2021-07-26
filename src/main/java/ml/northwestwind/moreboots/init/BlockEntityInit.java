package ml.northwestwind.moreboots.init;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.init.tileentity.BootRecyclerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Reference.MODID)
public class BlockEntityInit {
    public static final BlockEntityType<?> BOOT_RECYCLER = BlockEntityType.Builder.of((BlockEntityType.BlockEntitySupplier<BlockEntity>) BootRecyclerBlockEntity::new, BlockInit.BOOT_RECYCLER).build(null).setRegistryName("boot_recycler");

    @SubscribeEvent
    public static void registerTileEntity(final RegistryEvent.Register<BlockEntityType<?>> event) {
        event.getRegistry().register(BOOT_RECYCLER);
    }
}
