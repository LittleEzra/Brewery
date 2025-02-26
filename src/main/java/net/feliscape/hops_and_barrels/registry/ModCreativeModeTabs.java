package net.feliscape.hops_and_barrels.registry;

import net.feliscape.hops_and_barrels.Brewery;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Brewery.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BREWING_TAB = CREATIVE_MODE_TABS.register("brewing_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.KEG.get()))
                    .title(Component.translatable("creativetab.hops_and_barrels.brewing_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModBlocks.KEG.get());
                        pOutput.accept(ModBlocks.JUICER.get());
                        pOutput.accept(ModBlocks.TRELLIS.get());

                        pOutput.accept(ModItems.BARLEY_SEEDS.get());
                        pOutput.accept(ModItems.BARLEY.get());
                        pOutput.accept(ModItems.MALTED_BARLEY.get());
                        pOutput.accept(ModItems.WHISKEY.get());

                        pOutput.accept(ModItems.GLOW_BERRY_JUICE.get());
                        pOutput.accept(ModItems.APPLE_JUICE.get());
                        pOutput.accept(ModItems.SUNSET_ROSE.get());

                        pOutput.accept(ModItems.WINE_BOTTLE.get());

                        pOutput.accept(ModBlocks.HOODED_MUSHROOM.get());
                        pOutput.accept(ModBlocks.HOODED_MUSHROOM_BLOCK.get());
                        pOutput.accept(ModBlocks.HOODED_MUSHROOM_MUCUS_BLOCK.get());
                        pOutput.accept(ModItems.HOODED_MUSHROOM_MUCUS.get());
                        pOutput.accept(ModItems.HOODED_MUSHROOM_JUICE.get());

                        pOutput.accept(ModItems.RED_GRAPE_SEEDS.get());
                        pOutput.accept(ModItems.RED_GRAPES.get());
                        pOutput.accept(ModItems.RED_GRAPE_JUICE.get());
                        pOutput.accept(ModItems.RED_WINE.get());

                        pOutput.accept(ModItems.WHITE_GRAPE_SEEDS.get());
                        pOutput.accept(ModItems.WHITE_GRAPES.get());
                        pOutput.accept(ModItems.WHITE_GRAPE_JUICE.get());
                        pOutput.accept(ModItems.WHITE_WINE.get());

                        pOutput.accept(ModBlocks.ELDERBERRY.get());
                        pOutput.accept(ModItems.ELDERBERRIES.get());
                        pOutput.accept(ModItems.ELDERBERRY_JUICE.get());
                        pOutput.accept(ModItems.OLD_WINE.get());

                        pOutput.accept(ModItems.BEER.get());

                        pOutput.accept(ModItems.HOPS_SEEDS.get());
                        pOutput.accept(ModItems.HOPS.get());
                        pOutput.accept(ModItems.HARDY_BREW.get());
                        pOutput.accept(ModItems.STARSHINE.get());
                        pOutput.accept(ModItems.BLOODY_MARY.get());
                        pOutput.accept(ModItems.VODKA.get());
                        pOutput.accept(ModItems.RUM.get());
                        pOutput.accept(ModItems.MEAD.get());
                        pOutput.accept(ModItems.PIRATE_RUM.get());

                        pOutput.accept(ModItems.HANGOVER_CURE.get());

                        pOutput.accept(ModBlocks.ELDERBERRY_LEAVES.get());
                        pOutput.accept(ModBlocks.ELDERBERRY_LOG.get());
                        pOutput.accept(ModBlocks.ELDERBERRY_WOOD.get());
                        pOutput.accept(ModBlocks.STRIPPED_ELDERBERRY_LOG.get());
                        pOutput.accept(ModBlocks.STRIPPED_ELDERBERRY_WOOD.get());
                        pOutput.accept(ModBlocks.ELDERBERRY_PLANKS.get());

                        pOutput.accept(ModItems.ELDERBERRY_SIGN.get());
                        pOutput.accept(ModItems.ELDERBERRY_HANGING_SIGN.get());
                        pOutput.accept(ModItems.ELDERBERRY_BOAT.get());
                        pOutput.accept(ModItems.ELDERBERRY_CHEST_BOAT.get());

                        pOutput.accept(ModBlocks.ELDERBERRY_STAIRS.get());
                        pOutput.accept(ModBlocks.ELDERBERRY_SLAB.get());
                        pOutput.accept(ModBlocks.ELDERBERRY_FENCE.get());
                        pOutput.accept(ModBlocks.ELDERBERRY_FENCE_GATE.get());
                        pOutput.accept(ModBlocks.ELDERBERRY_PRESSURE_PLATE.get());
                        pOutput.accept(ModBlocks.ELDERBERRY_BUTTON.get());
                        pOutput.accept(ModBlocks.ELDERBERRY_DOOR.get());
                        pOutput.accept(ModBlocks.ELDERBERRY_TRAPDOOR.get());
                        pOutput.accept(ModBlocks.ELDERBERRY_SAPLING.get());
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
