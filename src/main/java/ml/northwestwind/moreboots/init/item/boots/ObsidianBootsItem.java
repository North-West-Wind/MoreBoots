package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;

public class ObsidianBootsItem extends BootsItem {
    public ObsidianBootsItem() {
        super(ItemInit.ModArmorMaterial.OBSIDIAN, "obsidian_boots");
    }

    @Override
    public void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        EquipmentSlot slot = event.getSlot();
        if (slot.equals(EquipmentSlot.FEET)) return;
        LivingEntity entity = event.getEntityLiving();
        ItemStack equipment = entity.getItemBySlot(slot);
        if (!equipment.isDamageableItem()) return;
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        ItemStack to = event.getTo();
        ItemStack from = event.getFrom();
        int damage = to.getDamageValue() - from.getDamageValue();
        int maxRepair = boots.getMaxDamage() - boots.getDamageValue();
        if (maxRepair > damage) equipment.setDamageValue(0);
        else equipment.setDamageValue(equipment.getDamageValue() - maxRepair);
        boots.hurtAndBreak(damage, entity, entity1 -> entity1.playSound(SoundEvents.ANVIL_BREAK, 1, 1));
    }
}
