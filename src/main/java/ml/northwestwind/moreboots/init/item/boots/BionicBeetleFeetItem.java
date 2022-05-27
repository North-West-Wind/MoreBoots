package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.config.MoreBootsConfig;
import ml.northwestwind.moreboots.handler.MoreBootsHandler;
import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.Utils;
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

import java.util.Arrays;
import java.util.stream.IntStream;

/*
Mutually exclusives:
Abi:
lightweight/fins
levitate/flutter

Aft:
slippery/sticky
lightweight/fins
bouncy/jump boost
 */
public class BionicBeetleFeetItem extends BootsItem {
    public BionicBeetleFeetItem() {
        super(ItemInit.ModArmorMaterial.BIONIC, "bionic_beetle_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = boots.getOrCreateTag();
        boolean switched = tag.getBoolean("switched");
        int viscousity = tag.getInt("worn") - tag.getInt("viscousity");
        int weight = tag.getInt("weight");
        int bounciness = tag.getInt("bounciness");
        boolean[] abilities = new boolean[10];
        for (int ii = 0; ii < 10; ii++) {
            int finali = ii;
            abilities[ii] = Arrays.stream(tag.getIntArray("abilities")).anyMatch(jj -> jj == finali);
        }
        if (viscousity == 0) sticky(entity, boots);
        else slippery(entity);
        if (bounciness == 0) buildSpeed(entity, boots, tag);
        if (abilities[0]) trailing(entity);
        if (abilities[2]) slowFalling(entity, weight);
        if (abilities[3]) speed(entity);
        if (abilities[4] && (!abilities[1] || switched)) fastSwim(entity);
        if (abilities[5] && (!abilities[7] || !switched)) levitate(entity);
        if (abilities[6]) wiggly(entity);
        if (abilities[7] && (!abilities[5] || switched)) flutter(entity, boots, tag);
        if (abilities[8]) buildSpeed(entity, boots, tag);
    }

    @Override
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        CompoundTag tag = entity.getItemBySlot(EquipmentSlot.FEET).getOrCreateTag();
        int bounciness = tag.getInt("bounciness");
        if (bounciness == 0) launchSpeed(entity);
        else if (bounciness == 2) jumpBoost(entity);
    }

    @Override
    public void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        ItemStack from = event.getFrom(), to = event.getTo();
        if (from.getItem().equals(ItemInit.BIONIC_BEETLE_FEET.get()) && !to.getItem().equals(ItemInit.BIONIC_BEETLE_FEET.get())) {
            boolean[] aftermaths = new boolean[11];
            for (int ii = 0; ii < 10; ii++) {
                int finali = ii;
                aftermaths[ii] = Arrays.stream(from.getOrCreateTag().getIntArray("aftermaths")).anyMatch(jj -> jj == finali);
            }
            if (aftermaths[10] && (!aftermaths[9] || from.getOrCreateTag().getBoolean("switched"))) MoreBootsHandler.setCollision();
            MoreBootsHandler.setAftermath(livingEvent -> {
                boolean isUpdate = livingEvent instanceof LivingEvent.LivingUpdateEvent;
                boolean isJump = livingEvent instanceof LivingEvent.LivingJumpEvent;
                LivingEntity entity = null;
                ItemStack boots = null;
                CompoundTag tag = null;
                if (livingEvent != null) {
                    entity = livingEvent.getEntityLiving();
                    boots = from;
                    tag = boots.getOrCreateTag();
                }
                boolean switched = tag == null ? from.getOrCreateTag().getBoolean("switched") : tag.getBoolean("switched");
                int weight = tag == null ? from.getOrCreateTag().getInt("weight") : tag.getInt("weight");

                if (aftermaths[0] && isUpdate) wiggly(livingEvent.getEntityLiving());
                if (aftermaths[1] && isUpdate) flutter(entity, boots, tag);
                if (aftermaths[2] && isJump && (!aftermaths[7] || !switched)) jumpBoost(entity);
                if (aftermaths[3] && isUpdate) speed(entity);
                if (aftermaths[4] && isUpdate) slowFalling(entity, weight);
                if (aftermaths[5] && isUpdate && (!aftermaths[6] || !switched)) slippery(entity);
                if (aftermaths[6] && isUpdate && (!aftermaths[5] || switched)) sticky(entity, boots);
                if (aftermaths[7] && (isUpdate || isJump) && (!aftermaths[2] || switched)) {
                    if (isUpdate) buildSpeed(entity, boots, tag);
                    else launchSpeed(entity);
                }
                if (aftermaths[8] && livingEvent == null) dash();
                if (aftermaths[9] && isUpdate && (!aftermaths[10] || !switched)) fastSwim(entity);
                return null;
            });
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
        IntStream abilities = Arrays.stream(tag.getIntArray("ability"));
        if (abilities.anyMatch(ii -> ii == 9)) dash();
    }

    @Override
    public void getCollisionShape(BlockGetter worldIn, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (!(context instanceof EntityCollisionContext ctx)) return;
        if (!(ctx.getEntity() instanceof LivingEntity entity)) return;
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = boots.getOrCreateTag();
        boolean switched = tag.getBoolean("switched");
        IntStream abilities = Arrays.stream(tag.getIntArray("ability"));
        if (abilities.anyMatch(ii -> ii == 1) && (abilities.noneMatch(ii -> ii == 4) || !switched)) jesus(worldIn, pos, cir);
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
        if (!tag.contains("abilities")) tag.putIntArray("abilities", config.getAbilities());
        if (!tag.contains("aftermaths")) tag.putIntArray("aftermaths", config.getAftermaths());
    }

    private void trailing(LivingEntity entity) {
        BlockState state = entity.level.getBlockState(entity.blockPosition().below());
        BlockState blockState = entity.level.getBlockState(entity.blockPosition());
        if (blockState.is(BlockInit.VISCOUS_GOO.get()) || !Block.isFaceFull(state.getCollisionShape(entity.level, entity.blockPosition()), Direction.UP) && (blockState.isAir() || !blockState.canBeReplaced(new BlockPlaceContext((Player) entity, InteractionHand.MAIN_HAND, new ItemStack(ItemInit.VISCOUS_GOO.get()), new BlockHitResult(new Vec3(0.5, 1, 0.5), Direction.UP, entity.blockPosition().below(), false))))) return;
        entity.level.setBlockAndUpdate(entity.blockPosition(), BlockInit.VISCOUS_GOO.get().defaultBlockState());
    }

    private void jesus(BlockGetter worldIn, BlockPos pos, CallbackInfoReturnable<VoxelShape> cir) {
        BlockState state = worldIn.getBlockState(pos);
        if (state.getMaterial().equals(Material.WATER) || state.getMaterial().equals(Material.POWDER_SNOW) || state.getMaterial().equals(Material.LAVA))
            cir.setReturnValue(Shapes.block());
    }

    private void slowFalling(LivingEntity entity, int weight) {
        if (!entity.isCrouching() && entity.getDeltaMovement().y() < -0.05 - weight * 0.5) {
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.05, 0, 1.05).add(0, -0.05 - weight * 0.5, 0));
            entity.hasImpulse = true;
            entity.fallDistance = 0;
        }
    }

    private void speed(LivingEntity entity) {
        if (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) || entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() < 1)
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 0, false, false, false));
    }

    private void fastSwim(LivingEntity entity) {
        if (entity.isVisuallySwimming() && entity.isInWater()) {
            Vec3 motion = entity.getDeltaMovement();
            Vec3 direction = entity.getLookAngle().scale(0.1);
            entity.setDeltaMovement(motion.multiply(1.02, 1, 1.02).add(direction));
        }
    }

    private void levitate(LivingEntity entity) {
        if (entity instanceof Player && entity.getDeltaMovement().y() < 0.04 &&  ((MixinLivingEntityAccessor) entity).isJumping()) {
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.05, 0, 1.05).add(0, 0.04, 0));
            entity.hasImpulse = true;
            entity.fallDistance = 0;
        }
    }

    private void wiggly(LivingEntity entity) {
        if (!entity.isSprinting() && (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) || entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() < 2)) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 1, false, false, false));
        }
    }

    private void flutter(LivingEntity entity, ItemStack boots, CompoundTag tag) {
        int flutterTicks = tag.getInt("flutter_ticks");
        if (flutterTicks <= 20 && !entity.isOnGround() && entity.getDeltaMovement().y < 0.1 && ((MixinLivingEntityAccessor) entity).isJumping()) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.01 * flutterTicks, 0));
            entity.hasImpulse = true;
            tag.putInt("flutter_ticks", flutterTicks + 1);
        } else if (flutterTicks > 0 && entity.isOnGround()) tag.putInt("flutter_ticks", 0);
        boots.setTag(tag);
    }

    private void buildSpeed(LivingEntity entity, ItemStack boots, CompoundTag tag) {
        if (tag.getInt("bounciness") != 0) {
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

    private void launchSpeed(LivingEntity entity) {
        if (entity.isCrouching()) return;
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        Vec3 motion = entity.getDeltaMovement();
        CompoundTag tag = boots.getOrCreateTag();
        entity.setDeltaMovement(motion.add(0, 0.005 * tag.getLong("tickSneak"), 0));
        tag.putLong("tickSneak", 0);
        boots.setTag(tag);
    }

    private void dash() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        Vec3 lookVec = player.getLookAngle().scale(0.1);
        player.setDeltaMovement(player.getDeltaMovement().add(lookVec));
        player.hasImpulse = true;
        MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerDashPacket());
    }

    private void sticky(LivingEntity entity, ItemStack boots) {
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
    }

    private void slippery(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(EffectInit.SLIPPERINESS.get(), 10, 1, false, false));
    }

    private void jumpBoost(LivingEntity entity) {
        entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.5, 0));
        entity.hasImpulse = true;
    }
}
