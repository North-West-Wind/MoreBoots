package ml.northwestwind.moreboots.init;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.container.StorageBootsContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Reference.MODID)
public class ContainerInit {
    public static final ContainerType<StorageBootsContainer> STORAGE_BOOTS = IForgeContainerType.create((windowId, inv, data) -> new StorageBootsContainer(windowId, inv, data.readVarInt()));

    @SubscribeEvent
    public static void registerContainer(final RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(STORAGE_BOOTS.setRegistryName("storage_boots"));
    }
}
