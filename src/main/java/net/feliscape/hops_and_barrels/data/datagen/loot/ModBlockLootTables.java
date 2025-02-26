package net.feliscape.hops_and_barrels.data.datagen.loot;

import net.feliscape.hops_and_barrels.registry.ModBlocks;
import net.feliscape.hops_and_barrels.registry.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    //protected static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));

    @Override
    protected void generate() {
        // If you want a block to drop nothing ever, call .noLootTable() on the block. That will make it exempt from this.

        /* Special Loot Tables:
        this.add(ModBlocks.BLOCK.get(),
                block -> BUILDERFUNCTION);*/

        dropSelf(ModBlocks.KEG.get());
        dropSelf(ModBlocks.JUICER.get());
        dropSelf(ModBlocks.TRELLIS.get());
        dropSelf(ModBlocks.HOODED_MUSHROOM.get());
        this.add(ModBlocks.HOODED_MUSHROOM_BLOCK.get(), (block) -> this.createMushroomBlockDrop(block, ModBlocks.HOODED_MUSHROOM.get()));
        this.add(ModBlocks.HOODED_MUSHROOM_MUCUS_BLOCK.get(),
                createSingleItemTableWithSilkTouch(ModBlocks.HOODED_MUSHROOM_MUCUS_BLOCK.get(), ModItems.HOODED_MUSHROOM_MUCUS.get(), ConstantValue.exactly(4)));

        LootItemCondition.Builder barleyCondition = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.BARLEY.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7));
        this.add(ModBlocks.BARLEY.get(), block -> createCropDrops(ModBlocks.BARLEY.get(), ModItems.BARLEY.get(),
                ModItems.BARLEY_SEEDS.get(), barleyCondition));

        LootItemCondition.Builder hopsCondition = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.HOPS.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 3));
        this.add(ModBlocks.HOPS.get(), block -> createTrellisCropDrops(ModBlocks.HOPS.get(), ModItems.HOPS.get(), hopsCondition));

        LootItemCondition.Builder redGrapesCondition = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.RED_GRAPES.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 4));
        this.add(ModBlocks.RED_GRAPES.get(), block -> createTrellisCropDrops(ModBlocks.RED_GRAPES.get(), ModItems.RED_GRAPES.get(), redGrapesCondition));
        LootItemCondition.Builder whiteGrapesCondition = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.WHITE_GRAPES.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 4));
        this.add(ModBlocks.WHITE_GRAPES.get(), block -> createTrellisCropDrops(ModBlocks.WHITE_GRAPES.get(), ModItems.WHITE_GRAPES.get(), whiteGrapesCondition));

        LootItemCondition.Builder elderberryCondition = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(ModBlocks.ELDERBERRY.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 3));
        this.add(ModBlocks.ELDERBERRY.get(), block -> {
                    return this.applyExplosionDecay(ModBlocks.ELDERBERRY.get(),
                            LootTable.lootTable().withPool(LootPool.lootPool()
                                            .add(LootItem.lootTableItem(ModItems.ELDERBERRIES.get()).when(elderberryCondition))));
                });

        this.dropSelf(ModBlocks.ELDERBERRY_LOG.get());
        this.dropSelf(ModBlocks.ELDERBERRY_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_ELDERBERRY_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_ELDERBERRY_WOOD.get());
        this.dropSelf(ModBlocks.ELDERBERRY_PLANKS.get());

        this.add(ModBlocks.ELDERBERRY_SIGN.get(), block ->
                createSingleItemTable(ModItems.ELDERBERRY_SIGN.get()));
        this.add(ModBlocks.ELDERBERRY_WALL_SIGN.get(), block ->
                createSingleItemTable(ModItems.ELDERBERRY_SIGN.get()));
        this.add(ModBlocks.ELDERBERRY_HANGING_SIGN.get(), block ->
                createSingleItemTable(ModItems.ELDERBERRY_HANGING_SIGN.get()));
        this.add(ModBlocks.ELDERBERRY_WALL_HANGING_SIGN.get(), block ->
                createSingleItemTable(ModItems.ELDERBERRY_HANGING_SIGN.get()));

        dropSelf(ModBlocks.ELDERBERRY_STAIRS.get());
        dropSelf(ModBlocks.ELDERBERRY_BUTTON.get());
        dropSelf(ModBlocks.ELDERBERRY_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.ELDERBERRY_TRAPDOOR.get());
        dropSelf(ModBlocks.ELDERBERRY_FENCE.get());
        dropSelf(ModBlocks.ELDERBERRY_FENCE_GATE.get());
        dropSelf(ModBlocks.ELDERBERRY_SAPLING.get());

        this.add(ModBlocks.ELDERBERRY_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.ELDERBERRY_SLAB.get()));
        this.add(ModBlocks.ELDERBERRY_DOOR.get(),
                block -> createDoorTable(ModBlocks.ELDERBERRY_DOOR.get()));

        this.add(ModBlocks.ELDERBERRY_LEAVES.get(),
                block -> createLeavesDrops(ModBlocks.ELDERBERRY_LEAVES.get(), ModBlocks.ELDERBERRY_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
    protected LootTable.Builder createTrellisCropDrops(Block pCropBlock, Item pGrownCropItem, LootItemCondition.Builder pDropGrownCropCondition) {
        return this.applyExplosionDecay(pCropBlock,
                LootTable.lootTable().withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(pGrownCropItem).when(pDropGrownCropCondition)))
                        .withPool(LootPool.lootPool().add(LootItem.lootTableItem(ModBlocks.TRELLIS.get()))));
    }
}
