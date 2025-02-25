package net.feliscape.hops_and_barrels.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.block.ModBlocks;
import net.feliscape.hops_and_barrels.recipe.JuicingRecipe;
import net.feliscape.hops_and_barrels.recipe.KegBrewingRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class JuicingCategory implements IRecipeCategory<JuicingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Brewery.MOD_ID, "juicing");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Brewery.MOD_ID, "textures/gui/juicer.png");

    public static final RecipeType<JuicingRecipe> JUICING_TYPE =
            new RecipeType<>(UID, JuicingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public JuicingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 61, 8, 54, 83);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.JUICER.get()));
    }

    @Override
    public RecipeType<JuicingRecipe> getRecipeType() {
        return JUICING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container.hops_and_barrels.juicer");
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
    public void setRecipe(IRecipeLayoutBuilder builder, JuicingRecipe recipe, IFocusGroup focuses) {
        var ingredients = recipe.getIngredients();

        for(int slotY = 0; slotY < 3; ++slotY) {
            for(int slotX = 0; slotX < 3; ++slotX) {
                int index = slotX + slotY * 3;
                if (index >= ingredients.size()) continue;
                builder.addSlot(RecipeIngredientRole.INPUT, 1 + slotX * 18, 1 + slotY * 18).addIngredients(ingredients.get(index));
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 19, 66).addItemStack(recipe.getResultItem(null)); // Result Slot
    }

    /*@Override
    public void draw(KegBrewingCategory recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        if (recipe.isHeated()){
            burningFlame.draw(guiGraphics, 64, 24);
        } else{
            standardFlame.draw(guiGraphics, 64, 24);
        }
    }*/
}
