package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class SoulBootsItem extends BootsItem {
    public SoulBootsItem() {
        super(ItemInit.ModArmorMaterial.SOUL, "soul_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack stack = entity.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = stack.getOrCreateTag();
        int ticks = tag.getInt("remaining_ticks");
        if (ticks > 0) tag.putInt("remaining_ticks", --ticks);
        int punches = tag.getInt("punches");
        if (punches > 0 && (!entity.hasEffect(MobEffects.DIG_SPEED) || entity.getEffect(MobEffects.DIG_SPEED).getAmplifier() < punches + 1))
            entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 10, punches, false, false, false));
    }

    @Override
    public void onLivingAttack(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        if (source.isProjectile() || !(source.getDirectEntity() instanceof LivingEntity entity)) return;
        ItemStack stack = entity.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("punches")) {
            if (tag.getInt("punches") < 127)
                tag.putInt("punches", tag.getInt("punches") + 1);
        } else tag.putInt("punches", 1);
        tag.putInt("remaining_ticks", 100);
        stack.setTag(tag);
    }
}
