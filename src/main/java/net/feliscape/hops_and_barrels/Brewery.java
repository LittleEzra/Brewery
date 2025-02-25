package net.feliscape.hops_and_barrels;

import com.mojang.logging.LogUtils;
import net.feliscape.hops_and_barrels.advancements.criterion.ModCriteriaTriggers;
import net.feliscape.hops_and_barrels.block.ModBlocks;
import net.feliscape.hops_and_barrels.block.entity.ModBlockEntities;
import net.feliscape.hops_and_barrels.effect.ModMobEffects;
import net.feliscape.hops_and_barrels.entity.ModEntityTypes;
import net.feliscape.hops_and_barrels.entity.client.ModBoatRenderer;
import net.feliscape.hops_and_barrels.item.ModCreativeModeTabs;
import net.feliscape.hops_and_barrels.item.ModItems;
import net.feliscape.hops_and_barrels.loot.ModLootModifiers;
import net.feliscape.hops_and_barrels.loot.conditions.LootCropBlockCondition;
import net.feliscape.hops_and_barrels.loot.conditions.LootMobEffectCondition;
import net.feliscape.hops_and_barrels.networking.ModMessages;
import net.feliscape.hops_and_barrels.particle.ModParticles;
import net.feliscape.hops_and_barrels.recipe.ModRecipes;
import net.feliscape.hops_and_barrels.screen.JuicerScreen;
import net.feliscape.hops_and_barrels.screen.KegScreen;
import net.feliscape.hops_and_barrels.screen.ModMenuTypes;
import net.feliscape.hops_and_barrels.sound.ModSounds;
import net.feliscape.hops_and_barrels.util.ColorUtil;
import net.feliscape.hops_and_barrels.worldgen.ModFeatures;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;

import javax.annotation.Nullable;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Brewery.MOD_ID)
public class Brewery
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "hops_and_barrels";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public Brewery()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModMobEffects.register(modEventBus);
        ModSounds.register(modEventBus);
        ModParticles.register(modEventBus);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModLootModifiers.register(modEventBus);

        ModMenuTypes.register(modEventBus);

        ModEntityTypes.register(modEventBus);
        ModRecipes.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        ModFeatures.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::registerItemColors);
        modEventBus.addListener(this::registerBlockColors);
        modEventBus.addListener(this::registerLootData);
        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC); // Add alongside the config class
    }

    public static ResourceLocation asResource(String id) {
        return new ResourceLocation(MOD_ID, id);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            ModMessages.register();

            ModCriteriaTriggers.registerTriggers();
        });
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    public void registerLootData(RegisterEvent event)
    {
        if (!event.getRegistryKey().equals(Registries.LOOT_CONDITION_TYPE))
            return;

        event.register(Registries.LOOT_CONDITION_TYPE, new ResourceLocation(Brewery.MOD_ID, "is_crop_block"), () -> LootCropBlockCondition.LOOT_CONDITION_TYPE);
        event.register(Registries.LOOT_CONDITION_TYPE, new ResourceLocation(Brewery.MOD_ID, "has_mob_effect"), () -> LootMobEffectCondition.LOOT_CONDITION_TYPE);
    }

    public static void printDebug(String line){
        LOGGER.debug(line);
    }
    public static void printDebug(boolean value){
        LOGGER.debug(((Boolean)value).toString());
    }
    public static void printDebug(int value){
        LOGGER.debug(((Integer)value).toString());
    }
    public static void printDebug(float value){
        LOGGER.debug(((Float)value).toString());
    }
    public static void printDebug(double value){
        LOGGER.debug(((Double)value).toString());
    }

    public static void printServer(String line, @Nullable MinecraftServer server){
        if (server != null) {
            server.sendSystemMessage(Component.literal("[" + MOD_ID + "] " + line));
        }
    }
    public void registerItemColors(RegisterColorHandlersEvent.Item event){
        event.register((itemStack, i) -> {
            return ColorUtil.getIntColor("#75a98d");
        }, ModBlocks.ELDERBERRY_LEAVES.get());
    }
    public void registerBlockColors(RegisterColorHandlersEvent.Block event){
        event.register(((pState, pLevel, pPos, pTintIndex) -> {
            return ColorUtil.getIntColor("#75a98d");
        }), ModBlocks.ELDERBERRY_LEAVES.get());
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

            EntityRenderers.register(ModEntityTypes.MOD_BOAT.get(), pContext -> new ModBoatRenderer(pContext, false));
            EntityRenderers.register(ModEntityTypes.MOD_CHEST_BOAT.get(), pContext -> new ModBoatRenderer(pContext, true));

            MenuScreens.register(ModMenuTypes.KEG_MENU.get(), KegScreen::new);
            MenuScreens.register(ModMenuTypes.JUICER_MENU.get(), JuicerScreen::new);
        }
    }
}

// The example config class commented out. Maybe add this later?
/*package com.example.examplemod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = Darkwastes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
            .comment("Whether to log the dirt block on common setup")
            .define("logDirtBlock", true);

    private static final ForgeConfigSpec.IntValue MAGIC_NUMBER = BUILDER
            .comment("A magic number")
            .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

    public static final ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
            .comment("What you want the introduction message to be for the magic number")
            .define("magicNumberIntroduction", "The magic number is... ");

    // a list of strings that are treated as resource locations for items
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), Config::validateItemName);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean logDirtBlock;
    public static int magicNumber;
    public static String magicNumberIntroduction;
    public static Set<Item> items;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        logDirtBlock = LOG_DIRT_BLOCK.get();
        magicNumber = MAGIC_NUMBER.get();
        magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();

        // convert the list of strings into a set of items
        items = ITEM_STRINGS.get().stream()
                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
                .collect(Collectors.toSet());
    }
}
*/
