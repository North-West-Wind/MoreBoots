package com.northwestwind.moreboots.handler;

import com.northwestwind.moreboots.Reference;
import com.northwestwind.moreboots.handler.packet.*;
import com.northwestwind.moreboots.init.BlockInit;
import com.northwestwind.moreboots.init.ItemInit;
import com.northwestwind.moreboots.init.block.GlowstoneDustBlock;
import net.minecraft.block.*;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class MoreBootsHandler {
    private boolean firstTick = true;
    private ArrayList<UUID> fallEventFired;

    public MoreBootsHandler() {
        fallEventFired = new ArrayList<>();
    }

    @SubscribeEvent
    public void onPlayerFall(final LivingFallEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        if (livingEntity.world.isRemote) return;
        if (fallEventFired.contains(livingEntity.getUniqueID())) {
            fallEventFired.remove(livingEntity.getUniqueID());
            event.setCanceled(true);
            return;
        }
        fallEventFired.add(livingEntity.getUniqueID());
        if (livingEntity instanceof PlayerEntity && (((PlayerEntity) livingEntity).isCreative() || livingEntity.isSpectator()))
            return;
        float distance = event.getDistance();
        ItemStack boots = livingEntity.getItemStackFromSlot(EquipmentSlotType.FEET);
        CompoundNBT tag = boots.getOrCreateTag();
        if (boots.getItem().equals(ItemInit.quartz_boots)) {
            if (distance < 3.0f) return;
            boots.damageItem((int) (10 * distance), livingEntity, playerEntity -> playerEntity.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0f, 1.0f));
        } else if (boots.getItem().equals(ItemInit.spider_boots) && tag.getBoolean("climable")) {
            event.setCanceled(true);
            tag.putBoolean("climable", false);
            livingEntity.playSound(SoundEvents.ENTITY_PLAYER_BIG_FALL, 1, 1);
            boots.setTag(tag);
        } else if (boots.getItem().equals(ItemInit.rainbow_socks_boots)) {
            event.setCanceled(true);
            if (distance > 10) livingEntity.playSound(SoundEvents.ENTITY_PLAYER_BIG_FALL, 1, 1);
            else if (distance > 3) livingEntity.playSound(SoundEvents.ENTITY_PLAYER_SMALL_FALL, 1, 1);
        }
    }

    @SubscribeEvent
    public void onPlayerJump(final LivingEvent.LivingJumpEvent event) {
        if (!(event.getEntityLiving() instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        ItemStack boots = player.getItemStackFromSlot(EquipmentSlotType.FEET);
        ResourceLocation registryName = boots.getItem().getRegistryName();
        if (registryName == null) return;
        if (boots.getItem().equals(ItemInit.rainbow_socks_boots)) {
            Vector3d motion = player.getMotion();
            CompoundNBT tag = boots.getOrCreateTag();
            if (!player.isSneaking()) {
                if (player.world.isRemote) MoreBootsPacketHandler.INSTANCE.sendToServer(new CLivingMoonJumpPacket());
                player.setMotion(motion.add(0, 0.01 * tag.getLong("tickSneak"), 0));
                tag.putLong("tickSneak", 0);
                boots.setTag(tag);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerHurt(final LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.world.isRemote) return;
        ItemStack boots = entity.getItemStackFromSlot(EquipmentSlotType.FEET);
        if (boots.getItem().equals(ItemInit.rainbow_socks_boots) || boots.getItem().equals(ItemInit.socks_boots)) {
            if (event.getSource().equals(DamageSource.IN_FIRE) || event.getSource().equals(DamageSource.LAVA))
                boots.setDamage(boots.getMaxDamage());
        } else if (boots.getItem().equals(ItemInit.metal_boots)) {
            event.setCanceled(true);
            boots.damageItem(1, entity, entity1 -> entity1.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1.0f, 1.0f));
        }
    }

    @SubscribeEvent
    public void onPlayerXpChange(final PlayerXpEvent.XpChange event) {
        PlayerEntity player = event.getPlayer();
        if (player.world.isRemote) return;
        ItemStack boots = player.getItemStackFromSlot(EquipmentSlotType.FEET);
        if (boots.getItem().equals(ItemInit.lapis_boots)) {
            event.setAmount(event.getAmount() * 2);
        }
    }

    @SubscribeEvent
    public void onLivingEquipmentChange(final LivingEquipmentChangeEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.world.isRemote) return;
        ItemStack boots = entity.getItemStackFromSlot(EquipmentSlotType.FEET);
        if (boots.getItem().equals(ItemInit.obsidian_boots)) {
            EquipmentSlotType slot = event.getSlot();
            if (slot.equals(EquipmentSlotType.FEET)) return;
            ItemStack to = event.getTo();
            ItemStack from = event.getFrom();
            ItemStack equipment = entity.getItemStackFromSlot(slot);
            int damage = from.getDamage() - to.getDamage();
            int maxRepair = boots.getMaxDamage() - boots.getDamage();
            if (maxRepair > damage) equipment.setDamage(0);
            else equipment.setDamage(equipment.getDamage() - maxRepair);
            boots.damageItem(damage, entity, entity1 -> entity1.playSound(SoundEvents.BLOCK_ANVIL_BREAK, 1, 1));
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemStackFromSlot(EquipmentSlotType.FEET);
        CompoundNBT tag = boots.getOrCreateTag();
        if (boots.getItem().equals(ItemInit.ice_boots)) {
            FrostWalkerEnchantment.freezeNearby(entity, entity.world, new BlockPos(entity.getPositionVec()), 2);
            boots.damageItem(1, entity, playerEntity -> playerEntity.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1, 1));
            if (entity.world.isRemote)
                MoreBootsPacketHandler.INSTANCE.sendToServer(new CLivingFreezeWaterPacket(new BlockPos(entity.getPositionVec()), entity));
        } else if (boots.getItem().equals(ItemInit.milk_boots)) {
            Field[] fields = Effects.class.getDeclaredFields();
            for (Field f : fields) {
                Class<?> cl = f.getType();
                if (cl.equals(Effects.class) && Modifier.isStatic(f.getModifiers())) {
                    try {
                        Effect effect = (Effect) f.get(null);
                        if (!effect.isBeneficial()) event.getEntityLiving().removePotionEffect(effect);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (entity.world.isRemote)
                MoreBootsPacketHandler.INSTANCE.sendToServer(new CRemoveNegativeEffectsPacket(entity));
        } else if (boots.getItem().equals(ItemInit.water_boots)) {
            Vector3d pos = entity.getPositionVec();
            BlockPos blockPos = new BlockPos(pos);
            BlockPos under = blockPos.down();
            FluidState underneath = entity.world.getFluidState(under);
            BlockState underneathBlock = entity.world.getBlockState(under);
            if (underneath.getFluid() instanceof LavaFluid && !(underneathBlock.getBlock() instanceof IWaterLoggable)) {
                LavaFluid lava = (LavaFluid) underneath.getFluid();
                if (lava.isSource(underneath))
                    entity.world.setBlockState(under, Blocks.OBSIDIAN.getDefaultState());
                else entity.world.setBlockState(under, Blocks.COBBLESTONE.getDefaultState());
                entity.playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH, 1, 1);
                boots.damageItem(1, entity, playerEntity -> playerEntity.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
                if (entity.world.isRemote)
                    MoreBootsPacketHandler.INSTANCE.sendToServer(new CLivingFreezeLavaPacket(under, entity));
            }
            if (entity.isInWater() && boots.getMaxDamage() - boots.getDamage() > 0) {
                boots.setDamage(Math.max(boots.getDamage() - 2, 0));
                if (entity.world.isRemote) MoreBootsPacketHandler.INSTANCE.sendToServer(new CLivingRepairBootsPacket());
            }
        } else if (boots.getItem().equals(ItemInit.lava_boots)) {
            Vector3d pos = entity.getPositionVec();
            BlockPos blockPos = new BlockPos(pos);
            BlockPos under = blockPos.down();
            FluidState underneath = entity.world.getFluidState(under);
            if (underneath.getFluid() instanceof WaterFluid) {
                WaterFluid water = (WaterFluid) underneath.getFluid();
                if (water.isSource(underneath))
                    entity.world.setBlockState(under, Blocks.STONE.getDefaultState());
                else entity.world.setBlockState(under, Blocks.COBBLESTONE.getDefaultState());
                entity.playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH, 1, 1);
                boots.damageItem(1, entity, playerEntity -> playerEntity.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
                if (entity.world.isRemote)
                    MoreBootsPacketHandler.INSTANCE.sendToServer(new CLivingSolidifyWaterPacket(under, entity));
            }
            if (entity.isInLava() && boots.getMaxDamage() - boots.getDamage() > 0) {
                boots.setDamage(Math.max(boots.getDamage() - 2, 0));
                if (entity.world.isRemote) MoreBootsPacketHandler.INSTANCE.sendToServer(new CLivingRepairBootsPacket());
            }
        } else if (boots.getItem().equals(ItemInit.upwarp_boots)) {
            BlockPos pos = new BlockPos(entity.getPositionVec());
            BlockPos original = pos;
            if (!entity.func_233570_aj_()) {
                boolean isThisAirBlock = entity.world.isAirBlock(pos) || entity.world.getBlockState(pos).getCollisionShape(entity.world, pos).equals(VoxelShapes.empty());
                boolean isLastAirBlock = isThisAirBlock;
                while ((isLastAirBlock || !isThisAirBlock) && pos.getY() < 256) {
                    isLastAirBlock = isThisAirBlock;
                    pos = pos.up();
                    isThisAirBlock = entity.world.isAirBlock(pos) || entity.world.getBlockState(pos).getCollisionShape(entity.world, pos).equals(VoxelShapes.empty());
                }
                if (pos.getY() >= 255 || original.equals(pos.down())) return;
                entity.setPositionAndUpdate(entity.getPosX(), pos.getY(), entity.getPosZ());
                if (entity.world.isRemote)
                    MoreBootsPacketHandler.INSTANCE.sendToServer(new CLivingWarpPacket(new BlockPos(entity.getPosX(), pos.getY(), entity.getPosZ()), entity));
            }
        } else if (boots.getItem().equals(ItemInit.downwarp_boots)) {
            BlockPos pos = new BlockPos(entity.getPositionVec());
            BlockPos original = pos;
            if (!entity.func_233570_aj_()) {
                boolean isThisAirBlock = entity.world.isAirBlock(pos) || entity.world.getBlockState(pos).getCollisionShape(entity.world, pos).equals(VoxelShapes.empty());
                while (isThisAirBlock && pos.getY() > 0) {
                    pos = pos.down();
                    isThisAirBlock = entity.world.isAirBlock(pos) || entity.world.getBlockState(pos).getCollisionShape(entity.world, pos).equals(VoxelShapes.empty());
                }
                if (pos.getY() <= 0 || original.equals(pos.up())) return;
                entity.setPositionAndUpdate(entity.getPosX(), pos.getY() + 1, entity.getPosZ());
                if (entity.world.isRemote)
                    MoreBootsPacketHandler.INSTANCE.sendToServer(new CLivingWarpPacket(new BlockPos(entity.getPosX(), pos.getY() + 1, entity.getPosZ()), entity));
            }
        } else if (boots.getItem().equals(ItemInit.spider_boots)) {
            if (isSurroundedByInvalidBlocks(entity) || entity.isInLava() || entity.isInWater() || entity.isSpectator()) {
                tag.putBoolean("climable", false);
                boots.setTag(tag);
            } else if (entity.collidedHorizontally) {
                tag.putBoolean("climable", true);
                boots.setTag(tag);
            }
            boolean climable = tag.getBoolean("climable");
            Vector3d motion = entity.getMotion();
            motion.mul(1, 0, 1);
            boolean ascending = entity.collidedHorizontally;
            boolean descending = !ascending && !entity.isSneaking();
            if (!ascending && !descending && climable) entity.setMotion(motion.mul(1, 0, 1));
            else if (ascending && climable) entity.setMotion(motion.add(0, 0.1, 0));
            else if (descending && climable) entity.setMotion(motion.subtract(0, 0.1, 0));
            if (entity.world.isRemote) MoreBootsPacketHandler.INSTANCE.sendToServer(new CLivingClimbPacket(entity));
        } else if (boots.getItem().equals(ItemInit.glowstone_boots)) {
            Vector3d pos = entity.getPositionVec();
            BlockPos blockPos = new BlockPos(pos);
            BlockPos under = blockPos.down();
            BlockState underneath = entity.world.getBlockState(under);
            if (underneath.isSolid() && entity.world.isAirBlock(blockPos)) {
                entity.world.setBlockState(blockPos, BlockInit.glowstone_dust.getDefaultState().with(GlowstoneDustBlock.FACING, GlowstoneDustBlock.getRandomDirection()));
                boots.damageItem(1, entity, playerEntity -> playerEntity.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0f, 1.0f));
            }
            if (entity.world.isRemote)
                MoreBootsPacketHandler.INSTANCE.sendToServer(new CLivingIlluminateSurroundingsPacket(blockPos, entity));
        } else if (boots.getItem().equals(ItemInit.miner_boots)) {
            entity.addPotionEffect(new EffectInstance(Effects.HASTE, 1, 1));
            if (entity.world.isRemote)
                MoreBootsPacketHandler.INSTANCE.sendToServer(new CLivingHastePacket(entity));
        } else if (boots.getItem().equals(ItemInit.socks_boots)) {
            if (entity.isInWater() && !entity.isSpectator()) {
                if (!tag.getBoolean("wet")) tag.putBoolean("wet", true);
                tag.putLong("wetTick", 1);
                boots.setTag(tag);
                boots.setDisplayName(new TranslationTextComponent("item.moreboots.socks_boots_wet"));
            }
            if (tag.getBoolean("wet")) entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 1, 1));
            else {
                Vector3d pos = entity.getPositionVec();
                Biome biome = entity.world.getBiome(new BlockPos(pos));
                float temperature = biome.getTemperature(new BlockPos(pos));
                if (temperature < 0.2) entity.addPotionEffect(new EffectInstance(Effects.SPEED, 1));
                else if (temperature > 1) entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 1));
            }
            if (tag.getLong("wetTick") >= 6000) {
                tag.putBoolean("wet", false);
                tag.putLong("wetTick", 0);
                boots.setDisplayName(new TranslationTextComponent("item.moreboots.socks_boots"));
            }
            boots.setTag(tag);
            if (entity.world.isRemote) MoreBootsPacketHandler.INSTANCE.sendToServer(new CLivingSocksPacket(entity));
        } else if (boots.getItem().equals(ItemInit.rainbow_socks_boots)) {
            if (entity.isSneaking() && entity.func_233570_aj_()) {
                long tickSneak = tag.getLong("tickSneak");
                tag.putLong("tickSneak", tag.getLong("tickSneak") + 1);
                tickSneak += 1;
                if (entity instanceof PlayerEntity && !entity.world.isRemote)
                    ((PlayerEntity) entity).sendStatusMessage(new TranslationTextComponent("message.moreboots.building_speed", tickSneak), true);
                if (tickSneak >= 864000 && !entity.isSpectator()) {
                    Vector3d pos = entity.getPositionVec();
                    tag.putLong("tickSneak", 0);
                    boots.setDamage(boots.getMaxDamage());
                    entity.world.createExplosion(entity, pos.x, entity.getPosYHeight(-0.0625D), pos.z, 10.0F, Explosion.Mode.BREAK);
                    entity.setMotion(entity.getMotion().add(0, 0.01 * 864000, 0));
                    if (entity instanceof PlayerEntity) {
                        MinecraftServer server = entity.world.getServer();
                        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) entity;
                        serverPlayerEntity.getAdvancements().grantCriterion(server.getAdvancementManager().getAdvancement(new ResourceLocation("moreboots", "moreboots/twelve_hours")), "twelve_hours");
                    }
                }
                boots.setTag(tag);
                if (entity.world.isRemote) MoreBootsPacketHandler.INSTANCE.sendToServer(new CLivingRainbowSocksPacket(entity));
            }
        } else if (boots.getItem().equals(ItemInit.plumber_boots) && !entity.func_233570_aj_()) {
            List<Entity> collidedEntities = entity.world.getEntitiesInAABBexcluding(entity, entity.getBoundingBox(), EntityPredicates.NOT_SPECTATING);
            boolean stomped = false;
            for (Entity collidedEntity : collidedEntities) {
                if (!(collidedEntity instanceof LivingEntity)) continue;
                if (collidedEntity.getPosY() + collidedEntity.getHeight() < entity.getPosY() || collidedEntity.getPosY() + collidedEntity.getEyeHeight() > entity.getPosY())
                    continue;
                boolean flag = collidedEntity.attackEntityFrom(Reference.STOMP, 4);
                if (!stomped) stomped = flag;
            }
            Vector3d original = entity.getMotion();
            if (stomped) {
                entity.setMotion(original.add(0, 1, 0));
            }
            if(entity.world.isRemote) MoreBootsPacketHandler.INSTANCE.sendToServer(new CLivingStompPacket(entity));
        }
    }

    @SubscribeEvent
    public void onLivingKnockback(final LivingKnockBackEvent event) {
        LivingEntity attacker = event.getEntityLiving().getLastAttackedEntity();
        if (attacker == null) return;
        if (attacker.getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemInit.baseball_boots)) {
            event.setStrength(event.getStrength() * 69);
            event.setRatioX(event.getRatioX() * 2);
            event.setRatioZ(event.getRatioZ() * 2);
        }
    }

    public static boolean isSurroundedByInvalidBlocks(LivingEntity player) {
        BlockPos pos = new BlockPos(player.getPositionVec());
        BlockPos pos1 = pos.add(1, 0, 1);
        BlockPos pos2 = pos.add(-1, 0, -1);
        Iterator<BlockPos> iterator = BlockPos.getAllInBox(pos1, pos2).iterator();
        while (iterator.hasNext()) {
            BlockPos blockPos = iterator.next();
            Block block = player.world.getBlockState(blockPos).getBlock();
            Fluid fluid = player.world.getFluidState(blockPos).getFluid();
            if (!player.world.isAirBlock(blockPos) && !(fluid instanceof FlowingFluid) && !block.getCollisionShape(player.world.getBlockState(blockPos), player.world, blockPos, ISelectionContext.dummy()).equals(VoxelShapes.empty()))
                return false;
        }
        return true;
    }
}
