package net.feliscape.hops_and_barrels.content.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.feliscape.hops_and_barrels.Brewery;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class KegBrewingRecipe implements Recipe<SimpleContainer> {
    private final Ingredient container;
    private final ItemStack result;
    private final ResourceLocation id;
    final NonNullList<Ingredient> ingredients;

    public static final int MAX_INGREDIENTS = 2;

    public KegBrewingRecipe(Ingredient pIngredient, NonNullList<Ingredient> pReagents, ItemStack pResult, ResourceLocation pId) {
        this.container = pIngredient;
        this.ingredients = pReagents;
        this.result = pResult;
        this.id = pId;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (!container.test(pContainer.getItem(2))){
            return false;
        }

        NonNullList<Ingredient> neededIngredients = copyIngredients();

        int reagentCount = 0;

        for (int i = 0; i < MAX_INGREDIENTS; i++){ // Get all reagents
            ItemStack itemStack = pContainer.getItem(i);
            if (itemStack.isEmpty()){
                continue;
            }
            reagentCount++;
            for (int j = 0; j < neededIngredients.size(); j++){
                if (neededIngredients.get(j).test(itemStack)){
                    neededIngredients.set(j, Ingredient.EMPTY);
                    break;
                }
            }
        }

        return reagentCount == neededIngredients.size() && neededIngredients.stream().allMatch(Ingredient::isEmpty);
    }

    private NonNullList<Ingredient> copyIngredients() {
        NonNullList<Ingredient> reagentsCopy = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);
        for (int i = 0; i < ingredients.size(); i++){
            reagentsCopy.set(i, ingredients.get(i));
        }
        return reagentsCopy;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return result.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        ingredients.add(this.container);
        ingredients.addAll(this.ingredients);
        return ingredients;
    }


    public static class Type implements RecipeType<KegBrewingRecipe>{
        public static final Type INSTANCE = new Type();
        public static final String ID = "keg_brewing";
    }

    public static class Serializer implements RecipeSerializer<KegBrewingRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Brewery.MOD_ID, "keg_brewing");

        @Override
        public KegBrewingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            Ingredient ingredient;
            if (GsonHelper.isArrayNode(pSerializedRecipe, "container")) {
                ingredient = Ingredient.fromJson(GsonHelper.getAsJsonArray(pSerializedRecipe, "container"), false);
            } else {
                ingredient = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "container"), false);
            }
            NonNullList<Ingredient> ingredients = ingredientsFromJson(GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for brewing");
            } else if (ingredients.size() > MAX_INGREDIENTS) {
                throw new JsonParseException("Too many ingredients for brewing. The maximum is " + MAX_INGREDIENTS);
            } else {
                ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));
                return new KegBrewingRecipe(ingredient, ingredients, itemstack, pRecipeId);
            }
        }

        private static NonNullList<Ingredient> ingredientsFromJson(JsonArray pIngredientArray) {
            NonNullList<Ingredient> ingredients = NonNullList.create();

            for(int i = 0; i < pIngredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(pIngredientArray.get(i), false);
                ingredients.add(ingredient);
            }

            return ingredients;
        }

        @Override
        public @Nullable KegBrewingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
            int i = pBuffer.readVarInt();
            NonNullList<Ingredient> reagents = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < reagents.size(); ++j) {
                reagents.set(j, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack result = pBuffer.readItem();
            return new KegBrewingRecipe(ingredient, reagents, result, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, KegBrewingRecipe pRecipe) {
            pRecipe.container.toNetwork(pBuffer);
            pBuffer.writeVarInt(pRecipe.ingredients.size());

            for(Ingredient ingredient : pRecipe.ingredients) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}
