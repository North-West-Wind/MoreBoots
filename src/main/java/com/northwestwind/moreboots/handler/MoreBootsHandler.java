package com.northwestwind.moreboots.handler;

import com.northwestwind.moreboots.Reference;
import com.northwestwind.moreboots.handler.packet.*;
import com.northwestwind.moreboots.init.BlockInit;
import com.northwestwind.moreboots.init.EffectInit;
import com.northwestwind.moreboots.init.ItemInit;
import com.northwestwind.moreboots.init.block.GlowstoneDustBlock;
import com.northwestwind.moreboots.init.block.KeybindInit;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

import java.util.*;

public class MoreBootsHandler {
    private static final Random rng = new Random();

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onPlayerLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
        PlayerEntity player = event.getPlayer();
        ItemStack boots = player.getItemStackFromSlot(EquipmentSlotType.FEET);
        if (boots.getItem().equals(ItemInit.KA_BOOTS))
            MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerKAPacket());
    }

    @SubscribeEvent
    public void onLivingFall(final LivingFallEvent event) {
        LivingEntity entity = event.getEntityLiving();
        float distance = event.getDistance();
        ItemStack boots = entity.getItemStackFromSlot(EquipmentSlotType.FEET);
        if (boots.getItem().equals(ItemInit.SLIME_BOOTS) && !entity.isSneaking()) {
            if (distance < 1.5) return;
            Vector3d motion = entity.getMotion();
            entity.setMotion(motion.getX() * 1.1, Math.sqrt(distance) / 3.0, motion.getZ() * 1.1);
            entity.velocityChanged = true;
            entity.playSound(SoundEvents.BLOCK_SLIME_BLOCK_HIT, 1, 1);
            event.setCanceled(true);
        } else if (boots.getItem().equals(ItemInit.QUARTZ_BOOTS)) {
            if (entity.world.isRemote) return;
            if (distance < 3.0f) return;
            boots.damageItem((int) (4 * distance), entity, playerEntity -> playerEntity.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0f, 1.0f));
        } else if (boots.getItem().equals(ItemInit.SPIDER_BOOTS) || boots.getItem().equals(ItemInit.DOWNWARP_BOOTS)) {
            if (entity.world.isRemote) return;
            event.setCanceled(true);
        } else if (boots.getItem().equals(ItemInit.RAINBOW_SOCKS_BOOTS)) {
            if (entity.world.isRemote) return;
            event.setCanceled(true);
            if (distance > 10) entity.playSound(SoundEvents.ENTITY_PLAYER_BIG_FALL, 1, 1);
            else if (distance > 3) entity.playSound(SoundEvents.ENTITY_PLAYER_SMALL_FALL, 1, 1);
        } else if (boots.getItem().equals(ItemInit.GLASS_BOOTS)) {
            if (entity.world.isRemote) return;
            if (distance < 3.0f) return;
            int damage = (int) (10 * distance);
            if (rng.nextInt(2) == 0) damage = boots.getMaxDamage();
            boots.damageItem(damage, entity, livingEntity -> {
                livingEntity.world.playSound(null, new BlockPos(livingEntity.getPositionVec()), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 1, 1);
                breakGlassBoots(boots, PotionUtils.getPotionFromItem(boots), livingEntity);
            });
        }
    }

    @SubscribeEvent
    public void onLivingJump(final LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemStackFromSlot(EquipmentSlotType.FEET);
        if (boots.getItem().equals(ItemInit.RAINBOW_SOCKS_BOOTS) && !entity.isSneaking()) {
            Vector3d motion = entity.getMotion();
            CompoundNBT tag = boots.getOrCreateTag();
            entity.setMotion(motion.add(0, 0.01 * tag.getLong("tickSneak"), 0));
            tag.putLong("tickSneak", 0);
            boots.setTag(tag);
        }
    }

    @SubscribeEvent
    public void onLivingDamage(final LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemStackFromSlot(EquipmentSlotType.FEET);
        if (boots.getItem().equals(ItemInit.RAINBOW_SOCKS_BOOTS) || boots.getItem().equals(ItemInit.SOCKS_BOOTS)) {
            if (event.getSource().equals(DamageSource.IN_FIRE) || event.getSource().equals(DamageSource.LAVA))
                boots.setDamage(boots.getMaxDamage());
        } else if (boots.getItem().equals(ItemInit.METAL_BOOTS)) {
            if (entity.world.isRemote) return;
            event.setCanceled(true);
            boots.damageItem(1, entity, entity1 -> entity1.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1.0f, 1.0f));
        } else if (boots.getItem().equals(ItemInit.BLAZE_BOOTS)) {
            DamageSource source = event.getSource();
            if (source.equals(DamageSource.IN_FIRE) || source.equals(DamageSource.LAVA) || source.equals(DamageSource.ON_FIRE))
                event.setCanceled(true);
        } else if (boots.getItem().equals(ItemInit.CACTUS_BOOTS)) {
            if (event.getSource().isProjectile()) return;
            float amount = event.getAmount();
            Entity attacker = event.getSource().getTrueSource();
            if (attacker instanceof LivingEntity) attacker.attackEntityFrom(DamageSource.CACTUS, amount / 3.0f);
        } else if (boots.getItem().equals(ItemInit.EXPLOSIVE_BOOTS)) {
            Entity attacker = event.getSource().getTrueSource();
            if (!(event.getSource() instanceof EntityDamageSource) || !(attacker instanceof LivingEntity))
                event.setCanceled(true);
            else {
                entity.world.createExplosion(entity, entity.getPosX(), entity.getPosYHeight(0.0625D), entity.getPosZ(), 4.0F, Explosion.Mode.BREAK);
                List<Entity> collidedEntities = entity.world.getEntitiesInAABBexcluding(entity, new AxisAlignedBB(new BlockPos(entity.getPositionVec())).expand(3, 3, 3), EntityPredicates.NOT_SPECTATING);
                for (Entity collidedEntity : collidedEntities) {
                    if (!(collidedEntity instanceof LivingEntity)) continue;
                    collidedEntity.attackEntityFrom(new DamageSource("explosion"), 40);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerXpChange(final PlayerXpEvent.XpChange event) {
        PlayerEntity player = event.getPlayer();
        ItemStack boots = player.getItemStackFromSlot(EquipmentSlotType.FEET);
        if (boots.getItem().equals(ItemInit.LAPIS_BOOTS)) {
            event.setAmount(event.getAmount() * 2);
        }
    }

    @SubscribeEvent
    public void onLivingEquipmentChange(final LivingEquipmentChangeEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemStackFromSlot(EquipmentSlotType.FEET);
        EquipmentSlotType slot = event.getSlot();
        ItemStack to = event.getTo();
        ItemStack from = event.getFrom();
        ItemStack equipment = entity.getItemStackFromSlot(slot);
        if (boots.getItem().equals(ItemInit.OBSIDIAN_BOOTS) && !slot.equals(EquipmentSlotType.FEET)) {
            int damage = to.getDamage() - from.getDamage();
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
        if (boots.getItem().equals(ItemInit.ICE_BOOTS)) {
            FrostWalkerEnchantment.freezeNearby(entity, entity.world, new BlockPos(entity.getPositionVec()), 2);
            int num = rng.nextInt(100);
            if (num == 0)
                boots.damageItem(1, entity, playerEntity -> playerEntity.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1, 1));
        } else if (boots.getItem().equals(ItemInit.MILK_BOOTS) && !entity.world.isRemote) {
            Collection<EffectInstance> potions = entity.getActivePotionEffects();
            for (EffectInstance effect : potions)
                if (!effect.getPotion().isBeneficial()) {
                    entity.removePotionEffect(effect.getPotion());
                    break;
                }
        } else if (boots.getItem().equals(ItemInit.WATER_BOOTS)) {
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
            } else if (entity.world.getBlockState(blockPos).getBlock().equals(Blocks.FIRE))
                entity.world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
            if (entity.isInWater() && boots.getMaxDamage() - boots.getDamage() > 0 && rng.nextInt(10) == 0) {
                boots.setDamage(Math.max(boots.getDamage() - 2, 0));
            }
        } else if (boots.getItem().equals(ItemInit.LAVA_BOOTS)) {
            Vector3d pos = entity.getPositionVec();
            BlockPos blockPos = new BlockPos(pos);
            BlockPos under = blockPos.down();
            Block block = entity.world.getBlockState(blockPos).getBlock();
            FluidState underneath = entity.world.getFluidState(under);
            if (underneath.getFluid() instanceof WaterFluid) {
                WaterFluid water = (WaterFluid) underneath.getFluid();
                if (water.isSource(underneath))
                    entity.world.setBlockState(under, Blocks.STONE.getDefaultState());
                else entity.world.setBlockState(under, Blocks.COBBLESTONE.getDefaultState());
                entity.playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH, 1, 1);
                boots.damageItem(1, entity, playerEntity -> playerEntity.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
            } else if (block.equals(Blocks.ICE) || block.equals(Blocks.FROSTED_ICE))
                entity.world.setBlockState(blockPos, Blocks.WATER.getDefaultState());
            if (entity.isInLava() && boots.getMaxDamage() - boots.getDamage() > 0 && rng.nextInt(10) == 0) {
                boots.setDamage(Math.max(boots.getDamage() - 2, 0));
            }
        } else if (boots.getItem().equals(ItemInit.UPWARP_BOOTS)) {
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
            }
        } else if (boots.getItem().equals(ItemInit.DOWNWARP_BOOTS)) {
            BlockPos pos = new BlockPos(entity.getPositionVec());
            if (!entity.func_233570_aj_()) {
                boolean isThisAirBlock = entity.world.isAirBlock(pos) || entity.world.getBlockState(pos).getCollisionShape(entity.world, pos).equals(VoxelShapes.empty());
                while (isThisAirBlock && pos.getY() > 0) {
                    pos = pos.down();
                    isThisAirBlock = entity.world.isAirBlock(pos) || entity.world.getBlockState(pos).getCollisionShape(entity.world, pos).equals(VoxelShapes.empty());
                }
                if (pos.getY() <= 0 || entity.getPosY() - pos.up().getY() < 0.2) return;
                entity.setPositionAndUpdate(entity.getPosX(), pos.getY() + 1, entity.getPosZ());
            }
        } else if (boots.getItem().equals(ItemInit.SPIDER_BOOTS)) {
            boolean climable = !Utils.isSurroundedByInvalidBlocks(entity) && !entity.isInLava() && !entity.isInWater() && !entity.isSpectator() && !entity.func_233570_aj_();
            Vector3d motion = entity.getMotion();
            motion = motion.mul(1, 0, 1);
            boolean ascending = entity.collidedHorizontally;
            boolean descending = !ascending && entity.isSneaking();
            if (!ascending && !descending && climable) entity.setMotion(motion.mul(1, 0, 1));
            else if (ascending && climable) entity.setMotion(motion.add(0, 0.2, 0));
            else if (descending && climable) entity.setMotion(motion.subtract(0, 0.2, 0));
            if (climable && rng.nextInt(100) == 0)
                boots.damageItem(1, entity, entity1 -> entity1.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
        } else if (boots.getItem().equals(ItemInit.GLOWSTONE_BOOTS)) {
            Vector3d pos = entity.getPositionVec();
            BlockPos blockPos = new BlockPos(pos);
            BlockPos under = blockPos.down();
            BlockState underneath = entity.world.getBlockState(under);
            if (underneath.isSolid() && entity.world.isAirBlock(blockPos)) {
                entity.world.setBlockState(blockPos, BlockInit.GLOWSTONE_DUST.getDefaultState().with(GlowstoneDustBlock.FACING, GlowstoneDustBlock.getRandomDirection()));
                boots.damageItem(1, entity, playerEntity -> playerEntity.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0f, 1.0f));
            }
        } else if (boots.getItem().equals(ItemInit.MINER_BOOTS)) {
            entity.addPotionEffect(new EffectInstance(Effects.HASTE, 20, 1));
            entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20, 1));
        } else if (boots.getItem().equals(ItemInit.SOCKS_BOOTS)) {
            if (entity.isInWater() && !entity.isSpectator()) {
                if (!tag.getBoolean("wet")) tag.putBoolean("wet", true);
                tag.putLong("wetTick", System.currentTimeMillis());
                boots.setTag(tag);
                boots.setDisplayName(new TranslationTextComponent("item.moreboots.socks_boots_wet"));
            }
            if (tag.getBoolean("wet")) {
                entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 20, 1));
                if (System.currentTimeMillis() - tag.getLong("wetTick") >= 300000) {
                    tag.putBoolean("wet", false);
                    tag.putLong("wetTick", 0);
                    boots.setDisplayName(new TranslationTextComponent("item.moreboots.socks_boots"));
                }
            } else {
                entity.addPotionEffect(new EffectInstance(EffectInit.WARMTH, 205));
            }
            boots.setTag(tag);
        } else if (boots.getItem().equals(ItemInit.RAINBOW_SOCKS_BOOTS)) {
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
                    if (entity instanceof PlayerEntity && !entity.world.isRemote) {
                        MinecraftServer server = entity.world.getServer();
                        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) entity;
                        serverPlayerEntity.getAdvancements().grantCriterion(server.getAdvancementManager().getAdvancement(new ResourceLocation("moreboots", "moreboots/twelve_hours")), "twelve_hours");
                    }
                }
                boots.setTag(tag);
            }
        } else if (boots.getItem().equals(ItemInit.PLUMBER_BOOTS)) {
            List<Entity> collidedEntities = entity.world.getEntitiesInAABBexcluding(entity, entity.getBoundingBox(), EntityPredicates.NOT_SPECTATING);
            Vector3d motion = entity.getMotion();
            boolean stomped = false;
            for (Entity collidedEntity : collidedEntities) {
                if (!(collidedEntity instanceof LivingEntity)) continue;
                if (collidedEntity.getPosY() + collidedEntity.getHeight() < entity.getPosY() || collidedEntity.getPosY() + collidedEntity.getEyeHeight() > entity.getPosY())
                    continue;
                boolean flag = collidedEntity.attackEntityFrom(Reference.STOMP, 4);
                if (!stomped) stomped = flag;
            }
            if (stomped) {
                entity.setMotion(motion.mul(1, 0, 1).add(0, 0.75, 0));
                entity.velocityChanged = true;
            }
        } else if (boots.getItem().equals(ItemInit.BONE_BOOTS) && !entity.getMotion().equals(Vector3d.ZERO)) {
            if (entity.world.isRemote) return;
            BlockPos pos = new BlockPos(entity.getPositionVec());
            Iterator<BlockPos> iterator = BlockPos.getAllInBox(pos.add(5, 5, 5), pos.add(-5, -5, -5)).iterator();
            while (iterator.hasNext()) {
                BlockPos blockPos = iterator.next();
                BlockState state = entity.world.getBlockState(blockPos);
                if (!(state.getBlock() instanceof GrassPathBlock))
                    state.randomTick((ServerWorld) entity.world, blockPos, rng);
            }
        } else if (boots.getItem().equals(ItemInit.MUSHROOM_BOOTS)) {
            Utils.changeGroundBlocks(entity, entity.world, new BlockPos(entity.getPositionVec()).up(), 2, Utils.grass, Utils.air);
            Utils.changeGroundBlocks(entity, entity.world, new BlockPos(entity.getPositionVec()), 2, Utils.target, Utils.to);
        } else if (boots.getItem().equals(ItemInit.BLAZE_BOOTS)) {
            Vector3d pos = entity.getPositionVec();
            BlockPos blockPos = new BlockPos(pos);
            BlockPos under = blockPos.down();
            BlockState underneath = entity.world.getBlockState(under);
            if (underneath.isSolid() && entity.world.isAirBlock(blockPos))
                entity.world.setBlockState(blockPos, Blocks.FIRE.getDefaultState());
        /*} else if(boots.getItem().equals(ItemInit.REDSTONE_BOOTS)) {
            Vector3d pos = entity.getPositionVec();
            BlockPos blockPos = new BlockPos(pos);
            BlockState state = entity.world.getBlockState(blockPos);
            if (state.func_235901_b_(BlockStateProperties.POWER_0_15)) entity.world.setBlockState(blockPos, state.with(BlockStateProperties.POWER_0_15, 15), 3);
            if (state.func_235901_b_(BlockStateProperties.POWERED)) entity.world.setBlockState(blockPos, state.with(BlockStateProperties.POWERED, true), 3);*/
        } else if (boots.getItem().equals(ItemInit.WINDY_BOOTS) && !entity.func_233570_aj_() && !entity.isSneaking() && !entity.isInWater() && !entity.isInLava()) {
            Vector3d motion = entity.getMotion();
            entity.setMotion(motion.mul(1.1, 1, 1.1));
        } else if (boots.getItem().equals(ItemInit.PRISMARINE_BOOTS) && entity.isActualySwimming() && entity.isInWater()) {
            Vector3d motion = entity.getMotion();
            Vector3d direction = entity.getLookVec().scale(0.15);
            entity.setMotion(motion.mul(1.01, 1, 1.01).add(direction));
            if (rng.nextInt(100) == 0)
                boots.damageItem(1, entity, entity1 -> entity1.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
        } else if (boots.getItem().equals(ItemInit.BAT_BOOTS)) {
            Vector3d upper = entity.getPositionVec().add(0, entity.getHeight(), 0);
            boolean climable = !entity.func_233570_aj_() && !entity.world.isAirBlock(new BlockPos(upper)) && !entity.isSneaking();
            Vector3d motion = entity.getMotion();
            if (climable) entity.setMotion(motion.mul(1, 0, 1));
            if (climable && rng.nextInt(100) == 0)
                boots.damageItem(1, entity, entity1 -> entity1.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
        } else if (boots.getItem().equals(ItemInit.GLASS_BOOTS)) {
            Potion potion = PotionUtils.getPotionFromItem(boots);
            for (EffectInstance instance : potion.getEffects()) {
                EffectInstance newEffect = new EffectInstance(instance.getPotion(), 205, instance.getAmplifier());
                entity.addPotionEffect(newEffect);
            }
            if (potion.hasInstantEffect()) {
                ItemStack newBoots = new ItemStack(ItemInit.GLASS_BOOTS_EMPTY);
                newBoots.setDamage(boots.getDamage());
                entity.setItemStackToSlot(EquipmentSlotType.FEET, newBoots);
            }
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (KeybindInit.teleport.isPressed()) {
            PlayerEntity player = Minecraft.getInstance().player;
            if (player == null) return;
            ItemStack boots = player.getItemStackFromSlot(EquipmentSlotType.FEET);
            if (!boots.getItem().equals(ItemInit.ENDER_BOOTS)) return;
            Vector3d pos = player.getPositionVec().add(player.getLookVec().mul(8, 8, 8));
            player.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
            boots.damageItem(1, player, playerEntity -> playerEntity.playSound(SoundEvents.ENTITY_ENDERMAN_DEATH, 1, 1));
            if (player.world.isRemote) MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerEnderTeleportPacket());
        } else if (Minecraft.getInstance().gameSettings.keyBindSneak.isPressed() && event.getAction() == GLFW.GLFW_PRESS) {
            PlayerEntity player = Minecraft.getInstance().player;
            if (player == null) return;
            ItemStack boots = player.getItemStackFromSlot(EquipmentSlotType.FEET);
            if (!boots.getItem().equals(ItemInit.SKATER)) return;
            BlockPos pos = new BlockPos(player.getPositionVec());
            Material material = player.world.getBlockState(pos.down()).getMaterial();
            if (material.equals(Material.ICE) || material.equals(Material.PACKED_ICE)) {
                Vector3d motion = player.getMotion();
                Vector3d direction = player.getLookVec().scale(0.75);
                player.setMotion(motion.mul(0, 1, 0).add(direction.getX(), 0, direction.getZ()));
            }
            if (player.world.isRemote) MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerSkatePacket());
        }
    }

    @SubscribeEvent
    public void onLivingDrop(final LivingDropsEvent event) {
        int looting = event.getLootingLevel();
        int shouldDrop = rng.nextInt((1 + looting) * 2) + looting;
        if (shouldDrop < 1) return;
        LivingEntity entity = event.getEntityLiving();
        if (!entity.getType().equals(EntityType.BAT)) return;
        ItemStack stack = new ItemStack(ItemInit.BAT_HIDE, shouldDrop);
        ItemEntity item = new ItemEntity(entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), stack);
        event.getDrops().add(item);
    }

    private void breakGlassBoots(ItemStack stack, Potion potion, LivingEntity entity) {
        if (entity.world.isRemote) return;
        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ());
        areaeffectcloudentity.setOwner(entity);

        areaeffectcloudentity.setRadius(3.0F);
        areaeffectcloudentity.setRadiusOnUse(-0.5F);
        areaeffectcloudentity.setWaitTime(10);
        areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float)areaeffectcloudentity.getDuration());
        areaeffectcloudentity.setPotion(potion);

        for(EffectInstance effectinstance : PotionUtils.getFullEffectsFromItem(stack)) {
            areaeffectcloudentity.addEffect(new EffectInstance(effectinstance));
        }

        CompoundNBT compoundnbt = stack.getTag();
        if (compoundnbt != null && compoundnbt.contains("CustomPotionColor", 99)) {
            areaeffectcloudentity.setColor(compoundnbt.getInt("CustomPotionColor"));
        }

        entity.world.addEntity(areaeffectcloudentity);
    }
}
