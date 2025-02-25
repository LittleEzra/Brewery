package net.feliscape.hops_and_barrels.util;

import net.feliscape.hops_and_barrels.Brewery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks{
        public static final TagKey<Block> ELDERBERRY_LOGS = create("elderberry_logs");
        public static final TagKey<Block> TRELLIS = create("trellis");
        public static final TagKey<Block> TREASURE = create("treasure");

        private static TagKey<Block> create(String path){
            return BlockTags.create(new ResourceLocation(Brewery.MOD_ID, path));
        }
    }

    public static class Items{
        public static final TagKey<Item> ELDERBERRY_LOGS = create("elderberry_logs");
        public static final TagKey<Item> ALCOHOL = create("alcohol");

        private static TagKey<Item> create(String path){
            return ItemTags.create(new ResourceLocation(Brewery.MOD_ID, path));
        }
    }
}
