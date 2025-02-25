package net.feliscape.hops_and_barrels.datagen;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.block.ModBlocks;
import net.feliscape.hops_and_barrels.item.ModItems;
import net.feliscape.hops_and_barrels.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                               CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, Brewery.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ModTags.Items.ALCOHOL)
                .add(
                        ModItems.BEER.get(),
                        ModItems.RED_WINE.get(),
                        ModItems.WHITE_WINE.get(),
                        ModItems.STARSHINE.get(),
                        ModItems.HARDY_BREW.get(),
                        ModItems.BLOODY_MARY.get(),
                        ModItems.RUM.get(),
                        ModItems.VODKA.get(),
                        ModItems.MEAD.get(),
                        ModItems.SUNSET_ROSE.get(),
                        ModItems.WHISKEY.get(),
                        ModItems.OLD_WINE.get()
                );

        this.tag(ItemTags.LEAVES)
                .add(ModBlocks.ELDERBERRY_LEAVES.get().asItem());
        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.ELDERBERRY_LOG.get().asItem());
        this.tag(ItemTags.PLANKS)
                .add(ModBlocks.ELDERBERRY_PLANKS.get().asItem());
        this.tag(ItemTags.WOODEN_STAIRS)
                .add(ModBlocks.ELDERBERRY_STAIRS.get().asItem());
        this.tag(ItemTags.WOODEN_SLABS)
                .add(ModBlocks.ELDERBERRY_SLAB.get().asItem());
        this.tag(ItemTags.WOODEN_BUTTONS)
                .add(ModBlocks.ELDERBERRY_BUTTON.get().asItem());
        this.tag(ItemTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.ELDERBERRY_PRESSURE_PLATE.get().asItem());
        this.tag(ItemTags.WOODEN_FENCES)
                .add(ModBlocks.ELDERBERRY_FENCE.get().asItem());
        this.tag(ItemTags.WOODEN_DOORS)
                .add(ModBlocks.ELDERBERRY_DOOR.get().asItem());
        this.tag(ItemTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.ELDERBERRY_TRAPDOOR.get().asItem());
        this.tag(ItemTags.SAPLINGS)
                .add(ModBlocks.ELDERBERRY_SAPLING.get().asItem());

        this.copy(ModTags.Blocks.ELDERBERRY_LOGS, ModTags.Items.ELDERBERRY_LOGS);
    }
}
