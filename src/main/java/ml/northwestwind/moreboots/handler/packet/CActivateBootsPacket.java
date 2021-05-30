package ml.northwestwind.moreboots.handler.packet;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.network.NetworkEvent;

public class CActivateBootsPacket implements IPacket {
    private final boolean activate;

    public CActivateBootsPacket(boolean activate) {
        this.activate = activate;
    }

    @Override
    public void handle(NetworkEvent.Context ctx) {
        ServerPlayerEntity player = ctx.getSender();
        if (player == null) return;
        ItemStack boots = player.getItemBySlot(EquipmentSlotType.FEET);
        CompoundNBT tag = boots.getOrCreateTag();
        tag.putBoolean("Activated", activate);
        boots.setTag(tag);
    }
}
