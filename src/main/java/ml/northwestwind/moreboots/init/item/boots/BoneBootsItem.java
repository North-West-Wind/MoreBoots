package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.Iterator;

public class BoneBootsItem extends BootsItem {
    public BoneBootsItem() {
        super(ItemInit.ModArmorMaterial.BONE, "bone_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level.isClientSide || entity instanceof ArmorStand) return;
        BlockPos pos = new BlockPos(entity.position());
        Iterator<BlockPos> iterator = BlockPos.betweenClosedStream(pos.offset(5, 5, 5), pos.offset(-5, -5, -5)).iterator();
        while (iterator.hasNext()) {
            BlockPos blockPos = iterator.next();
            BlockState state = entity.level.getBlockState(blockPos);
            if (state.getBlock() instanceof BonemealableBlock)
                state.randomTick((ServerLevel) entity.level, blockPos, entity.getRandom());
        }
    }
}
