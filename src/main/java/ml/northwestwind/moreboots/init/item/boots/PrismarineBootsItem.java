package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;

public class PrismarineBootsItem extends BootsItem {
    public PrismarineBootsItem() {
        super(ItemInit.ModArmorMaterial.PRISMARINE, "prismarine_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (!entity.isVisuallySwimming() || !entity.isInWater()) return;
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        Vec3 motion = entity.getDeltaMovement();
        Vec3 direction = entity.getLookAngle().scale(0.15);
        entity.setDeltaMovement(motion.multiply(1.02, 1, 1.02).add(direction));
        boots.hurtAndBreak(1, entity, entity1 -> entity1.playSound(SoundEvents.ITEM_BREAK, 1, 1));
    }
}
