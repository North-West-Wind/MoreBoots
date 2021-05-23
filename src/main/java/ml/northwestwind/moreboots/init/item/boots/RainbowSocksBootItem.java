package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class RainbowSocksBootItem extends SocksBootsItem {
    public RainbowSocksBootItem() {
        super(ItemInit.ModArmorMaterial.RAINBOW_SOCKS, "rainbow_socks_boots");
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
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isCrouching()) return;
        ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
        Vector3d motion = entity.getDeltaMovement();
        CompoundNBT tag = boots.getOrCreateTag();
        entity.setDeltaMovement(motion.add(0, 0.01 * tag.getLong("tickSneak"), 0));
        tag.putLong("tickSneak", 0);
        boots.setTag(tag);
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isCrouching() && entity.isOnGround()) {
            ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
            CompoundNBT tag = boots.getOrCreateTag();
            long tickSneak = tag.getLong("tickSneak");
            tag.putLong("tickSneak", tag.getLong("tickSneak") + 1);
            tickSneak += 1;
            if (entity instanceof PlayerEntity && !entity.level.isClientSide)
                ((PlayerEntity) entity).displayClientMessage(new TranslationTextComponent("message.moreboots.building_speed", tickSneak), true);
            if (tickSneak >= 864000 && !entity.isSpectator()) {
                Vector3d pos = entity.position();
                tag.putLong("tickSneak", 0);
                boots.setDamageValue(boots.getMaxDamage());
                entity.level.explode(entity, pos.x, entity.getY(-0.0625D), pos.z, 10.0F, Explosion.Mode.BREAK);
                entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.01 * 864000, 0));
                if (entity instanceof PlayerEntity && !entity.level.isClientSide) {
                    MinecraftServer server = entity.level.getServer();
                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) entity;
                    serverPlayerEntity.getAdvancements().award(server.getAdvancements().getAdvancement(new ResourceLocation("moreboots", "moreboots/twelve_hours")), "twelve_hours");
                }
            }
            boots.setTag(tag);
        }
    }
}
