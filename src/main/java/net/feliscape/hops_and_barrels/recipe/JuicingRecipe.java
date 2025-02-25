package net.feliscape.hops_and_barrels.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.ints.IntList;
import net.feliscape.hops_and_barrels.Brewery;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class JuicingRecipe implements Recipe<SimpleContainer> {
    private final ItemStack result;
    private final ResourceLocation id;
    final NonNullList<Ingredient> ingredients;
    private final boolean isSimple;

    public static final int MAX_INGREDIENTS = 9;

    public JuicingRecipe(NonNullList<Ingredient> pIngredients, ItemStack pResult, ResourceLocation pId) {
        this.ingredients = pIngredients;
        this.result = pResult;
        this.id = pId;
        this.isSimple = pIngredients.stream().allMatch(Ingredient::isSimple);
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        StackedContents stackedcontents = new StackedContents();
        java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
        int i = 0;

        for(int j = 0; j < pContainer.getContainerSize(); ++j) {
            ItemStack itemstack = pContainer.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                if (isSimple)
                    stackedcontents.accountStack(itemstack, 1);
                else inputs.add(itemstack);
            }
        }

        return i == this.ingredients.size() && (isSimple ? stackedcontents.canCraft(this, (IntList)null) : net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs,  this.ingredients) != null);
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
        return this.ingredients;
    }


    public static class Type implements RecipeType<JuicingRecipe>{
        public static final Type INSTANCE = new Type();
        public static final String ID = "juicing";
    }

    public static class Serializer implements RecipeSerializer<JuicingRecipe>{
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Brewery.MOD_ID, "juicing");

        @Override
        public JuicingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            NonNullList<Ingredient> ingredients = ingredientsFromJson(GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for brewing");
            } else if (ingredients.size() > MAX_INGREDIENTS) {
                throw new JsonParseException("Too many ingredients for brewing. The maximum is " + MAX_INGREDIENTS);
            } else {
                ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "result"));
                return new JuicingRecipe(ingredients, itemstack, pRecipeId);
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
        public @Nullable JuicingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            int i = pBuffer.readVarInt();
            NonNullList<Ingredient> reagents = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < reagents.size(); ++j) {
                reagents.set(j, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack result = pBuffer.readItem();
            return new JuicingRecipe(reagents, result, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, JuicingRecipe pRecipe) {
            pBuffer.writeVarInt(pRecipe.ingredients.size());

            for(Ingredient ingredient : pRecipe.ingredients) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
}
