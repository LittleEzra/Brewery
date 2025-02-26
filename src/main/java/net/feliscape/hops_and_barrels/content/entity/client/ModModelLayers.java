package net.feliscape.hops_and_barrels.content.entity.client;

import net.feliscape.hops_and_barrels.Brewery;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation ELDERBERRY_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(Brewery.MOD_ID, "boat/elderberry"), "main");
    public static final ModelLayerLocation ELDERBERRY_CHEST_BOAT_LAYER = new ModelLayerLocation(
            new ResourceLocation(Brewery.MOD_ID, "chest_boat/elderberry"), "main");
}
