package net.feliscape.hops_and_barrels.data.advancements.criterion;

import net.minecraft.advancements.CriteriaTriggers;

public class ModCriteriaTriggers {
    public static BrewingTrigger BREWING;
    public static LethalBulwarkProtectionTrigger LETHAL_BULWARK_PROTECTION;

    public static void registerTriggers(){
        BREWING = CriteriaTriggers.register(new BrewingTrigger());
        LETHAL_BULWARK_PROTECTION = CriteriaTriggers.register(new LethalBulwarkProtectionTrigger());
    }
}
