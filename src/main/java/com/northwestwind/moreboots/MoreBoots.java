package com.northwestwind.moreboots;

import com.northwestwind.moreboots.handler.MoreBootsHandler;
import com.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import com.northwestwind.moreboots.handler.Utils;
import com.northwestwind.moreboots.init.ItemInit;
import com.northwestwind.moreboots.init.block.KeybindInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

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
    }

    @SubscribeEvent
    public void clientSetup(final FMLClientSetupEvent event) {
        KeybindInit.register();
    }

    public static class MoreBootsItemGroup extends ItemGroup {
        public static final ItemGroup INSTANCE = new MoreBootsItemGroup(ItemGroup.GROUPS.length, "morebootstab");

        private MoreBootsItemGroup(int index, String label) {
            super(index, label);
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemInit.SPIDER_BOOTS);
        }
    }
}
