package net.feliscape.hops_and_barrels.content.block.custom;

import net.feliscape.hops_and_barrels.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ElderberryLeavesBlock extends LeavesBlock {
    private static final float ELDERBERRY_GROW_CHANCE = 0.04f;

    public ElderberryLeavesBlock(Properties pProperties) {
        super(pProperties);
    }

    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (this.decaying(pState)) {
            dropResources(pState, pLevel, pPos);
            pLevel.removeBlock(pPos, false);
            return;
        }
        BlockPos belowPos = pPos.below();
        if (pRandom.nextFloat() <= ELDERBERRY_GROW_CHANCE && pLevel.getBlockState(belowPos).isAir()){
            pLevel.setBlock(belowPos, ModBlocks.ELDERBERRY.get().defaultBlockState(), 11);
        }
    }
}
