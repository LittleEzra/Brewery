package net.feliscape.hops_and_barrels.datagen.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.feliscape.hops_and_barrels.recipe.ModRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class KegBrewingRecipeBuilder implements RecipeBuilder {
    private final Item result;
    private final Ingredient ingredient0;
    @Nullable
    private Ingredient ingredient1;
    private final int count;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();

    public KegBrewingRecipeBuilder(RecipeCategory pCategory, RecipeSerializer<?> pType, Ingredient pIngredient0,
                                   @Nullable Ingredient pIngredient1, ItemLike pResult, int pCount) {
        this.result = pResult.asItem();
        this.ingredient0 = pIngredient0;
        this.ingredient1 = pIngredient1;
        this.count = pCount;
    }

    @Override
    public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        return null;
    }

    @Override
    public RecipeBuilder group(@Nullable String pGroupName) {
        return null;
    }

    @Override
    public Item getResult() {
        return null;
    }

    @Override
    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {

    }

    public static class Result implements FinishedRecipe{

        private final ResourceLocation id;
        private final Ingredient ingredient0;
        @Nullable
        private final Ingredient ingredient1;
        private final Item result;
        private final int count;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, Ingredient ingredient0, @Nullable Ingredient ingredient1, Item result, int count, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.ingredient0 = ingredient0;
            this.ingredient1 = ingredient1;
            this.result = result;
            this.count = count;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("ingredients", new JsonArray());
            pJson.add("ingredient", this.ingredient0.toJson());
            pJson.addProperty("result", BuiltInRegistries.ITEM.getKey(this.result).toString());
            pJson.addProperty("count", this.count);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipes.KEG_BREWING.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}
