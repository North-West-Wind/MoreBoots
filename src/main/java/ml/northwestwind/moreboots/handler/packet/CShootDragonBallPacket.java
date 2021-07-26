package ml.northwestwind.moreboots.handler.packet;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class CShootDragonBallPacket implements IPacket {
    @Override
    public void handle(NetworkEvent.Context ctx) {
        ServerPlayer player = ctx.getSender();
        if (player == null) return;
        Vec3 position = player.getEyePosition(1f).add(player.getLookAngle().normalize());
        DragonFireball dragonBall = new DragonFireball(player.level, player, player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z);
        dragonBall.setPos(position.x, position.y, position.z);
        player.level.addFreshEntity(dragonBall);
        player.level.playSound(null, player.blockPosition(), SoundEvents.ENDER_DRAGON_SHOOT, SoundSource.PLAYERS, 1, 1);
    }
}
