package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.EffectInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

public class SocksBootsItem extends BootsItem {
    public SocksBootsItem() {
        super(ItemInit.ModArmorMaterial.SOCKS, "socks_boots");
    }

    public SocksBootsItem(IArmorMaterial material, String registryName) {
        super(material, registryName);
    }

    @Override
    public void onLivingDamage(final LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
        if (event.getSource().equals(DamageSource.IN_FIRE) || event.getSource().equals(DamageSource.LAVA))
            boots.setDamageValue(boots.getMaxDamage());
    }

    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
        CompoundNBT tag = boots.getOrCreateTag();
        if (entity.isInWater() && !entity.isSpectator()) {
            if (!tag.getBoolean("wet")) tag.putBoolean("wet", true);
            tag.putLong("wetTick", System.currentTimeMillis());
            boots.setTag(tag);
            boots.setHoverName(new TranslationTextComponent("item.moreboots.socks_boots_wet"));
        }
        if (tag.getBoolean("wet")) {
            entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 20, 1));
            if (System.currentTimeMillis() - tag.getLong("wetTick") >= 300000) {
                tag.putBoolean("wet", false);
                tag.putLong("wetTick", 0);
                boots.setHoverName(new TranslationTextComponent("item.moreboots.socks_boots"));
            }
        } else {
            entity.addEffect(new EffectInstance(EffectInit.WARMTH, 205));
        }
        boots.setTag(tag);
    }
}
