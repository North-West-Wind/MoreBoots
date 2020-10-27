package com.northwestwind.moreboots.handler.packet;

import com.northwestwind.moreboots.handler.MoreBootsHandler;
import com.northwestwind.moreboots.init.ItemInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.Serializable;
import java.util.UUID;

public class CLivingClimbPacket implements Serializable {
    private UUID uuid;
    public CLivingClimbPacket(LivingEntity entity) {
        uuid = entity.getUniqueID();
    }

    public void handle(final NetworkEvent.Context ctx) {
        ServerPlayerEntity player = ctx.getSender();
        if(player == null) return;
        LivingEntity entity = (LivingEntity) ByteBufUtils.findEntityByUUID(uuid, player.getServerWorld());
        if(entity == null) return;
        ItemStack boots = entity.getItemStackFromSlot(EquipmentSlotType.FEET);
        if(!boots.getItem().equals(ItemInit.spider_boots)) return;
        CompoundNBT tag = boots.getOrCreateTag();
        if (MoreBootsHandler.isSurroundedByInvalidBlocks(entity) || entity.isInLava() || entity.isInWater() || entity.isSpectator()) {
            tag.putBoolean("climable", false);
            boots.setTag(tag);
        } else if (entity.collidedHorizontally) {
            tag.putBoolean("climable", true);
            boots.setTag(tag);
        }
        boolean climable = tag.getBoolean("climable");
        Vector3d motion = entity.getMotion();
        motion = motion.mul(1, 0, 1);
        boolean ascending = entity.collidedHorizontally;
        boolean descending = !ascending && !entity.isSneaking();
        if (!ascending && !descending && climable) entity.setMotion(motion.mul(1, 0, 1));
        else if (ascending && climable) entity.setMotion(motion.add(0, 0.2, 0));
        else if (descending && climable) entity.setMotion(motion.subtract(0, 0.2, 0));
    }
}
