package net.feliscape.hops_and_barrels.data.datagen;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.registry.ModBlocks;
import net.feliscape.hops_and_barrels.registry.ModMobEffects;
import net.feliscape.hops_and_barrels.registry.ModItems;
import net.feliscape.hops_and_barrels.data.loot.AddItemModifier;
import net.feliscape.hops_and_barrels.data.loot.DoubleCropDropsModifier;
import net.feliscape.hops_and_barrels.data.loot.conditions.LootCropBlockCondition;
import net.feliscape.hops_and_barrels.data.loot.conditions.LootMobEffectCondition;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, Brewery.MOD_ID);
    }

    @Override
    protected void start() {
        add("barley_seeds_from_grass", new AddItemModifier(new LootItemCondition[]{
            LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.35F).build()}, ModItems.BARLEY_SEEDS.get()));
        add("hops_seeds_from_grass", new AddItemModifier(new LootItemCondition[]{
            LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.07F).build()}, ModItems.HOPS_SEEDS.get()));
        add("red_grape_seeds_from_grass", new AddItemModifier(new LootItemCondition[]{
            LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.14F).build()}, ModItems.RED_GRAPE_SEEDS.get()));
        add("white_grape_seeds_from_grass", new AddItemModifier(new LootItemCondition[]{
            LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
                LootItemRandomChanceCondition.randomChance(0.14F).build()}, ModItems.WHITE_GRAPE_SEEDS.get()));
        add("elderberry_sapling_from_taiga_villages", new AddItemModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(new ResourceLocation("chests/village/village_taiga_house")).build(),
                LootItemRandomChanceCondition.randomChance(0.65F).build()}, ModBlocks.ELDERBERRY_SAPLING.get()));
        add("pirate_rum_from_buried_treasure", new AddItemModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(new ResourceLocation("chests/buried_treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.25F).build()}, ModItems.PIRATE_RUM.get()));
        add("double_crop_drops", new DoubleCropDropsModifier(new LootItemCondition[]{
                LootCropBlockCondition.builder().build(),
                LootMobEffectCondition.builder(ModMobEffects.HUSBANDRY.get()).build()}));
    }
}
