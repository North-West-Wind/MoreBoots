package com.northwestwind.moreboots.handler.packet;

import com.northwestwind.moreboots.init.ItemInit;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.Serializable;

public class CPlayerEnderTeleportPacket implements Serializable {
    public void handle(final NetworkEvent.Context ctx) {
        if (!ctx.getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) return;
        ServerPlayerEntity player = ctx.getSender();
        if(player == null) return;
        ItemStack boots = player.getItemStackFromSlot(EquipmentSlotType.FEET);
        if(!boots.getItem().equals(ItemInit.ENDER_BOOTS)) return;
        Vector3d pos = player.getPositionVec().add(player.getLookVec().mul(8, 8, 8));
        player.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
        boots.damageItem(1, player, playerEntity -> playerEntity.playSound(SoundEvents.ENTITY_ENDERMAN_DEATH, 1, 1));
    }
}
