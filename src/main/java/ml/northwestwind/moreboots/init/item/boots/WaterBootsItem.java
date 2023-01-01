package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;

public class WaterBootsItem extends BootsItem {
    public WaterBootsItem() {
        super(ItemInit.ModArmorMaterial.WATER, "water_boots");
    }

    @Override
    public void onLivingUpdate(final LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        Vec3 pos = entity.position();
        BlockPos blockPos = new BlockPos(pos);
        BlockPos under = blockPos.below();
        FluidState underneath = entity.level.getFluidState(under);
        BlockState underneathBlock = entity.level.getBlockState(under);
        if (underneath.getType() instanceof LavaFluid && !(underneathBlock.getBlock() instanceof SimpleWaterloggedBlock)) {
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
