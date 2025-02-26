package net.feliscape.hops_and_barrels.content.block.custom;

import net.feliscape.hops_and_barrels.Brewery;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;

import javax.swing.text.html.Option;
import java.util.Optional;

public class HoodedMushroomBlock extends BushBlock implements BonemealableBlock {
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 9.0D, 12.0D);
    private static final double BONEMEAL_SUCCESS_PROBABILITY = 0.4D;
    private final ResourceKey<ConfiguredFeature<?, ?>> feature;

    public HoodedMushroomBlock(Properties pProperties, ResourceKey<ConfiguredFeature<?, ?>> pFeature) {
        super(pProperties);
        this.feature = pFeature;
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(Blocks.MYCELIUM) ||pState.is(BlockTags.DIRT) || pState.is(Blocks.FARMLAND);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    private Optional<? extends Holder<ConfiguredFeature<?, ?>>> getFeature(LevelReader pLevel) {
        return pLevel.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(this.feature);
    }

    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return true;
    }

    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return (double)pRandom.nextFloat() < BONEMEAL_SUCCESS_PROBABILITY;
    }

    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        Optional<? extends Holder<ConfiguredFeature<?, ?>>> optionalFeature = this.getFeature(pLevel);
        optionalFeature.ifPresent((treeEvent) -> {
            var event = net.minecraftforge.event.ForgeEventFactory.blockGrowFeature(pLevel, pRandom, pPos, treeEvent);
            if (event.getResult().equals(net.minecraftforge.eventbus.api.Event.Result.DENY)) {
                return;
            }
            event.getFeature().value().place(pLevel, pLevel.getChunkSource().getGenerator(), pRandom, pPos);
        });
    }
}
