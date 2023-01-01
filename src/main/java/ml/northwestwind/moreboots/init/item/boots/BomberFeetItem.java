package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.handler.packet.CPlayerMultiJumpPacket;
import ml.northwestwind.moreboots.handler.packet.CThrowTNTPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import ml.northwestwind.moreboots.mixins.MixinLivingEntityAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;

public class BomberFeetItem extends BootsItem {
    public BomberFeetItem() {
        super(ItemInit.ModArmorMaterial.BOMBER, "bomber_feet");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onJump() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || player.isOnGround() || player.getAbilities().flying) return;
        player.jumpFromGround();
        player.setDeltaMovement(player.getDeltaMovement().add(0, 0.25, 0));
        if (player.level.isClientSide)
            MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerMultiJumpPacket());
    }

    @Override
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (!entity.isCrouching()) {
            double addToY = 0;
            if (entity instanceof Player && entity.getDeltaMovement().y() < 0.05 && ((MixinLivingEntityAccessor) entity).isJumping() && entity.blockPosition().getY() - Utils.getGroundHeight(entity.level, entity.blockPosition()) < 9) addToY = 0.05;
            else if (entity.getDeltaMovement().y() < 0) addToY = -0.05;
            if (addToY != 0) {
                entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.05, 0, 1.05).add(0, addToY, 0));
                entity.hasImpulse = true;
                entity.fallDistance = 0;
            }
        }
    }

    @Override
    public void activateBoots() {
        MoreBootsPacketHandler.INSTANCE.sendToServer(new CThrowTNTPacket());
    }
}
