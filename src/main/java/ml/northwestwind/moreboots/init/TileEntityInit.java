package ml.northwestwind.moreboots.init;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.init.tileentity.BootRecyclerTileEntity;
import ml.northwestwind.moreboots.init.tileentity.InvisibleTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Reference.MODID)
public class TileEntityInit {
    public static final TileEntityType<?> BOOT_RECYCLER = TileEntityType.Builder.create((Supplier<TileEntity>) BootRecyclerTileEntity::new, BlockInit.BOOT_RECYCLER).build(null).setRegistryName("boot_recycler");
    public static final TileEntityType<?> INVISIBLE = TileEntityType.Builder.create((Supplier<TileEntity>) InvisibleTileEntity::new, BlockInit.INVISIBLE).build(null).setRegistryName("boots_invisible_placeholder");

    @SubscribeEvent
    public static void registerTileEntity(final RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().register(BOOT_RECYCLER);
        event.getRegistry().register(INVISIBLE);
    }
}
