package com.northwestwind.moreboots;

import com.northwestwind.moreboots.handler.MoreBootsHandler;
import com.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import com.northwestwind.moreboots.init.ItemInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Reference.MODID)
public class MoreBoots {
    public static MoreBoots instance;

    public MoreBoots() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(new MoreBootsHandler());
        instance = this;
    }

    @SubscribeEvent
    public void setup(final FMLCommonSetupEvent event) {
        MoreBootsPacketHandler.registerPackets();
    }

    public static class MoreBootsItemGroup extends ItemGroup {
        public static final ItemGroup instance = new MoreBootsItemGroup(ItemGroup.GROUPS.length, "morebootstab");

        private MoreBootsItemGroup(int index, String label) {
            super(index, label);
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemInit.spider_boots);
        }
    }
}
