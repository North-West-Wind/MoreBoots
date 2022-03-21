package ml.northwestwind.moreboots.inventory;

import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class StorageBootsInventory implements Container {
    private final int size;
    private final NonNullList<ItemStack> items;
    public StorageBootsInventory(int rows)
    {
        this.size = 9 * rows;
        this.items = NonNullList.withSize(this.size, ItemStack.EMPTY);
    }

    @Override
    public int getContainerSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) if (!stack.isEmpty()) return false;
        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        List<ItemStack> list = getListSafe(index);
        return list.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int amount) {
        List<ItemStack> list = getListSafe(index);
        return list != null && !list.get(index).isEmpty() ? ContainerHelper.removeItem(list, index, amount) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        List<ItemStack> list = getListSafe(index);
        if (list != null && !list.get(index).isEmpty()) {
            ItemStack itemstack = list.get(index);
            list.set(index, ItemStack.EMPTY);
            return itemstack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        List<ItemStack> list = getListSafe(index);
        list.set(index, stack);
    }

    @Override
    public void setChanged() { }

    @Override
    public boolean stillValid(Player player)
    {
        return player.getItemBySlot(EquipmentSlot.FEET).getItem().equals(ItemInit.STORAGE_BOOTS.get());
    }

    @Override
    public void startOpen(Player player) {
        this.clearContent();
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        if(boots.getItem().equals(ItemInit.STORAGE_BOOTS.get())) {
            CompoundTag compound = boots.getOrCreateTag();
            if(compound.contains("Items")) loadAllItems((ListTag) compound.get("Items"), this);
        }
    }

    @Override
    public void stopOpen(Player player) {
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        if(boots.getItem().equals(ItemInit.STORAGE_BOOTS.get())) {
            CompoundTag compound = boots.getOrCreateTag();
            ListTag list = new ListTag();
            saveAllItems(list, this);
            compound.put("Items", list);
            boots.setTag(compound);
        }
    }

    public static void loadAllItems(ListTag list, Container inventory)
    {
        for(int i = 0; i < list.size(); i++) {
            CompoundTag compound = list.getCompound(i);
            int slot = compound.getInt("Slot");
            if(slot < inventory.getContainerSize()) inventory.setItem(slot, ItemStack.of(compound.getCompound("Item")));
        }
    }

    public static ListTag saveAllItems(ListTag list, Container inventory)
    {
        for(int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack itemstack = inventory.getItem(i);
            if(!itemstack.isEmpty()) {
                CompoundTag compound = new CompoundTag(), item = new CompoundTag();
                compound.putInt("Slot", i);
                itemstack.save(item);
                compound.put("Item", item);
                list.add(compound);
            }
        }
        return list;
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    private List<ItemStack> getListSafe(int index) {
        List<ItemStack> list = null;
        if (index < this.items.size()) list = this.items;
        else index -= this.items.size();
        return list;
    }
}
