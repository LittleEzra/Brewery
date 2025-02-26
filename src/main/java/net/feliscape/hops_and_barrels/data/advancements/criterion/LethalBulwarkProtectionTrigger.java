package net.feliscape.hops_and_barrels.data.advancements.criterion;

import com.google.gson.JsonObject;
import net.feliscape.hops_and_barrels.Brewery;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class LethalBulwarkProtectionTrigger extends SimpleCriterionTrigger<LethalBulwarkProtectionTrigger.TriggerInstance> {
    static final ResourceLocation ID = new ResourceLocation(Brewery.MOD_ID, "lethal_bulwark_protection");

    public ResourceLocation getId() {
        return ID;
    }

    public LethalBulwarkProtectionTrigger.TriggerInstance createInstance(JsonObject pJson, ContextAwarePredicate pPredicate, DeserializationContext pDeserializationContext) {
        return new LethalBulwarkProtectionTrigger.TriggerInstance(pPredicate);
    }

    public void trigger(ServerPlayer pPlayer) {
        this.trigger(pPlayer, (triggerInstance) -> {
            return triggerInstance.matches();
        });
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ContextAwarePredicate pPlayer) {
            super(LethalBulwarkProtectionTrigger.ID, pPlayer);
        }

        public static LethalBulwarkProtectionTrigger.TriggerInstance any(){
            return new LethalBulwarkProtectionTrigger.TriggerInstance(ContextAwarePredicate.ANY);
        }

        public boolean matches() {
            return true;
        }

        public JsonObject serializeToJson(SerializationContext pConditions) {
            return super.serializeToJson(pConditions);
        }
    }
}
