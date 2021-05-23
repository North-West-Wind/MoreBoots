package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

public class BlazeBootsItem extends BootsItem {
    public BlazeBootsItem() {
        super(ItemInit.ModArmorMaterial.BLAZE, "blaze_boots");
    }

    @Override
    public void onLivingDamage(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        if (source.equals(DamageSource.IN_FIRE) || source.equals(DamageSource.LAVA) || source.equals(DamageSource.ON_FIRE))
            event.setCanceled(true);
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        BlockPos blockPos = entity.blockPosition();
        BlockPos under = blockPos.below();
        BlockState underneath = entity.level.getBlockState(under);
        if (underneath.canOcclude() && entity.level.isEmptyBlock(blockPos))
            entity.level.setBlockAndUpdate(blockPos, Blocks.FIRE.defaultBlockState());
    }
}
