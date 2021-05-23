package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class MetalBootsItem extends BootsItem {
    public MetalBootsItem() {
        super(ItemInit.ModArmorMaterial.METAL, "metal_boots");
    }

    @Override
    public void onLivingDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
        if (entity.level.isClientSide) return;
        event.setCanceled(true);
        boots.hurtAndBreak(1, entity, entity1 -> entity1.playSound(SoundEvents.GLASS_BREAK, 1.0f, 1.0f));
    }
}
