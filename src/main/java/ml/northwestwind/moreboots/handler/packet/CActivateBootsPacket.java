package ml.northwestwind.moreboots.handler.packet;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class CActivateBootsPacket implements IPacket {
    private final boolean activate;

    public CActivateBootsPacket(boolean activate) {
        this.activate = activate;
    }

    @Override
    public void handle(NetworkEvent.Context ctx) {
        ServerPlayer player = ctx.getSender();
        if (player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);
        CompoundTag tag = boots.getOrCreateTag();
        tag.putBoolean("Activated", activate);
        boots.setTag(tag);
    }
}
