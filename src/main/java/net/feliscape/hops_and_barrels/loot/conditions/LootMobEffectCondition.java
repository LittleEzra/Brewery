package net.feliscape.hops_and_barrels.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.feliscape.hops_and_barrels.Brewery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.ForgeRegistries;

public class LootMobEffectCondition implements LootItemCondition {
    public static final LootItemConditionType LOOT_CONDITION_TYPE = new LootItemConditionType(new LootMobEffectCondition.Serializer());

    private final MobEffect mobEffect;

    private LootMobEffectCondition(MobEffect mobEffect)
    {
        this.mobEffect = mobEffect;
    }

    @Override
    public LootItemConditionType getType()
    {
        return LOOT_CONDITION_TYPE;
    }

    @Override
    public boolean test(LootContext lootContext)
    {
        Brewery.printDebug("Testing Mob Effect Condition");

        Entity entity = lootContext.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (!(entity instanceof LivingEntity)) return false;

        return ((LivingEntity) entity).hasEffect(mobEffect);
    }

    public static LootMobEffectCondition.Builder builder(MobEffect mobEffect)
    {
        return new LootMobEffectCondition.Builder(mobEffect);
    }

    public static class Builder implements LootItemCondition.Builder
    {
        private final MobEffect mobEffect;

        public Builder(MobEffect mobEffect)
        {
            this.mobEffect = mobEffect;
        }

        @Override
        public LootItemCondition build()
        {
            return new LootMobEffectCondition(this.mobEffect);
        }
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LootMobEffectCondition>
    {
        @Override
        public void serialize(JsonObject object, LootMobEffectCondition instance, JsonSerializationContext ctx)
        {
            ResourceLocation mobEffectLocation = ForgeRegistries.MOB_EFFECTS.getKey(instance.mobEffect);
            if (mobEffectLocation == null) throw new NullPointerException("Mob effect missing");
            object.addProperty("mob_effect", ForgeRegistries.MOB_EFFECTS.getKey(instance.mobEffect).toString());
        }

        @Override
        public LootMobEffectCondition deserialize(JsonObject object, JsonDeserializationContext ctx)
        {
            MobEffect mobEffect = ForgeRegistries.MOB_EFFECTS.getValue(
                    new ResourceLocation(GsonHelper.getAsString(object, "mob_effect")));
            if (mobEffect == null) throw new NullPointerException("Mob effect missing");
            return new LootMobEffectCondition(mobEffect);
        }
    }
}
