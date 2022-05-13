package ml.northwestwind.moreboots.handler;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class MoreBootsHandler {
    private static final Random rng = new Random();
    private static final ResourceLocation END_CITY_TREASURE_LOOT_TABLE = new ResourceLocation("chests/end_city_treasure");

    @SubscribeEvent
    public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
        ItemStack boots = event.getPlayer().getItemBySlot(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onPlayerLeftClick(event);
    }

    @SubscribeEvent
    public static void onLivingFall(final LivingFallEvent event) {
        ItemStack boots = event.getEntityLiving().getItemBySlot(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onLivingFall(event);
    }

    @SubscribeEvent
    public static void onLivingJump(final LivingEvent.LivingJumpEvent event) {
        ItemStack boots = event.getEntityLiving().getItemBySlot(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onLivingJump(event);
    }

    @SubscribeEvent
    public static void onLivingHurt(final LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Entity attacker = event.getSource().getEntity();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        ItemStack atBoots;
        if (attacker instanceof LivingEntity && (atBoots = ((LivingEntity) attacker).getItemBySlot(EquipmentSlot.FEET)).getItem() instanceof BootsItem) ((BootsItem) atBoots.getItem()).onLivingAttack(event);
        else if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onLivingHurt(event);
    }

    @SubscribeEvent
    public static void onPlayerXpChange(final PlayerXpEvent.XpChange event) {
        ItemStack boots = event.getPlayer().getItemBySlot(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onPlayerXpChange(event);
    }

    @SubscribeEvent
    public static void onLivingEquipmentChange(final LivingEquipmentChangeEvent event) {
        ItemStack to = event.getTo();
        ItemStack from = event.getFrom();
        if (from.getItem().equals(ItemInit.LOKI_BOOTS.get()) && !from.getItem().equals(to.getItem())) event.getEntityLiving().setInvisible(false);
        else {
            if (from.getItem() instanceof BootsItem) ((BootsItem) from.getItem()).onLivingEquipmentChange(event);
            if (to.getItem() instanceof BootsItem) ((BootsItem) to.getItem()).onLivingEquipmentChange(event);
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        ItemStack boots = event.getEntityLiving().getItemBySlot(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).onLivingUpdate(event);
    }

    @SubscribeEvent
    public static void onLivingDrop(final LivingDropsEvent event) {
        int looting = event.getLootingLevel();
        int shouldDrop = rng.nextInt((1 + looting) * 2) + looting;
        if (shouldDrop < 1) return;
        LivingEntity entity = event.getEntityLiving();
        if (entity.getType().equals(EntityType.BAT)) {
            ItemStack stack = new ItemStack(ItemInit.BAT_HIDE.get(), shouldDrop);
            ItemEntity item = new ItemEntity(entity.level, entity.getX(), entity.getY(), entity.getZ(), stack);
            event.getDrops().add(item);
        } else if (entity.getType().equals(EntityType.STRIDER)) {
            ItemStack stack = new ItemStack(ItemInit.STRIDER_FOOT.get(), shouldDrop);
            ItemEntity item = new ItemEntity(entity.level, entity.getX(), entity.getY(), entity.getZ(), stack);
            event.getDrops().add(item);
        }
    }

    @SubscribeEvent
    public static void onLootTableLoad(final LootTableLoadEvent event) {
        if (event.getName().getPath().startsWith("chests")) {
            event.getTable().addPool(
                    LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(0.1f))
                            .add(LootItem.lootTableItem(ItemInit.SUPER_AVIAN_FEET.get()))
                            .build()
            );
        }
        if (END_CITY_TREASURE_LOOT_TABLE.equals(event.getName())) {
            event.getTable().addPool(
                    LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(0.01f))
                            .add(LootItem.lootTableItem(ItemInit.FLOATING_CORE.get()))
                            .add(LootItem.lootTableItem(ItemInit.HEROIC_CORE.get()))
                            .build()
            );
            event.getTable().addPool(
                    LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(0.00027f))
                            .add(LootItem.lootTableItem(ItemInit.BIONIC_CORE.get()))
                            .build()
            );
        }
    }
}
