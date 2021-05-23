package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.MoreBoots;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

public class LokiBootsItem extends BootsItem {
    public LokiBootsItem() {
        super(ItemInit.ModArmorMaterial.LOKI, "loki_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        ItemStack boots = entity.getItemBySlot(EquipmentSlotType.FEET);
        CompoundNBT tag = boots.getOrCreateTag();
        int tickStill = tag.getInt("tickStill");
        Vector3d motion = entity.getDeltaMovement();
        MoreBoots.LOGGER.info(motion);
        if (Math.abs(motion.x) > 0 || Math.abs(motion.y) > 0.8 || Math.abs(motion.z) > 0) {
            tickStill = 0;
            entity.setInvisible(false);
        } else if (tickStill < 200) tickStill++;
        else entity.setInvisible(true);
        tag.putInt("tickStill", tickStill);
        boots.setTag(tag);
    }

    @Override
    public void preRenderLiving(RenderLivingEvent.Pre<?, ?> event) {
        CompoundNBT tag = event.getEntity().getItemBySlot(EquipmentSlotType.FEET).getOrCreateTag();
        if (tag.getInt("tickStill") >= 200) event.setCanceled(true);
    }

    @Override
    public void renderNameplate(RenderNameplateEvent event) {
        event.setCanceled(true);
    }
}
