package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;

import javax.annotation.Nullable;
import java.util.List;

public class AvianFeetItem extends BootsItem {
    public AvianFeetItem() {
        super(ItemInit.ModArmorMaterial.AVIAN, "avian_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) || entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() < 1) entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 5, 0, false, false, false));
        if (entity.getDeltaMovement().x() == 0 && entity.getDeltaMovement().z() == 0) return;
        if (entity.getDeltaMovement().y() < 0.02 && !entity.isCrouching()) {
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.05, 0, 1.05).add(0, -0.02, 0));
            entity.hasImpulse = true;
            entity.fallDistance = 0;
        }
    }

    @Override
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.15, 0));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(MutableComponent.create(new TranslatableContents("credit.moreboots."+registryName)));
    }
}
