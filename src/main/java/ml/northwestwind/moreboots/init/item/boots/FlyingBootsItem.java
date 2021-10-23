package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;

import javax.annotation.Nullable;
import java.util.List;

public class FlyingBootsItem extends BootsItem {
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
        if (!event.getSlot().equals(EquipmentSlot.FEET)) return;
        if (!(event.getEntityLiving() instanceof Player player)) return;
        ItemStack from = event.getFrom();
        ItemStack to = event.getTo();
        boolean oldFlying = player.getAbilities().mayfly;
        if (!from.getItem().equals(ItemInit.FLYING_BOOTS) && to.getItem().equals(ItemInit.FLYING_BOOTS)) player.getAbilities().mayfly = true;
        else if (from.getItem().equals(ItemInit.FLYING_BOOTS) && !to.getItem().equals(ItemInit.FLYING_BOOTS)) player.getAbilities().mayfly = false;
        if (oldFlying != player.getAbilities().mayfly) player.onUpdateAbilities();
    }
}