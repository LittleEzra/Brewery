package net.feliscape.hops_and_barrels.registry;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.content.block.custom.*;
import net.feliscape.hops_and_barrels.worldgen.tree.ElderberryTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Brewery.MOD_ID);

    public static final RegistryObject<Block> KEG = registerBlock("keg",
            () -> new KegBlock(BlockBehaviour.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> JUICER = registerBlock("juicer",
            () -> new JuicerBlock(BlockBehaviour.Properties.copy(Blocks.COMPOSTER)));
    public static final RegistryObject<Block> HOODED_MUSHROOM = registerBlock("hooded_mushroom",
            () -> new HoodedMushroomBlock(BlockBehaviour.Properties.copy(Blocks.WARPED_FUNGUS).lightLevel(state -> 5).mapColor(MapColor.COLOR_BLUE), ModConfiguredFeatures.HUGE_HOODED_MUSHROOM_KEY));

    public static final RegistryObject<Block> TRELLIS = registerBlock("trellis",
            () -> new TrellisBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));

    public static final RegistryObject<Block> BARLEY = BLOCKS.register("barley",
            () -> new BarleyBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noCollission().noOcclusion()));
    public static final RegistryObject<Block> HOPS = BLOCKS.register("hops",
            () -> new HopsBlock(BlockBehaviour.Properties.copy(ModBlocks.TRELLIS.get()).noOcclusion().randomTicks().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> RED_GRAPES = BLOCKS.register("red_grapes",
            () -> new RedGrapesBlock(BlockBehaviour.Properties.copy(ModBlocks.TRELLIS.get()).noOcclusion().randomTicks().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> WHITE_GRAPES = BLOCKS.register("white_grapes",
            () -> new WhiteGrapesBlock(BlockBehaviour.Properties.copy(ModBlocks.TRELLIS.get()).noOcclusion().randomTicks().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> ELDERBERRY = registerBlock("elderberry",
            () -> new ElderberryBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noCollission().noOcclusion()));

    public static final RegistryObject<Block> HOODED_MUSHROOM_BLOCK = registerBlock("hooded_mushroom_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F).sound(SoundType.WOOD).ignitedByLava().lightLevel(state -> 7)));
    public static final RegistryObject<Block> HOODED_MUSHROOM_MUCUS_BLOCK = registerBlock("hooded_mushroom_mucus_block",
            () -> new HoodedMushroomMucusBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).instrument(NoteBlockInstrument.SNARE)
                    .strength(0.2F).sound(SoundType.HONEY_BLOCK).noOcclusion().noCollission().lightLevel(state -> 7)));

    public static final RegistryObject<Block> ELDERBERRY_LEAVES = registerBlock("elderberry_leaves",
            () -> elderberryLeaves(SoundType.GRASS));

    public static final RegistryObject<Block> ELDERBERRY_LOG = registerBlock("elderberry_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(4f)));
    public static final RegistryObject<Block> ELDERBERRY_WOOD = registerBlock("elderberry_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(4f)));
    public static final RegistryObject<Block> STRIPPED_ELDERBERRY_LOG = registerBlock("stripped_elderberry_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).strength(4f)));
    public static final RegistryObject<Block> STRIPPED_ELDERBERRY_WOOD = registerBlock("stripped_elderberry_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).strength(4f)));

    public static final RegistryObject<Block> ELDERBERRY_PLANKS = registerBlock("elderberry_planks",
            () -> simpleFlammableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.COLOR_MAGENTA), 20, 5));

    public static final RegistryObject<Block> ELDERBERRY_SIGN = BLOCKS.register("elderberry_sign",
            () -> new ModStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN).mapColor(ELDERBERRY_PLANKS.get().defaultMapColor()), ModWoodTypes.ELDERBERRY));
    public static final RegistryObject<Block> ELDERBERRY_WALL_SIGN = BLOCKS.register("elderberry_wall_sign",
            () -> new ModWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN).mapColor(ELDERBERRY_PLANKS.get().defaultMapColor()), ModWoodTypes.ELDERBERRY));

    public static final RegistryObject<Block> ELDERBERRY_HANGING_SIGN = BLOCKS.register("elderberry_hanging_sign",
            () -> new ModHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN).mapColor(ELDERBERRY_PLANKS.get().defaultMapColor()), ModWoodTypes.ELDERBERRY));
    public static final RegistryObject<Block> ELDERBERRY_WALL_HANGING_SIGN = BLOCKS.register("elderberry_wall_hanging_sign",
            () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(ELDERBERRY_PLANKS.get().defaultMapColor()), ModWoodTypes.ELDERBERRY));

    //region Duskwood Non-Full Blocks
    public static final RegistryObject<Block> ELDERBERRY_STAIRS = registerBlock("elderberry_stairs",
            () -> flammableStairBlock(ELDERBERRY_PLANKS.get(), BlockBehaviour.Properties.copy(ELDERBERRY_PLANKS.get()), 5, 20));
    public static final RegistryObject<Block> ELDERBERRY_SLAB = registerBlock("elderberry_slab",
            () -> flammableSlabBlock(BlockBehaviour.Properties.of().mapColor(ELDERBERRY_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava(), 5, 20));

    public static final RegistryObject<Block> ELDERBERRY_BUTTON = registerBlock("elderberry_button",
            () -> new ButtonBlock(BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY),
                    BlockSetType.OAK, 30, true));
    public static final RegistryObject<Block> ELDERBERRY_PRESSURE_PLATE = registerBlock("elderberry_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
                    BlockBehaviour.Properties.of().mapColor(ELDERBERRY_PLANKS.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission()
                            .strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY) , BlockSetType.OAK));

    public static final RegistryObject<Block> ELDERBERRY_FENCE = registerBlock("elderberry_fence",
            () -> flammableFenceBlock(BlockBehaviour.Properties.of().mapColor(ELDERBERRY_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD), 5, 20));
    public static final RegistryObject<Block> ELDERBERRY_FENCE_GATE = registerBlock("elderberry_fence_gate",
            () -> flammableFenceGateBlock(BlockBehaviour.Properties.of().mapColor(ELDERBERRY_PLANKS.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS)
                    .strength(2.0F, 3.0F).ignitedByLava(), 5, 20));

    public static final RegistryObject<Block> ELDERBERRY_DOOR = registerBlock("elderberry_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(ELDERBERRY_PLANKS.get()).noOcclusion(), BlockSetType.OAK));
    public static final RegistryObject<Block> ELDERBERRY_TRAPDOOR = registerBlock("elderberry_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(ELDERBERRY_PLANKS.get()).noOcclusion(), BlockSetType.OAK));

    public static final RegistryObject<Block> ELDERBERRY_SAPLING = registerBlock("elderberry_sapling",
            () -> new SaplingBlock(new ElderberryTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    //endregion

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    //region HELPER FUNCTIONS
    private static LeavesBlock leaves(SoundType soundType) {
        return new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).strength(0.2F).randomTicks().sound(soundType).noOcclusion().isValidSpawn(ModBlocks::ocelotOrParrot).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never));
    }
    private static LeavesBlock elderberryLeaves(SoundType soundType) {
        return new ElderberryLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).strength(0.2F).randomTicks().sound(soundType).noOcclusion().isValidSpawn(ModBlocks::ocelotOrParrot).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never));
    }

    private static Boolean never(BlockState pState, BlockGetter pLevel, BlockPos pPos, EntityType<?> pEntityType) {
        return (boolean)false;
    }
    private static Boolean never(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return false;
    }

    private static Boolean always(BlockState pState, BlockGetter pLevel, BlockPos pPos, EntityType<?> pEntityType) {
        return (boolean)true;
    }
    private static boolean always(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return true;
    }

    private static Boolean ocelotOrParrot(BlockState pState, BlockGetter pLevel, BlockPos pPos, EntityType<?> pEntityType) {
        return (boolean)(pEntityType == EntityType.OCELOT || pEntityType == EntityType.PARROT);
    }

    private static Block simpleFlammableBlock(BlockBehaviour.Properties properties, int flammability, int firespread){
        return new Block(properties){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    private static SlabBlock flammableSlabBlock(BlockBehaviour.Properties properties, int flammability, int firespread){
        return new SlabBlock(properties){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    private static StairBlock flammableStairBlock(Block base, BlockBehaviour.Properties properties, int flammability, int firespread){
        return new StairBlock(base::defaultBlockState, properties){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    private static FenceBlock flammableFenceBlock(BlockBehaviour.Properties properties, int flammability, int firespread){
        return new FenceBlock(properties){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    private static FenceGateBlock flammableFenceGateBlock(BlockBehaviour.Properties properties, int flammability, int firespread){
        return new FenceGateBlock(properties, SoundEvents.FENCE_GATE_CLOSE, SoundEvents.FENCE_GATE_OPEN){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    private static FenceGateBlock flammableFenceGateBlock(BlockBehaviour.Properties properties, WoodType woodType, int flammability, int firespread){
        return new FenceGateBlock(properties, woodType){
            @Override
            public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return true;
            }

            @Override
            public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return flammability;
            }

            @Override
            public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                return firespread;
            }
        };
    }
    //endregion

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block)
    {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) { BLOCKS.register(eventBus); }
}
