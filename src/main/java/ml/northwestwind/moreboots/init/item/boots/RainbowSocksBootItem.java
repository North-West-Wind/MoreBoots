package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class RainbowSocksBootItem extends SocksBootsItem {
    public RainbowSocksBootItem() {
        super(ItemInit.ModArmorMaterial.RAINBOW_SOCKS, "rainbow_socks_boots");
    }

    @Override
    public void onLivingFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.isCrouching()) return;
        float distance = event.getDistance();
        if (distance < 1.5) return;
        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.x * 1.05, Math.sqrt(distance) / 3.0, motion.z * 1.05);
        entity.hasImpulse = true;
        entity.playSound(SoundEvents.SLIME_BLOCK_HIT, 1, 1);
        event.setCanceled(true);
        if (!entity.level.isClientSide) ((ServerLevel) entity.level).getServer().getPlayerList().broadcastAll(new ClientboundSetEntityMotionPacket(entity));
    }

    @Override
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.isCrouching()) return;
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        Vec3 motion = entity.getDeltaMovement();
        CompoundTag tag = boots.getOrCreateTag();
        entity.setDeltaMovement(motion.add(0, 0.005 * tag.getLong("tickSneak"), 0));
        tag.putLong("tickSneak", 0);
        boots.setTag(tag);
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.isCrouching() && entity.isOnGround()) {
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
}
