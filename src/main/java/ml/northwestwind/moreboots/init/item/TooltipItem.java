package ml.northwestwind.moreboots.init.item;

import ml.northwestwind.moreboots.MoreBoots;
import ml.northwestwind.moreboots.Reference;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class TooltipItem extends Item {
    private final String registryName;

    public TooltipItem(Properties properties, String registryName) {
        super(properties);
        this.registryName = registryName;
        setRegistryName(Reference.MODID, registryName);
    }

    public TooltipItem(String registryName) {
        this(new Item.Properties().tab(MoreBoots.MoreBootsItemGroup.INSTANCE), registryName);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
    }
}
