package ml.northwestwind.moreboots.init.tileentity;

import ml.northwestwind.moreboots.init.BlockEntityInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.block.BootRecyclerBlock;
import ml.northwestwind.moreboots.init.block.RecyclerStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BootRecyclerBlockEntity extends BlockEntity {
    private ItemStackHandler handler = new ItemStackHandler(1);
    private RecyclerStorage storage = new RecyclerStorage(4000000, 0, 4000000);
    public int energy = storage.getEnergyStored();
    private int cookTime;

    public BootRecyclerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.BOOT_RECYCLER.get(), pos, state);
    }

    public void tick() {
        energy = storage.getEnergyStored();
        if (!handler.getStackInSlot(0).isEmpty() && isItemFuel(handler.getStackInSlot(0))) {
            if (level != null && !level.isClientSide) level.setBlockAndUpdate(worldPosition, level.getBlockState(worldPosition).setValue(BootRecyclerBlock.POWERED, true));
            cookTime++;
            energy += getFuelValue(handler.getStackInSlot(0)) / 100;
            if (cookTime == 1000) {
                cookTime = 0;
                handler.getStackInSlot(0).shrink(1);
            }
        } else if (level != null && !level.isClientSide) level.setBlockAndUpdate(worldPosition, level.getBlockState(worldPosition).setValue(BootRecyclerBlock.POWERED, false));
        storage.setEnergyStored(energy);
    }

    private boolean isItemFuel(ItemStack stack) {
        return getFuelValue(stack) > 0;
    }

    private int getFuelValue(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof ArmorItem)) return 0;
        if (!((ArmorItem) item).getSlot().equals(EquipmentSlot.FEET)) return 0;
        if (((ArmorItem) item).getMaterial() instanceof ItemInit.ModArmorMaterial) {
            int shouldGenerate = ((ItemInit.ModArmorMaterial) ((ArmorItem) item).getMaterial()).getEnergy();
            if (shouldGenerate > -1) return shouldGenerate;
        }
        return calculateFuelValue(stack);
    }

    private int calculateFuelValue(ItemStack stack) {
        ArmorMaterial material = ((ArmorItem) stack.getItem()).getMaterial();
        int armor = material.getDefenseForSlot(EquipmentSlot.FEET);
        float toughness = material.getToughness();
        int enchantability = material.getEnchantmentValue();
        int enchantments = EnchantmentHelper.getEnchantments(stack).size();
        int durability = material.getDurabilityForSlot(EquipmentSlot.FEET);

        return (int) ((Math.pow(durability, 2) / 2.0 + Math.pow(armor + toughness, 4) + Math.pow(enchantability + enchantments, 4)));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, final @Nullable Direction side) {
        if (cap.equals(CapabilityEnergy.ENERGY)) return LazyOptional.of(() -> (T) storage);
        if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) return LazyOptional.of(() -> (T) handler);
        return super.getCapability(cap, side);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        compound.put("Inventory", handler.getStackInSlot(0).serializeNBT());
        compound.putInt("GuiEnergy", energy);
        compound.putInt("CookTime", cookTime);
        storage.writeToNBT(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        handler.getStackInSlot(0).deserializeNBT(compound.getCompound("Inventory"));
        cookTime = compound.getInt("CookTime");
        energy = compound.getInt("GuiEnergy");
        storage.readFromNBT(compound);
    }
}
