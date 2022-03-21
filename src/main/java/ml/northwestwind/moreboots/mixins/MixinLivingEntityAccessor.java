package ml.northwestwind.moreboots.mixins;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface MixinLivingEntityAccessor {
    @Accessor("jumping")
    boolean isJumping();
}
