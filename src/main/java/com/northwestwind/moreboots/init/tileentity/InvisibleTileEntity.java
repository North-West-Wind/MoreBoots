package com.northwestwind.moreboots.init.tileentity;

import com.northwestwind.moreboots.init.ItemInit;
import com.northwestwind.moreboots.init.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class InvisibleTileEntity extends TileEntity implements ITickableTileEntity {
    public InvisibleTileEntity() {
        super(TileEntityInit.INVISIBLE);
    }

    @Override
    public void tick() {
        if (!hasWorld()) return;
        List<Entity> entities = world.getEntitiesInAABBexcluding(null, new AxisAlignedBB(pos).grow(0, 1, 0), EntityPredicates.NOT_SPECTATING);
        if (shouldBeGone(entities)) {
            world.removeBlock(pos, false);

        }
    }

    private boolean shouldBeGone(List<Entity> entities) {
        for (Entity entity : entities) {
            if (!(entity instanceof LivingEntity)) continue;
            if (((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemInit.JESUS_BOOTS)) return false;
        }
        return true;
    }
}
