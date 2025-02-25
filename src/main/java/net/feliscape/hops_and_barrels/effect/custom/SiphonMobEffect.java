package net.feliscape.hops_and_barrels.effect.custom;

import net.feliscape.hops_and_barrels.capability.EffectHandlerProvider;
import net.feliscape.hops_and_barrels.networking.ModMessages;
import net.feliscape.hops_and_barrels.networking.packets.SiphonDataSyncS2CPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class SiphonMobEffect extends MobEffect {
    public SiphonMobEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
        pLivingEntity.getCapability(EffectHandlerProvider.EFFECT_HANDLER).ifPresent(effectHandler -> {
            effectHandler.siphon.applySiphonedHearts(pLivingEntity);
            if (pLivingEntity instanceof ServerPlayer player){
                ModMessages.sendToPlayer(new SiphonDataSyncS2CPacket(effectHandler.siphon.getSiphonedHearts()), player);
            }
        });
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
