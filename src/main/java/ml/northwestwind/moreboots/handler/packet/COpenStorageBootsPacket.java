package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.init.item.boots.StorageBootsItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class COpenStorageBootsPacket implements IPacket {
    public void handle(final NetworkEvent.Context ctx) {
        ServerPlayer player = ctx.getSender();
        if (player != null) {
            ItemStack storageBoots = player.getItemBySlot(EquipmentSlot.FEET);
            if (storageBoots.getItem() instanceof StorageBootsItem) {
                ((StorageBootsItem) storageBoots.getItem()).showInventory(player);
                player.level.playSound(null, player.blockPosition(), SoundEvents.CHEST_OPEN, SoundSource.PLAYERS, 0.75f, 1f);
            }
        }
        ctx.setPacketHandled(true);
    }
}