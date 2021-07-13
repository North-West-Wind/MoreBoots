package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.container.StorageBootsContainer;
import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.COpenStorageBootsPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import ml.northwestwind.moreboots.inventory.StorageBootsInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;

public class StorageBootsItem extends BootsItem {
    private static final int rows = 6;

    public StorageBootsItem() {
        super(ItemInit.ModArmorMaterial.STORAGE, "storage_boots");
    }

    public void showInventory(ServerPlayerEntity player) {
        ItemStack backpack = player.getItemBySlot(EquipmentSlotType.FEET);
        if (!backpack.isEmpty()) {
            int rows = this.getRows();
            NetworkHooks.openGui(player, new SimpleNamedContainerProvider((id, playerInventory, entity) -> new StorageBootsContainer(id, player.inventory, new StorageBootsInventory(rows), rows), new TranslationTextComponent("container.moreboots.storage_boots")), buffer -> buffer.writeVarInt(rows));
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
