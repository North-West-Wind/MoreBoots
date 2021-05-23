package ml.northwestwind.moreboots.inventory;

import ml.northwestwind.moreboots.MoreBoots;
import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;

public class StorageBootsInventory extends Inventory
{
    public StorageBootsInventory(int rows)
    {
        super(9 * rows);
    }

    @Override
    public boolean stillValid(PlayerEntity player)
    {
        return player.getItemBySlot(EquipmentSlotType.FEET).getItem().equals(ItemInit.STORAGE_BOOTS);
    }

    @Override
    public void startOpen(PlayerEntity player) {
        this.clearContent();
        ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
        if(boots.getItem().equals(ItemInit.STORAGE_BOOTS)) {
            CompoundNBT compound = boots.getOrCreateTag();
            if(compound.contains("Items")) loadAllItems(compound.getList("Items", Constants.NBT.TAG_COMPOUND), this);
        }
    }

    @Override
    public void stopOpen(PlayerEntity player) {
        ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
        if(boots.getItem().equals(ItemInit.STORAGE_BOOTS)) {
            CompoundNBT compound = boots.getOrCreateTag();
            ListNBT list = new ListNBT();
            saveAllItems(list, this);
            compound.put("Items", list);
            boots.setTag(compound);
        }
    }

    public static void loadAllItems(ListNBT list, Inventory inventory)
    {
        for(int i = 0; i < list.size(); i++) {
            CompoundNBT compound = list.getCompound(i);
            int slot = compound.getInt("Slot");
            if(slot < inventory.getContainerSize()) inventory.setItem(slot, ItemStack.of(compound.getCompound("Item")));
        }
    }

    public static ListNBT saveAllItems(ListNBT list, Inventory inventory)
    {
        for(int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack itemstack = inventory.getItem(i);
            if(!itemstack.isEmpty()) {
                CompoundNBT compound = new CompoundNBT(), item = new CompoundNBT();
                compound.putInt("Slot", i);
                itemstack.save(item);
                compound.put("Item", item);
                list.add(compound);
            }
        }
        return list;
    }
}
