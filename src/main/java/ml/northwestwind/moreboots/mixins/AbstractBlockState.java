package ml.northwestwind.moreboots.mixins;

import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.EntitySelectionContext;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class AbstractBlockState {

    @Inject(at = @At("HEAD"), method = "getCollisionShape(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/shapes/ISelectionContext;)Lnet/minecraft/util/math/shapes/VoxelShape;", cancellable = true)
    public void getCollisionShape(IBlockReader worldIn, BlockPos pos, ISelectionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (!(context instanceof EntitySelectionContext)) return;
        EntitySelectionContext entCtx = (EntitySelectionContext) context;
        if (!(entCtx.getEntity() instanceof LivingEntity)) return;
        LivingEntity entity = (LivingEntity) entCtx.getEntity();
        ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
        if (boots.getItem() instanceof BootsItem) ((BootsItem) boots.getItem()).getCollisionShape(worldIn, pos, context, cir);
    }
}
