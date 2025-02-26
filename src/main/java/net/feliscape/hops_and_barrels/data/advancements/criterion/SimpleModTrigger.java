package net.feliscape.hops_and_barrels.data.advancements.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class SimpleModTrigger extends SimpleCriterionTrigger<SimpleModTrigger.TriggerInstance> {

    @Override
    protected TriggerInstance createInstance(JsonObject pJson, ContextAwarePredicate pPredicate, DeserializationContext pDeserializationContext) {
        return new TriggerInstance(getId());
    }

    @Override
    public ResourceLocation getId() {
        return null;
    }

    public void trigger(ServerPlayer player) {
        super.trigger(player, null);
    }

    public TriggerInstance instance() {
        return new TriggerInstance(getId());
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        public TriggerInstance(ResourceLocation idIn) {
            super(idIn, ContextAwarePredicate.ANY);
        }
    }

    public boolean matches(ItemStack pItem) {
        return true;
    }
}
