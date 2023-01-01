package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Method;
import java.util.List;

public class PlumberBootsItem extends BootsItem {
    private static final Method jumpFromGround = ObfuscationReflectionHelper.findMethod(LivingEntity.class, "m_6135_");

    public PlumberBootsItem() {
        super(ItemInit.ModArmorMaterial.PLUMBER, "plumber_boots");
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        List<Entity> collidedEntities = entity.level.getEntities(entity, entity.getBoundingBox(), EntitySelector.NO_SPECTATORS);
        Vec3 motion = entity.getDeltaMovement();
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
            if (!entity.level.isClientSide) ((ServerLevel) entity.level).getServer().getPlayerList().broadcastAll(new ClientboundSetEntityMotionPacket(entity));
        }
    }
}
