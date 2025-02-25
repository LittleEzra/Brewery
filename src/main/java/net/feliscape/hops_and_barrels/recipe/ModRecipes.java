package net.feliscape.hops_and_barrels.recipe;

import net.feliscape.hops_and_barrels.Brewery;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Brewery.MOD_ID);

    public static final RegistryObject<RecipeSerializer<KegBrewingRecipe>> KEG_BREWING =
            SERIALIZERS.register("keg_brewing", () -> KegBrewingRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<JuicingRecipe>> JUICING =
            SERIALIZERS.register("juicing", () -> JuicingRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
