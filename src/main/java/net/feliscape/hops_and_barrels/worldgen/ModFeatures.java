package net.feliscape.hops_and_barrels.worldgen;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.worldgen.feature.HugeHoodedMushroomFeature;
import net.feliscape.hops_and_barrels.worldgen.feature.configurations.HugeHoodedMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.HugeRedMushroomFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(ForgeRegistries.FEATURES, Brewery.MOD_ID);

    public static final RegistryObject<Feature<HugeHoodedMushroomFeatureConfiguration>> HUGE_HOODED_MUSHROOM = FEATURES.register("huge_hooded_mushroom",
            () -> new HugeHoodedMushroomFeature(HugeHoodedMushroomFeatureConfiguration.CODEC));

    public static void register(IEventBus eventBus){
        FEATURES.register(eventBus);
    }
}
