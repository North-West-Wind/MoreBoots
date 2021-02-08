package ml.northwestwind.moreboots.init.block;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeybindInit {
    public static KeyBinding teleport, openStorage;

    public static void register() {
        teleport = new KeyBinding(new TranslationTextComponent("key.moreboots.teleport").getString(), GLFW.GLFW_KEY_G, "key.categories.moreboots");
        openStorage = new KeyBinding(new TranslationTextComponent("key.moreboots.openStorage").getString(), GLFW.GLFW_KEY_B, "key.categories.moreboots");
        ClientRegistry.registerKeyBinding(teleport);
        ClientRegistry.registerKeyBinding(openStorage);
    }
}
