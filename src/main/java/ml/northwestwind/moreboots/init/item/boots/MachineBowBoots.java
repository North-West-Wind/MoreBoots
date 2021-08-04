package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CActivateBootsPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;

public class MachineBowBoots extends BootsItem {
    public MachineBowBoots() {
        super(ItemInit.ModArmorMaterial.MACHINE_BOW, "machine_bow_boots");
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

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = boots.getOrCreateTag();
        if (tag.getBoolean("Activated") && hasArrows(entity)) {
            Arrow arrow = new Arrow(entity.level, entity);
            arrow.shootFromRotation(entity, entity.getXRot(), entity.getYRot(), 0, 1, 1);
            entity.level.addFreshEntity(arrow);
            entity.level.playSound(null, entity.blockPosition(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1, 1);
            if (entity.getRandom().nextInt(100) == 0) boots.hurtAndBreak(1, entity, e -> e.playSound(SoundEvents.ITEM_BREAK, 1, 1));
        }
    }

    private boolean hasArrows(LivingEntity entity) {
        if (!(entity instanceof Player)) return true;
        Player player = (Player) entity;
        boolean shouldJump = player.isCreative();
        if (!shouldJump) for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem().equals(Items.ARROW)) {
                int slot = player.getInventory().findSlotMatchingItem(stack);
                stack.shrink(1);
                player.getInventory().setItem(slot, stack);
                shouldJump = true;
                break;
            }
        }
        if (!shouldJump) for (ItemStack stack : player.getInventory().offhand) {
            if (stack.getItem().equals(Items.ARROW)) {
                int slot = player.getInventory().findSlotMatchingItem(stack);
                stack.shrink(1);
                player.getInventory().setItem(slot, stack);
                shouldJump = true;
                break;
            }
        }
        if (!shouldJump) for (ItemStack stack : player.getInventory().armor) {
            if (stack.getItem().equals(Items.ARROW)) {
                int slot = player.getInventory().findSlotMatchingItem(stack);
                stack.shrink(1);
                player.getInventory().setItem(slot, stack);
                shouldJump = true;
                break;
            }
        }
        return shouldJump;
    }
}
