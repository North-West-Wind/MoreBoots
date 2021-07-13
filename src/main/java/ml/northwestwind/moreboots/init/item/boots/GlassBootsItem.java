package ml.northwestwind.moreboots.init.item.boots;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
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
        LivingEntity entity = event.getEntityLiving();
        float distance = event.getDistance();
        ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
        if (entity.level.isClientSide) return;
        if (distance < 3.0f) return;
        int damage = (int) (10 * distance);
        if (entity.getRandom().nextInt(2) == 0) damage = boots.getMaxDamage();
        boots.hurtAndBreak(damage, entity, livingEntity -> {
            livingEntity.level.playSound(null, livingEntity.blockPosition(), SoundEvents.GLASS_BREAK, SoundCategory.NEUTRAL, 1, 1);
            breakGlassBoots(boots, PotionUtils.getPotion(boots), livingEntity);
        });
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
        Potion potion = PotionUtils.getPotion(boots);
        for (EffectInstance instance : potion.getEffects()) {
            EffectInstance newEffect = new EffectInstance(instance.getEffect(), 205, instance.getAmplifier());
            entity.addEffect(newEffect);
        }
        if (potion.hasInstantEffects()) {
            ItemStack newBoots = new ItemStack(ItemInit.GLASS_BOOTS_EMPTY);
            newBoots.setDamageValue(boots.getDamageValue());
            entity.setItemSlot(EquipmentSlotType.FEET, newBoots);
        } else {
            CompoundNBT tag = boots.getOrCreateTag();
            int ticksLeft = tag.getInt("Duration") - 1;
            tag.putInt("Duration", ticksLeft);
            boots.setTag(tag);
            if (ticksLeft <= 0) {
                ItemStack newBoots = new ItemStack(ItemInit.GLASS_BOOTS_EMPTY);
                newBoots.setDamageValue(boots.getDamageValue());
                entity.setItemSlot(EquipmentSlotType.FEET, newBoots);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        addPotionTooltip(stack, tooltip);
    }

    @OnlyIn(Dist.CLIENT)
    public static void addPotionTooltip(ItemStack itemIn, List<ITextComponent> lores) {
        List<EffectInstance> list = PotionUtils.getCustomEffects(itemIn);
        List<Pair<Attribute, AttributeModifier>> list1 = Lists.newArrayList();
        if (!list.isEmpty()) {
            for(EffectInstance effectinstance : list) {
                IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(effectinstance.getDescriptionId());
                Effect effect = effectinstance.getEffect();
                Map<Attribute, AttributeModifier> map = effect.getAttributeModifiers();
                if (!map.isEmpty()) {
                    for(Map.Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
                        AttributeModifier attributemodifier = entry.getValue();
                        AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), effect.getAttributeModifierValue(effectinstance.getAmplifier(), attributemodifier), attributemodifier.getOperation());
                        list1.add(new Pair<>(entry.getKey(), attributemodifier1));
                    }
                }

                if (effectinstance.getAmplifier() > 0) {
                    iformattabletextcomponent = new TranslationTextComponent("potion.withAmplifier", iformattabletextcomponent, new TranslationTextComponent("potion.potency." + effectinstance.getAmplifier()));
                }

                lores.add(iformattabletextcomponent.withStyle(effect.getCategory().getTooltipFormatting()));
            }
        } else {
            Potion potion = PotionUtils.getPotion(itemIn);
            IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(potion.getName("item.minecraft.potion.effect."));
            lores.add(iformattabletextcomponent.withStyle(TextFormatting.GREEN));
        }
    }

    private static void breakGlassBoots(ItemStack stack, Potion potion, LivingEntity entity) {
        if (entity.level.isClientSide) return;
        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(entity.level, entity.getX(), entity.getY(), entity.getZ());
        areaeffectcloudentity.setOwner(entity);

        areaeffectcloudentity.setRadius(3.0F);
        areaeffectcloudentity.setRadiusOnUse(-0.5F);
        areaeffectcloudentity.setWaitTime(10);
        areaeffectcloudentity.setRadiusPerTick(-areaeffectcloudentity.getRadius() / (float) areaeffectcloudentity.getDuration());
        areaeffectcloudentity.setPotion(potion);

        for (EffectInstance effectinstance : PotionUtils.getCustomEffects(stack)) {
            areaeffectcloudentity.addEffect(new EffectInstance(effectinstance));
        }

        CompoundNBT compoundnbt = stack.getTag();
        if (compoundnbt != null && compoundnbt.contains("CustomPotionColor", 99)) {
            areaeffectcloudentity.setFixedColor(compoundnbt.getInt("CustomPotionColor"));
        }

        entity.level.addFreshEntity(areaeffectcloudentity);
    }
}
