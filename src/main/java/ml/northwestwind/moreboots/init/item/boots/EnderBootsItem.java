package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CPlayerEnderTeleportPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class EnderBootsItem extends BootsItem {
    public EnderBootsItem() {
        super(ItemInit.ModArmorMaterial.ENDER, "ender_boots");
    }

    @Override
    public void activateBoots() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientPlayerEntity player = minecraft.player;
        if (player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
        Vector3d pos = player.position().add(player.getLookAngle().multiply(8, 8, 8));
        BlockPos blockPos = new BlockPos(pos);
        while (!player.level.isEmptyBlock(blockPos)) blockPos = blockPos.above();
        player.setPos(pos.x(), blockPos.getY() + pos.y() % 1, pos.z());
        boots.hurtAndBreak(1, player, playerEntity -> playerEntity.playSound(SoundEvents.ENDERMAN_DEATH, 1, 1));
        MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerEnderTeleportPacket());
    }
}
