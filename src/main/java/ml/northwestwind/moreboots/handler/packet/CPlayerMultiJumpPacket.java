package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class CPlayerMultiJumpPacket implements IPacket {
    public void handle(final NetworkEvent.Context ctx) {
        if (!ctx.getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) return;
        ServerPlayer player = ctx.getSender();
        if(player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        if(!boots.getItem().equals(ItemInit.SANDALS)) return;
        BlockPos pos = new BlockPos(player.position());
        if (pos.getY() > 255 || pos.getY() < 0) return;
        if (!player.level.isEmptyBlock(pos)) return;
        boolean shouldJump = player.isCreative();
        if (!shouldJump) for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem().equals(Items.SAND)) {
                int slot = player.getInventory().findSlotMatchingItem(stack);
                stack.shrink(1);
                player.getInventory().setItem(slot, stack);
                shouldJump = true;
                break;
            }
        }
        if (!shouldJump) for (ItemStack stack : player.getInventory().offhand) {
            if (stack.getItem().equals(Items.SAND)) {
                int slot = player.getInventory().findSlotMatchingItem(stack);
                stack.shrink(1);
                player.getInventory().setItem(slot, stack);
                shouldJump = true;
                break;
            }
        }
        if (!shouldJump) for (ItemStack stack : player.getInventory().armor) {
            if (stack.getItem().equals(Items.SAND)) {
                int slot = player.getInventory().findSlotMatchingItem(stack);
                stack.shrink(1);
                player.getInventory().setItem(slot, stack);
                shouldJump = true;
                break;
            }
        }
        if (shouldJump) {
            player.level.setBlock(pos, Blocks.SAND.defaultBlockState(), 3);
            player.jumpFromGround();
            player.setDeltaMovement(player.getDeltaMovement().add(0,1,0));
        }
    }
}
