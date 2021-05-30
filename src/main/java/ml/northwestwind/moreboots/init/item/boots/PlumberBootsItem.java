package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Method;
import java.util.List;

public class PlumberBootsItem extends BootsItem {
    private static final Method jumpFromGround = ObfuscationReflectionHelper.findMethod(LivingEntity.class, "func_70664_aZ");

    public PlumberBootsItem() {
        super(ItemInit.ModArmorMaterial.PLUMBER, "plumber_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        List<Entity> collidedEntities = entity.level.getEntities(entity, entity.getBoundingBox(), EntityPredicates.NO_SPECTATORS);
        Vector3d motion = entity.getDeltaMovement();
        boolean stomped = false;
        for (Entity collidedEntity : collidedEntities) {
            if (!(collidedEntity instanceof LivingEntity)) continue;
            if (collidedEntity.getY() + collidedEntity.getBbHeight() < entity.getY() || collidedEntity.getY() + collidedEntity.getEyeHeight() > entity.getY())
                continue;
            boolean flag = collidedEntity.hurt(Reference.STOMP, 4);
            if (!stomped) stomped = flag;
        }
        if (stomped) {
            try {
                jumpFromGround.invoke(entity);
            } catch (Exception e) {
                entity.setDeltaMovement(motion.multiply(1, 0, 1).add(0, 0.75, 0));
                entity.hasImpulse = true;
            }
            if (!entity.level.isClientSide) ((ServerWorld) entity.level).getServer().getPlayerList().broadcastAll(new SEntityVelocityPacket(entity));
        }
    }
}
