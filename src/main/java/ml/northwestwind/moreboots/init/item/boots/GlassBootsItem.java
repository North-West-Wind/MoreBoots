package ml.northwestwind.moreboots.init.item.boots;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class GlassBootsItem extends BootsItem {
    public GlassBootsItem() {
        super(ItemInit.ModArmorMaterial.GLASS, "glass_boots");
    }

    @Override
    public void onLivingFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        float distance = event.getDistance();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        if (entity.level.isClientSide) return;
        if (distance < 3.0f) return;
        int damage = (int) (10 * distance);
        if (entity.getRandom().nextInt(2) == 0) damage = boots.getMaxDamage();
        boots.hurtAndBreak(damage, entity, livingEntity -> {
            livingEntity.level.playSound(null, livingEntity.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.NEUTRAL, 1, 1);
            breakGlassBoots(boots, PotionUtils.getPotion(boots), livingEntity);
        });
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        Potion potion = PotionUtils.getPotion(boots);
        for (MobEffectInstance instance : potion.getEffects()) {
            MobEffectInstance newEffect = new MobEffectInstance(instance.getEffect(), 205, instance.getAmplifier());
            entity.addEffect(newEffect);
        }
        if (potion.hasInstantEffects()) {
            ItemStack newBoots = new ItemStack(ItemInit.GLASS_BOOTS_EMPTY.get());
            newBoots.setDamageValue(boots.getDamageValue());
            entity.setItemSlot(EquipmentSlot.FEET, newBoots);
        } else {
            CompoundTag tag = boots.getOrCreateTag();
            int ticksLeft = tag.getInt("Duration") - 1;
            tag.putInt("Duration", ticksLeft);
            boots.setTag(tag);
            if (ticksLeft <= 0) {
                ItemStack newBoots = new ItemStack(ItemInit.GLASS_BOOTS_EMPTY.get());
                newBoots.setDamageValue(boots.getDamageValue());
                entity.setItemSlot(EquipmentSlot.FEET, newBoots);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        addPotionTooltip(stack, tooltip);
    }

    @OnlyIn(Dist.CLIENT)
    public static void addPotionTooltip(ItemStack itemIn, List<Component> lores) {
        List<MobEffectInstance> list = PotionUtils.getCustomEffects(itemIn);
        List<Pair<Attribute, AttributeModifier>> list1 = Lists.newArrayList();
        if (!list.isEmpty()) {
            for(MobEffectInstance effectinstance : list) {
                MutableComponent iformattabletextcomponent = MutableComponent.create(new TranslatableContents(effectinstance.getDescriptionId()));
                MobEffect effect = effectinstance.getEffect();
                Map<Attribute, AttributeModifier> map = effect.getAttributeModifiers();
                if (!map.isEmpty()) {
                    for(Map.Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
                        AttributeModifier attributemodifier = entry.getValue();
                        AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), effect.getAttributeModifierValue(effectinstance.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                        list1.add(new Pair<>(entry.getKey(), attributemodifier1));
                    }
                }

                if (effectinstance.getAmplifier() > 0) {
                    iformattabletextcomponent = MutableComponent.create(new TranslatableContents("potion.withAmplifier", iformattabletextcomponent, MutableComponent.create(new TranslatableContents("potion.potency." + effectinstance.getAmplifier()))));
                }

                lores.add(iformattabletextcomponent.withStyle(effect.getCategory().getTooltipFormatting()));
            }
        } else {
            Potion potion = PotionUtils.getPotion(itemIn);
            MutableComponent iformattabletextcomponent = MutableComponent.create(new TranslatableContents(potion.getName("item.minecraft.potion.effect.")));
            lores.add(iformattabletextcomponent.withStyle(ChatFormatting.GREEN));
        }
    }

    private static void breakGlassBoots(ItemStack stack, Potion potion, LivingEntity entity) {
        if (entity.level.isClientSide) return;
        AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(entity.level, entity.getX(), entity.getY(), entity.getZ());
        areaeffectcloudentity.setOwner(entity);

        areaeffectcloudentity.setRadius(3.0F);
        areaeffectcloudentity.setRadiusOnUse(-0.5F);
        areaeffectcloudentity.setWaitTime(10);
        areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float) areaeffectcloudentity.getDuration());
        areaeffectcloudentity.setPotion(potion);

        for (MobEffectInstance effectinstance : PotionUtils.getCustomEffects(stack)) {
            areaeffectcloudentity.addEffect(new MobEffectInstance(effectinstance));
        }

        CompoundTag compoundnbt = stack.getTag();
        if (compoundnbt != null && compoundnbt.contains("CustomPotionColor", 99)) {
            areaeffectcloudentity.setFixedColor(compoundnbt.getInt("CustomPotionColor"));
        }

        entity.level.addFreshEntity(areaeffectcloudentity);
    }
}
