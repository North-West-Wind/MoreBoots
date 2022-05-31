package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CPlayerMultiJumpPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import ml.northwestwind.moreboots.mixins.MixinLivingEntityAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;

public class BugFeetItem extends BootsItem {
    public BugFeetItem() {
        super(ItemInit.ModArmorMaterial.BUG, "bug_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) || entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() < 1)
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 0, false, false, false));
        if (!entity.hasEffect(MobEffects.WEAKNESS) || entity.getEffect(MobEffects.WEAKNESS).getAmplifier() < 1)
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10, 0, false, false, false));
        if (!entity.isCrouching()) {
            ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
            CompoundTag tag = boots.getOrCreateTag();
            int flutterTicks = tag.getInt("flutter_ticks");
            if (flutterTicks <= 20 && !entity.isOnGround() && entity.getDeltaMovement().y < 0.1 && ((MixinLivingEntityAccessor) entity).isJumping()) {
                entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.01 * flutterTicks, 0));
                entity.hasImpulse = true;
                tag.putInt("flutter_ticks", flutterTicks + 1);
            } else if (flutterTicks > 0 && entity.isOnGround()) tag.putInt("flutter_ticks", 0);
            else {
                double addToY = 0;
                if (entity.getDeltaMovement().y() < 0.05 && ((MixinLivingEntityAccessor) entity).isJumping()) addToY = 0.02;
                else if (entity.getDeltaMovement().y() < 0) addToY = -0.05;
                if (addToY != 0) {
                    entity.setDeltaMovement(entity.getDeltaMovement().add(0, addToY, 0));
                    entity.hasImpulse = true;
                    entity.fallDistance = 0;
                }
            }
            boots.setTag(tag);
        }
    }

    @Override
    public void onJump() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || player.isOnGround() || player.getAbilities().flying) return;
        player.jumpFromGround();
        player.setDeltaMovement(player.getDeltaMovement().add(0, 0.25, 0));
        if (player.level.isClientSide)
            MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerMultiJumpPacket());
    }
}
