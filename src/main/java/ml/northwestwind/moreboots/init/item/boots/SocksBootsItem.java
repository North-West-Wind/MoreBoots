package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.EffectInit;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class SocksBootsItem extends BootsItem {
    public SocksBootsItem() {
        super(ItemInit.ModArmorMaterial.SOCKS, "socks_boots");
    }

    public SocksBootsItem(ArmorMaterial material, String registryName) {
        super(material, registryName);
    }

    @Override
    public void onLivingHurt(final LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        if (event.getSource().equals(DamageSource.IN_FIRE) || event.getSource().equals(DamageSource.LAVA))
            boots.setDamageValue(boots.getMaxDamage());
    }

    @Override
    public void onLivingUpdate(final LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = boots.getOrCreateTag();
        if (entity.isInWater() && !entity.isSpectator()) {
            if (!tag.getBoolean("wet")) tag.putBoolean("wet", true);
            tag.putLong("wetTick", System.currentTimeMillis());
            boots.setTag(tag);
            boots.setHoverName(new TranslatableComponent("item.moreboots.socks_boots_wet"));
        }
        if (tag.getBoolean("wet")) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));
            if (System.currentTimeMillis() - tag.getLong("wetTick") >= 300000) {
                tag.putBoolean("wet", false);
                tag.putLong("wetTick", 0);
                boots.setHoverName(new TranslatableComponent("item.moreboots.socks_boots"));
            }
        } else {
            entity.addEffect(new MobEffectInstance(EffectInit.WARMTH, 205));
        }
        boots.setTag(tag);
    }
}
