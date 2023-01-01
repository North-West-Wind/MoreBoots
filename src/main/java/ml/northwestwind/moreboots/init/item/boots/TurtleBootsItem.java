package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import javax.annotation.Nullable;
import java.util.List;

public class TurtleBootsItem extends BootsItem {
    public TurtleBootsItem() {
        super(ItemInit.ModArmorMaterial.TURTLE, "turtle_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (!entity.isVisuallySwimming() || !entity.isInWater()) return;
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        Vec3 motion = entity.getDeltaMovement();
        Vec3 direction = entity.getLookAngle().scale(0.05);
        entity.setDeltaMovement(motion.add(direction));
        boots.hurtAndBreak(1, entity, entity1 -> entity1.playSound(SoundEvents.ITEM_BREAK, 1, 1));
    }

    @Override
    public void onLivingFall(LivingFallEvent event) {
        event.setDamageMultiplier(event.getDamageMultiplier() * 0.9f);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(MutableComponent.create(new TranslatableContents("credit.moreboots."+registryName)));
    }
}
