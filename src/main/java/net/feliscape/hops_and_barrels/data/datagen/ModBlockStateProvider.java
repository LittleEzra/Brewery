package net.feliscape.hops_and_barrels.data.datagen;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Brewery.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        crossBlockWithRenderType(ModBlocks.HOODED_MUSHROOM.get(), "cutout");
        blockWithItem(ModBlocks.HOODED_MUSHROOM_BLOCK);
        blockWithItemAndRenderType(ModBlocks.HOODED_MUSHROOM_MUCUS_BLOCK, "translucent");
        makeBarleyCrop(((CropBlock) ModBlocks.BARLEY.get()), "barley_stage", "barley_stage");

        leavesBlock(ModBlocks.ELDERBERRY_LEAVES, "cutout");

        logBlock((RotatedPillarBlock) ModBlocks.ELDERBERRY_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.ELDERBERRY_WOOD.get(), blockTexture(ModBlocks.ELDERBERRY_LOG.get()), blockTexture(ModBlocks.ELDERBERRY_LOG.get()));
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_ELDERBERRY_LOG.get(), blockTexture(ModBlocks.STRIPPED_ELDERBERRY_LOG.get()),
                new ResourceLocation(Brewery.MOD_ID, "block/stripped_elderberry_log_top"));
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_ELDERBERRY_WOOD.get(), blockTexture(ModBlocks.STRIPPED_ELDERBERRY_LOG.get()),
                blockTexture(ModBlocks.STRIPPED_ELDERBERRY_LOG.get()));

        blockItem(ModBlocks.ELDERBERRY_LOG);
        blockItem(ModBlocks.ELDERBERRY_WOOD);
        blockItem(ModBlocks.STRIPPED_ELDERBERRY_LOG);
        blockItem(ModBlocks.STRIPPED_ELDERBERRY_WOOD);

        blockWithItem(ModBlocks.ELDERBERRY_PLANKS);

        signBlock(((StandingSignBlock) ModBlocks.ELDERBERRY_SIGN.get()), ((WallSignBlock) ModBlocks.ELDERBERRY_WALL_SIGN.get()),
                blockTexture(ModBlocks.ELDERBERRY_PLANKS.get()));

        hangingSignBlock(ModBlocks.ELDERBERRY_HANGING_SIGN.get(), ModBlocks.ELDERBERRY_WALL_HANGING_SIGN.get(),
                blockTexture(ModBlocks.ELDERBERRY_PLANKS.get()));

        stairsBlock(((StairBlock) ModBlocks.ELDERBERRY_STAIRS.get()), blockTexture(ModBlocks.ELDERBERRY_PLANKS.get()));
        slabBlock(((SlabBlock) ModBlocks.ELDERBERRY_SLAB.get()), blockTexture(ModBlocks.ELDERBERRY_PLANKS.get()), blockTexture(ModBlocks.ELDERBERRY_PLANKS.get()));

        buttonBlock(((ButtonBlock) ModBlocks.ELDERBERRY_BUTTON.get()), blockTexture(ModBlocks.ELDERBERRY_PLANKS.get()));
        pressurePlateBlock(((PressurePlateBlock) ModBlocks.ELDERBERRY_PRESSURE_PLATE.get()), blockTexture(ModBlocks.ELDERBERRY_PLANKS.get()));

        fenceBlock(((FenceBlock) ModBlocks.ELDERBERRY_FENCE.get()), blockTexture(ModBlocks.ELDERBERRY_PLANKS.get()));
        fenceGateBlock(((FenceGateBlock) ModBlocks.ELDERBERRY_FENCE_GATE.get()), blockTexture(ModBlocks.ELDERBERRY_PLANKS.get()));

        doorBlockWithRenderType(((DoorBlock) ModBlocks.ELDERBERRY_DOOR.get()), modLoc("block/elderberry_door_bottom"), modLoc("block/elderberry_door_top"), "cutout");
        trapdoorBlockWithRenderType(((TrapDoorBlock) ModBlocks.ELDERBERRY_TRAPDOOR.get()), modLoc("block/elderberry_trapdoor"), true, "cutout");

        crossBlockWithRenderType(ModBlocks.ELDERBERRY_SAPLING.get(), "cutout");
    }

    public void makeBarleyCrop(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> barleyStates(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] barleyStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(CropBlock.AGE),
                new ResourceLocation(Brewery.MOD_ID, "block/" + textureName + state.getValue(CropBlock.AGE))).renderType("cutout"));

        return models;
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(Brewery.MOD_ID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
    private void blockWithItemAndRenderType(RegistryObject<Block> blockRegistryObject, String renderType){
        simpleBlockWithItem(blockRegistryObject.get(), models().cubeAll(name(blockRegistryObject.get()), blockTexture(blockRegistryObject.get())).renderType(renderType));
    }
    private void leavesBlock(RegistryObject<Block> blockRegistryObject, String renderType){
        ModelFile model = models().withExistingParent(blockRegistryObject.getId().getPath(), "minecraft:block/leaves")
                .texture("all", blockTexture(blockRegistryObject.get())).renderType(renderType);
        getVariantBuilder(blockRegistryObject.get())
                .partialState().setModels( new ConfiguredModel(model));
        simpleBlockItem(blockRegistryObject.get(), model);
    }

    public void logBlockWithItem(RotatedPillarBlock block) {
        axisBlockWithItem(block, blockTexture(block), extend(blockTexture(block), "_top"));
    }

    public void axisBlockWithItem(RotatedPillarBlock block) {
        axisBlockWithItem(block, extend(blockTexture(block), "_side"),
                extend(blockTexture(block), "_end"));
    }

    public void axisBlockWithItem(RotatedPillarBlock block, ResourceLocation side, ResourceLocation end) {
        axisBlockWithItem(block,
                models().cubeColumn(name(block), side, end),
                models().cubeColumnHorizontal(name(block) + "_horizontal", side, end));
    }

    public void axisBlockWithItem(RotatedPillarBlock block, ModelFile vertical, ModelFile horizontal) {
        getVariantBuilder(block)
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Y)
                .modelForState().modelFile(vertical).addModel()
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.Z)
                .modelForState().modelFile(horizontal).rotationX(90).addModel()
                .partialState().with(RotatedPillarBlock.AXIS, Direction.Axis.X)
                .modelForState().modelFile(horizontal).rotationX(90).rotationY(90).addModel();
        simpleBlockItem(block, vertical);
    }

    public void crossBlockWithRenderType(Block block, String renderType) {
        getVariantBuilder(block).partialState().setModels(new ConfiguredModel(models().cross(name(block), blockTexture(block)).renderType(renderType)));
    }

    public void horizontalBlockWithItem(RegistryObject<Block> block, ModelFile model){
        horizontalBlock(block.get(), model);
        simpleBlockItem(block.get(), model);
    }
    public void cubeBottomTop(Block block){
        simpleBlockWithItem(block, models().cubeBottomTop(name(block),
                extend(blockTexture(block), "_side"),
                extend(blockTexture(block), "_bottom"),
                extend(blockTexture(block), "_top")));
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
    private String name(Block block) {
        return key(block).getPath();
    }
    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
    }

}
