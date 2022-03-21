package ml.northwestwind.moreboots.init.item;

import ml.northwestwind.moreboots.MoreBoots;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class TooltipItem extends Item {
    private final String registryName;

    public TooltipItem(Properties properties, String registryName) {
        super(properties);
        this.registryName = registryName;
    }

    public TooltipItem(String registryName) {
        this(new Item.Properties().tab(MoreBoots.MoreBootsItemGroup.INSTANCE), registryName);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {

    }
}
