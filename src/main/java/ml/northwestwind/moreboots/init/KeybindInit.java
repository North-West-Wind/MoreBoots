package ml.northwestwind.moreboots.init;

import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class KeybindInit {
    public static KeyMapping activate;

    public static void register(RegisterKeyMappingsEvent event) {
        activate = new KeyMapping(MutableComponent.create(new TranslatableContents("key.moreboots.activate")).getString(), GLFW.GLFW_KEY_B, "key.categories.moreboots");
        event.register(activate);
    }
}
