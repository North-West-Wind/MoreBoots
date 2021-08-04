package ml.northwestwind.moreboots.init.item.boots;

import com.google.common.collect.Sets;
import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CPlayerMultiJumpPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SandBootsItem extends BootsItem {
    public SandBootsItem() {
        super(ItemInit.ModArmorMaterial.SAND, "sand_boots");
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onJump() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || player.isOnGround() || player.getAbilities().flying) return;
        BlockPos pos = player.blockPosition();
        if (pos.getY() > 255 || pos.getY() < 0) return;
        if (!player.level.isEmptyBlock(pos) || !player.getInventory().hasAnyOf(Sets.newHashSet(Items.SAND))) return;
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
            FallingBlockEntity sand = new FallingBlockEntity(player.level, pos.getX(), pos.getY(), pos.getZ(), Blocks.SAND.defaultBlockState());
            player.level.addFreshEntity(sand);
            player.jumpFromGround();
            player.setDeltaMovement(player.getDeltaMovement().add(0, 1, 0));
            if (player.level.isClientSide)
                MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerMultiJumpPacket());
        }
    }
}
