package ml.northwestwind.moreboots.config;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class MoreBootsConfig {
    private static final Pair<MoreBootsConfig, ForgeConfigSpec> PAIR;
    private final ForgeConfigSpec.IntValue weight;
    private final ForgeConfigSpec.ConfigValue<List<? extends Integer>> abilities, aftermaths;

    private MoreBootsConfig(ForgeConfigSpec.Builder builder) {
        weight = builder.comment("Weight of Bionic Beetle Feet. 0: Light, 1: Heavy").defineInRange("bionic.weight", 0, 0, 1);

        abilities = builder.comment("Abilities of Bionic Beetle Feet. 0: Trailing, 1: Lightweight, 2: Slow falling, 3: Speed, 4: Swim, 5: Float, 6: Fast when no sprint, 7: Yoshi, 8: Build up speed for 12 hours to reach parallel universe, 9: Dash").defineList("bionic.abilities", Lists.newArrayList(0,1,2,3,4,5,6,7,8,9), o -> (o instanceof Integer ii) && ii >= 0 && ii <= 9);
        aftermaths = builder.comment("Aftermath of Bionic Beetle Feet. Last for 15 seconds. 0: Fast when no sprint, 1: Yoshi, 2: Jump boost, 3: Speed, 4: Slow falling, 5: Slippery, 6: Sticky, 7: Charge jumping, 8: Dash, 9: Swim faster, 10: Water/Lava/Power Snow Walker").defineList("bionic.aftermaths", Lists.newArrayList(0,1,2,3,4,5,6,7,8,9,10), o -> (o instanceof Integer ii) && ii >= 0 && ii <= 10);
    }

    public int getWeight() {
        return weight.get();
    }

    public List<Integer> getAbilities() {
        return (List<Integer>) abilities.get();
    }

    public List<Integer> getAftermaths() {
        return (List<Integer>) aftermaths.get();
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
