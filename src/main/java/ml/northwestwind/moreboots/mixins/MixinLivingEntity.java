package ml.northwestwind.moreboots.mixins;

import ml.northwestwind.moreboots.init.EffectInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.boots.SlipperyBootsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    @Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getFriction(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)F"))
    public float getFriction(BlockState instance, LevelReader levelReader, BlockPos blockPos, Entity entity) {
        LivingEntity lvEnt = (LivingEntity) (Object) this;
        if (lvEnt.getItemBySlot(EquipmentSlot.FEET).getItem().equals(ItemInit.SLIPPERY_BOOTS.get()) && !lvEnt.isCrouching()) return SlipperyBootsItem.SLIPPERINESS;
        else if (lvEnt.hasEffect(EffectInit.SLIPPERINESS.get())) return 0.989F + lvEnt.getEffect(EffectInit.SLIPPERINESS.get()).getAmplifier() * 0.05F;
        else return instance.getFriction(levelReader, blockPos, entity);
    }
}
