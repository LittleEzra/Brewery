package net.feliscape.hops_and_barrels.data.datagen;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.registry.ModBlocks;
import net.feliscape.hops_and_barrels.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Brewery.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.HANGOVER_CURE);
        simpleItem(ModItems.WINE_BOTTLE);

        mugItem(ModItems.BEER);
        simpleItem(ModItems.RED_WINE);
        simpleItem(ModItems.WHITE_WINE);
        simpleItem(ModItems.STARSHINE);
        mugItem(ModItems.HARDY_BREW);
        simpleItem(ModItems.BLOODY_MARY);
        simpleItem(ModItems.RUM);
        simpleItem(ModItems.VODKA);
        simpleItem(ModItems.MEAD);
        simpleItem(ModItems.SUNSET_ROSE);
        simpleItem(ModItems.WHISKEY);
        simpleItem(ModItems.OLD_WINE);
        simpleItem(ModItems.PIRATE_RUM);

        simpleItem(ModItems.RED_GRAPE_SEEDS);
        simpleItem(ModItems.RED_GRAPES);
        simpleItem(ModItems.RED_GRAPE_JUICE);
        simpleItem(ModItems.WHITE_GRAPE_SEEDS);
        simpleItem(ModItems.WHITE_GRAPES);
        simpleItem(ModItems.WHITE_GRAPE_JUICE);
        simpleItem(ModItems.HOPS_SEEDS);
        simpleItem(ModItems.HOPS);
        simpleItem(ModItems.HOODED_MUSHROOM_MUCUS);
        simpleItem(ModItems.HOODED_MUSHROOM_JUICE);
        simpleItem(ModItems.APPLE_JUICE);
        simpleItem(ModItems.GLOW_BERRY_JUICE);

        simpleItem(ModItems.ELDERBERRIES);
        simpleItem(ModItems.ELDERBERRY_JUICE);

        simpleItem(ModItems.BARLEY_SEEDS);
        simpleItem(ModItems.BARLEY);
        simpleItem(ModItems.MALTED_BARLEY);

        generatedBlockItem(ModBlocks.HOODED_MUSHROOM);

        manualBlockItem(ModBlocks.JUICER);

        blockItemSprite(ModBlocks.TRELLIS);
        generatedBlockItem(ModBlocks.ELDERBERRY, "elderberry_stage2");

        simpleItem(ModItems.ELDERBERRY_SIGN);
        simpleItem(ModItems.ELDERBERRY_HANGING_SIGN);

        simpleItem(ModItems.ELDERBERRY_BOAT);
        simpleItem(ModItems.ELDERBERRY_CHEST_BOAT);

        blockItemSprite(ModBlocks.ELDERBERRY_DOOR);

        manualBlockItem(ModBlocks.ELDERBERRY_STAIRS);
        manualBlockItem(ModBlocks.ELDERBERRY_SLAB);
        manualBlockItem(ModBlocks.ELDERBERRY_PRESSURE_PLATE);
        manualBlockItem(ModBlocks.ELDERBERRY_FENCE_GATE);
        buttonItem(ModBlocks.ELDERBERRY_BUTTON, ModBlocks.ELDERBERRY_PLANKS);
        fenceItem(ModBlocks.ELDERBERRY_FENCE, ModBlocks.ELDERBERRY_PLANKS);
        trapdoorItem(ModBlocks.ELDERBERRY_TRAPDOOR);

        generatedBlockItem(ModBlocks.ELDERBERRY_SAPLING);
    }

    // Shoutout to El_Redstoniano for making this
    private void trimmedArmorItem(RegistryObject<Item> itemRegistryObject) {
        final String MOD_ID = Brewery.MOD_ID; // Change this to your mod id

        if(itemRegistryObject.get() instanceof ArmorItem armorItem) {
            trimMaterials.entrySet().forEach(entry -> {

                ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
                float trimValue = entry.getValue();

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = "item/" + armorItem;
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = new ResourceLocation(MOD_ID, armorItemPath);
                ResourceLocation trimResLoc = new ResourceLocation(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = new ResourceLocation(MOD_ID, currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc)
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemRegistryObject.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                new ResourceLocation(MOD_ID,
                                        "item/" + itemRegistryObject.getId().getPath()));
            });
        }
    }
    private void trimmedLeatherArmorItem(RegistryObject<Item> itemRegistryObject) {
        final String MOD_ID = Brewery.MOD_ID; // Change this to your mod id

        if(itemRegistryObject.get() instanceof ArmorItem armorItem) {
            trimMaterials.entrySet().forEach(entry -> {

                ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
                float trimValue = entry.getValue();

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = "item/" + armorItem;
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = new ResourceLocation(MOD_ID, armorItemPath);
                ResourceLocation armorItemOverlayResLoc = new ResourceLocation(MOD_ID, armorItemPath + "_overlay");
                ResourceLocation trimResLoc = new ResourceLocation(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = new ResourceLocation(MOD_ID, currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc)
                        .texture("layer1", armorItemOverlayResLoc)
                        .texture("layer2", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemRegistryObject.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                new ResourceLocation(MOD_ID,
                                        "item/" + itemRegistryObject.getId().getPath()))
                        .texture("layer1",
                                new ResourceLocation(MOD_ID,
                                        "item/" + itemRegistryObject.getId().getPath() + "_overlay"));
            });
        }
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Brewery.MOD_ID, "item/" + item.getId().getPath()));
    }
    private ItemModelBuilder handheldItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(Brewery.MOD_ID, "item/" + item.getId().getPath()));
    }
    private ItemModelBuilder rotatedHandheldItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation(Brewery.MOD_ID, "item/rotated_handheld")).texture("layer0",
                new ResourceLocation(Brewery.MOD_ID, "item/" + item.getId().getPath()));
    }

    public void manualBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(Brewery.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }

    public void trapdoorItem(RegistryObject<Block> block) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  new ResourceLocation(Brewery.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  new ResourceLocation(Brewery.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  new ResourceLocation(Brewery.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder blockItemSprite(RegistryObject<Block> item) { // Uses a block instead of item
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Brewery.MOD_ID,"item/" + item.getId().getPath()));
    }
    private ItemModelBuilder generatedBlockItem(RegistryObject<Block> item, String texture) { // Uses a block instead of item
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Brewery.MOD_ID,"block/" + texture));
    }
    private ItemModelBuilder generatedBlockItem(RegistryObject<Block> item) { // Uses the texture from textures/block
        return generatedBlockItem(item, item.getId().getPath());
    }
    private ItemModelBuilder torchItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(Brewery.MOD_ID,"block/" + item.getId().getPath()));
    }
    private ItemModelBuilder mugItem(RegistryObject<Item> item){
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation(Brewery.MOD_ID, "item/mug_item")).texture("layer0",
                new ResourceLocation(Brewery.MOD_ID, "item/" + item.getId().getPath()));
    }
}
