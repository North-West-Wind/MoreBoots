package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.container.StorageBootsContainer;
import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.COpenStorageBootsPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import ml.northwestwind.moreboots.inventory.StorageBootsInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkHooks;

public class StorageBootsItem extends BootsItem {
    private static final int rows = 6;

    public StorageBootsItem() {
        super(ItemInit.ModArmorMaterial.STORAGE, "storage_boots");
    }

    public void showInventory(ServerPlayer player) {
        ItemStack backpack = player.getItemBySlot(EquipmentSlot.FEET);
        if (!backpack.isEmpty()) {
            int rows = this.getRows();
            NetworkHooks.openScreen(player, new SimpleMenuProvider((id, playerInventory, entity) -> new StorageBootsContainer(id, player.getInventory(), new StorageBootsInventory(rows), rows), MutableComponent.create(new TranslatableContents("container.moreboots.storage_boots"))), buffer -> buffer.writeVarInt(rows));
        }
    }

    public int getRows() {
        return rows;
    }

    @Override
    public void activateBoots() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;
        MoreBootsPacketHandler.INSTANCE.sendToServer(new COpenStorageBootsPacket());
    }
}
