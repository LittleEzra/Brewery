package net.feliscape.hops_and_barrels.registry;

import net.feliscape.hops_and_barrels.Brewery;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> ALCOHOL_POISONING = register("alcohol_poisoning");

    public static void bootstrap(BootstapContext<DamageType> context){
        context.register(ModDamageTypes.ALCOHOL_POISONING, new DamageType("alcohol_poisoning", 0.0F));
    }

    private static ResourceKey<DamageType> register(String name)
    {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Brewery.MOD_ID, name));
    }
}
