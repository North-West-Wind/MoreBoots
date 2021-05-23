package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.BlockInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.block.GlowstoneDustBlock;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.living.LivingEvent;

public class GlowstoneBootsItem extends BootsItem {
    public GlowstoneBootsItem() {
        super(ItemInit.ModArmorMaterial.GLOWSTONE, "glowstone_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
        Vector3d pos = entity.position();
        BlockPos blockPos = new BlockPos(pos);
        BlockPos under = blockPos.below();
        BlockState underneath = entity.level.getBlockState(under);
        if (underneath.canOcclude() && entity.level.isEmptyBlock(blockPos) && entity.getRandom().nextInt(100) == 0) {
            entity.level.setBlockAndUpdate(blockPos, BlockInit.GLOWSTONE_DUST.defaultBlockState().setValue(GlowstoneDustBlock.FACING, GlowstoneDustBlock.getRandomDirection()));
            boots.hurtAndBreak(1, entity, playerEntity -> playerEntity.playSound(SoundEvents.ITEM_BREAK, 1.0f, 1.0f));
        }
    }
}
