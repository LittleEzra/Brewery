package net.feliscape.hops_and_barrels.item.custom;

import net.feliscape.hops_and_barrels.block.custom.TrellisCropBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class TrellisSeedItem extends Item {
    private final TrellisCropBlock block;

    public TrellisSeedItem(TrellisCropBlock pBlock, Properties pProperties) {
        super(pProperties);
        this.block = pBlock;
    }

    public TrellisCropBlock getBlock(){
        return block;
    }
}
