package ml.northwestwind.moreboots.client;


import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import ml.northwestwind.moreboots.Reference;
import ml.northwestwind.moreboots.init.ItemInit;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;

@JeiPlugin
public class MoreBootsJEIPlugin implements IModPlugin {
    private static final ResourceLocation UID = new ResourceLocation(Reference.MODID, "jei_plugin");
    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.getIngredientManager().removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, Collections.singleton(ItemInit.BIONIC_BEETLE_FEET.get().getDefaultInstance()));
    }
}
