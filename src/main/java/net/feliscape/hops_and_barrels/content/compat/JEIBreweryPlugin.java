package net.feliscape.hops_and_barrels.content.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.content.recipe.JuicingRecipe;
import net.feliscape.hops_and_barrels.content.recipe.KegBrewingRecipe;
import net.feliscape.hops_and_barrels.client.screen.JuicerScreen;
import net.feliscape.hops_and_barrels.client.screen.KegScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIBreweryPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Brewery.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new KegBrewingCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new JuicingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<KegBrewingRecipe> kegBrewingRecipes = recipeManager.getAllRecipesFor(KegBrewingRecipe.Type.INSTANCE);
        registration.addRecipes(KegBrewingCategory.KEG_BREWING_TYPE, kegBrewingRecipes);
        List<JuicingRecipe> juicingRecipes = recipeManager.getAllRecipesFor(JuicingRecipe.Type.INSTANCE);
        registration.addRecipes(JuicingCategory.JUICING_TYPE, juicingRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(KegScreen.class, 61, 19, 13, 45,
                KegBrewingCategory.KEG_BREWING_TYPE);
        registration.addRecipeClickArea(JuicerScreen.class, 83, 54, 10, 10,
                JuicingCategory.JUICING_TYPE);
    }
}
