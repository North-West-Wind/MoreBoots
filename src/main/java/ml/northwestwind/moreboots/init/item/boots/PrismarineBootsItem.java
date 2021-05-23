package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.living.LivingEvent;

public class PrismarineBootsItem extends BootsItem {
    public PrismarineBootsItem() {
        super(ItemInit.ModArmorMaterial.PRISMARINE, "prismarine_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (!entity.isVisuallySwimming() || !entity.isInWater()) return;
        ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
        Vector3d motion = entity.getDeltaMovement();
        Vector3d direction = entity.getLookAngle().scale(0.15);
        entity.setDeltaMovement(motion.multiply(1.01, 1, 1.01).add(direction));
        if (entity.getRandom().nextInt(100) == 0)
            boots.hurtAndBreak(1, entity, entity1 -> entity1.playSound(SoundEvents.ITEM_BREAK, 1, 1));
    }
}
