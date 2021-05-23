package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraftforge.event.entity.living.LivingEvent;

public class DownwarpBootsItem extends BootsItem {
    public DownwarpBootsItem() {
        super(ItemInit.ModArmorMaterial.DOWNWARP, "downwarp_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        BlockPos pos = entity.blockPosition();
        if (!entity.isOnGround()) {
            boolean isThisAirBlock = entity.level.isEmptyBlock(pos) || entity.level.getBlockState(pos).getCollisionShape(entity.level, pos).equals(VoxelShapes.empty());
            while (isThisAirBlock && pos.getY() > 0) {
                pos = pos.below();
                isThisAirBlock = entity.level.isEmptyBlock(pos) || entity.level.getBlockState(pos).getCollisionShape(entity.level, pos).equals(VoxelShapes.empty());
            }
            if (pos.getY() <= 0 || entity.getY() - pos.above().getY() < 0.2) return;
            entity.fallDistance = 0;
            entity.setPos(entity.getX(), pos.getY() + 1, entity.getZ());
        }
    }
}
