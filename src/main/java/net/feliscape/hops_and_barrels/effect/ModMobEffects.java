package net.feliscape.hops_and_barrels.effect;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.effect.custom.NoTickEffect;
import net.feliscape.hops_and_barrels.effect.custom.SiphonMobEffect;
import net.feliscape.hops_and_barrels.effect.custom.TreasureHunterMobEffect;
import net.feliscape.hops_and_barrels.util.ColorUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Brewery.MOD_ID);

    //public static final RegistryObject<MobEffect> COMFORT = MOB_EFFECTS.register("comfort",
    //        () -> new ComfortEffect(MobEffectCategory.BENEFICIAL, getColor("#b1624d")));
    //public static final RegistryObject<MobEffect> DUMB_LUCK = MOB_EFFECTS.register("dumb_luck",
    //        () -> new NoTickEffect(MobEffectCategory.BENEFICIAL, getColor("#b1624d")));
    public static final RegistryObject<MobEffect> VITALITY = MOB_EFFECTS.register("vitality",
            () -> new NoTickEffect(MobEffectCategory.BENEFICIAL, ColorUtil.getIntColor("#f9a9d9")));
    public static final RegistryObject<MobEffect> BULWARK = MOB_EFFECTS.register("bulwark",
            () -> new NoTickEffect(MobEffectCategory.BENEFICIAL, ColorUtil.getIntColor("#7e7e7e")));
    public static final RegistryObject<MobEffect> SIPHON = MOB_EFFECTS.register("siphon",
            () -> new SiphonMobEffect(MobEffectCategory.BENEFICIAL, ColorUtil.getIntColor("#ae2334")));
    public static final RegistryObject<MobEffect> HUSBANDRY = MOB_EFFECTS.register("husbandry",
            () -> new NoTickEffect(MobEffectCategory.BENEFICIAL, ColorUtil.getIntColor("#ffd32d")));
    public static final RegistryObject<MobEffect> PILFERING = MOB_EFFECTS.register("pilfering",
            () -> new NoTickEffect(MobEffectCategory.BENEFICIAL, ColorUtil.getIntColor("#7aa2eb")));
    public static final RegistryObject<MobEffect> TREASURE_HUNTER = MOB_EFFECTS.register("treasure_hunter",
            () -> new TreasureHunterMobEffect(MobEffectCategory.BENEFICIAL, ColorUtil.getIntColor("#ffd069")));
    public static final RegistryObject<MobEffect> PIRACY = MOB_EFFECTS.register("piracy",
            () -> new NoTickEffect(MobEffectCategory.BENEFICIAL, ColorUtil.getIntColor("#ffd069")));

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }
}
