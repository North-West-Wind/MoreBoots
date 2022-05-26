package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.config.MoreBootsConfig;
import ml.northwestwind.moreboots.handler.MoreBootsHandler;
import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.handler.packet.CActivateBootsPacket;
import ml.northwestwind.moreboots.handler.packet.CPlayerDashPacket;
import ml.northwestwind.moreboots.handler.packet.CPlayerMultiJumpPacket;
import ml.northwestwind.moreboots.init.BlockInit;
import ml.northwestwind.moreboots.init.EffectInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import ml.northwestwind.moreboots.mixins.MixinLivingEntityAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

public class BionicBeetleFeetItem extends BootsItem {
    public BionicBeetleFeetItem() {
        super(ItemInit.ModArmorMaterial.BIONIC, "bionic_beetle_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = boots.getOrCreateTag();
        int viscousity = tag.getInt("worn") - tag.getInt("viscousity");
        int weight = tag.getInt("weight");
        int bounciness = tag.getInt("bounciness");
        int ability = tag.getInt("ability");
        if (viscousity == 0) {
            boolean climable = !Utils.isSurroundedByInvalidBlocks(entity) && !entity.isInLava() && !entity.isInWater() && !entity.isSpectator() && !entity.isOnGround();
            Vec3 motion = entity.getDeltaMovement();
            motion = motion.multiply(1, 0, 1);
            boolean ascending = entity.horizontalCollision;
            boolean descending = !ascending && entity.isCrouching();
            if (climable) {
                if (!ascending && !descending) entity.setDeltaMovement(motion);
                else if (ascending) entity.setDeltaMovement(motion.add(0, 0.2, 0));
                else entity.setDeltaMovement(motion.subtract(0, 0.2, 0));
                if (entity.getRandom().nextInt(Math.max(1, boots.getMaxDamage() - boots.getDamageValue())) == 0)
                    boots.hurtAndBreak(1, entity, entity1 -> entity1.playSound(SoundEvents.ITEM_BREAK, 1, 1));
                entity.fallDistance = 0f;
            }
        } else
            entity.addEffect(new MobEffectInstance(EffectInit.SLIPPERINESS.get(), 10, 1, false, false));
        if (bounciness == 0) {
            long tickSneak = tag.getLong("tickSneak");
            tag.putLong("tickSneak", tag.getLong("tickSneak") + 1);
            if (tickSneak < 200) {
                tickSneak += 1;
                tag.putLong("tickSneak", tickSneak);
            }
            if (entity instanceof Player && !entity.level.isClientSide)
                ((Player) entity).displayClientMessage(new TranslatableComponent("message.moreboots.charging_jump", tickSneak), true);
            boots.setTag(tag);
        }
        if (ability == 0) {
            BlockState state = entity.level.getBlockState(entity.blockPosition().below());
            BlockState blockState = entity.level.getBlockState(entity.blockPosition());
            if (blockState.is(BlockInit.VISCOUS_GOO.get()) || !Block.isFaceFull(state.getCollisionShape(entity.level, entity.blockPosition()), Direction.UP) && (blockState.isAir() || !blockState.canBeReplaced(new BlockPlaceContext((Player) entity, InteractionHand.MAIN_HAND, new ItemStack(ItemInit.VISCOUS_GOO.get()), new BlockHitResult(new Vec3(0.5, 1, 0.5), Direction.UP, entity.blockPosition().below(), false))))) return;
            entity.level.setBlockAndUpdate(entity.blockPosition(), BlockInit.VISCOUS_GOO.get().defaultBlockState());
        } else if (ability == 2 && !entity.isCrouching() && entity.getDeltaMovement().y() < -0.05 - weight * 0.5) {
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.05, 0, 1.05).add(0, -0.05 - weight * 0.5, 0));
            entity.hasImpulse = true;
            entity.fallDistance = 0;
        } else if (ability == 3 && (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) || entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() < 1)) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 0, false, false, false));
        } else if (ability == 4 && entity.isVisuallySwimming() && entity.isInWater()) {
            Vec3 motion = entity.getDeltaMovement();
            Vec3 direction = entity.getLookAngle().scale(0.1);
            entity.setDeltaMovement(motion.multiply(1.02, 1, 1.02).add(direction));
        } else if (ability == 5 && entity instanceof Player && entity.getDeltaMovement().y() < 0.04 &&  ((MixinLivingEntityAccessor) entity).isJumping()) {
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.05, 0, 1.05).add(0, 0.04, 0));
            entity.hasImpulse = true;
            entity.fallDistance = 0;
        } else if (ability == 6 && !entity.isSprinting() && (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) || entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() < 1)) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 0, false, false, false));
        } else if (ability == 7) {
            int flutterTicks = tag.getInt("flutter_ticks");
            if (flutterTicks <= 20 && !entity.isOnGround() && entity.getDeltaMovement().y < 0.1 && ((MixinLivingEntityAccessor) entity).isJumping()) {
                entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.01 * flutterTicks, 0));
                entity.hasImpulse = true;
                tag.putInt("flutter_ticks", flutterTicks + 1);
            } else if (flutterTicks > 0 && entity.isOnGround()) tag.putInt("flutter_ticks", 0);
            boots.setTag(tag);
        } else if (ability == 8 && bounciness != 0) {
            long tickSneak = tag.getLong("tickSneak");
            tag.putLong("tickSneak", tag.getLong("tickSneak") + 1);
            if (tickSneak < 200) {
                tickSneak += 1;
                tag.putLong("tickSneak", tickSneak);
            }
            if (entity instanceof Player && !entity.level.isClientSide)
                ((Player) entity).displayClientMessage(new TranslatableComponent("message.moreboots.charging_jump", tickSneak), true);
            boots.setTag(tag);
        }
    }

    @Override
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        CompoundTag tag = entity.getItemBySlot(EquipmentSlot.FEET).getOrCreateTag();
        if (tag.getInt("bounciness") == 2) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.5, 0));
            entity.hasImpulse = true;
        }
    }

    @Override
    public void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        ItemStack from = event.getFrom(), to = event.getTo();
        if (from.getItem().equals(ItemInit.BIONIC_BEETLE_FEET.get()) && !to.getItem().equals(ItemInit.BIONIC_BEETLE_FEET.get())) {
            int aftermath = from.getOrCreateTag().getInt("aftermath");
            if (aftermath == 0) {
                MoreBootsHandler.setAftermath(livingEvent -> {
                    if (!(livingEvent instanceof LivingEvent.LivingUpdateEvent)) return null;
                    LivingEntity entity = livingEvent.getEntityLiving();
                    if (!entity.isSprinting() && (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) || entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() < 1))
                        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 0, false, false, false));
                    return null;
                });
            } else if (aftermath == 1) {
                MoreBootsHandler.setAftermath(livingEvent -> {
                    if (!(livingEvent instanceof LivingEvent.LivingUpdateEvent)) return null;
                    LivingEntity entity = livingEvent.getEntityLiving();
                    ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
                    CompoundTag tag = boots.getOrCreateTag();
                    int flutterTicks = tag.getInt("flutter_ticks");
                    if (flutterTicks <= 20 && !entity.isOnGround() && entity.getDeltaMovement().y < 0.1 && ((MixinLivingEntityAccessor) entity).isJumping()) {
                        entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.01 * flutterTicks, 0));
                        entity.hasImpulse = true;
                        tag.putInt("flutter_ticks", flutterTicks + 1);
                    } else if (flutterTicks > 0 && entity.isOnGround()) tag.putInt("flutter_ticks", 0);
                    boots.setTag(tag);
                    return null;
                });
            } else if (aftermath == 2) {
                MoreBootsHandler.setAftermath(livingEvent -> {
                    if (!(livingEvent instanceof LivingEvent.LivingJumpEvent)) return null;
                    LivingEntity entity = livingEvent.getEntityLiving();
                    entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.5, 0));
                    entity.hasImpulse = true;
                    return null;
                });
            } else if (aftermath == 3) {
                MoreBootsHandler.setAftermath(livingEvent -> {
                    if (!(livingEvent instanceof LivingEvent.LivingUpdateEvent)) return null;
                    LivingEntity entity = livingEvent.getEntityLiving();
                    if (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) || entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() < 1)
                        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 0, false, false, false));
                    return null;
                });
            } else if (aftermath == 4) {
                MoreBootsHandler.setAftermath(livingEvent -> {
                    if (!(livingEvent instanceof LivingEvent.LivingUpdateEvent)) return null;
                    LivingEntity entity = livingEvent.getEntityLiving();
                    ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
                    CompoundTag tag = boots.getOrCreateTag();
                    int weight = tag.getInt("weight");
                    if (!entity.isCrouching() && entity.getDeltaMovement().y() < -0.05 - weight * 0.5) {
                        entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.05, 0, 1.05).add(0, -0.05 - weight * 0.5, 0));
                        entity.hasImpulse = true;
                        entity.fallDistance = 0;
                    }
                    return null;
                });
            } else if (aftermath == 5) {
                MoreBootsHandler.setAftermath(livingEvent -> {
                    if (!(livingEvent instanceof LivingEvent.LivingUpdateEvent)) return null;
                    LivingEntity entity = livingEvent.getEntityLiving();
                    entity.addEffect(new MobEffectInstance(EffectInit.SLIPPERINESS.get(), 10, 1, false, false));
                    return null;
                });
            } else if (aftermath == 6) {
                MoreBootsHandler.setAftermath(livingEvent -> {
                    if (!(livingEvent instanceof LivingEvent.LivingUpdateEvent)) return null;
                    LivingEntity entity = livingEvent.getEntityLiving();
                    ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
                    boolean climable = !Utils.isSurroundedByInvalidBlocks(entity) && !entity.isInLava() && !entity.isInWater() && !entity.isSpectator() && !entity.isOnGround();
                    Vec3 motion = entity.getDeltaMovement();
                    motion = motion.multiply(1, 0, 1);
                    boolean ascending = entity.horizontalCollision;
                    boolean descending = !ascending && entity.isCrouching();
                    if (climable) {
                        if (!ascending && !descending) entity.setDeltaMovement(motion);
                        else if (ascending) entity.setDeltaMovement(motion.add(0, 0.2, 0));
                        else entity.setDeltaMovement(motion.subtract(0, 0.2, 0));
                        if (entity.getRandom().nextInt(Math.max(1, boots.getMaxDamage() - boots.getDamageValue())) == 0)
                            boots.hurtAndBreak(1, entity, entity1 -> entity1.playSound(SoundEvents.ITEM_BREAK, 1, 1));
                        entity.fallDistance = 0f;
                    }
                    return null;
                });
            } else if (aftermath == 7) {
                MoreBootsHandler.setAftermath(livingEvent -> {
                    if (!(livingEvent instanceof LivingEvent.LivingUpdateEvent)) return null;
                    LivingEntity entity = livingEvent.getEntityLiving();
                    ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
                    CompoundTag tag = boots.getOrCreateTag();
                    long tickSneak = tag.getLong("tickSneak");
                    tag.putLong("tickSneak", tag.getLong("tickSneak") + 1);
                    if (tickSneak < 200) {
                        tickSneak += 1;
                        tag.putLong("tickSneak", tickSneak);
                    }
                    if (entity instanceof Player && !entity.level.isClientSide)
                        ((Player) entity).displayClientMessage(new TranslatableComponent("message.moreboots.charging_jump", tickSneak), true);
                    boots.setTag(tag);
                    return null;
                });
            } else if (aftermath == 8) {
                MoreBootsHandler.setAftermath(livingEvent -> {
                    if (livingEvent != null) return null;
                    LocalPlayer player = Minecraft.getInstance().player;
                    if (player == null) return null;
                    Vec3 lookVec = player.getLookAngle().scale(0.1);
                    player.setDeltaMovement(player.getDeltaMovement().add(lookVec));
                    player.hasImpulse = true;
                    MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerDashPacket());
                    return null;
                });
            } else if (aftermath == 9) {
                MoreBootsHandler.setAftermath(livingEvent -> {
                    if (!(livingEvent instanceof LivingEvent.LivingUpdateEvent)) return null;
                    LivingEntity entity = livingEvent.getEntityLiving();
                    if (entity.isVisuallySwimming() && entity.isInWater()) {
                        Vec3 motion = entity.getDeltaMovement();
                        Vec3 direction = entity.getLookAngle().scale(0.1);
                        entity.setDeltaMovement(motion.multiply(1.02, 1, 1.02).add(direction));
                    }
                    return null;
                });
            } else if (aftermath == 10) {
                MoreBootsHandler.setAftermath(null);
            }
        }
    }

    @Override
    public void onJump() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || player.isOnGround() || player.getAbilities().flying || player.getItemBySlot(EquipmentSlot.FEET).getOrCreateTag().getInt("bounciness") != 1) return;
        player.jumpFromGround();
        player.setDeltaMovement(player.getDeltaMovement().add(0, 0.25, 0));
        if (player.level.isClientSide)
            MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerMultiJumpPacket());
    }

    @Override
    public void activateBoots() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = boots.getOrCreateTag();
        int ability = tag.getInt("ability");
        if (ability == 1) {
            boolean newState = !tag.getBoolean("Activated");
            tag.putBoolean("Activated", newState);
            boots.setTag(tag);
            MoreBootsPacketHandler.INSTANCE.sendToServer(new CActivateBootsPacket(newState));
        } else if (ability == 9) {
            Vec3 lookVec = player.getLookAngle().scale(0.1);
            player.setDeltaMovement(player.getDeltaMovement().add(lookVec));
            player.hasImpulse = true;
            MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerDashPacket());
        }
    }

    @Override
    public void getCollisionShape(BlockGetter worldIn, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (!(context instanceof EntityCollisionContext ctx)) return;
        if (!(ctx.getEntity() instanceof LivingEntity entity)) return;
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = boots.getOrCreateTag();
        int ability = tag.getInt("ability");
        if (ability == 1 && tag.getBoolean("Activated")) {
            BlockState state = worldIn.getBlockState(pos);
            if (state.getMaterial().equals(Material.WATER) || state.getMaterial().equals(Material.POWDER_SNOW) || state.getMaterial().equals(Material.LAVA))
                cir.setReturnValue(Shapes.block());
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean forced) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.getBoolean("inited")) return;
        MoreBootsConfig config = MoreBootsConfig.getConfig();
        if (!tag.contains("viscousity")) tag.putInt("viscousity", config.getViscousity());
        if (!tag.contains("bounciness")) tag.putInt("bounciness", config.getBounciness());
        if (!tag.contains("weight")) tag.putInt("weight", config.getWeight());
        if (!tag.contains("worn")) tag.putInt("worn", config.getWorn());
        if (!tag.contains("ability")) tag.putInt("ability", config.getAbility());
        if (!tag.contains("aftermath")) tag.putInt("aftermath", config.getAftermath());
    }
}
