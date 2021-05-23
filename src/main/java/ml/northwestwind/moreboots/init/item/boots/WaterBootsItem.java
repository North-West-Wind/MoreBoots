package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.living.LivingEvent;

public class WaterBootsItem extends BootsItem {
    public WaterBootsItem() {
        super(ItemInit.ModArmorMaterial.WATER, "water_boots");
    }

    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
        Vector3d pos = entity.position();
        BlockPos blockPos = new BlockPos(pos);
        BlockPos under = blockPos.below();
        FluidState underneath = entity.level.getFluidState(under);
        BlockState underneathBlock = entity.level.getBlockState(under);
        if (underneath.getType() instanceof LavaFluid && !(underneathBlock.getBlock() instanceof IWaterLoggable)) {
            LavaFluid lava = (LavaFluid) underneath.getType();
            if (lava.isSource(underneath)) entity.level.setBlockAndUpdate(under, Blocks.OBSIDIAN.defaultBlockState());
            else entity.level.setBlockAndUpdate(under, Blocks.COBBLESTONE.defaultBlockState());
            entity.playSound(SoundEvents.LAVA_EXTINGUISH, 1, 1);
            boots.hurtAndBreak(1, entity, playerEntity -> playerEntity.playSound(SoundEvents.ITEM_BREAK, 1, 1));
        } else if (entity.level.getBlockState(blockPos).getBlock().equals(Blocks.FIRE))
            entity.level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
        if (entity.isInWater() && boots.getMaxDamage() - boots.getDamageValue() > 0 && entity.getRandom().nextInt(10) == 0)
            boots.setDamageValue(Math.max(boots.getDamageValue() - 2, 0));
    }
}
