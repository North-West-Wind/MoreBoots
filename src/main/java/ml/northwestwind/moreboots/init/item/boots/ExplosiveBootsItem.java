package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public class ExplosiveBootsItem extends BootsItem {
    public ExplosiveBootsItem() {
        super(ItemInit.ModArmorMaterial.EXPLOSIVE, "explosive_boots");
    }

    @Override
    public void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Entity attacker = event.getSource().getEntity();
        if (!(event.getSource() instanceof EntityDamageSource) || !(attacker instanceof LivingEntity))
            event.setCanceled(true);
        else {
            entity.level.explode(entity, entity.getX(), entity.getY(0.0625D), entity.getZ(), 4.0F, Explosion.BlockInteraction.BREAK);
            List<Entity> collidedEntities = entity.level.getEntities(entity, new AABB(new BlockPos(entity.position())).inflate(3, 3, 3), EntitySelector.NO_SPECTATORS);
            for (Entity collidedEntity : collidedEntities) {
                if (!(collidedEntity instanceof LivingEntity)) continue;
                collidedEntity.hurt(new DamageSource("explosion"), 40);
            }
        }
    }
}
