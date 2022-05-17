package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.Utils;
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
            FallingBlockEntity sand = FallingBlockEntity.fall(player.level, pos, Blocks.SAND.defaultBlockState());
            player.level.addFreshEntity(sand);
            player.jumpFromGround();
            player.setDeltaMovement(player.getDeltaMovement().add(0, 1, 0));
            if (player.level.isClientSide)
                MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerMultiJumpPacket());
        }
    }
}
