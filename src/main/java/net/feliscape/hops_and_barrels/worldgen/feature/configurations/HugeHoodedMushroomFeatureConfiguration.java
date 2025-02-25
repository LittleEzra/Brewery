package net.feliscape.hops_and_barrels.worldgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class HugeHoodedMushroomFeatureConfiguration implements FeatureConfiguration {
    public static final Codec<HugeHoodedMushroomFeatureConfiguration> CODEC = RecordCodecBuilder.create((group) -> {
        return group.group(BlockStateProvider.CODEC.fieldOf("cap_provider").forGetter((config) -> {
            return config.capProvider;
        }), BlockStateProvider.CODEC.fieldOf("stem_provider").forGetter((config) -> {
            return config.stemProvider;
        }), BlockStateProvider.CODEC.fieldOf("mucus_provider").forGetter((config) -> {
            return config.mucusProvider;
        }), Codec.INT.fieldOf("foliage_radius").orElse(2).forGetter((config) -> {
            return config.foliageRadius;
        })).apply(group, HugeHoodedMushroomFeatureConfiguration::new);
    });
    public final BlockStateProvider capProvider;
    public final BlockStateProvider stemProvider;
    public final BlockStateProvider mucusProvider;
    public final int foliageRadius;

    public HugeHoodedMushroomFeatureConfiguration(BlockStateProvider capProvider, BlockStateProvider stemProvider, BlockStateProvider mucusProvider, int foliageRadius) {
        this.capProvider = capProvider;
        this.stemProvider = stemProvider;
        this.mucusProvider = mucusProvider;
        this.foliageRadius = foliageRadius;
    }
}