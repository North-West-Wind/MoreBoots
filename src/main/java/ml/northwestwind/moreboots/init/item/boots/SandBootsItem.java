package ml.northwestwind.moreboots.init.item.boots;

import com.google.common.collect.Sets;
import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CPlayerMultiJumpPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SandBootsItem extends BootsItem {
    public SandBootsItem() {
        super(ItemInit.ModArmorMaterial.SAND, "sand_boots");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onJump() {
        net.minecraft.client.entity.player.ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null || player.isOnGround() || player.abilities.flying) return;
        BlockPos pos = player.blockPosition();
        if (pos.getY() > 255 || pos.getY() < 0) return;
        if (!player.level.isEmptyBlock(pos) || !player.inventory.hasAnyOf(Sets.newHashSet(Items.SAND))) return;
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
            FallingBlockEntity sand = new FallingBlockEntity(player.level, pos.getX(), pos.getY(), pos.getZ(), Blocks.SAND.defaultBlockState());
            player.level.addFreshEntity(sand);
            player.jumpFromGround();
            player.setDeltaMovement(player.getDeltaMovement().add(0, 1, 0));
            if (player.level.isClientSide)
                MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerMultiJumpPacket());
        }
    }
}
