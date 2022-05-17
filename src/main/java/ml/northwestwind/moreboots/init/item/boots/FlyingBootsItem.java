package ml.northwestwind.moreboots.init.item.boots;

import com.google.common.collect.Maps;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FlyingBootsItem extends BootsItem {
    private final Map<UUID, Boolean> originalMayFly = Maps.newHashMap();

    public FlyingBootsItem() {
        super(ItemInit.ModArmorMaterial.FLYING, "flying_boots");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("credit.moreboots."+registryName));
    }

    @Override
    public void onLivingEquipmentChange(final LivingEquipmentChangeEvent event) {
        if (!(event.getEntityLiving() instanceof Player player)) return;
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
}