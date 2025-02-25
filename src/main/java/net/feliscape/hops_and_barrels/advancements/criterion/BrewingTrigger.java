package net.feliscape.hops_and_barrels.advancements.criterion;

import com.google.gson.JsonObject;
import net.feliscape.hops_and_barrels.Brewery;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class BrewingTrigger extends SimpleCriterionTrigger<BrewingTrigger.TriggerInstance> {
    static final ResourceLocation ID = new ResourceLocation(Brewery.MOD_ID, "brewing");

    public ResourceLocation getId() {
        return ID;
    }

    public BrewingTrigger.TriggerInstance createInstance(JsonObject pJson, ContextAwarePredicate pPredicate, DeserializationContext pDeserializationContext) {
        ItemPredicate itempredicate = ItemPredicate.fromJson(pJson.get("item"));
        return new BrewingTrigger.TriggerInstance(pPredicate, itempredicate);
    }

    public void trigger(ServerPlayer pPlayer, ItemStack pItem) {
        this.trigger(pPlayer, (triggerInstance) -> {
            return triggerInstance.matches(pItem);
        });
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate item;

        public TriggerInstance(ContextAwarePredicate pPlayer, ItemPredicate pItem) {
            super(BrewingTrigger.ID, pPlayer);
            this.item = pItem;
        }

        public static BrewingTrigger.TriggerInstance anyItem() {
            return new BrewingTrigger.TriggerInstance(ContextAwarePredicate.ANY, ItemPredicate.ANY);
        }

        public static BrewingTrigger.TriggerInstance brewedItem(ItemPredicate pItem) {
            return new BrewingTrigger.TriggerInstance(ContextAwarePredicate.ANY, pItem);
        }
        public static BrewingTrigger.TriggerInstance brewedItem(ItemLike pItem) {
            return new BrewingTrigger.TriggerInstance(ContextAwarePredicate.ANY, ItemPredicate.Builder.item().of(pItem).build());
        }
        public static BrewingTrigger.TriggerInstance brewedItem(TagKey<Item> pItem) {
            return new BrewingTrigger.TriggerInstance(ContextAwarePredicate.ANY, ItemPredicate.Builder.item().of(pItem).build());
        }

        public boolean matches(ItemStack pItem) {
            return this.item.matches(pItem);
        }

        public JsonObject serializeToJson(SerializationContext pConditions) {
            JsonObject jsonobject = super.serializeToJson(pConditions);
            jsonobject.add("item", this.item.serializeToJson());
            return jsonobject;
        }
    }
}
