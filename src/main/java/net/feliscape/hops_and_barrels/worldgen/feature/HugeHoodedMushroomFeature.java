package net.feliscape.hops_and_barrels.worldgen.feature;

import com.mojang.serialization.Codec;
import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.registry.ModBlocks;
import net.feliscape.hops_and_barrels.worldgen.feature.configurations.HugeHoodedMushroomFeatureConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class HugeHoodedMushroomFeature extends Feature<HugeHoodedMushroomFeatureConfiguration> {
    public HugeHoodedMushroomFeature(Codec<HugeHoodedMushroomFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    protected int getTreeRadiusForHeight(int p_65094_, int i, int pFoliageRadius, int pY) {
        int height = 0;
        if (pY < i && pY >= i - 3) {
            height = pFoliageRadius;
        } else if (pY == i) {
            height = pFoliageRadius;
        }

        return height;
    }

    protected void makeCap(LevelAccessor pLevel, RandomSource pRandom, BlockPos pPos, int pTreeHeight, BlockPos.MutableBlockPos pMutablePos,
                           HugeHoodedMushroomFeatureConfiguration pConfig) {
        for(int i = pTreeHeight - 4; i <= pTreeHeight; ++i) {
            int radius = i < pTreeHeight ? pConfig.foliageRadius : pConfig.foliageRadius - 1; // not-at-top? normal_radius : top_radius
            if (i == pTreeHeight - 4){ // first circle
                radius = pConfig.foliageRadius + 1;
            }
            int k = pConfig.foliageRadius - 2;

            if (i == pTreeHeight - 4){ // handle first circle
                for(int radX = -radius; radX <= radius; ++radX) {
                    for(int radZ = -radius; radZ <= radius; ++radZ) {
                        boolean pXpZCorner = radX > 1 && radZ > 1;
                        boolean pXmZCorner = radX > 1 && radZ < -1;
                        boolean mXpZCorner = radX < -1 && radZ > 1;
                        boolean mXmZCorner = radX < -1 && radZ < -1;
                        boolean isCorner = pXpZCorner || pXmZCorner || mXpZCorner || mXmZCorner; // = true if any of the corner flags are true
                        boolean isOuterEdge = radX == -radius || radX == radius || radZ == -radius || radZ == radius; // true if the pos is on the very outer edges

                        if (isCorner != isOuterEdge){
                            pMutablePos.setWithOffset(pPos, radX, i, radZ);
                            if (!pLevel.getBlockState(pMutablePos).isSolidRender(pLevel, pMutablePos)) {
                                BlockState blockstate = pConfig.capProvider.getState(pRandom, pPos);
                                this.setBlock(pLevel, pMutablePos, blockstate);
                            }
                            int mucusSize = pRandom.nextInt(3);
                            for (int mucusI = 0; mucusI < mucusSize; mucusI++){
                                pMutablePos.setWithOffset(pPos, radX, i - mucusI - 1, radZ);
                                if (!pLevel.getBlockState(pMutablePos).isSolidRender(pLevel, pMutablePos)) {
                                    BlockState blockstate = pConfig.mucusProvider.getState(pRandom, pPos);
                                    this.setBlock(pLevel, pMutablePos, blockstate);
                                }
                            }
                        }
                    }
                }
            }
            else{
                for(int radX = -radius; radX <= radius; ++radX) {
                    for(int radZ = -radius; radZ <= radius; ++radZ) {
                        boolean leftWall = radX == -radius;
                        boolean rightWall = radX == radius;
                        boolean topWall = radZ == -radius;
                        boolean bottomWall = radZ == radius;
                        boolean leftOrRightWall = leftWall || rightWall;
                        boolean topOrBottomWall = topWall || bottomWall;

                        if (i >= pTreeHeight || leftOrRightWall != topOrBottomWall) {
                            pMutablePos.setWithOffset(pPos, radX, i, radZ);
                            if (!pLevel.getBlockState(pMutablePos).isSolidRender(pLevel, pMutablePos)) {
                                BlockState blockstate = pConfig.capProvider.getState(pRandom, pPos);
                                this.setBlock(pLevel, pMutablePos, blockstate);
                            }
                        }
                    }
                }
            }
        }
    }

    protected void placeTrunk(LevelAccessor pLevel, RandomSource pRandom, BlockPos pPos, HugeHoodedMushroomFeatureConfiguration pConfig, int pMaxHeight, BlockPos.MutableBlockPos pMutablePos) {
        for(int i = 0; i < pMaxHeight; ++i) {
            pMutablePos.set(pPos).move(Direction.UP, i);
            if (!pLevel.getBlockState(pMutablePos).isSolidRender(pLevel, pMutablePos)) {
                this.setBlock(pLevel, pMutablePos, pConfig.stemProvider.getState(pRandom, pPos));
            }
        }

    }

    protected int getTreeHeight(RandomSource pRandom) {
        int i = pRandom.nextInt(3) + 7;
        if (pRandom.nextInt(12) == 0) {
            i *= 2;
        }

        return i;
    }

    protected boolean isValidPosition(LevelAccessor pLevel, BlockPos pPos, int pMaxHeight, BlockPos.MutableBlockPos pMutablePos, HugeHoodedMushroomFeatureConfiguration pConfig) {
        int i = pPos.getY();
        if (i >= pLevel.getMinBuildHeight() + 1 && i + pMaxHeight + 1 < pLevel.getMaxBuildHeight()) {
            BlockState groundState = pLevel.getBlockState(pPos.below());
            if (!isDirt(groundState) && !groundState.is(BlockTags.MUSHROOM_GROW_BLOCK)) {
                Brewery.printDebug("Couldn't place because of Ground Block");
                return false;
            } else {
                for(int y = 0; y <= pMaxHeight; ++y) {
                    int radius = this.getTreeRadiusForHeight(-1, -1, pConfig.foliageRadius, y);

                    for(int x = -radius; x <= radius; ++x) {
                        for(int z = -radius; z <= radius; ++z) {
                            BlockState blockState = pLevel.getBlockState(pMutablePos.setWithOffset(pPos, x, y, z));
                            if (!blockState.isAir() && !blockState.is(BlockTags.LEAVES) && !blockState.is(ModBlocks.HOODED_MUSHROOM.get())) {
                                Brewery.printDebug("Couldn't place because of " + blockState.getBlock().getName());
                                return false;
                            }
                        }
                    }
                }

                Brewery.printDebug("/!\\ Returned true! /!\\");
                return true;
            }
        } else {
            Brewery.printDebug("Couldn't place because of Build Height");
            return false;
        }
    }

    /**
     * Places the given feature at the given location.
     * During world generation, features are provided with a 3x3 region of chunks, centered on the chunk being generated,
     * that they can safely generate into.
     * @param pContext A context object with a reference to the level and the position the feature is being placed at
     */
    public boolean place(FeaturePlaceContext<HugeHoodedMushroomFeatureConfiguration> pContext) {
        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos blockpos = pContext.origin();
        RandomSource randomsource = pContext.random();
        HugeHoodedMushroomFeatureConfiguration config = pContext.config();
        int i = this.getTreeHeight(randomsource);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        if (!this.isValidPosition(worldgenlevel, blockpos, i, blockpos$mutableblockpos, config)) {
            return false;
        } else {
            this.makeCap(worldgenlevel, randomsource, blockpos, i, blockpos$mutableblockpos, config);
            this.placeTrunk(worldgenlevel, randomsource, blockpos, config, i, blockpos$mutableblockpos);
            return true;
        }
    }
}
