package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CPlayerKAPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class KABootsItem extends BootsItem {
    public KABootsItem() {
        super(ItemInit.ModArmorMaterial.KA, "ka_boots");
    }

    @Override
    public void onPlayerLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
        MoreBootsPacketHandler.INSTANCE.sendToServer(new CPlayerKAPacket());
    }
}
