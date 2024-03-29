package ml.northwestwind.moreboots.handler;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
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
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class MoreBootsHandler {
    private static final Random rng = new Random();
    private static final ResourceLocation END_CITY_TREASURE_LOOT_TABLE = new ResourceLocation("chests/end_city_treasure");
    private static Function<LivingEvent, Void> aftermathInjection = null;
    private static long aftermathTicks = 0;
    private static boolean aftermathCollision = false;

    @SubscribeEvent
    public static void onPlayerLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
        ItemStack boots = event.getEntity().getItemBySlot(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) {
            ((BootsItem) boots.getItem()).onPlayerLeftClick(event);
            if (aftermathTicks > 0 && aftermathInjection != null) aftermathInjection.apply(event);
        }
    }

    @SubscribeEvent
    public static void onLivingFall(final LivingFallEvent event) {
        ItemStack boots = event.getEntity().getItemBySlot(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) {
            ((BootsItem) boots.getItem()).onLivingFall(event);
            if (aftermathTicks > 0 && aftermathInjection != null) aftermathInjection.apply(event);
        }
    }

    @SubscribeEvent
    public static void onLivingJump(final LivingEvent.LivingJumpEvent event) {
        ItemStack boots = event.getEntity().getItemBySlot(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) {
            ((BootsItem) boots.getItem()).onLivingJump(event);
            if (aftermathTicks > 0 && aftermathInjection != null) aftermathInjection.apply(event);
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(final LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        Entity attacker = event.getSource().getDirectEntity();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        ItemStack atBoots;
        if (attacker instanceof LivingEntity && (atBoots = ((LivingEntity) attacker).getItemBySlot(EquipmentSlot.FEET)).getItem() instanceof BootsItem) {
            ((BootsItem) atBoots.getItem()).onLivingAttack(event);
            if (aftermathTicks > 0 && aftermathInjection != null) aftermathInjection.apply(event);
        }
        if (boots.getItem() instanceof BootsItem) {
            ((BootsItem) boots.getItem()).onLivingHurt(event);
            if (aftermathTicks > 0 && aftermathInjection != null) aftermathInjection.apply(event);
        }
    }

    @SubscribeEvent
    public static void onPlayerXpChange(final PlayerXpEvent.XpChange event) {
        ItemStack boots = event.getEntity().getItemBySlot(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) {
            ((BootsItem) boots.getItem()).onPlayerXpChange(event);
            if (aftermathTicks > 0 && aftermathInjection != null) aftermathInjection.apply(event);
        }
    }

    @SubscribeEvent
    public static void onLivingEquipmentChange(final LivingEquipmentChangeEvent event) {
        ItemStack to = event.getTo();
        ItemStack from = event.getFrom();
        if (event.getSlot().equals(EquipmentSlot.FEET)) {
            if (from.getItem() instanceof BootsItem) {
                ((BootsItem) from.getItem()).onLivingEquipmentChange(event);
                if (aftermathTicks > 0 && aftermathInjection != null) aftermathInjection.apply(event);
            }
            if (to.getItem() instanceof BootsItem) {
                ((BootsItem) to.getItem()).onLivingEquipmentChange(event);
                if (aftermathTicks > 0 && aftermathInjection != null) aftermathInjection.apply(event);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingUpdate(final LivingEvent.LivingTickEvent event) {
        if (aftermathTicks > 0) aftermathTicks--;
        else {
            aftermathInjection = null;
            aftermathCollision = false;
        }
        ItemStack boots = event.getEntity().getItemBySlot(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) {
            ((BootsItem) boots.getItem()).onLivingUpdate(event);
            if (aftermathTicks > 0 && aftermathInjection != null) aftermathInjection.apply(event);
        }
    }

    @SubscribeEvent
    public static void onLivingDrop(final LivingDropsEvent event) {
        int looting = event.getLootingLevel();
        int shouldDrop = rng.nextInt((1 + looting) * 2) + looting;
        if (shouldDrop < 1) return;
        LivingEntity entity = event.getEntity();
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
    public static void onLivingKnockBack(final LivingKnockBackEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        DamageSource source = entity.getLastDamageSource();
        if (source != null && (source.getDirectEntity() instanceof LivingEntity attacker)) {
            ItemStack attBoots = attacker.getItemBySlot(EquipmentSlot.FEET);
            if (attBoots.getItem() instanceof BootsItem item) {
                item.onLivingKnockBack(event);
                if (aftermathTicks > 0 && aftermathInjection != null) aftermathInjection.apply(event);
            }
        }
        if (boots.getItem() instanceof BootsItem item) {
            item.onLivingKnockedBack(event);
            if (aftermathTicks > 0 && aftermathInjection != null) aftermathInjection.apply(event);
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

    public static void setAftermath(Function<LivingEvent, Void> func) {
        aftermathInjection = func;
        aftermathTicks = 300;
    }

    public static void setCollision() {
        aftermathCollision = true;
    }

    public static boolean aftermathCollision() {
        return aftermathTicks > 0 && aftermathCollision;
    }
}
