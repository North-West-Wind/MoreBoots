package com.northwestwind.moreboots.handler.packet;

import com.northwestwind.moreboots.init.BlockInit;
import com.northwestwind.moreboots.init.block.GlowstoneDustBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.Serializable;
import java.util.UUID;

public class CLivingIlluminateSurroundingsPacket implements Serializable {
    private int x, y, z;
    private UUID uuid;
    public CLivingIlluminateSurroundingsPacket(BlockPos pos, LivingEntity entity) {
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
        BlockPos blockPos = getPos();
        BlockPos under = blockPos.down();
        BlockState underneath = entity.world.getBlockState(under);
        if (underneath.isSolid() && entity.world.isAirBlock(blockPos)) {
            entity.world.setBlockState(blockPos, BlockInit.glowstone_dust.getDefaultState().with(GlowstoneDustBlock.FACING, GlowstoneDustBlock.getRandomDirection()));
            boots.damageItem(1, entity, playerEntity -> playerEntity.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0f, 1.0f));
        }
    }
}
