package ml.northwestwind.moreboots.mixins;

import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.EntitySelectionContext;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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
        ItemStack boots = entity.getItemStackFromSlot(EquipmentSlotType.FEET);
        if (boots.getItem().equals(ItemInit.VANISHING_BOOTS) && (!isSolid() || getMaterial().equals(Material.GLASS))) cir.setReturnValue(VoxelShapes.empty());
        else if (boots.getItem().equals(ItemInit.FLOATIE_BOOTS) && getMaterial().equals(Material.WATER) && context.func_216378_a(VoxelShapes.fullCube(), pos, true)) cir.setReturnValue(VoxelShapes.fullCube());
        else if (boots.getItem().equals(ItemInit.STRIDER_BOOTS) && getMaterial().equals(Material.LAVA) && context.func_216378_a(VoxelShapes.fullCube(), pos, true)) cir.setReturnValue(VoxelShapes.fullCube());
    }

    @Shadow
    public boolean isSolid() {
        throw new IllegalStateException("Mixin failed to shadow isSolid()");
    }

    @Shadow
    public Material getMaterial() {
        throw new IllegalStateException("Mixin failed to shadow getMaterial()");
    }
}
