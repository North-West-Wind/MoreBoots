package ml.northwestwind.moreboots.handler.packet;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

public class CPlayerDashPacket implements IPacket {
    @Override
    public void handle(NetworkEvent.Context ctx) {
        ServerPlayer player = ctx.getSender();
        if(player == null) return;
        Vec3 lookVec = player.getLookAngle().scale(0.1);
        player.setDeltaMovement(player.getDeltaMovement().add(lookVec));
        player.hasImpulse = true;
        player.getCooldowns().addCooldown(player.getItemBySlot(EquipmentSlot.FEET).getItem(), 100);
    }
}
