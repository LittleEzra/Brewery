package net.feliscape.hops_and_barrels.damagesource;

import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import org.jetbrains.annotations.NotNull;

public class ModDamageSources {
    public static DamageSource alcoholPoisoning(@NotNull RegistryAccess registryAccess){
        return new DamageSource(registryAccess.registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(ModDamageTypes.ALCOHOL_POISONING));
    }
}
