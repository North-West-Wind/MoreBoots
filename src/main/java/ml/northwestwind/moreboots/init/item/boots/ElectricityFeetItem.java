package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CActivateBootsPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;

public class ElectricityFeetItem extends BootsItem {
    public ElectricityFeetItem() {
        super(ItemInit.ModArmorMaterial.ELECTRICITY, "electricity_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = boots.getOrCreateTag();
        if (tag.getBoolean("Activated")) {
            if (entity.isInWater()) {
                LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.level);
                lightning.setPos(entity.getX(), entity.getY(), entity.getZ());
                entity.level.addFreshEntity(lightning);
                entity.hurt(DamageSource.LIGHTNING_BOLT, entity.getHealth() / 2);
                tag.putBoolean("Activated", false);
                boots.setTag(tag);
            } else if (!entity.hasEffect(MobEffects.MOVEMENT_SPEED) || entity.getEffect(MobEffects.MOVEMENT_SPEED).getAmplifier() < 2)
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, 1, false, false, false));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void activateBoots() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = boots.getOrCreateTag();
        boolean newState = !tag.getBoolean("Activated");
        tag.putBoolean("Activated", newState);
        boots.setTag(tag);
        MoreBootsPacketHandler.INSTANCE.sendToServer(new CActivateBootsPacket(newState));
    }
}
