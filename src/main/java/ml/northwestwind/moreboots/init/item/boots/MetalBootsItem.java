package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class MetalBootsItem extends BootsItem {
    public MetalBootsItem() {
        super(ItemInit.ModArmorMaterial.METAL, "metal_boots");
    }

    @Override
    public void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        if (entity.level.isClientSide) return;
        event.setCanceled(true);
        boots.hurtAndBreak(1, entity, entity1 -> entity1.playSound(SoundEvents.GLASS_BREAK, 1.0f, 1.0f));
    }
}
