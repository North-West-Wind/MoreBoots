package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CShootWitherSkullPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;

public class WitherBootsItem extends BootsItem {
    public WitherBootsItem() {
        super(ItemInit.ModArmorMaterial.WITHER, "wither_boots");
    }

    @Override
    public void activateBoots() {
        MoreBootsPacketHandler.INSTANCE.sendToServer(new CShootWitherSkullPacket());
    }
}
