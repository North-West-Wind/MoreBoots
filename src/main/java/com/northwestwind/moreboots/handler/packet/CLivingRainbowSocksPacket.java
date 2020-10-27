package com.northwestwind.moreboots.handler.packet;

import com.northwestwind.moreboots.init.ItemInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.Serializable;
import java.util.UUID;

public class CLivingRainbowSocksPacket implements Serializable {
    private UUID uuid;
    public CLivingRainbowSocksPacket(LivingEntity entity) {
        uuid = entity.getUniqueID();
    }

    public void handle(final NetworkEvent.Context ctx) {
        ServerPlayerEntity player = ctx.getSender();
        if(player == null) return;
        LivingEntity entity = (LivingEntity) ByteBufUtils.findEntityByUUID(uuid, player.getServerWorld());
        if(entity == null) return;
        ItemStack boots = entity.getItemStackFromSlot(EquipmentSlotType.FEET);
        if(!boots.getItem().equals(ItemInit.rainbow_socks_boots)) return;
        CompoundNBT tag = boots.getOrCreateTag();
        long tickSneak = tag.getLong("tickSneak");
        tag.putLong("tickSneak", tag.getLong("tickSneak") + 1);
        tickSneak += 1;
        if (entity instanceof PlayerEntity) ((PlayerEntity) entity).sendStatusMessage(new TranslationTextComponent("message.moreboots.building_speed", tickSneak), true);
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
    }
}
