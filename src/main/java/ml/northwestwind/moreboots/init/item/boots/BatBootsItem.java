package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.living.LivingEvent;

public class BatBootsItem extends BootsItem {
    public BatBootsItem() {
        super(ItemInit.ModArmorMaterial.BAT, "bat_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
        Vector3d upper = entity.position().add(0, entity.getBbHeight() + 1, 0);
        boolean climable = !entity.isOnGround() && !entity.level.isEmptyBlock(new BlockPos(upper)) && !entity.isCrouching();
        Vector3d motion = entity.getDeltaMovement();
        if (climable) entity.setDeltaMovement(motion.multiply(1, 0, 1));
        if (climable && entity.getRandom().nextInt(100) == 0)
            boots.hurtAndBreak(1, entity, entity1 -> entity1.playSound(SoundEvents.ITEM_BREAK, 1, 1));
    }
}
