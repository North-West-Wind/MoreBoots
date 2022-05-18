package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class CPlayerMultiJumpPacket implements IPacket {
    public void handle(final NetworkEvent.Context ctx) {
        if (!ctx.getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) return;
        ServerPlayer player = ctx.getSender();
        if (player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        if (boots.getItem().equals(ItemInit.SANDALS.get())) {
            BlockPos pos = new BlockPos(player.position());
            if (pos.getY() > 255 || pos.getY() < 0) return;
            if (!player.level.isEmptyBlock(pos) || player.getInventory().countItem(Items.SAND) == 0) return;
            boolean shouldJump = player.isCreative();
            if (!shouldJump) {
                int slot = Utils.getStackSlot(player.getInventory(), Items.LIGHTNING_ROD);
                if (slot > -1) {
                    ItemStack stack = player.getInventory().getItem(slot);
                    stack.shrink(1);
                    shouldJump = true;
                }
            }
            if (shouldJump) {
                player.level.setBlock(pos, Blocks.SAND.defaultBlockState(), 3);
                player.jumpFromGround();
                player.setDeltaMovement(player.getDeltaMovement().add(0, 1, 0));
            }
        } else if (boots.getItem().equals(ItemInit.SUPER_FEET.get())) {
            double yDiff = player.position().y - player.blockPosition().getY();
            if (yDiff > 0 && yDiff < 0.6) player.jumpFromGround();
        } else if (boots.getItem().equals(ItemInit.BOMBERFEET.get())) {
            player.jumpFromGround();
            player.setDeltaMovement(player.getDeltaMovement().add(0, 0.25, 0));
            player.level.explode(player, player.getX(), player.getY() - 0.5, player.getZ(), 2, Explosion.BlockInteraction.BREAK);
        }
    }
}
