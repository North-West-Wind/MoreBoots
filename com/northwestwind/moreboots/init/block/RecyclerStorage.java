package com.northwestwind.moreboots.init.block;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.EnergyStorage;

public class RecyclerStorage extends EnergyStorage {
    public RecyclerStorage(int capacity) {
        super(capacity);
    }

    public RecyclerStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public RecyclerStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public RecyclerStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public void setEnergyStored(int energy) {
        this.energy = energy;
    }

    public void readFromNBT(CompoundNBT compound) {
        this.energy = compound.getInt("Energy");
        this.capacity = compound.getInt("Capacity");
        this.maxReceive = compound.getInt("MaxReceive");
        this.maxExtract = compound.getInt("MaxExtract");
    }

    public void writeToNBT(CompoundNBT compound) {
        compound.putInt("Energy", this.energy);
        compound.putInt("Capacity", this.capacity);
        compound.putInt("MaxReceive", this.maxReceive);
        compound.putInt("MaxExtract", this.maxExtract);
    }
}
