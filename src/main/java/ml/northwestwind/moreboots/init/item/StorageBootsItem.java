package ml.northwestwind.moreboots.init.item;

import ml.northwestwind.moreboots.container.StorageBootsContainer;
import ml.northwestwind.moreboots.inventory.StorageBootsInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;

public class StorageBootsItem extends ArmorItemBase {
    private final int rows;

    public StorageBootsItem(IArmorMaterial material, String registryName, int rows) {
        super(material, registryName, false);
        this.rows = rows;
    }

    public void showInventory(ServerPlayerEntity player) {
        ItemStack backpack = player.getItemStackFromSlot(EquipmentSlotType.FEET);
        if (!backpack.isEmpty()) {
            int rows = this.getRows();
            NetworkHooks.openGui(player, new SimpleNamedContainerProvider((id, playerInventory, entity) -> new StorageBootsContainer(id, player.inventory, new StorageBootsInventory(rows), rows), new TranslationTextComponent("container.moreboots.storage_boots")), buffer -> buffer.writeVarInt(rows));
        }
    }

    public int getRows() {
        return rows;
    }
}
