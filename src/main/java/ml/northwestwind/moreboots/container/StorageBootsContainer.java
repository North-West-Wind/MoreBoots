package ml.northwestwind.moreboots.container;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.MenuTypeInit;
import ml.northwestwind.moreboots.inventory.StorageBootsInventory;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class StorageBootsContainer extends ChestMenu {
    private final Container storage;
    private final int containerRows;

    public StorageBootsContainer(int id, Inventory playerInventory, int containerRows) {
        this(id, playerInventory, new StorageBootsInventory(containerRows), containerRows);
    }

    public StorageBootsContainer(int id, Inventory playerInventory, Container storage, int containerRows) {
        super(MenuTypeInit.STORAGE_BOOTS.get(), id, playerInventory, storage, containerRows);
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
    public boolean stillValid(Player playerIn) {
        return this.storage.stillValid(playerIn) && playerIn.getItemBySlot(EquipmentSlot.FEET).getItem().equals(ItemInit.STORAGE_BOOTS.get());
    }

    @Override
    public ItemStack quickMoveStack(Player p_82846_1_, int p_82846_2_) {
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
    public void removed(Player playerIn) {
        super.removed(playerIn);
        this.storage.stopOpen(playerIn);
        playerIn.level.playSound(null, playerIn.blockPosition(), SoundEvents.CHEST_CLOSE, SoundSource.PLAYERS, 0.75f, 1f);
    }

    public int getContainerRows() {
        return containerRows;
    }
}