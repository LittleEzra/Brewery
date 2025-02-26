package net.feliscape.hops_and_barrels.content.item.custom;

import net.feliscape.hops_and_barrels.content.block.custom.TrellisCropBlock;
import net.minecraft.world.item.Item;

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
