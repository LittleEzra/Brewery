package net.feliscape.hops_and_barrels.util;


import net.feliscape.hops_and_barrels.Brewery;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public class ModWoodTypes {
    public static final WoodType ELDERBERRY = WoodType.register(new WoodType(Brewery.MOD_ID + ":elderberry", BlockSetType.OAK));
}
