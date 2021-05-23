package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.handler.MoreBootsPacketHandler;
import ml.northwestwind.moreboots.handler.packet.CShootDragonBallPacket;
import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;

public class EnderDragonBootsItem extends BootsItem {
    public EnderDragonBootsItem() {
        super(ItemInit.ModArmorMaterial.DRAGON, "ender_dragon_boots");
    }

    @Override
    public void activateBoots() {
        MoreBootsPacketHandler.INSTANCE.sendToServer(new CShootDragonBallPacket());
    }
}
