package net.feliscape.hops_and_barrels.datagen;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.block.ModBlocks;
import net.feliscape.hops_and_barrels.item.ModItems;
import net.feliscape.hops_and_barrels.util.ModTags;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WINE_BOTTLE.get(), 3)
                .pattern("#")
                .pattern("#")
                .pattern("#")
                .define('#', Blocks.GLASS)
                .unlockedBy(getHasName(Blocks.GLASS), has(Blocks.GLASS))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TRELLIS.get())
                .pattern("/")
                .pattern("/")
                .pattern("/")
                .define('/', Items.STICK)
                .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.HOODED_MUSHROOM_MUCUS_BLOCK.get())
                .pattern("##")
                .pattern("##")
                .define('#', ModItems.HOODED_MUSHROOM_MUCUS.get())
                .unlockedBy(getHasName(ModItems.HOODED_MUSHROOM_MUCUS.get()), has(ModItems.HOODED_MUSHROOM_MUCUS.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SUNSET_ROSE.get())
                .requires(ModItems.VODKA.get())
                .requires(ModItems.APPLE_JUICE.get())
                .requires(Items.PINK_PETALS, 2)
                .unlockedBy(getHasName(ModItems.VODKA.get()), has(ModItems.VODKA.get()))
                .save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BLOODY_MARY.get())
                .requires(ModItems.VODKA.get())
                .requires(Items.REDSTONE, 4)
                .requires(Items.FIRE_CORAL)
                .unlockedBy(getHasName(ModItems.VODKA.get()), has(ModItems.VODKA.get()))
                .save(pWriter);

        planksFromLog(pWriter, ModBlocks.ELDERBERRY_PLANKS.get(), ModTags.Items.ELDERBERRY_LOGS, 4);
        woodFromLogs(pWriter, ModBlocks.ELDERBERRY_WOOD.get(), ModBlocks.ELDERBERRY_LOG.get());
        woodFromLogs(pWriter, ModBlocks.STRIPPED_ELDERBERRY_WOOD.get(), ModBlocks.STRIPPED_ELDERBERRY_LOG.get());

        Ingredient elderberryPlankIngredient = Ingredient.of(ModBlocks.ELDERBERRY_PLANKS.get());

        stairBuilder(ModBlocks.ELDERBERRY_STAIRS.get(), elderberryPlankIngredient);
        slab(pWriter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.ELDERBERRY_SLAB.get(), ModBlocks.ELDERBERRY_PLANKS.get());
        doorBuilder(ModBlocks.ELDERBERRY_DOOR.get(), elderberryPlankIngredient)
                .unlockedBy(getHasName(ModBlocks.ELDERBERRY_PLANKS.get()), has(ModBlocks.ELDERBERRY_PLANKS.get()))
                .save(pWriter);
        trapdoorBuilder(ModBlocks.ELDERBERRY_TRAPDOOR.get(), elderberryPlankIngredient)
                .unlockedBy(getHasName(ModBlocks.ELDERBERRY_PLANKS.get()), has(ModBlocks.ELDERBERRY_PLANKS.get()))
                .save(pWriter);
        signBuilder(ModItems.ELDERBERRY_SIGN.get(), elderberryPlankIngredient)
                .unlockedBy(getHasName(ModBlocks.ELDERBERRY_PLANKS.get()), has(ModBlocks.ELDERBERRY_PLANKS.get()))
                .save(pWriter);
        hangingSign(pWriter, ModItems.ELDERBERRY_HANGING_SIGN.get(), ModBlocks.ELDERBERRY_PLANKS.get());

        fenceBuilder(ModBlocks.ELDERBERRY_FENCE.get(), elderberryPlankIngredient)
                .unlockedBy(getHasName(ModBlocks.ELDERBERRY_PLANKS.get()), has(ModBlocks.ELDERBERRY_PLANKS.get()))
                .save(pWriter);
        fenceGateBuilder(ModBlocks.ELDERBERRY_FENCE_GATE.get(), elderberryPlankIngredient)
                .unlockedBy(getHasName(ModBlocks.ELDERBERRY_PLANKS.get()), has(ModBlocks.ELDERBERRY_PLANKS.get()))
                .save(pWriter);;
        buttonBuilder(ModBlocks.ELDERBERRY_BUTTON.get(), elderberryPlankIngredient)
                .unlockedBy(getHasName(ModBlocks.ELDERBERRY_PLANKS.get()), has(ModBlocks.ELDERBERRY_PLANKS.get()))
                .save(pWriter);;
        pressurePlate(pWriter, ModBlocks.ELDERBERRY_PRESSURE_PLATE.get(), ModBlocks.ELDERBERRY_PLANKS.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.JUICER.get())
                .pattern("P P")
                .pattern("PSP")
                .pattern("PSP")
                .define('P', ItemTags.PLANKS)
                .define('S', ItemTags.WOODEN_SLABS)
                .unlockedBy("has_planks", has(ItemTags.PLANKS))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.KEG.get())
                .pattern("PIP")
                .pattern("S S")
                .pattern("PIP")
                .define('P', ItemTags.PLANKS)
                .define('S', ItemTags.WOODEN_SLABS)
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.HANGOVER_CURE.get())
                .requires(Items.GLASS_BOTTLE)
                .requires(Items.EGG, 2)
                .requires(Items.SUGAR, 2);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                    pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, Brewery.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }
}
