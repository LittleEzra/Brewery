package net.feliscape.hops_and_barrels.block.custom;

import net.feliscape.hops_and_barrels.item.ModItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;

public class BarleyBlock extends CropBlock {
    public BarleyBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.BARLEY_SEEDS.get();
    }
}
