package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CPlayerMultiJumpPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import ml.northwestwind.moreboots.mixins.MixinLivingEntityAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;

public class SuperSocksItem extends BootsItem {
    public SuperSocksItem() {
        super(ItemInit.ModArmorMaterial.SUPER_SOCKS, "super_socks");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) || entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() < 1)
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 0, false, false, false));
        if (!entity.isCrouching()) {
            if (entity.getDeltaMovement().y() < 0) {
                entity.setDeltaMovement(entity.getDeltaMovement().multiply(1, 0, 1).add(0, -0.08, 0));
                entity.hasImpulse = true;
                entity.fallDistance = 0;
            }
            ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
            CompoundTag tag = boots.getOrCreateTag();
            int flutterTicks = tag.getInt("flutter_ticks");
            if (flutterTicks <= 20 && !entity.isOnGround() && entity.getDeltaMovement().y < 0.1 && ((MixinLivingEntityAccessor) entity).isJumping()) {
                entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.01 * flutterTicks, 0));
                entity.hasImpulse = true;
                tag.putInt("flutter_ticks", flutterTicks + 1);
            } else if (flutterTicks > 0 && entity.isOnGround()) tag.putInt("flutter_ticks", 0);
            boots.setTag(tag);
        } else if (entity.isOnGround()) {
            ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
            CompoundTag tag = boots.getOrCreateTag();
            long tickSneak = tag.getLong("tickSneak");
            tag.putLong("tickSneak", tag.getLong("tickSneak") + 1);
            tickSneak += 1;
            if (entity instanceof Player && !entity.level.isClientSide)
                ((Player) entity).displayClientMessage(MutableComponent.create(new TranslatableContents("message.moreboots.charging_jump", tickSneak)), true);
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
    }

    @Override
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.isCrouching()) return;
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        Vec3 motion = entity.getDeltaMovement();
        CompoundTag tag = boots.getOrCreateTag();
        entity.setDeltaMovement(motion.add(0, 0.05 + 0.005 * tag.getLong("tickSneak"), 0));
        tag.putLong("tickSneak", 0);
        boots.setTag(tag);
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
