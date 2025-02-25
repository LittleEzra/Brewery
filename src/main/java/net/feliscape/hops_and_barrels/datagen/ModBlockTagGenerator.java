package net.feliscape.hops_and_barrels.datagen;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.block.ModBlocks;
import net.feliscape.hops_and_barrels.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Brewery.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        //this.tag(ModTags.Blocks.NAME)
        //        .add(Tags.Blocks.STONE);

        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.TRELLIS.get(),
                        ModBlocks.HOPS.get(),
                        ModBlocks.RED_GRAPES.get(),
                        ModBlocks.WHITE_GRAPES.get());

        this.tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.ELDERBERRY_FENCE_GATE.get());


        this.tag(BlockTags.PLANKS)
                .add(ModBlocks.ELDERBERRY_PLANKS.get());
        this.tag(ModTags.Blocks.ELDERBERRY_LOGS)
                .add(ModBlocks.ELDERBERRY_LOG.get())
                .add(ModBlocks.ELDERBERRY_WOOD.get())
                .add(ModBlocks.STRIPPED_ELDERBERRY_LOG.get())
                .add(ModBlocks.STRIPPED_ELDERBERRY_WOOD.get());

        this.tag(BlockTags.LOGS_THAT_BURN)
                .addTag(ModTags.Blocks.ELDERBERRY_LOGS);

        this.tag(BlockTags.WOODEN_STAIRS)
                .add(ModBlocks.ELDERBERRY_STAIRS.get());
        this.tag(BlockTags.WOODEN_SLABS)
                .add(ModBlocks.ELDERBERRY_SLAB.get());
        this.tag(BlockTags.WOODEN_BUTTONS)
                .add(ModBlocks.ELDERBERRY_BUTTON.get());
        this.tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.ELDERBERRY_PRESSURE_PLATE.get());
        this.tag(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.ELDERBERRY_FENCE.get());
        this.tag(BlockTags.WOODEN_DOORS)
                .add(ModBlocks.ELDERBERRY_DOOR.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.ELDERBERRY_TRAPDOOR.get());
        this.tag(BlockTags.SAPLINGS)
                .add(ModBlocks.ELDERBERRY_SAPLING.get());

        this.tag(ModTags.Blocks.TRELLIS)
                .add(ModBlocks.TRELLIS.get(),
                        ModBlocks.HOPS.get(),
                        ModBlocks.RED_GRAPES.get(),
                        ModBlocks.WHITE_GRAPES.get()
                );
        this.tag(BlockTags.LEAVES)
                .add(ModBlocks.ELDERBERRY_LEAVES.get()
                );
        this.tag(BlockTags.CROPS)
                .add(ModBlocks.BARLEY.get(),
                        ModBlocks.HOPS.get(),
                        ModBlocks.RED_GRAPES.get(),
                        ModBlocks.WHITE_GRAPES.get());
        this.tag(BlockTags.BEE_GROWABLES).remove(
                ModBlocks.HOPS.get(),
                ModBlocks.RED_GRAPES.get(),
                ModBlocks.WHITE_GRAPES.get()
        );
        this.tag(ModTags.Blocks.TREASURE).add(
                Blocks.GOLD_BLOCK,
                Blocks.EMERALD_BLOCK,
                Blocks.DIAMOND_BLOCK,
                Blocks.NETHERITE_BLOCK,
                ModBlocks.HOODED_MUSHROOM.get()
        ).addTag(Tags.Blocks.CHESTS);

    }
}
