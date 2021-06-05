package ml.northwestwind.moreboots.handler;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.init.KeybindInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT)
public class MoreBootsClientHandler {
    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onPlaySound(event);
    }

    @SubscribeEvent
    public static void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (Minecraft.getInstance().options.keyShift.consumeClick() && event.getAction() == GLFW.GLFW_PRESS) {
            PlayerEntity player = Minecraft.getInstance().player;
            if (player == null) return;
            ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
            if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onShift();
        } else if (Minecraft.getInstance().options.keyJump.consumeClick() && event.getAction() == GLFW.GLFW_PRESS) {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            if (player == null) return;
            ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
            if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onJump();
        } else if (KeybindInit.activate.consumeClick() && event.getAction() == GLFW.GLFW_PRESS) {
            Minecraft minecraft = Minecraft.getInstance();
            ClientPlayerEntity player = minecraft.player;
            if (player == null) return;
            ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
            if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).activateBoots();
        }
    }

    @SubscribeEvent
    public static void preRenderLiving(final RenderLivingEvent.Pre<?, ?> event) {
        ItemStack boots = event.getEntity().getItemBySlot(EquipmentSlotType.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).preRenderLiving(event);
    }

    @SubscribeEvent
    public static void renderNameplate(final RenderNameplateEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;
        ItemStack boots = ((LivingEntity) event.getEntity()).getItemBySlot(EquipmentSlotType.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).renderNameplate(event);
    }
}
