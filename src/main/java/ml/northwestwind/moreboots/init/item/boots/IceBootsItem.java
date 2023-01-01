package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import net.minecraftforge.event.entity.living.LivingEvent;

public class IceBootsItem extends BootsItem {
    public IceBootsItem() {
        super(ItemInit.ModArmorMaterial.ICE, "ice_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        FrostWalkerEnchantment.onEntityMoved(entity, entity.level, new BlockPos(entity.position()), 2);
        int num = entity.getRandom().nextInt(100);
        if (num == 0)
            boots.hurtAndBreak(1, entity, playerEntity -> playerEntity.playSound(SoundEvents.GLASS_BREAK, 1, 1));
    }
}
