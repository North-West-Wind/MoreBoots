package ml.northwestwind.moreboots.init.item.boots;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.BlockInit;
import ml.northwestwind.moreboots.init.EffectInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;

public class SuperAvianFeetItem extends BootsItem {
    public SuperAvianFeetItem() {
        super(ItemInit.ModArmorMaterial.SUPER_AVIAN, "super_avian_feet");
    }

    @Override
    public void onLivingFall(final LivingFallEvent event) {
        LivingEntity entity = event.getEntityLiving();
        float distance = event.getDistance();
        if (entity.level.isClientSide) return;
        event.setCanceled(true);
        if (distance > 10) entity.playSound(SoundEvents.PLAYER_BIG_FALL, 1, 1);
        else if (distance > 3) entity.playSound(SoundEvents.PLAYER_SMALL_FALL, 1, 1);
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        if (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) || entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() < 1) entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 5, 0, false, false, false));
        if (entity.getDeltaMovement().y() < 0.02 && !entity.isCrouching()) {
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.05, 0, 1.05).add(0, -0.02, 0));
            entity.hasImpulse = true;
            entity.fallDistance = 0;
        }
        if (entity.isCrouching() && entity.isOnGround()) {
            CompoundTag tag = boots.getOrCreateTag();
            long tickSneak = tag.getLong("tickSneak");
            tag.putLong("tickSneak", tag.getLong("tickSneak") + 1);
            tickSneak += 1;
            if (entity instanceof Player && !entity.level.isClientSide)
                ((Player) entity).displayClientMessage(new TranslatableComponent("message.moreboots.building_speed", tickSneak), true);
            if (tickSneak >= 864000 && !entity.isSpectator()) {
                Vec3 pos = entity.position();
                tag.putLong("tickSneak", 0);
                boots.setDamageValue(boots.getMaxDamage());
                entity.level.explode(entity, pos.x, entity.getY(-0.0625D), pos.z, 10.0F, Explosion.BlockInteraction.BREAK);
                entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.01 * 864000, 0));
                if (entity instanceof Player && !entity.level.isClientSide) {
                    MinecraftServer server = entity.level.getServer();
                    ServerPlayer serverPlayerEntity = (ServerPlayer) entity;
                    serverPlayerEntity.getAdvancements().award(server.getAdvancements().getAdvancement(new ResourceLocation("moreboots", "moreboots/twelve_hours")), "twelve_hours");
                }
            }
            boots.setTag(tag);
        }
        boolean climable = !Utils.isSurroundedByInvalidBlocks(entity) && !entity.isInLava() && !entity.isInWater() && !entity.isSpectator() && !entity.isOnGround();
        Vec3 motion = entity.getDeltaMovement();
        motion = motion.multiply(1, 0, 1);
        boolean ascending = entity.horizontalCollision;
        boolean descending = !ascending && entity.isCrouching();
        if (climable) {
            if (!ascending && !descending) entity.setDeltaMovement(motion);
            else if (ascending) entity.setDeltaMovement(motion.add(0, 0.2, 0));
            else entity.setDeltaMovement(motion.subtract(0, 0.2, 0));
            entity.fallDistance = 0f;
        }
        BlockState state = entity.level.getBlockState(entity.blockPosition().below());
        if (state.getFriction(entity.level, entity.blockPosition().below(), entity) > 0.6f) entity.addEffect(new MobEffectInstance(EffectInit.SLIPPERINESS, 600, 1, false, false));
        BlockState blockState = entity.level.getBlockState(entity.blockPosition());
        if (blockState.is(BlockInit.VISCOUS_GOO) || !Block.isFaceFull(state.getCollisionShape(entity.level, entity.blockPosition()), Direction.UP) && (blockState.isAir() || !blockState.canBeReplaced(new BlockPlaceContext((Player) entity, InteractionHand.MAIN_HAND, new ItemStack(ItemInit.VISCOUS_GOO), new BlockHitResult(new Vec3(0.5, 1, 0.5), Direction.UP, entity.blockPosition().below(), false))))) return;
        entity.level.setBlockAndUpdate(entity.blockPosition(), BlockInit.VISCOUS_GOO.defaultBlockState());
    }

    @Override
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isCrouching()) return;
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        Vec3 motion = entity.getDeltaMovement();
        CompoundTag tag = boots.getOrCreateTag();
        entity.setDeltaMovement(motion.add(0, 0.3 + 0.01 * tag.getLong("tickSneak"), 0));
        tag.putLong("tickSneak", 0);
        boots.setTag(tag);
    }

    @Override
    public void getCollisionShape(BlockGetter worldIn, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        BlockState state = worldIn.getBlockState(pos);
        if(state.getMaterial().equals(Material.WATER) && context.isAbove(Shapes.block(), pos, true)) cir.setReturnValue(Shapes.block());
    }

    @Override
    public void preRenderLiving(RenderLivingEvent.Pre<?, ?> event) {
        LivingEntity entity = event.getEntity();
        PoseStack matrix = event.getPoseStack();
        matrix.pushPose();
        if (!(entity instanceof Player)) return;
        BlockPos blockPos = entity.blockPosition();
        BlockPos closest = blockPos;
        double distanceSqr = 4;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                if (Math.abs(x) == Math.abs(z)) continue;
                BlockPos adjacent = blockPos.offset(x, 0, z);
                double newDistSqr = entity.distanceToSqr(adjacent.getX(), adjacent.getY(), adjacent.getZ());
                if (newDistSqr < distanceSqr && entity.level.getBlockState(adjacent).canOcclude()) {
                    closest = adjacent;
                    distanceSqr = newDistSqr;
                }
            }
        }
        if (blockPos.equals(closest)) return;
        BlockPos subtracted = blockPos.subtract(closest);
        if (subtracted.getX() == 1) matrix.mulPose(Vector3f.ZN.rotationDegrees(90));
        else if (subtracted.getX() == -1) matrix.mulPose(Vector3f.ZP.rotationDegrees(90));
        else if (subtracted.getZ() == 1) matrix.mulPose(Vector3f.XP.rotationDegrees(90));
        else if (subtracted.getZ() == -1) matrix.mulPose(Vector3f.XN.rotationDegrees(90));
        matrix.translate(0, -0.25, 0);
    }

    @Override
    public void postRenderLiving(RenderLivingEvent.Post<?, ?> event) {
        event.getPoseStack().popPose();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("credit.moreboots."+registryName));
    }
}
