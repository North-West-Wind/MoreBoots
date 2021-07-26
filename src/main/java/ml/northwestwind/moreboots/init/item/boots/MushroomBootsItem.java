package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent;

public class MushroomBootsItem extends BootsItem {
    public MushroomBootsItem() {
        super(ItemInit.ModArmorMaterial.MUSHROOM, "mushroom_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Utils.changeGroundBlocks(entity, entity.level, new BlockPos(entity.position()).above(), 2, Utils.grass, Utils.air);
        Utils.changeGroundBlocks(entity, entity.level, new BlockPos(entity.position()), 2, Utils.target, Utils.to);
    }
}
