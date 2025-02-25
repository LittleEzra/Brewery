package net.feliscape.hops_and_barrels.item;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.block.ModBlocks;
import net.feliscape.hops_and_barrels.block.custom.TrellisCropBlock;
import net.feliscape.hops_and_barrels.effect.ModMobEffects;
import net.feliscape.hops_and_barrels.entity.custom.ModBoat;
import net.feliscape.hops_and_barrels.item.custom.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Brewery.MOD_ID);

    public static final RegistryObject<Item> HANGOVER_CURE = ITEMS.register("hangover_cure",
            () -> new DrinkableCureItem(new Item.Properties().stacksTo(16), 2, true));

    public static final RegistryObject<Item> WINE_BOTTLE = ITEMS.register("wine_bottle",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BEER = ITEMS.register("beer",
            () -> new AlcoholItem(new Item.Properties().stacksTo(16), () -> Items.GLASS_BOTTLE));
    public static final RegistryObject<Item> RED_WINE = ITEMS.register("red_wine",
            () -> new AlcoholBuffItem(new Item.Properties().stacksTo(16), ModItems.WINE_BOTTLE,
                    () -> new MobEffectInstance(MobEffects.REGENERATION, 1800, 2)));
    public static final RegistryObject<Item> WHITE_WINE = ITEMS.register("white_wine",
            () -> new AlcoholBuffItem(new Item.Properties().stacksTo(16), ModItems.WINE_BOTTLE,
                    () -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 4800, 2)));
    public static final RegistryObject<Item> OLD_WINE = ITEMS.register("old_wine",
            () -> new AlcoholBuffItem(new Item.Properties().stacksTo(16), ModItems.WINE_BOTTLE,
                    () -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 4800, 2)));
    public static final RegistryObject<Item> STARSHINE = ITEMS.register("starshine",
            () -> new AlcoholBuffItem(new Item.Properties().stacksTo(16), () -> Items.GLASS_BOTTLE,
                    () -> new MobEffectInstance(ModMobEffects.PILFERING.get(), 4800, 0)));
    public static final RegistryObject<Item> HARDY_BREW = ITEMS.register("hardy_brew",
            () -> new AlcoholBuffItem(new Item.Properties().stacksTo(16), () -> Items.GLASS_BOTTLE,
                    () -> new MobEffectInstance(ModMobEffects.BULWARK.get(), 4800, 0)));
    public static final RegistryObject<Item> BLOODY_MARY = ITEMS.register("bloody_mary",
            () -> new BloodyMaryItem(new Item.Properties().stacksTo(16), () -> Items.GLASS_BOTTLE));
    public static final RegistryObject<Item> RUM = ITEMS.register("rum",
            () -> new AlcoholBuffItem(new Item.Properties().stacksTo(16), () -> Items.GLASS_BOTTLE,
                    () -> new MobEffectInstance(ModMobEffects.TREASURE_HUNTER.get(), 7200, 0)));
    public static final RegistryObject<Item> VODKA = ITEMS.register("vodka",
            () -> new AlcoholItem(new Item.Properties().stacksTo(16), () -> Items.GLASS_BOTTLE, 2));
    public static final RegistryObject<Item> MEAD = ITEMS.register("mead",
            () -> new AlcoholBuffItem(new Item.Properties().stacksTo(16), ModItems.WINE_BOTTLE,
                    () -> new MobEffectInstance(ModMobEffects.HUSBANDRY.get(), 9600, 0)));
    public static final RegistryObject<Item> SUNSET_ROSE = ITEMS.register("sunset_rose",
            () -> new AlcoholBuffItem(new Item.Properties().stacksTo(16), () -> Items.GLASS_BOTTLE,
                    () -> new MobEffectInstance(ModMobEffects.VITALITY.get(), 7200, 0)));
    public static final RegistryObject<Item> WHISKEY = ITEMS.register("whiskey",
            () -> new AlcoholItem(new Item.Properties().stacksTo(16), () -> Items.GLASS_BOTTLE));
    public static final RegistryObject<Item> PIRATE_RUM = ITEMS.register("pirate_rum",
            () -> new AlcoholBuffItem(new Item.Properties().stacksTo(16).rarity(Rarity.RARE), () -> Items.GLASS_BOTTLE,
                    () -> new MobEffectInstance(ModMobEffects.PIRACY.get(), 7200, 0)));

    public static final RegistryObject<Item> RED_GRAPE_SEEDS = ITEMS.register("red_grape_seeds",
            () -> new TrellisSeedItem(((TrellisCropBlock) ModBlocks.RED_GRAPES.get()), new Item.Properties()));
    public static final RegistryObject<Item> RED_GRAPES = ITEMS.register("red_grapes",
            () -> new Item(new Item.Properties().food(ModFoods.RED_GRAPES)));
    public static final RegistryObject<Item> RED_GRAPE_JUICE = ITEMS.register("red_grape_juice",
            () -> new Item(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> WHITE_GRAPE_SEEDS = ITEMS.register("white_grape_seeds",
            () -> new TrellisSeedItem(((TrellisCropBlock) ModBlocks.WHITE_GRAPES.get()), new Item.Properties()));
    public static final RegistryObject<Item> WHITE_GRAPES = ITEMS.register("white_grapes",
            () -> new Item(new Item.Properties().food(ModFoods.WHITE_GRAPES)));
    public static final RegistryObject<Item> WHITE_GRAPE_JUICE = ITEMS.register("white_grape_juice",
            () -> new Item(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> HOPS_SEEDS = ITEMS.register("hops_seeds",
            () -> new TrellisSeedItem(((TrellisCropBlock) ModBlocks.HOPS.get()), new Item.Properties()));
    public static final RegistryObject<Item> HOPS = ITEMS.register("hops",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HOODED_MUSHROOM_MUCUS = ITEMS.register("hooded_mushroom_mucus",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HOODED_MUSHROOM_JUICE = ITEMS.register("hooded_mushroom_juice",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> APPLE_JUICE = ITEMS.register("apple_juice",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GLOW_BERRY_JUICE = ITEMS.register("glow_berry_juice",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ELDERBERRIES = ITEMS.register("elderberries",
            () -> new Item(new Item.Properties().food(ModFoods.ELDERBERRIES)));
    public static final RegistryObject<Item> ELDERBERRY_JUICE = ITEMS.register("elderberry_juice",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BARLEY_SEEDS = ITEMS.register("barley_seeds",
            () -> new ItemNameBlockItem(ModBlocks.BARLEY.get(), new Item.Properties()));
    public static final RegistryObject<Item> BARLEY = ITEMS.register("barley",
            () -> new BarleyItem(new Item.Properties()));
    public static final RegistryObject<Item> MALTED_BARLEY = ITEMS.register("malted_barley",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ELDERBERRY_SIGN = ITEMS.register("elderberry_sign",
            () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.ELDERBERRY_SIGN.get(), ModBlocks.ELDERBERRY_WALL_SIGN.get()));
    public static final RegistryObject<Item> ELDERBERRY_HANGING_SIGN = ITEMS.register("elderberry_hanging_sign",
            () -> new HangingSignItem(ModBlocks.ELDERBERRY_HANGING_SIGN.get(), ModBlocks.ELDERBERRY_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> ELDERBERRY_BOAT = ITEMS.register("elderberry_boat",
            () -> new ModBoatItem(false, ModBoat.Type.ELDERBERRY, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ELDERBERRY_CHEST_BOAT = ITEMS.register("elderberry_chest_boat",
            () -> new ModBoatItem(true, ModBoat.Type.ELDERBERRY, new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
