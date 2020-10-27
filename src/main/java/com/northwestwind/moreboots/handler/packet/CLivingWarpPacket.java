package com.northwestwind.moreboots.handler.packet;

import com.northwestwind.moreboots.init.ItemInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.Serializable;
import java.util.UUID;

public class CLivingWarpPacket implements Serializable {
    private int x, y, z;
    private UUID uuid;
    public CLivingWarpPacket(BlockPos pos, LivingEntity entity) {
        x = pos.getX();
        y = pos.getY();
        z = pos.getZ();
        uuid = entity.getUniqueID();
    }

    public BlockPos getPos() {
        return new BlockPos(x, y, z);
    }

    public void handle(final NetworkEvent.Context ctx) {
        ServerPlayerEntity player = ctx.getSender();
        if(player == null) return;
        LivingEntity entity = (LivingEntity) ByteBufUtils.findEntityByUUID(uuid, player.getServerWorld());
        if(entity == null) return;
        ItemStack boots = entity.getItemStackFromSlot(EquipmentSlotType.FEET);
        if(!boots.getItem().equals(ItemInit.upwarp_boots) || !boots.getItem().equals(ItemInit.downwarp_boots)) return;
        entity.setPositionAndUpdate(x, y, z);
    }
}
