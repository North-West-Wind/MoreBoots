package ml.northwestwind.moreboots.init.potion;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

public class WarmthEffect extends MobEffect {
    public WarmthEffect() {
        super(MobEffectCategory.NEUTRAL, 16738816);
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity livingEntity, int amplifier) {
        Vec3 pos = livingEntity.position();
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
