package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.List;

public class ExplosiveBootsItem extends BootsItem {
    public ExplosiveBootsItem() {
        super(ItemInit.ModArmorMaterial.EXPLOSIVE, "explosive_boots");
    }

    @Override
    public void onLivingDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Entity attacker = event.getSource().getEntity();
        if (!(event.getSource() instanceof EntityDamageSource) || !(attacker instanceof LivingEntity))
            event.setCanceled(true);
        else {
            entity.level.explode(entity, entity.getX(), entity.getY(0.0625D), entity.getZ(), 4.0F, Explosion.Mode.BREAK);
            List<Entity> collidedEntities = entity.level.getEntities(entity, new AxisAlignedBB(new BlockPos(entity.position())).inflate(3, 3, 3), EntityPredicates.NO_SPECTATORS);
            for (Entity collidedEntity : collidedEntities) {
                if (!(collidedEntity instanceof LivingEntity)) continue;
                collidedEntity.hurt(new DamageSource("explosion"), 40);
            }
        }
    }
}
