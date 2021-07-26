package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class QuartzBootsItem extends BootsItem {
    public QuartzBootsItem() {
        super(ItemInit.ModArmorMaterial.QUARTZ, "quartz_boots");
    }

    @Override
    public void onLivingFall(final LivingFallEvent event) {
        LivingEntity entity = event.getEntityLiving();
        float distance = event.getDistance();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        if (entity.level.isClientSide) return;
        if (distance < 3.0f) return;
        boots.hurtAndBreak((int) (4 * distance), entity, playerEntity -> playerEntity.playSound(SoundEvents.ITEM_BREAK, 1.0f, 1.0f));
    }
}
