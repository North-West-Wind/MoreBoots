package ml.northwestwind.moreboots.handler.packet;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkEvent;

public class CShootDragonBallPacket implements IPacket {
    @Override
    public void handle(NetworkEvent.Context ctx) {
        ServerPlayerEntity player = ctx.getSender();
        if (player == null) return;
        Vector3d position = player.getEyePosition(1f).add(player.getLookAngle().normalize());
        DragonFireballEntity dragonBall = new DragonFireballEntity(player.level, player, player.getLookAngle().x, player.getLookAngle().y, player.getLookAngle().z);
        dragonBall.setPos(position.x, position.y, position.z);
        player.level.addFreshEntity(dragonBall);
        player.level.playSound(null, player.blockPosition(), SoundEvents.ENDER_DRAGON_SHOOT, SoundCategory.PLAYERS, 1, 1);
    }
}
