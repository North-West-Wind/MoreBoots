package ml.northwestwind.moreboots.init;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.container.StorageBootsContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegistryObject;

public class ContainerInit {
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Reference.MODID);

    public static final RegistryObject<MenuType<StorageBootsContainer>> STORAGE_BOOTS = CONTAINERS.register("storage_boots", () -> IForgeMenuType.create((windowId, inv, data) -> new StorageBootsContainer(windowId, inv, data.readVarInt())));

    public static void registerContainer() {
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
