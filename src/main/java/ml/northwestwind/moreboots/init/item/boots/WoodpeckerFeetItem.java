package ml.northwestwind.moreboots.init.item.boots;

import com.google.common.collect.Maps;
import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.Map;
import java.util.UUID;

public class WoodpeckerFeetItem extends SuperAvianFeetItem {
    private final Map<UUID, Boolean> originalMayFly = Maps.newHashMap();

    public WoodpeckerFeetItem() {
        super(ItemInit.ModArmorMaterial.WOODPECKER, "woodpecker_feet");
    }

    @Override
    public void onLivingEquipmentChange(final LivingEquipmentChangeEvent event) {
        if (!(event.getEntityLiving() instanceof Player player)) return;
        ItemStack from = event.getFrom();
        ItemStack to = event.getTo();
        if (!from.getItem().equals(ItemInit.WOODPECKER_FEET.get()) && to.getItem().equals(ItemInit.WOODPECKER_FEET.get())) {
            originalMayFly.put(player.getUUID(), player.getAbilities().mayfly);
            player.getAbilities().mayfly = true;
            player.onUpdateAbilities();
        } else if (from.getItem().equals(ItemInit.WOODPECKER_FEET.get()) && !to.getItem().equals(ItemInit.WOODPECKER_FEET.get())) {
            player.getAbilities().mayfly = originalMayFly.get(player.getUUID());
            originalMayFly.remove(player.getUUID());
            if (player.getAbilities().flying && !player.getAbilities().mayfly) player.getAbilities().flying = false;
            player.onUpdateAbilities();
        }
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.getDeltaMovement().x() == 0 && entity.getDeltaMovement().z() == 0) return;
        if (entity.getDeltaMovement().y() < 0.02 && !entity.isCrouching()) {
            if (entity instanceof Player player && player.getAbilities().flying) return;
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.05, 0, 1.05).add(0, -0.02, 0));
            entity.hasImpulse = true;
            entity.fallDistance = 0;
        }
    }
}
