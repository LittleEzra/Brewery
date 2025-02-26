package net.feliscape.hops_and_barrels.data.datagen.loot;

import com.google.common.collect.Sets;
import net.feliscape.hops_and_barrels.Brewery;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.Set;

public class ModBuiltInLootTables {
    private static final Set<ResourceLocation> LOCATIONS = Sets.newHashSet();
    private static final Set<ResourceLocation> IMMUTABLE_LOCATIONS = Collections.unmodifiableSet(LOCATIONS);

    public static final ResourceLocation PIRACY_LOOT = register("gameplay/effect/piracy");

    private static ResourceLocation register(String pId) {
        return register(new ResourceLocation(Brewery.MOD_ID, pId));
    }

    private static ResourceLocation register(ResourceLocation pId) {
        if (LOCATIONS.add(pId)) {
            return pId;
        } else {
            throw new IllegalArgumentException(pId + " is already a registered built-in loot table");
        }
    }

    public static Set<ResourceLocation> all() {
        return IMMUTABLE_LOCATIONS;
    }
}
