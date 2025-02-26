package net.feliscape.hops_and_barrels.content.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class AlcoholBuffItem extends AlcoholItem {
    private final Supplier<MobEffectInstance> effect;

    public AlcoholBuffItem(Properties pProperties, Supplier<Item> remainder, Supplier<MobEffectInstance> effect) {
        super(pProperties, remainder);
        this.effect = effect;
    }
    public AlcoholBuffItem(Properties pProperties, Supplier<Item> remainder, Supplier<MobEffectInstance> effect, int severity) {
        super(pProperties, remainder, severity);
        this.effect = effect;
    }

    @Override
    public void onDrink(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        Player player = pEntityLiving instanceof Player ? (Player)pEntityLiving : null;
        if (!pLevel.isClientSide) {
            MobEffectInstance mobeffectinstance = effect.get();
            if (mobeffectinstance.getEffect().isInstantenous()) {
                mobeffectinstance.getEffect().applyInstantenousEffect(player, player, pEntityLiving, mobeffectinstance.getAmplifier(), 1.0D);
            } else {
                pEntityLiving.addEffect(new MobEffectInstance(mobeffectinstance));
            }
        }
    }

    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        PotionUtils.addPotionTooltip(List.of(effect.get()), pTooltip, 1.0F);
    }
}
