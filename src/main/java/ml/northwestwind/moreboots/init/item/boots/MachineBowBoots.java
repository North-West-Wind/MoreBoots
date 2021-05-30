package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CActivateBootsPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingEvent;

public class MachineBowBoots extends BootsItem {
    public MachineBowBoots() {
        super(ItemInit.ModArmorMaterial.MACHINE_BOW, "machine_bow_boots");
    }

    @Override
    public void activateBoots() {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
        CompoundNBT tag = boots.getOrCreateTag();
        boolean newState = !tag.getBoolean("Activated");
        tag.putBoolean("Activated", newState);
        boots.setTag(tag);
        MoreBootsPacketHandler.INSTANCE.sendToServer(new CActivateBootsPacket(newState));
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
        CompoundNBT tag = boots.getOrCreateTag();
        if (tag.getBoolean("Activated")) {
            ArrowEntity arrow = new ArrowEntity(entity.level, entity);
            arrow.shootFromRotation(entity, entity.xRot, entity.yRot, 0, 1, 1);
            entity.level.addFreshEntity(arrow);
            entity.level.playSound(null, entity.blockPosition(), SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1, 1);
            if (entity.getRandom().nextInt(100) == 0) boots.hurtAndBreak(1, entity, e -> e.playSound(SoundEvents.ITEM_BREAK, 1, 1));
        }
    }
}
