package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.BlockInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.block.RedstoneDustBlock;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;

public class RedstoneBootsItem extends BootsItem {
    public RedstoneBootsItem() {
        super(ItemInit.ModArmorMaterial.REDSTONE, "redstone_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        Vec3 pos = entity.position();
        BlockPos blockPos = new BlockPos(pos);
        BlockPos under = blockPos.below();
        BlockState underneath = entity.level.getBlockState(under);
        if (underneath.canOcclude() && entity.level.isEmptyBlock(blockPos) && entity.getRandom().nextInt(100) == 0) {
            entity.level.setBlockAndUpdate(blockPos, BlockInit.REDSTONE_DUST.get().defaultBlockState().setValue(RedstoneDustBlock.FACING, RedstoneDustBlock.getRandomDirection()));
            boots.hurtAndBreak(1, entity, playerEntity -> playerEntity.playSound(SoundEvents.ITEM_BREAK, 1.0f, 1.0f));
        }
    }
}
