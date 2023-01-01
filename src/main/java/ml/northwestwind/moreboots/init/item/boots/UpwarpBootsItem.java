package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.event.entity.living.LivingEvent;

public class UpwarpBootsItem extends BootsItem {
    public UpwarpBootsItem() {
        super(ItemInit.ModArmorMaterial.UPWARP, "upwarp_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        BlockPos pos = new BlockPos(entity.position());
        BlockPos original = pos;
        if (!entity.isOnGround()) {
            boolean isThisAirBlock = entity.level.isEmptyBlock(pos) || entity.level.getBlockState(pos).getCollisionShape(entity.level, pos).equals(Shapes.empty());
            boolean isLastAirBlock = isThisAirBlock;
            while ((isLastAirBlock || !isThisAirBlock) && pos.getY() < 256) {
                isLastAirBlock = isThisAirBlock;
                pos = pos.above();
                isThisAirBlock = entity.level.isEmptyBlock(pos) || entity.level.getBlockState(pos).getCollisionShape(entity.level, pos).equals(Shapes.empty());
            }
            if (pos.getY() >= 255 || original.equals(pos.below())) return;
            entity.setPos(entity.getX(), pos.getY(), entity.getZ());
        }
    }
}
