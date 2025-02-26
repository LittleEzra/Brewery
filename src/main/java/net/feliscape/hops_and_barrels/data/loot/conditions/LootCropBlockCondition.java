package net.feliscape.hops_and_barrels.data.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.feliscape.hops_and_barrels.Brewery;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class LootCropBlockCondition implements LootItemCondition {
    public static final LootItemConditionType LOOT_CONDITION_TYPE = new LootItemConditionType(new LootCropBlockCondition.Serializer());

    private LootCropBlockCondition()
    {

    }

    @Override
    public LootItemConditionType getType()
    {
        return LOOT_CONDITION_TYPE;
    }

    @Override
    public boolean test(LootContext lootContext)
    {
        Brewery.printDebug("Testing Crop Block Condition");
        BlockState blockState = lootContext.getParamOrNull(LootContextParams.BLOCK_STATE);
        return blockState != null && (blockState.getBlock() instanceof CropBlock || blockState.is(BlockTags.CROPS));
    }

    public static LootCropBlockCondition.Builder builder()
    {
        return new LootCropBlockCondition.Builder();
    }

    public static class Builder implements LootItemCondition.Builder
    {
        public Builder()
        {

        }

        @Override
        public LootItemCondition build()
        {
            return new LootCropBlockCondition();
        }
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LootCropBlockCondition>
    {
        @Override
        public void serialize(JsonObject object, LootCropBlockCondition instance, JsonSerializationContext ctx)
        {

        }

        @Override
        public LootCropBlockCondition deserialize(JsonObject object, JsonDeserializationContext ctx)
        {
            return new LootCropBlockCondition();
        }
    }
}
