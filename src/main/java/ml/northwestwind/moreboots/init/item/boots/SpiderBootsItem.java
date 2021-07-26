package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;

public class SpiderBootsItem extends BootsItem {
    public SpiderBootsItem() {
        super(ItemInit.ModArmorMaterial.SPIDER, "spider_boots");
    }

    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        boolean climable = !Utils.isSurroundedByInvalidBlocks(entity) && !entity.isInLava() && !entity.isInWater() && !entity.isSpectator() && !entity.isOnGround();
        Vec3 motion = entity.getDeltaMovement();
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
