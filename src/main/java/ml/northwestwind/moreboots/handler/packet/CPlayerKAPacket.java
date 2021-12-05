package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;

public class CPlayerKAPacket implements IPacket {
    public void handle(final NetworkEvent.Context ctx) {
        if (!ctx.getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) return;
        ServerPlayer player = ctx.getSender();
        if(player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        if(!boots.getItem().equals(ItemInit.KA_BOOTS)) return;
        BlockPos pos = new BlockPos(player.position());
        AABB area = new AABB(pos).inflate(4);
        List<Entity> collidedEntities = player.level.getEntities(player, area, EntitySelector.NO_SPECTATORS);
        LivingEntity closest = null;
        for (Entity entity : collidedEntities) {
            if (!(entity instanceof LivingEntity)) continue;
            if (closest == null || closest.position().distanceTo(player.position()) > entity.position().distanceTo(player.position())) closest = (LivingEntity) entity;
        }
        if (closest == null) return;
        player.attack(closest);
    }
}
