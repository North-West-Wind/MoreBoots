package ml.northwestwind.moreboots.init.item.boots;

import com.google.common.collect.Maps;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.Map;
import java.util.UUID;

public class AwakenedBahamutsFeet extends BootsItem {
    private final Map<UUID, Boolean> originalMayFly = Maps.newHashMap();

    public AwakenedBahamutsFeet() {
        super(ItemInit.ModArmorMaterial.AWAKENED, "awakened_bahamuts_feet");
    }

    @Override
    public void onLivingEquipmentChange(LivingEquipmentChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ItemStack from = event.getFrom();
        ItemStack to = event.getTo();
        if (!from.getItem().equals(ItemInit.AWAKENED_BAHAMUTS_FEET.get()) && to.getItem().equals(ItemInit.AWAKENED_BAHAMUTS_FEET.get())) {
            originalMayFly.put(player.getUUID(), player.getAbilities().mayfly);
            player.getAbilities().mayfly = true;
            player.onUpdateAbilities();
        } else if (from.getItem().equals(ItemInit.AWAKENED_BAHAMUTS_FEET.get()) && !to.getItem().equals(ItemInit.AWAKENED_BAHAMUTS_FEET.get())) {
            player.getAbilities().mayfly = originalMayFly.get(player.getUUID());
            originalMayFly.remove(player.getUUID());
            if (player.getAbilities().flying && !player.getAbilities().mayfly) player.getAbilities().flying = false;
            player.onUpdateAbilities();
        }
    }

    @Override
    public void onLivingAttack(LivingHurtEvent event) {
        event.setAmount(event.getAmount() + 69);
    }
}
