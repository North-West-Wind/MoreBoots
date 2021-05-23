package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.init.item.boots.StorageBootsItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.network.NetworkEvent;

public class COpenStorageBootsPacket implements IPacket {
    public void handle(final NetworkEvent.Context ctx) {
        ServerPlayerEntity player = ctx.getSender();
        if (player != null) {
            ItemStack storageBoots = player.getItemBySlot(EquipmentSlotType.FEET);
            if (storageBoots.getItem() instanceof StorageBootsItem) {
                ((StorageBootsItem) storageBoots.getItem()).showInventory(player);
                player.level.playSound(null, player.blockPosition(), SoundEvents.CHEST_OPEN, SoundCategory.PLAYERS, 0.75f, 1f);
            }
        }
        ctx.setPacketHandled(true);
    }
}