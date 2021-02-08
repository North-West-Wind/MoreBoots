package ml.northwestwind.moreboots.inventory;

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
    public boolean isUsableByPlayer(PlayerEntity player)
    {
        return !player.getItemStackFromSlot(EquipmentSlotType.FEET).isEmpty();
    }

    @Override
    public void openInventory(PlayerEntity player)
    {
        this.clear();
        ItemStack boots = player.getItemStackFromSlot(EquipmentSlotType.FEET);
        if(boots.getItem().equals(ItemInit.STORAGE_BOOTS))
        {
            CompoundNBT compound = boots.getTag();
            if(compound != null)
            {
                if(compound.contains("Items", Constants.NBT.TAG_LIST))
                {
                    loadAllItems(compound.getList("Items", Constants.NBT.TAG_COMPOUND), this);
                }
            }
        }
    }

    @Override
    public void closeInventory(PlayerEntity player)
    {
        ItemStack boots = player.getItemStackFromSlot(EquipmentSlotType.FEET);
        if(boots.getItem().equals(ItemInit.STORAGE_BOOTS))
        {
            CompoundNBT compound = boots.getTag();
            if(compound == null)
            {
                compound = new CompoundNBT();
            }
            ListNBT list = new ListNBT();
            saveAllItems(list, this);
            compound.put("Items", list);
            boots.setTag(compound);
        }
    }

    public static void loadAllItems(ListNBT list, Inventory inventory)
    {
        for(int i = 0; i < list.size(); i++)
        {
            CompoundNBT compound = list.getCompound(i);
            int slot = compound.getByte("Slot") & 255;
            if(slot < inventory.getSizeInventory())
            {
                inventory.setInventorySlotContents(slot, ItemStack.read(compound));
            }
        }
    }

    public static ListNBT saveAllItems(ListNBT list, Inventory inventory)
    {
        for(int i = 0; i < inventory.getSizeInventory(); ++i)
        {
            ItemStack itemstack = inventory.getStackInSlot(i);
            if(!itemstack.isEmpty())
            {
                CompoundNBT compound = new CompoundNBT();
                compound.putByte("Slot", (byte) i);
                itemstack.write(compound);
                list.add(compound);
            }
        }
        return list;
    }
}
