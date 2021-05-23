package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.List;

public class HopperBootsItem extends BootsItem {
    public HopperBootsItem() {
        super(ItemInit.ModArmorMaterial.HOPPER, "hopper_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (!(entity instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) entity;
        List<Entity> entities = entity.level.getEntities(entity, new AxisAlignedBB(entity.blockPosition()).inflate(5), EntityPredicates.NO_SPECTATORS);
        if (entities.size() < 1) return;
        int available = 0;
        for (ItemStack stack : player.inventory.items) {
            if (stack.equals(ItemStack.EMPTY)) available++;
        }
        for (Entity ent : entities) {
            if (!(ent instanceof ItemEntity)) continue;
            if (available < 1) break;
            if (player.addItem(((ItemEntity) ent).getItem())) available--;
        }
    }
}
