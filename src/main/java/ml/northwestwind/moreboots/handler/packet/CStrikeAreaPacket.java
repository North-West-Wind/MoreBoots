package ml.northwestwind.moreboots.handler.packet;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;

public class CStrikeAreaPacket implements IPacket {
    @Override
    public void handle(NetworkEvent.Context ctx) {
        ServerPlayer player = ctx.getSender();
        if (player == null) return;
        if (player.isInWater()) {
            LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, player.level);
            lightning.setPos(player.getX(), player.getY(), player.getZ());
            player.level.addFreshEntity(lightning);
            player.hurt(DamageSource.LIGHTNING_BOLT, player.getHealth() / 2);
        } else {
            List<Entity> entities = player.level.getEntities(player, player.getBoundingBox().inflate(64, 1, 64), entity -> (entity instanceof Monster));
            for (Entity entity : entities) {
                LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, entity.level);
                lightning.setPos(entity.getX(), entity.getY(), entity.getZ());
                entity.level.addFreshEntity(lightning);
            }
        }
    }
}
