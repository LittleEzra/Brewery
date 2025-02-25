package net.feliscape.hops_and_barrels.datagen.loot;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.item.ModItems;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ModPiracyLootTables implements LootTableSubProvider {

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> pOutput) {
        pOutput.accept(ModBuiltInLootTables.PIRACY_LOOT, LootTable.lootTable()
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.IRON_NUGGET))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 7.0F))).when(randomChance(0.7F)))
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.GOLD_NUGGET))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))).when(randomChance(0.45F)))
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.GOLD_INGOT))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).when(randomChance(0.25F)))
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.EMERALD))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))).when(randomChance(0.15F)))
                .withPool(LootPool.lootPool().add(LootItem.lootTableItem(Items.DIAMOND))
                        .when(randomChance(0.05F)))
            );
    }

    private LootItemCondition.Builder randomChance(float chance){
        return LootItemRandomChanceCondition.randomChance(chance);
    }
}
