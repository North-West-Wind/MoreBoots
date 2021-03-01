package ml.northwestwind.moreboots;

import ml.northwestwind.moreboots.container.screen.StorageBootsScreen;
import ml.northwestwind.moreboots.handler.MoreBootsHandler;
import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.ContainerInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.block.KeybindInit;
import ml.northwestwind.moreboots.init.brewing.GlassBootsBrewingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;

@Mod(Reference.MODID)
public class MoreBoots {
    public static MoreBoots INSTANCE;

    public MoreBoots() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(new MoreBootsHandler());
        Utils.initialize();
        INSTANCE = this;
    }

    @SubscribeEvent
    public void setup(final FMLCommonSetupEvent event) {
        MoreBootsPacketHandler.registerPackets();
        BrewingRecipeRegistry.addRecipe(new GlassBootsBrewingRecipe());
    }

    @SubscribeEvent
    public void clientSetup(final FMLClientSetupEvent event) {
        KeybindInit.register();
        Minecraft.getInstance().getItemColors().register((stack, layer) -> {
            if (layer == 0) return -1;
            Potion potion = PotionUtils.getPotionFromItem(stack);
            return PotionUtils.getPotionColor(potion);
        }, ItemInit.GLASS_BOOTS);
        ScreenManager.registerFactory(ContainerInit.STORAGE_BOOTS, StorageBootsScreen::new);
    }

    public static class MoreBootsItemGroup extends ItemGroup {
        public static final ItemGroup INSTANCE = new MoreBootsItemGroup(ItemGroup.GROUPS.length, "morebootstab");

        private MoreBootsItemGroup(int index, String label) {
            super(index, label);
        }

        @Nonnull
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemInit.CACTUS_BOOTS);
        }
    }
}