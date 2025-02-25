package net.feliscape.hops_and_barrels.effect.custom;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.particle.ModParticles;
import net.feliscape.hops_and_barrels.particle.client.ModParticleRenderTypes;
import net.feliscape.hops_and_barrels.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;

public class TreasureHunterMobEffect extends MobEffect {
    public TreasureHunterMobEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity.level().isClientSide()){
            int range = 6 + pAmplifier * 2;
            RandomSource randomSource = pLivingEntity.getRandom();
            for (int x = -range; x <= range; x++){
                for (int y = -range; y <= range; y++){
                    for (int z = -range; z <= range; z++){
                        BlockPos blockPos = pLivingEntity.blockPosition().offset(x, y, z);
                        BlockState blockState = pLivingEntity.level().getBlockState(blockPos);
                        if (blockState.is(ModTags.Blocks.TREASURE)){
                            pLivingEntity.level().addParticle(ModParticles.TREASURE.get(),
                                    blockPos.getX() + randomSource.nextDouble(),
                                    blockPos.getY() + randomSource.nextDouble(),
                                    blockPos.getZ() + randomSource.nextDouble(), 0D, 0D, 0D);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 6 == 0;
    }
}
