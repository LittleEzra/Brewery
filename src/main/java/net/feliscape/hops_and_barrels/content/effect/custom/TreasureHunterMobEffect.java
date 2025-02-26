package net.feliscape.hops_and_barrels.content.effect.custom;

import net.feliscape.hops_and_barrels.registry.ModParticles;
import net.feliscape.hops_and_barrels.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;

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
