package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.living.LivingEvent;

public class LavaBootsItem extends BootsItem {
    public LavaBootsItem() {
        super(ItemInit.ModArmorMaterial.LAVA, "lava_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
        Vector3d pos = entity.position();
        BlockPos blockPos = new BlockPos(pos);
        BlockPos under = blockPos.below();
        Block block = entity.level.getBlockState(blockPos).getBlock();
        FluidState underneath = entity.level.getFluidState(under);
        if (underneath.getType() instanceof WaterFluid) {
            WaterFluid water = (WaterFluid) underneath.getType();
            if (water.isSource(underneath))
                entity.level.setBlockAndUpdate(under, Blocks.STONE.defaultBlockState());
            else entity.level.setBlockAndUpdate(under, Blocks.COBBLESTONE.defaultBlockState());
            entity.playSound(SoundEvents.LAVA_EXTINGUISH, 1, 1);
            boots.hurtAndBreak(1, entity, playerEntity -> playerEntity.playSound(SoundEvents.ITEM_BREAK, 1, 1));
        } else if (block.equals(Blocks.ICE) || block.equals(Blocks.FROSTED_ICE))
            entity.level.setBlockAndUpdate(blockPos, Blocks.WATER.defaultBlockState());
        if (entity.isInLava() && boots.getMaxDamage() - boots.getDamageValue() > 0 && entity.getRandom().nextInt(10) == 0) {
            boots.setDamageValue(Math.max(boots.getDamageValue() - 2, 0));
        }
    }
}
