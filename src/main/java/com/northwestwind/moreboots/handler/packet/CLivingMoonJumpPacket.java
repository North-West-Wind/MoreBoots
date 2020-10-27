package com.northwestwind.moreboots.handler.packet;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.Serializable;

public class CLivingMoonJumpPacket implements Serializable {
    public void handle(final NetworkEvent.Context ctx) {
        ServerPlayerEntity player = ctx.getSender();
        ItemStack boots = player.getItemStackFromSlot(EquipmentSlotType.FEET);
        CompoundNBT tag = boots.getOrCreateTag();
        tag.putLong("tickSneak", 0);
        boots.setTag(tag);
    }
}
