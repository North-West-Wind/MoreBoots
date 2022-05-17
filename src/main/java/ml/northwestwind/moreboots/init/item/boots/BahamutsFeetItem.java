package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CThrowTNTPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;

import java.util.Map;

public class BahamutsFeetItem extends BootsItem {
    public BahamutsFeetItem() {
        super(ItemInit.ModArmorMaterial.BAHAMUTS, "bahamuts_feet");
    }

    @Override
    public void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        ItemStack to = event.getTo();
        if (!event.getFrom().getItem().equals(ItemInit.BAHAMUTS_FEET.get()) && to.getItem().equals(ItemInit.BAHAMUTS_FEET.get())) {
            if (!EnchantmentHelper.hasBindingCurse(to)) {
                Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(to);
                enchantments.put(Enchantments.BINDING_CURSE, 1);
                EnchantmentHelper.setEnchantments(enchantments, to);
            }
        }
    }

    @Override
    public void onLivingKnockBack(LivingKnockBackEvent event) {
        event.setStrength(event.getStrength() * 10);
    }

    @Override
    public void activateBoots() {
        MoreBootsPacketHandler.INSTANCE.sendToServer(new CThrowTNTPacket());
    }
}
