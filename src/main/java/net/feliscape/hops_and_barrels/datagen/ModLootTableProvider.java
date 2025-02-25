package net.feliscape.hops_and_barrels.datagen;

import net.feliscape.hops_and_barrels.datagen.loot.ModBlockLootTables;
import net.feliscape.hops_and_barrels.datagen.loot.ModBuiltInLootTables;
import net.feliscape.hops_and_barrels.datagen.loot.ModPiracyLootTables;
import net.feliscape.hops_and_barrels.datagen.loot.ModEntityLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;

public class ModLootTableProvider {
    public static LootTableProvider create(PackOutput output){
        return new LootTableProvider(output, ModBuiltInLootTables.all(), List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(ModEntityLootTables::new, LootContextParamSets.ENTITY),
                new LootTableProvider.SubProviderEntry(ModPiracyLootTables::new, LootContextParamSets.ENTITY)
        ));
    }
}
