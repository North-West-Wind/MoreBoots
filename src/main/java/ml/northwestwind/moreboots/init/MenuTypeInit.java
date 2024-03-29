package ml.northwestwind.moreboots.init;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.container.StorageBootsContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeInit {
    private static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Reference.MODID);

    public static final RegistryObject<MenuType<StorageBootsContainer>> STORAGE_BOOTS = MENU_TYPES.register("storage_boots", () -> IForgeMenuType.create((windowId, inv, data) -> new StorageBootsContainer(windowId, inv, data.readVarInt())));

    public static void registerContainer() {
        MENU_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
