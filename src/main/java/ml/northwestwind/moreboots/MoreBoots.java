package ml.northwestwind.moreboots;

import ml.northwestwind.moreboots.handler.Utils;
import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@Mod(Reference.MODID)
public class MoreBoots {
    public static MoreBoots INSTANCE;
    public static final Logger LOGGER = LogManager.getLogger();

    public MoreBoots() {
        Utils.initialize();
        INSTANCE = this;
    }

    public static class MoreBootsItemGroup extends CreativeModeTab {
        public static final CreativeModeTab INSTANCE = new MoreBootsItemGroup(CreativeModeTab.TABS.length, "morebootstab");

        private MoreBootsItemGroup(int index, String label) {
            super(index, label);
        }

        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemInit.CACTUS_BOOTS);
        }
    }
}
