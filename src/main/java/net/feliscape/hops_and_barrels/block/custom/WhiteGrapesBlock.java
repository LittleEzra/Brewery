package net.feliscape.hops_and_barrels.block.custom;

import net.feliscape.hops_and_barrels.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WhiteGrapesBlock extends TrellisCropBlock {
    public static final int MAX_AGE = 4;
    public static IntegerProperty AGE = BlockStateProperties.AGE_4;

    private static final VoxelShape[] SHAPE_BY_AGE = createShapesByAge(2, 3, 4, 4, 4);


    public WhiteGrapesBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_AGE[this.getAge(pState)];
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    protected ItemLike getBaseSeedId() {
        return ModItems.WHITE_GRAPE_SEEDS.get();
    }

    @Override
    protected int getReturnHarvestAge() {
        return 2;
    }

    @Override
    protected boolean canHarvest(BlockState pState) {
        return super.canHarvest(pState) && pState.getValue(HEIGHT) > 0; // can't harvest base
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextInt(3) != 0) {
            super.randomTick(pState, pLevel, pPos, pRandom);
        }

    }

    protected int getBonemealAgeIncrease(Level pLevel) {
        return super.getBonemealAgeIncrease(pLevel) / 3;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE, HEIGHT);
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }
}
