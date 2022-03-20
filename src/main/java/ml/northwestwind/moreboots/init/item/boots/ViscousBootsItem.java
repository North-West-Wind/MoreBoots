package ml.northwestwind.moreboots.init.item.boots;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.BlockInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

import javax.annotation.Nullable;
import java.util.List;

public class ViscousBootsItem extends BootsItem {
    public ViscousBootsItem() {
        super(ItemInit.ModArmorMaterial.VISCOUS, "viscous_boots");
    }

    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
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
        BlockState state = entity.level.getBlockState(entity.blockPosition().below());
        BlockState blockState = entity.level.getBlockState(entity.blockPosition());
        if (blockState.is(BlockInit.VISCOUS_GOO.get()) || !Block.isFaceFull(state.getCollisionShape(entity.level, entity.blockPosition()), Direction.UP) && (blockState.isAir() || !blockState.canBeReplaced(new BlockPlaceContext((Player) entity, InteractionHand.MAIN_HAND, new ItemStack(ItemInit.VISCOUS_GOO.get()), new BlockHitResult(new Vec3(0.5, 1, 0.5), Direction.UP, entity.blockPosition().below(), false))))) return;
        entity.level.setBlockAndUpdate(entity.blockPosition(), BlockInit.VISCOUS_GOO.get().defaultBlockState());
    }

    @Override
    public void preRenderLiving(RenderLivingEvent.Pre<?, ?> event) {
        LivingEntity entity = event.getEntity();
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
        PoseStack matrix = event.getPoseStack();
        matrix.pushPose();
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
