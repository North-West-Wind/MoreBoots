package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;

public class ObsidianBootsItem extends BootsItem {
    public ObsidianBootsItem() {
        super(ItemInit.ModArmorMaterial.OBSIDIAN, "obsidian_boots");
    }

    @Override
    public void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        EquipmentSlotType slot = event.getSlot();
        if (slot.equals(EquipmentSlotType.FEET)) return;
        LivingEntity entity = event.getEntityLiving();
        ItemStack equipment = entity.getItemBySlot(slot);
        if (!equipment.isDamageableItem()) return;
        ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
        ItemStack to = event.getTo();
        ItemStack from = event.getFrom();
        int damage = to.getDamageValue() - from.getDamageValue();
        int maxRepair = boots.getMaxDamage() - boots.getDamageValue();
        if (maxRepair > damage) equipment.setDamageValue(0);
        else equipment.setDamageValue(equipment.getDamageValue() - maxRepair);
        boots.hurtAndBreak(damage, entity, entity1 -> entity1.playSound(SoundEvents.ANVIL_BREAK, 1, 1));
    }
}
