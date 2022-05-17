package ml.northwestwind.moreboots;

import net.minecraft.world.damagesource.DamageSource;

public class Reference {
    public static final String MODID = "moreboots";

    public static final DamageSource STOMP = (new DamageSource("stomp")).damageHelmet();
    public static final DamageSource WATER = (new DamageSource("water")).bypassArmor();
}
