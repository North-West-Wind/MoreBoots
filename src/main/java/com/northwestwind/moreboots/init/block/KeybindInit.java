package com.northwestwind.moreboots.init.block;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeybindInit {
    public static KeyBinding teleport;

    public static void register() {
        teleport = new KeyBinding(new TranslationTextComponent("key.moreboots.teleport").getString(), 34, "key.categories.moreboots");
        ClientRegistry.registerKeyBinding(teleport);
    }
}
