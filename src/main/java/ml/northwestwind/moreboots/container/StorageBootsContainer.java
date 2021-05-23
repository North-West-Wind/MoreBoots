package ml.northwestwind.moreboots.container;

import ml.northwestwind.moreboots.init.ContainerInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.inventory.StorageBootsInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

public class StorageBootsContainer extends Container {
    private final IInventory storage;
    private final int containerRows;

    public StorageBootsContainer(int id, PlayerInventory playerInventory, int containerRows) {
        this(id, playerInventory, new StorageBootsInventory(containerRows), containerRows);
    }

    public StorageBootsContainer(int id, PlayerInventory playerInventory, IInventory storage, int containerRows) {
        super(ContainerInit.STORAGE_BOOTS, id);
        checkContainerSize(storage, containerRows * 9);
        this.storage = storage;
        this.containerRows = containerRows;
        this.storage.startOpen(playerInventory.player);
        int i = (this.containerRows - 4) * 18;

        for (int j = 0; j < this.containerRows; ++j) for (int k = 0; k < 9; ++k) this.addSlot(new Slot(storage, k + j * 9, 8 + k * 18, 18 + j * 18));
        for (int l = 0; l < 3; ++l) for (int j1 = 0; j1 < 9; ++j1) this.addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
        for (int i1 = 0; i1 < 9; ++i1) this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 161 + i));
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return this.storage.stillValid(playerIn) && playerIn.getItemBySlot(EquipmentSlotType.FEET).getItem().equals(ItemInit.STORAGE_BOOTS);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity p_82846_1_, int p_82846_2_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_82846_2_);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (p_82846_2_ < this.containerRows * 9) {
                if (!this.moveItemStackTo(itemstack1, this.containerRows * 9, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.containerRows * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public void removed(PlayerEntity playerIn) {
        super.removed(playerIn);
        this.storage.stopOpen(playerIn);
        playerIn.level.playSound(null, playerIn.blockPosition(), SoundEvents.CHEST_CLOSE, SoundCategory.PLAYERS, 0.75f, 1f);
    }

    public int getContainerRows() {
        return containerRows;
    }
}