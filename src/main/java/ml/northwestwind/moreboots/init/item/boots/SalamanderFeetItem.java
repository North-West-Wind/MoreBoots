package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.stream.Collectors;

public class SalamanderFeetItem extends BootsItem {
    private static final List<MobEffect> BENEFICIAL_EFFECTS;

    public SalamanderFeetItem() {
        super(ItemInit.ModArmorMaterial.SALAMANDER, "salamander_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        BlockPos pos = entity.blockPosition();
        int skylight = entity.level.getLightEngine().getLayerListener(LightLayer.SKY).getLightValue(pos);
        Biome biome = entity.level.getBiome(pos).value();
        int amplifier = (int) (skylight * (biome.getBaseTemperature() - (pos.getY() - entity.level.getSeaLevel()) / 600) / 5);
        if (!entity.hasEffect(MobEffects.REGENERATION) || entity.getEffect(MobEffects.REGENERATION).getAmplifier() < amplifier + 1)
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 10, amplifier, false, false, false));
        if (entity.level.random.nextDouble() < 0.05) {
            MobEffect effect = BENEFICIAL_EFFECTS.get(entity.level.random.nextInt(BENEFICIAL_EFFECTS.size()));
            int amp = entity.level.random.nextInt(3);
            if (!entity.hasEffect(effect) || entity.getEffect(effect).getAmplifier() < amp + 1)
                entity.addEffect(new MobEffectInstance(effect, 100 + entity.level.random.nextInt(500), amp, false, false, true));
        }
    }

    static {
        BENEFICIAL_EFFECTS = ForgeRegistries.MOB_EFFECTS.getValues().stream().filter(MobEffect::isBeneficial).collect(Collectors.toList());
    }
}
