package ml.northwestwind.moreboots.init;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeybindInit {
    public static KeyBinding activate;

    public static void register() {
        activate = new KeyBinding(new TranslationTextComponent("key.moreboots.activate").getString(), GLFW.GLFW_KEY_B, "key.categories.moreboots");
        ClientRegistry.registerKeyBinding(activate);
    }
}
