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
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack stack = entity.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = stack.getOrCreateTag();
        int punches = tag.getInt("punches");
        if (entity.hasEffect(MobEffects.DIG_SPEED) || entity.getEffect(MobEffects.DIG_SPEED).getAmplifier() > punches) return;
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
        stack.setTag(tag);
    }
}
