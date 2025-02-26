package net.feliscape.hops_and_barrels.data.advancements.criterion;

import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public abstract class ModCriterionTrigger extends SimpleCriterionTrigger<ModCriterionTrigger.TriggerInstance> {
    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public TriggerInstance(ResourceLocation idIn) {
            super(idIn, ContextAwarePredicate.ANY);
        }
    }

    public boolean matches(ItemStack pItem) {
        return true;
    }
}
