package ml.northwestwind.moreboots.handler.packet;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

public class CThrowTNTPacket implements IPacket {
    @Override
    public void handle(NetworkEvent.Context ctx) {
        ServerPlayer player = ctx.getSender();
        if (player == null) return;
        Vec3 position = player.getEyePosition().add(player.getLookAngle().normalize());
        PrimedTnt tnt = new PrimedTnt(player.level, position.x, position.y, position.z, player);
        tnt.setDeltaMovement(player.getLookAngle().multiply(4, 4, 4));
        player.level.addFreshEntity(tnt);
        // This sound is totally intentional not because of copying from CShootDragonBallPacket, but it sounds cooler for Bahamut
        player.level.playSound(null, player.blockPosition(), SoundEvents.ENDER_DRAGON_SHOOT, SoundSource.PLAYERS, 1, 1);
    }
}
