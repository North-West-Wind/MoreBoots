package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.init.item.StorageBootsItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.network.NetworkEvent;

import java.io.Serializable;

public class COpenStorageBootsPacket implements Serializable {
    public void handle(final NetworkEvent.Context ctx) {
        ServerPlayerEntity player = ctx.getSender();
        if (player != null) {
            ItemStack storageBoots = player.getItemStackFromSlot(EquipmentSlotType.FEET);
            if (storageBoots.getItem() instanceof StorageBootsItem) {
                ((StorageBootsItem) storageBoots.getItem()).showInventory(player);
            }
        }
        ctx.setPacketHandled(true);
    }
}