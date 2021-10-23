package ml.northwestwind.moreboots.mixins;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.boots.SlipperyBootsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import javax.annotation.Nullable;

@Mixin(Boat.class)
public abstract class MixinBoat {
    @Shadow @Nullable public abstract Entity getControllingPassenger();

    @Redirect(method = "getGroundFriction", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getFriction(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)F"))
    public float getFriction(BlockState instance, LevelReader levelReader, BlockPos blockPos, Entity entity) {
        Entity passenger = this.getControllingPassenger();
        if (!(passenger instanceof LivingEntity) || !((LivingEntity) passenger).getItemBySlot(EquipmentSlot.FEET).getItem().equals(ItemInit.SLIPPERY_BOOTS)) return instance.getFriction(levelReader, blockPos, entity);
        return SlipperyBootsItem.SLIPPERINESS;
    }
}
