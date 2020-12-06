package com.northwestwind.moreboots.handler.packet;

import com.northwestwind.moreboots.init.ItemInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.Serializable;
import java.util.List;

public class CPlayerKAPacket implements Serializable {
    public void handle(final NetworkEvent.Context ctx) {
        if (!ctx.getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) return;
        ServerPlayerEntity player = ctx.getSender();
        if(player == null) return;
        ItemStack boots = player.getItemStackFromSlot(EquipmentSlotType.FEET);
        if(!boots.getItem().equals(ItemInit.KA_BOOTS)) return;
        BlockPos pos = new BlockPos(player.getPositionVec());
        AxisAlignedBB area = new AxisAlignedBB(pos).expand(8, 8, 8);
        List<Entity> collidedEntities = player.world.getEntitiesInAABBexcluding(player, area, EntityPredicates.NOT_SPECTATING);
        LivingEntity closest = null;
        for (Entity entity : collidedEntities) {
            if (!(entity instanceof LivingEntity)) continue;
            if (closest == null || closest.getPositionVec().distanceTo(player.getPositionVec()) > entity.getPositionVec().distanceTo(player.getPositionVec())) closest = (LivingEntity) entity;
        }
        if (closest == null) return;
        player.attackTargetEntityWithCurrentItem(closest);
    }
}
