package com.northwestwind.moreboots.handler.packet;

import com.northwestwind.moreboots.init.ItemInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.Serializable;
import java.util.UUID;
import java.util.stream.Stream;

public class CLivingFreezeWaterPacket implements Serializable {
    private int x, y, z;
    private UUID uuid;
    public CLivingFreezeWaterPacket(BlockPos pos, LivingEntity entity) {
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
        if(!boots.getItem().equals(ItemInit.ice_boots)) return;
        BlockPos blockPos = new BlockPos(x, y, z);
        FrostWalkerEnchantment.freezeNearby(entity, entity.world, blockPos, 2);
        boots.damageItem(1, entity, playerEntity -> playerEntity.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1, 1));
    }
}
