package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class MightyFeetItem extends BootsItem {
    public MightyFeetItem() {
        super(ItemInit.ModArmorMaterial.MIGHTY, "mighty_feet");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isCrouching() && entity.isOnGround()) {
            ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
            CompoundTag tag = boots.getOrCreateTag();
            long tickSneak = tag.getLong("tickSneak");
            if (tickSneak < 600) {
                tickSneak += 1;
                tag.putLong("tickSneak", tickSneak);
            }
            if (entity instanceof Player && !entity.level.isClientSide)
                ((Player) entity).displayClientMessage(new TranslatableComponent("message.moreboots.charging_explosion", Utils.roundTo(tickSneak / 60D, 2)), true);
            boots.setTag(tag);
        }
    }

    @Override
    public void onLivingAttack(LivingHurtEvent event) {
        LivingEntity source = (LivingEntity) event.getSource().getDirectEntity();
        ItemStack boots = source.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = boots.getOrCreateTag();
        float power = tag.getLong("tickSneak") / 60f;
        Vec3 pos = source.position().add(source.getLookAngle().scale(0.1));
        source.level.explode(source, pos.x, pos.y, pos.z, power, Explosion.BlockInteraction.NONE);
        event.setAmount(event.getAmount() * power / 2);
    }
}
