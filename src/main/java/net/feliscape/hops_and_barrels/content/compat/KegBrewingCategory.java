package net.feliscape.hops_and_barrels.content.compat;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.registry.ModBlocks;
import net.feliscape.hops_and_barrels.content.recipe.KegBrewingRecipe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class KegBrewingCategory implements IRecipeCategory<KegBrewingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Brewery.MOD_ID, "keg_brewing");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Brewery.MOD_ID, "textures/gui/keg.png");

    public static final RecipeType<KegBrewingRecipe> KEG_BREWING_TYPE =
            new RecipeType<>(UID, KegBrewingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final LoadingCache<Integer, IDrawableAnimated> cachedBubbles;

    public KegBrewingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 29, 19, 120, 45);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.KEG.get()));
        this.cachedBubbles = CacheBuilder.newBuilder()
                .maximumSize(48)
                .build(new CacheLoader<>() {
                    @Override
                    public IDrawableAnimated load(Integer cookTime) {
                        return helper.drawableBuilder(TEXTURE, 176, 0, 13, 45)
                                .buildAnimated(cookTime, IDrawableAnimated.StartDirection.BOTTOM, false);
                    }
                });
    }

    protected IDrawableAnimated getBubbles(int brewTime) {
        return this.cachedBubbles.getUnchecked(brewTime);
    }

    @Override
    public RecipeType<KegBrewingRecipe> getRecipeType() {
        return KEG_BREWING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container.hops_and_barrels.keg");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, KegBrewingRecipe recipe, IFocusGroup focuses) {
        var ingredients = recipe.getIngredients();
        if (ingredients.size() >= 1) builder.addSlot(RecipeIngredientRole.INPUT, 62, 14).addIngredients(ingredients.get(0)); // Container Slot
        if (ingredients.size() >= 2) builder.addSlot(RecipeIngredientRole.INPUT, 1, 5).addIngredients(ingredients.get(1)); // Ingredient 1
        if (ingredients.size() >= 3) builder.addSlot(RecipeIngredientRole.INPUT, 1, 23).addIngredients(ingredients.get(2)); // Ingredient 2
        builder.addSlot(RecipeIngredientRole.OUTPUT, 99, 14).addItemStack(recipe.getResultItem(null)); // Result Slot
    }

    @Override
    public void draw(KegBrewingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        IDrawableAnimated bubbles = getBubbles(360);
        bubbles.draw(guiGraphics, 32, 0);
    }
}
