package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.init.ItemInit;
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

import java.util.List;

public class CPlayerKAPacket implements IPacket {
    public void handle(final NetworkEvent.Context ctx) {
        if (!ctx.getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) return;
        ServerPlayerEntity player = ctx.getSender();
        if(player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
        if(!boots.getItem().equals(ItemInit.KA_BOOTS)) return;
        BlockPos pos = new BlockPos(player.position());
        AxisAlignedBB area = new AxisAlignedBB(pos).inflate(4);
        List<Entity> collidedEntities = player.level.getEntities(player, area, EntityPredicates.NO_SPECTATORS);
        LivingEntity closest = null;
        for (Entity entity : collidedEntities) {
            if (!(entity instanceof LivingEntity)) continue;
            if (closest == null || closest.position().distanceTo(player.position()) > entity.position().distanceTo(player.position())) closest = (LivingEntity) entity;
        }
        if (closest == null) return;
        player.attack(closest);
    }
}
