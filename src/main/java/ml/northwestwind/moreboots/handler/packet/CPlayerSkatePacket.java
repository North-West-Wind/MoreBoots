package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class CPlayerSkatePacket implements IPacket {
    public void handle(final NetworkEvent.Context ctx) {
        if (!ctx.getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) return;
        ServerPlayer player = ctx.getSender();
        if(player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        if(!boots.getItem().equals(ItemInit.SKATER)) return;
        BlockPos pos = new BlockPos(player.position());
        Material material = player.level.getBlockState(pos.below()).getMaterial();
        if (material.equals(Material.ICE) || material.equals(Material.ICE_SOLID)) {
            Vec3 motion = player.getDeltaMovement();
            Vec3 direction = player.getLookAngle().scale(0.75);
            player.setDeltaMovement(motion.multiply(0, 1, 0).add(direction.x(), 0, direction.z()));
        }
    }
}
