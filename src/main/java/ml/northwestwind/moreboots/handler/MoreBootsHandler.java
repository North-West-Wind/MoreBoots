package ml.northwestwind.moreboots.handler;

import ml.northwestwind.moreboots.MoreBoots;
import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.KeybindInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class MoreBootsHandler {
    private static final Random rng = new Random();

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onPlaySound(event);
    }

    @SubscribeEvent
    public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
        ItemStack boots = event.getPlayer().getItemBySlot(EquipmentSlotType.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onPlayerLeftClick(event);
    }

    @SubscribeEvent
    public static void onLivingFall(final LivingFallEvent event) {
        ItemStack boots = event.getEntityLiving().getItemBySlot(EquipmentSlotType.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onLivingFall(event);
    }

    @SubscribeEvent
    public static void onLivingJump(final LivingEvent.LivingJumpEvent event) {
        ItemStack boots = event.getEntityLiving().getItemBySlot(EquipmentSlotType.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onLivingJump(event);
    }

    @SubscribeEvent
    public static void onLivingDamage(final LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Entity attacker = event.getSource().getEntity();
        ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
        ItemStack atBoots;
        if (attacker instanceof LivingEntity && (atBoots = ((LivingEntity) attacker).getItemBySlot(EquipmentSlotType.FEET)).getItem() instanceof BootsItem) ((BootsItem) atBoots.getItem()).onLivingAttack(event);
        else if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onLivingDamage(event);
    }

    @SubscribeEvent
    public static void onPlayerXpChange(final PlayerXpEvent.XpChange event) {
        ItemStack boots = event.getPlayer().getItemBySlot(EquipmentSlotType.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onPlayerXpChange(event);
    }

    @SubscribeEvent
    public static void onLivingEquipmentChange(final LivingEquipmentChangeEvent event) {
        ItemStack boots = event.getEntityLiving().getItemBySlot(EquipmentSlotType.FEET);
        ItemStack to = event.getTo();
        ItemStack from = event.getFrom();
        if (from.getItem().equals(ItemInit.LOKI_BOOTS) && !from.getItem().equals(to.getItem())) event.getEntityLiving().setInvisible(false);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onLivingEquipmentChange(event);
    }

    @SubscribeEvent
    public static void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        ItemStack boots = event.getEntityLiving().getItemBySlot(EquipmentSlotType.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onLivingUpdate(event);
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
    public static void onLivingDrop(final LivingDropsEvent event) {
        int looting = event.getLootingLevel();
        int shouldDrop = rng.nextInt((1 + looting) * 2) + looting;
        if (shouldDrop < 1) return;
        LivingEntity entity = event.getEntityLiving();
        if (entity.getType().equals(EntityType.BAT)) {
            ItemStack stack = new ItemStack(ItemInit.BAT_HIDE, shouldDrop);
            ItemEntity item = new ItemEntity(entity.level, entity.getX(), entity.getY(), entity.getZ(), stack);
            event.getDrops().add(item);
        } else if (entity.getType().equals(EntityType.STRIDER)) {
            ItemStack stack = new ItemStack(ItemInit.STRIDER_FOOT, shouldDrop);
            ItemEntity item = new ItemEntity(entity.level, entity.getX(), entity.getY(), entity.getZ(), stack);
            event.getDrops().add(item);
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
