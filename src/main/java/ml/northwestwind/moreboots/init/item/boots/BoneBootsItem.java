package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassPathBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.Iterator;

public class BoneBootsItem extends BootsItem {
    public BoneBootsItem() {
        super(ItemInit.ModArmorMaterial.BONE, "bone_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.level.isClientSide) return;
        BlockPos pos = new BlockPos(entity.position());
        Iterator<BlockPos> iterator = BlockPos.betweenClosedStream(pos.offset(5, 5, 5), pos.offset(-5, -5, -5)).iterator();
        while (iterator.hasNext()) {
            BlockPos blockPos = iterator.next();
            BlockState state = entity.level.getBlockState(blockPos);
            if (state.getBlock() instanceof IGrowable)
                state.randomTick((ServerWorld) entity.level, blockPos, entity.getRandom());
        }
    }
}
