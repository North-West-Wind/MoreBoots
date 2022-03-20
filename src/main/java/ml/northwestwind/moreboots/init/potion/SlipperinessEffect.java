package ml.northwestwind.moreboots.init.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class SlipperinessEffect extends MobEffect {
    public SlipperinessEffect() {
        super(MobEffectCategory.NEUTRAL, 250876);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
