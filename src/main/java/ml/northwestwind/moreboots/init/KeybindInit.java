package ml.northwestwind.moreboots.init;

import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class KeybindInit {
    public static KeyMapping activate;

    public static void register() {
        activate = new KeyMapping(new TranslatableComponent("key.moreboots.activate").getString(), GLFW.GLFW_KEY_B, "key.categories.moreboots");
        ClientRegistry.registerKeyBinding(activate);
    }
}
