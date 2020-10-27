package com.northwestwind.moreboots.handler.packet;

import com.northwestwind.moreboots.Reference;
import com.northwestwind.moreboots.init.ItemInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.UUID;

public class CLivingStompPacket {
    private UUID uuid;
    public CLivingStompPacket(LivingEntity entity) {
        uuid = entity.getUniqueID();
    }

    public void handle(final NetworkEvent.Context ctx) {
        ServerPlayerEntity player = ctx.getSender();
        if(player == null) return;
        LivingEntity entity = (LivingEntity) ByteBufUtils.findEntityByUUID(uuid, player.getServerWorld());
        if(entity == null) return;
        ItemStack boots = entity.getItemStackFromSlot(EquipmentSlotType.FEET);
        if(!boots.getItem().equals(ItemInit.plumber_boots) || entity.func_233570_aj_()) return;
        List<Entity> collidedEntities = entity.world.getEntitiesInAABBexcluding(entity, entity.getBoundingBox(), EntityPredicates.NOT_SPECTATING);
        boolean stomped = false;
        for (Entity collidedEntity : collidedEntities) {
            if (!(collidedEntity instanceof LivingEntity)) continue;
            if (collidedEntity.getPosY() + collidedEntity.getHeight() < entity.getPosY() || collidedEntity.getPosY() + collidedEntity.getEyeHeight() > entity.getPosY())
                continue;
            boolean flag = collidedEntity.attackEntityFrom(Reference.STOMP, 4);
            if (!stomped) stomped = flag;
        }
        Vector3d original = entity.getMotion();
        if (stomped) {
            entity.setMotion(original.add(0, 1, 0));
        }
    }
}
