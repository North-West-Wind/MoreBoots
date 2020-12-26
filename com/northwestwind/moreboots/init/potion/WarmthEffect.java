package com.northwestwind.moreboots.init.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;

public class WarmthEffect extends Effect {
    public WarmthEffect() {
        super(EffectType.NEUTRAL, 16738816);
    }

    @Override
    public void performEffect(@Nonnull LivingEntity livingEntity, int amplifier) {
        Vector3d pos = livingEntity.getPositionVec();
        Biome biome = livingEntity.world.getBiome(new BlockPos(pos));
        float temperature = biome.getTemperature(new BlockPos(pos));
        if (temperature < 0.2) applyAttributesModifiersToEntity(livingEntity, livingEntity.func_233645_dx_(), 0);
        else removeAttributesModifiersFromEntity(livingEntity, livingEntity.func_233645_dx_(), 0);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration > 0;
    }
}
