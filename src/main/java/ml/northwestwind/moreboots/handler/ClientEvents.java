package ml.northwestwind.moreboots.handler;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.container.screen.StorageBootsScreen;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.KeybindInit;
import ml.northwestwind.moreboots.init.MenuTypeInit;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(MenuTypeInit.STORAGE_BOOTS.get(), StorageBootsScreen::new);
    }

    @SubscribeEvent
    public static void registerKeyMappings(final RegisterKeyMappingsEvent event) {
        KeybindInit.register(event);
    }

    @SubscribeEvent
    public static void itemColors(final RegisterColorHandlersEvent.Item event) {
        event.getItemColors().register((stack, layer) -> {
            if (layer == 0) return -1;
            Potion potion = PotionUtils.getPotion(stack);
            return PotionUtils.getColor(potion);
        }, ItemInit.GLASS_BOOTS.get());
    }
}
