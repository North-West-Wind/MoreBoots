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

import java.io.Serializable;

public class CPlayerMultiJumpPacket implements Serializable {
    public void handle(final NetworkEvent.Context ctx) {
        if (!ctx.getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) return;
        ServerPlayerEntity player = ctx.getSender();
        if(player == null) return;
        ItemStack boots = player.getItemStackFromSlot(EquipmentSlotType.FEET);
        if(!boots.getItem().equals(ItemInit.SANDALS)) return;
        BlockPos pos = new BlockPos(player.getPositionVec());
        if (pos.getY() > 255 || pos.getY() < 0) return;
        if (!player.world.isAirBlock(pos)) return;
        boolean shouldJump = player.isCreative();
        if (!shouldJump) for (ItemStack stack : player.inventory.mainInventory) {
            if (stack.getItem().equals(Items.SAND)) {
                int slot = player.inventory.getSlotFor(stack);
                stack.shrink(1);
                player.inventory.setInventorySlotContents(slot, stack);
                shouldJump = true;
                break;
            }
        }
        if (!shouldJump) for (ItemStack stack : player.inventory.offHandInventory) {
            if (stack.getItem().equals(Items.SAND)) {
                int slot = player.inventory.getSlotFor(stack);
                stack.shrink(1);
                player.inventory.setInventorySlotContents(slot, stack);
                shouldJump = true;
                break;
            }
        }
        if (!shouldJump) for (ItemStack stack : player.inventory.armorInventory) {
            if (stack.getItem().equals(Items.SAND)) {
                int slot = player.inventory.getSlotFor(stack);
                stack.shrink(1);
                player.inventory.setInventorySlotContents(slot, stack);
                shouldJump = true;
                break;
            }
        }
        if (shouldJump) {
            player.world.setBlockState(pos, Blocks.SAND.getDefaultState(), 3);
            player.jump();
            player.setMotion(player.getMotion().add(0,1,0));
        }
    }
}
