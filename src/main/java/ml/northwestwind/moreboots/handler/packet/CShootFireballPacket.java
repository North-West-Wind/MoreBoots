package ml.northwestwind.moreboots.handler.packet;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

public class CShootFireballPacket implements IPacket {
    @Override
    public void handle(NetworkEvent.Context ctx) {
        ServerPlayer player = ctx.getSender();
        if (player == null || !player.getAbilities().flying) return;
        Vec3 position = player.getEyePosition(1f).add(player.getLookAngle().normalize());
        LargeFireball fireball = new LargeFireball(player.level, player, player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z, 10);
        fireball.setPos(position.x, position.y, position.z);
        player.level.addFreshEntity(fireball);
        player.level.playSound(null, player.blockPosition(), SoundEvents.ENDER_DRAGON_SHOOT, SoundSource.PLAYERS, 1, 1);
    }
}
