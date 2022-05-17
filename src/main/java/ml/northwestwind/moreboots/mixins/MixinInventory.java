package ml.northwestwind.moreboots.mixins;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(Inventory.class)
public interface MixinInventory {
    @Accessor("compartments")
    List<NonNullList<ItemStack>> compartments();
}
