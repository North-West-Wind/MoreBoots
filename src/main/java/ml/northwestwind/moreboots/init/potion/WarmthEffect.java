package ml.northwestwind.moreboots.init.potion;

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
    public void applyEffectTick(@Nonnull LivingEntity livingEntity, int amplifier) {
        Vector3d pos = livingEntity.position();
        Biome biome = livingEntity.level.getBiome(new BlockPos(pos));
        float temperature = biome.getTemperature(new BlockPos(pos));
        if (temperature < 0.2) addAttributeModifiers(livingEntity, livingEntity.getAttributes(), 0);
        else removeAttributeModifiers(livingEntity, livingEntity.getAttributes(), 0);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
