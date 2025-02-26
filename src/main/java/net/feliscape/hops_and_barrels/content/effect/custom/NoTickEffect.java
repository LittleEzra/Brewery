package net.feliscape.hops_and_barrels.content.effect.custom;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class NoTickEffect extends MobEffect {
    public NoTickEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }



    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
