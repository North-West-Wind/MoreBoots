package com.northwestwind.moreboots.handler.packet;

import com.northwestwind.moreboots.init.ItemInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.Serializable;
import java.util.UUID;

public class CLivingHastePacket implements Serializable {
    private UUID uuid;

    public CLivingHastePacket(LivingEntity entity) {
        uuid = entity.getUniqueID();
    }

    public void handle(NetworkEvent.Context ctx) {
        ServerPlayerEntity player = ctx.getSender();
        if(player == null) return;
        LivingEntity entity = (LivingEntity) ByteBufUtils.findEntityByUUID(uuid, player.getServerWorld());
        if(entity == null) return;
        ItemStack boots = entity.getItemStackFromSlot(EquipmentSlotType.FEET);
        if(!boots.getItem().equals(ItemInit.miner_boots)) return;
        entity.addPotionEffect(new EffectInstance(Effects.HASTE, 1, 1));
    }
}
