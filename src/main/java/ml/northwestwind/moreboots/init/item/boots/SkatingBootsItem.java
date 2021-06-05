package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CPlayerSkatePacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SkatingBootsItem extends BootsItem {
    public SkatingBootsItem() {
        super(ItemInit.ModArmorMaterial.SKATER, "skating_boots");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onShift() {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null) return;
        BlockPos pos = player.blockPosition();
        Material material = player.level.getBlockState(pos.below()).getMaterial();
        if (material.equals(Material.ICE) || material.equals(Material.ICE_SOLID)) {
            Vector3d motion = player.getDeltaMovement();
            Vector3d direction = player.getLookAngle().scale(0.75);
            player.setDeltaMovement(motion.multiply(0, 1, 0).add(direction.x(), 0, direction.z()));
        }
        if (player.level.isClientSide) MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerSkatePacket());
    }
}
