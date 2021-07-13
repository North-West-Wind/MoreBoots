package ml.northwestwind.moreboots.handler.packet;

import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

public class CPlayerMultiJumpPacket implements IPacket {
    public void handle(final NetworkEvent.Context ctx) {
        if (!ctx.getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) return;
        ServerPlayerEntity player = ctx.getSender();
        if(player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
        if(!boots.getItem().equals(ItemInit.SANDALS)) return;
        BlockPos pos = new BlockPos(player.position());
        if (pos.getY() > 255 || pos.getY() < 0) return;
        if (!player.level.isEmptyBlock(pos)) return;
        boolean shouldJump = player.isCreative();
        if (!shouldJump) for (ItemStack stack : player.inventory.items) {
            if (stack.getItem().equals(Items.SAND)) {
                int slot = player.inventory.findSlotMatchingItem(stack);
                stack.shrink(1);
                player.inventory.setItem(slot, stack);
                shouldJump = true;
                break;
            }
        }
        if (!shouldJump) for (ItemStack stack : player.inventory.offhand) {
            if (stack.getItem().equals(Items.SAND)) {
                int slot = player.inventory.findSlotMatchingItem(stack);
                stack.shrink(1);
                player.inventory.setItem(slot, stack);
                shouldJump = true;
                break;
            }
        }
        if (!shouldJump) for (ItemStack stack : player.inventory.armor) {
            if (stack.getItem().equals(Items.SAND)) {
                int slot = player.inventory.findSlotMatchingItem(stack);
                stack.shrink(1);
                player.inventory.setItem(slot, stack);
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
