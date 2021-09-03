package ml.northwestwind.moreboots.mixins;

import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class MixinBlockStateBase {
    @Inject(at = @At("HEAD"), method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;", cancellable = true)
    public void getCollisionShape(BlockGetter worldIn, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (!(context instanceof EntityCollisionContext)) return;
        EntityCollisionContext entCtx = (EntityCollisionContext) context;
        if (!entCtx.getEntity().isPresent() || !(entCtx.getEntity().get() instanceof LivingEntity entity)) return;
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).getCollisionShape(worldIn, pos, context, cir);
    }
}
