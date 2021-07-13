package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

public class CPlayerSkatePacket implements IPacket {
    public void handle(final NetworkEvent.Context ctx) {
        if (!ctx.getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) return;
        ServerPlayerEntity player = ctx.getSender();
        if(player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
        if(!boots.getItem().equals(ItemInit.SKATER)) return;
        BlockPos pos = new BlockPos(player.position());
        Material material = player.level.getBlockState(pos.below()).getMaterial();
        if (material.equals(Material.ICE) || material.equals(Material.ICE_SOLID)) {
            Vector3d motion = player.getDeltaMovement();
            Vector3d direction = player.getLookAngle().scale(0.75);
            player.setDeltaMovement(motion.multiply(0, 1, 0).add(direction.x(), 0, direction.z()));
        }
    }
}
