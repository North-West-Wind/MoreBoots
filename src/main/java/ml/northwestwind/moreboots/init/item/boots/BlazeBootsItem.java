package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class BlazeBootsItem extends BootsItem {
    public BlazeBootsItem() {
        super(ItemInit.ModArmorMaterial.BLAZE, "blaze_boots");
    }

    @Override
    public void onLivingHurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        if (source.equals(DamageSource.IN_FIRE) || source.equals(DamageSource.LAVA) || source.equals(DamageSource.ON_FIRE))
            event.setCanceled(true);
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        BlockPos blockPos = entity.blockPosition();
        BlockPos under = blockPos.below();
        BlockState underneath = entity.level.getBlockState(under);
        if (underneath.canOcclude() && entity.level.isEmptyBlock(blockPos))
            entity.level.setBlockAndUpdate(blockPos, Blocks.FIRE.defaultBlockState());
    }
}
