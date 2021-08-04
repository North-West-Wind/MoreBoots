package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CPlayerEnderTeleportPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnderBootsItem extends BootsItem {
    public EnderBootsItem() {
        super(ItemInit.ModArmorMaterial.ENDER, "ender_boots");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void activateBoots() {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        Vec3 pos = player.position().add(player.getLookAngle().multiply(8, 8, 8));
        BlockPos blockPos = new BlockPos(pos);
        while (!player.level.isEmptyBlock(blockPos)) blockPos = blockPos.above();
        player.setPos(pos.x(), blockPos.getY() + pos.y() % 1, pos.z());
        boots.hurtAndBreak(1, player, playerEntity -> playerEntity.playSound(SoundEvents.ENDERMAN_DEATH, 1, 1));
        MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerEnderTeleportPacket());
    }
}
