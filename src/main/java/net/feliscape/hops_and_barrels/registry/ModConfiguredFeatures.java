package net.feliscape.hops_and_barrels.registry;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.worldgen.feature.configurations.HugeHoodedMushroomFeatureConfiguration;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_HOODED_MUSHROOM_KEY = registerKey("huge_hooded_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ELDERBERRY_TREE_KEY = registerKey("elderberry_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HOODED_MUSHROOM_KEY = registerKey("hooded_mushroom");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context){
        register(context, HUGE_HOODED_MUSHROOM_KEY, ModFeatures.HUGE_HOODED_MUSHROOM.get(), new HugeHoodedMushroomFeatureConfiguration(
                BlockStateProvider.simple(ModBlocks.HOODED_MUSHROOM_BLOCK.get()),
                BlockStateProvider.simple(Blocks.MUSHROOM_STEM.defaultBlockState()
                        .setValue(HugeMushroomBlock.UP, Boolean.valueOf(false))
                        .setValue(HugeMushroomBlock.DOWN, Boolean.valueOf(false))),
                BlockStateProvider.simple(ModBlocks.HOODED_MUSHROOM_MUCUS_BLOCK.get()),
                2
        ));
        register(context, ELDERBERRY_TREE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.ELDERBERRY_LOG.get()),
                new StraightTrunkPlacer(2, 1, 3),

                BlockStateProvider.simple(ModBlocks.ELDERBERRY_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 3),

                new TwoLayersFeatureSize(1, 0, 2)
        ).build());


        register(context, HOODED_MUSHROOM_KEY,
                Feature.RANDOM_PATCH, new RandomPatchConfiguration(1, 0, 0,
                        PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.HOODED_MUSHROOM.get())))));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Brewery.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
