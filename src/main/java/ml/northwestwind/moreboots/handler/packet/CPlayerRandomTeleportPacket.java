package ml.northwestwind.moreboots.handler.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class CPlayerRandomTeleportPacket implements IPacket {
    @Override
    public void handle(NetworkEvent.Context ctx) {
        ServerPlayer player = ctx.getSender();
        if (player == null) return;
        List<BlockPos> teleportable = Lists.newArrayList();
        Iterable<BlockPos> poses = BlockPos.betweenClosed(player.blockPosition().offset(8, 8, 8), player.blockPosition().offset(-8, -8, -8));
        for (BlockPos pos : poses) {
            BlockState state = player.level.getBlockState(pos);
            if (!state.getCollisionShape(player.level, pos).isEmpty() && player.level.getBlockState(pos.above()).getCollisionShape(player.level, pos.above()).isEmpty())
                teleportable.add(pos);
        }
        BlockPos pos = teleportable.get(player.level.random.nextInt(teleportable.size()));
        player.teleportTo(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
    }
}
