package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;

public class LavaBootsItem extends BootsItem {
    public LavaBootsItem() {
        super(ItemInit.ModArmorMaterial.LAVA, "lava_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        Vec3 pos = entity.position();
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
