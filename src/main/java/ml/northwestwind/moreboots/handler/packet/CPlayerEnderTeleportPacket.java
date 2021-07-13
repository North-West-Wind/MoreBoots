package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

public class CPlayerEnderTeleportPacket implements IPacket {
    public void handle(final NetworkEvent.Context ctx) {
        if (!ctx.getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) return;
        ServerPlayerEntity player = ctx.getSender();
        if(player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
        if(!boots.getItem().equals(ItemInit.ENDER_BOOTS)) return;
        Vector3d pos = player.position().add(player.getLookAngle().multiply(8, 8, 8));
        BlockPos blockPos = new BlockPos(pos);
        while (!player.level.isEmptyBlock(blockPos)) blockPos = blockPos.above();
        player.setPos(pos.x(), blockPos.getY() + pos.y() % 1, pos.z());
        boots.hurtAndBreak(1, player, playerEntity -> playerEntity.playSound(SoundEvents.ENDERMAN_DEATH, 1, 1));
    }
}
