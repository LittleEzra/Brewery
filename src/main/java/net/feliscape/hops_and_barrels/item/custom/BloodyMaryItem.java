package net.feliscape.hops_and_barrels.item.custom;

import net.feliscape.hops_and_barrels.effect.ModMobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class BloodyMaryItem extends AlcoholItem{
    private static final Supplier<MobEffectInstance> effect = () -> new MobEffectInstance(ModMobEffects.SIPHON.get(), 200, 0);

    public BloodyMaryItem(Properties pProperties, Supplier<Item> remainder) {
        super(pProperties, remainder, 1);
    }

    @Override
    public void onDrink(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        pEntityLiving.addEffect(effect.get());
        pEntityLiving.setHealth(1.0f);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        PotionUtils.addPotionTooltip(List.of(effect.get()), pTooltipComponents, 1.0F);
    }
}
