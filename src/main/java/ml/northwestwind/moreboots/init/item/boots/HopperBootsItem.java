package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.List;

public class HopperBootsItem extends BootsItem {
    public HopperBootsItem() {
        super(ItemInit.ModArmorMaterial.HOPPER, "hopper_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (!(entity instanceof Player)) return;
        Player player = (Player) entity;
        List<Entity> entities = entity.level.getEntities(entity, new AABB(entity.blockPosition()).inflate(5), EntitySelector.NO_SPECTATORS);
        if (entities.size() < 1) return;
        int available = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (stack.equals(ItemStack.EMPTY)) available++;
        }
        for (Entity ent : entities) {
            if (!(ent instanceof ItemEntity)) continue;
            if (available < 1) break;
            if (player.addItem(((ItemEntity) ent).getItem())) available--;
        }
    }
}
