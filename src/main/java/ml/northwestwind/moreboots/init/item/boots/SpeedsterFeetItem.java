package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;

public class SpeedsterFeetItem extends BootsItem {
    public SpeedsterFeetItem() {
        super(ItemInit.ModArmorMaterial.SPEEDSTER, "speedster_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isOnGround()) {
            ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
            CompoundTag tag = boots.getOrCreateTag();
            if (entity.isCrouching()) {
                long tickSneak = tag.getLong("tickSneak");
                if (tickSneak < 600) {
                    tickSneak += 1;
                    tag.putLong("tickSneak", tickSneak);
                }
                if (entity instanceof Player && !entity.level.isClientSide)
                    ((Player) entity).displayClientMessage(new TranslatableComponent("message.moreboots.charging_speed", Utils.roundTo(tickSneak / 60D, 2)), true);
                boots.setTag(tag);
            } else if (!entity.getDeltaMovement().equals(Vec3.ZERO) && !entity.hasEffect(MobEffects.MOVEMENT_SPEED)) {
                long tickSneak = tag.getLong("tickSneak");
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10, (int) (tickSneak / 20), false, false, false));
                if (tickSneak > 0) {
                    tickSneak -= 1;
                    tag.putLong("tickSneak", tickSneak);
                }
                boots.setTag(tag);
            }
        }
    }
}
