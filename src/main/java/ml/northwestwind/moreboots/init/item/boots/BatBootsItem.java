package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;

public class BatBootsItem extends BootsItem {
    public BatBootsItem() {
        super(ItemInit.ModArmorMaterial.BAT, "bat_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        Vec3 upper = entity.position().add(0, entity.getBbHeight() + 1, 0);
        boolean climable = !entity.isOnGround() && !entity.level.isEmptyBlock(new BlockPos(upper)) && !entity.isCrouching();
        Vec3 motion = entity.getDeltaMovement();
        if (climable) entity.setDeltaMovement(motion.multiply(1, 0, 1));
        if (climable && entity.getRandom().nextInt(100) == 0)
            boots.hurtAndBreak(1, entity, entity1 -> entity1.playSound(SoundEvents.ITEM_BREAK, 1, 1));
    }
}
