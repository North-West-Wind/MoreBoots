package com.northwestwind.moreboots.handler.packet;

import com.northwestwind.moreboots.init.ItemInit;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.Serializable;

public class CLivingRepairBootsPacket implements Serializable {

    public void handle(final NetworkEvent.Context ctx) {
        ServerPlayerEntity player = ctx.getSender();
        if(player == null) return;
        ItemStack boots = player.getItemStackFromSlot(EquipmentSlotType.FEET);
        if((boots.getItem().equals(ItemInit.lava_boots) && player.isInLava()) || (boots.getItem().equals(ItemInit.water_boots) && player.isInWater())) boots.setDamage(Math.max(boots.getDamage() - 2, 0));
    }
}
