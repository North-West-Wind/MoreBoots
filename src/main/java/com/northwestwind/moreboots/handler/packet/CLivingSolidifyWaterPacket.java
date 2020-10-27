package com.northwestwind.moreboots.handler.packet;

import com.northwestwind.moreboots.init.ItemInit;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.Serializable;
import java.util.UUID;

public class CLivingSolidifyWaterPacket implements Serializable {
    private int x, y, z;
    private UUID uuid;
    public CLivingSolidifyWaterPacket(BlockPos pos, LivingEntity entity) {
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
        FluidState water = entity.world.getFluidState(getPos());
        if(!boots.getItem().equals(ItemInit.lava_boots) || !(water.getFluid() instanceof WaterFluid)) return;
        if(water.isSource()) entity.world.setBlockState(getPos(), Blocks.STONE.getDefaultState());
        else entity.world.setBlockState(getPos(), Blocks.COBBLESTONE.getDefaultState());
        entity.playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH, 1, 1);
        entity.getItemStackFromSlot(EquipmentSlotType.FEET).damageItem(1, entity, playerEntity -> playerEntity.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1, 1));
    }
}
