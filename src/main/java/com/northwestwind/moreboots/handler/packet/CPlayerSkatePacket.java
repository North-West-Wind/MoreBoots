package com.northwestwind.moreboots.handler.packet;

import com.northwestwind.moreboots.init.ItemInit;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.Serializable;

public class CPlayerSkatePacket implements Serializable {
    public void handle(final NetworkEvent.Context ctx) {
        if (!ctx.getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) return;
        ServerPlayerEntity player = ctx.getSender();
        if(player == null) return;
        ItemStack boots = player.getItemStackFromSlot(EquipmentSlotType.FEET);
        if(!boots.getItem().equals(ItemInit.SKATER)) return;
        BlockPos pos = new BlockPos(player.getPositionVec());
        Material material = player.world.getBlockState(pos.down()).getMaterial();
        if (material.equals(Material.ICE) || material.equals(Material.PACKED_ICE)) {
            Vector3d motion = player.getMotion();
            Vector3d direction = player.getLookVec().scale(0.75);
            player.setMotion(motion.mul(0, 1, 0).add(direction.getX(), 0, direction.getZ()));
        }
    }
}
