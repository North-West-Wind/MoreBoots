package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class CPlayerEnderTeleportPacket implements IPacket {
    public void handle(final NetworkEvent.Context ctx) {
        if (!ctx.getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) return;
        ServerPlayer player = ctx.getSender();
        if(player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        if(!boots.getItem().equals(ItemInit.ENDER_BOOTS.get())) return;
        Vec3 pos = player.position().add(player.getLookAngle().multiply(8, 8, 8));
        BlockPos blockPos = new BlockPos(pos);
        while (!player.level.isEmptyBlock(blockPos)) blockPos = blockPos.above();
        player.setPos(pos.x(), blockPos.getY() + pos.y() % 1, pos.z());
        boots.hurtAndBreak(1, player, playerEntity -> playerEntity.playSound(SoundEvents.ENDERMAN_DEATH, 1, 1));
    }
}
