package ml.northwestwind.moreboots.handler.packet;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class CShootWitherSkullPacket implements IPacket {
    @Override
    public void handle(NetworkEvent.Context ctx) {
        ServerPlayer player = ctx.getSender();
        if (player == null) return;
        Vec3 position = player.getEyePosition(1f).add(player.getLookAngle().normalize());
        WitherSkull skull = new WitherSkull(player.level, player, player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z);
        skull.setPos(position.x, position.y, position.z);
        player.level.addFreshEntity(skull);
        player.level.playSound(null, player.blockPosition(), SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 1, 1);
    }
}
