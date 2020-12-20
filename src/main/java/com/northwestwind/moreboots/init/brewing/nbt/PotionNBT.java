package com.northwestwind.moreboots.init.brewing.nbt;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

public class PotionNBT implements INBTSerializable<CompoundNBT> {
    private String name;
    private int amp;
    public PotionNBT() { }

    public PotionNBT(ResourceLocation registryName, int amp) {
        name = registryName.toString();
        this.amp = amp;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putString("Potion", name);
        tag.putInt("Amplifier", amp);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        name = nbt.getString("Potion");
        amp = nbt.getInt("Amplifier");
    }

    public String getName() {
        return name;
    }
    public int getAmp() {
        return amp;
    }
}
