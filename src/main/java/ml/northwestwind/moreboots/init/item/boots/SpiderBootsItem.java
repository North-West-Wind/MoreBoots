package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.living.LivingEvent;

public class SpiderBootsItem extends BootsItem {
    public SpiderBootsItem() {
        super(ItemInit.ModArmorMaterial.SPIDER, "spider_boots");
    }

    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
        boolean climable = !Utils.isSurroundedByInvalidBlocks(entity) && !entity.isInLava() && !entity.isInWater() && !entity.isSpectator() && !entity.isOnGround();
        Vector3d motion = entity.getDeltaMovement();
        motion = motion.multiply(1, 0, 1);
        boolean ascending = entity.horizontalCollision;
        boolean descending = !ascending && entity.isCrouching();
        if (climable) {
            if (!ascending && !descending) entity.setDeltaMovement(motion);
            else if (ascending) entity.setDeltaMovement(motion.add(0, 0.2, 0));
            else entity.setDeltaMovement(motion.subtract(0, 0.2, 0));
            if (entity.getRandom().nextInt(100) == 0)
                boots.hurtAndBreak(1, entity, entity1 -> entity1.playSound(SoundEvents.ITEM_BREAK, 1, 1));
            entity.fallDistance = 0f;
        }
    }
}
