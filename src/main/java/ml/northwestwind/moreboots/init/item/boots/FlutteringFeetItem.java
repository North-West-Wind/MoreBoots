package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import ml.northwestwind.moreboots.mixins.MixinLivingEntityAccessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;

public class FlutteringFeetItem extends BootsItem {
    public FlutteringFeetItem() {
        super(ItemInit.ModArmorMaterial.FLUTTERING, "fluttering_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = boots.getOrCreateTag();
        int flutterTicks = tag.getInt("flutter_ticks");
        if (flutterTicks <= 20 && !entity.isOnGround() && entity.getDeltaMovement().y < 0.1 && ((MixinLivingEntityAccessor) entity).isJumping()) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.01 * flutterTicks, 0));
            entity.hasImpulse = true;
            tag.putInt("flutter_ticks", flutterTicks + 1);
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.15, 0));
        } else if (flutterTicks > 0 && entity.isOnGround()) tag.putInt("flutter_ticks", 0);
        boots.setTag(tag);
    }
}
