package ml.northwestwind.moreboots.handler;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.container.screen.StorageBootsScreen;
import ml.northwestwind.moreboots.init.ContainerInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.KeybindInit;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        KeybindInit.register();
        MenuScreens.register(ContainerInit.STORAGE_BOOTS, StorageBootsScreen::new);
    }

    @SubscribeEvent
    public static void itemColors(final ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, layer) -> {
            if (layer == 0) return -1;
            Potion potion = PotionUtils.getPotion(stack);
            return PotionUtils.getColor(potion);
        }, ItemInit.GLASS_BOOTS);
    }
}
