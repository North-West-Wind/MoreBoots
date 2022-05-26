package ml.northwestwind.moreboots.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class MoreBootsConfig {
    private static final Pair<MoreBootsConfig, ForgeConfigSpec> PAIR;
    private final ForgeConfigSpec.IntValue viscousity, bounciness, weight, worn, ability, aftermath;

    private MoreBootsConfig(ForgeConfigSpec.Builder builder) {
        viscousity = builder.comment("Viscousity of Bionic Beetle Feet. 0: Sticky, 1: Slippery").defineInRange("bionic.viscousity", 0, 0, 1);
        bounciness = builder.comment("Bounciness of Bionic Beetle Feet. 0: Super jump, 1: Multi-jump, 2: Jump boost").defineInRange("bionic.bounciness", 0, 0, 2);
        weight = builder.comment("Weight of Bionic Beetle Feet. 0: Light, 1: Heavy").defineInRange("bionic.weight", 0, 0, 1);
        worn = builder.comment("Whether the viscousity is reversed for Bionic Beetle Feet. 0: Normal, 1: Reversed").defineInRange("bionic.worn", 0, 0, 1);
        ability = builder.comment("Abilities of Bionic Beetle Feet. 0: Trailing, 1: Lightweight, 2: Slow falling, 3: Speed, 4: Swim, 5: Float, 6: Fast when no sprint, 7: Yoshi, 8: Build up speed for 12 hours to reach parallel universe, 9: Dash").defineInRange("bionic.ability", 0, 0, 9);
        aftermath = builder.comment("Aftermath of Bionic Beetle Feet. Last for exactly 15 seconds. 0: Fast when no sprint, 1: Yoshi, 2: Jump boost, 3: Speed, 4: Slow falling, 5: Slippery, 6: Sticky, 7: Charge jumping, 8: Dash, 9: Swim faster, 10: Water/Lava/Power Snow Walker").defineInRange("bionic.aftermath", 0, 0, 10);
    }

    public int getViscousity() {
        return viscousity.get();
    }

    public int getBounciness() {
        return bounciness.get();
    }

    public int getWeight() {
        return weight.get();
    }

    public int getWorn() {
        return worn.get();
    }

    public int getAbility() {
        return ability.get();
    }

    public int getAftermath() {
        return aftermath.get();
    }

    public static MoreBootsConfig getConfig() {
        return PAIR.getLeft();
    }

    public static ForgeConfigSpec getConfigSpec() {
        return PAIR.getRight();
    }

    static {
        PAIR = new ForgeConfigSpec.Builder().configure(MoreBootsConfig::new);
    }
}
