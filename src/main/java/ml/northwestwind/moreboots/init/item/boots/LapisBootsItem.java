package ml.northwestwind.moreboots.init.item.boots;

import ml.northwestwind.moreboots.init.ItemInit;
import ml.northwestwind.moreboots.init.item.BootsItem;
import net.minecraftforge.event.entity.player.PlayerXpEvent;

public class LapisBootsItem extends BootsItem {
    public LapisBootsItem() {
        super(ItemInit.ModArmorMaterial.LAPIS, "lapis_boots");
    }

    @Override
    public void onPlayerXpChange(PlayerXpEvent.XpChange event) {
        event.setAmount(event.getAmount() * 2);
    }
}
