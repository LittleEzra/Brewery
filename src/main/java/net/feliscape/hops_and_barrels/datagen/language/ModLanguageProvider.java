package net.feliscape.hops_and_barrels.datagen.language;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.advancements.ModAdvancement;
import net.feliscape.hops_and_barrels.block.ModBlocks;
import net.feliscape.hops_and_barrels.datagen.advancements.ModAdvancements;
import net.feliscape.hops_and_barrels.effect.ModMobEffects;
import net.feliscape.hops_and_barrels.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

/*
  "item.hops_and_barrels.beer": "Beer",
  "item.hops_and_barrels.red_wine": "Red Wine",

  "item.hops_and_barrels.red_grapes": "Red Grapes",

  "block.hops_and_barrels.keg": "Keg",

  "creativetab.hops_and_barrels.brewing_tab": "Brewing",

  "advancements.hops_and_barrels.root.title": "Hops and Barrels",
  "advancements.hops_and_barrels.root.description": "Brew your alcoholic beverage",

  "death.attack.alcohol_poisoning": "%1$s died from alcohol poisoning",
  "death.attack.alcohol_poisoning.player": "%1$s died from alcohol poisoning whilst fighting %2$s"
 */

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, Brewery.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        ModAdvancements.ENTRIES.forEach(this::addAdvancement);

        this.addBlock(ModBlocks.KEG, "Keg");
        this.addBlock(ModBlocks.JUICER, "Juicer");

        this.addBlock(ModBlocks.TRELLIS, "Trellis");
        this.addBlock(ModBlocks.BARLEY, "Barley");
        this.addBlock(ModBlocks.HOPS, "Hops");
        this.addBlock(ModBlocks.RED_GRAPES, "Red Grape Vine");
        this.addBlock(ModBlocks.ELDERBERRY, "Elderberry");

        this.addBlock(ModBlocks.HOODED_MUSHROOM, "Hooded Mushroom");
        this.addBlock(ModBlocks.HOODED_MUSHROOM_BLOCK, "Hooded Mushroom Block");
        this.addBlock(ModBlocks.HOODED_MUSHROOM_MUCUS_BLOCK, "Hooded Mushroom Mucus Block");

        this.addBlock(ModBlocks.ELDERBERRY_LEAVES, "Elderberry Leaves");
        this.addBlock(ModBlocks.ELDERBERRY_LOG, "Elderberry Log");
        this.addBlock(ModBlocks.ELDERBERRY_WOOD, "Elderberry Wood");
        this.addBlock(ModBlocks.STRIPPED_ELDERBERRY_LOG, "Stripped Elderberry Log");
        this.addBlock(ModBlocks.STRIPPED_ELDERBERRY_WOOD, "Stripped Elderberry Wood");
        this.addBlock(ModBlocks.ELDERBERRY_PLANKS, "Elderberry Planks");
        this.addBlock(ModBlocks.ELDERBERRY_STAIRS, "Elderberry Stairs");
        this.addBlock(ModBlocks.ELDERBERRY_SLAB, "Elderberry Slab");
        this.addBlock(ModBlocks.ELDERBERRY_BUTTON, "Elderberry Button");
        this.addBlock(ModBlocks.ELDERBERRY_PRESSURE_PLATE, "Elderberry Pressure Plate");
        this.addBlock(ModBlocks.ELDERBERRY_FENCE, "Elderberry Fence");
        this.addBlock(ModBlocks.ELDERBERRY_FENCE_GATE, "Elderberry Fence Gate");
        this.addBlock(ModBlocks.ELDERBERRY_DOOR, "Elderberry Door");
        this.addBlock(ModBlocks.ELDERBERRY_TRAPDOOR, "Elderberry Trapdoor");

        this.add(ModItems.ELDERBERRY_SIGN.get(), "Elderberry Sign");
        this.add(ModItems.ELDERBERRY_HANGING_SIGN.get(), "Elderberry Hanging Sign");
        this.add(ModItems.ELDERBERRY_BOAT.get(), "Elderberry Boat");
        this.add(ModItems.ELDERBERRY_CHEST_BOAT.get(), "Elderberry Chest Boat");

        this.add(ModBlocks.ELDERBERRY_SAPLING.get(), "Elderberry Sapling");

        this.add(ModItems.WINE_BOTTLE.get(), "Wine Bottle");

        this.add(ModItems.BEER.get(), "Beer");
        this.add(ModItems.RED_WINE.get(), "Red Wine");
        this.add(ModItems.WHITE_WINE.get(), "White Wine");
        this.add(ModItems.STARSHINE.get(), "Starshine");
        this.add(ModItems.HARDY_BREW.get(), "Hardy Brew");
        this.add(ModItems.BLOODY_MARY.get(), "Bloody Mary");
        this.add(ModItems.RUM.get(), "Rum");
        this.add(ModItems.VODKA.get(), "Vodka");
        this.add(ModItems.MEAD.get(), "Mead");
        this.add(ModItems.SUNSET_ROSE.get(), "Sunset Rose");
        this.add(ModItems.WHISKEY.get(), "Whiskey");
        this.add(ModItems.OLD_WINE.get(), "Old Wine");
        this.add(ModItems.PIRATE_RUM.get(), "Pirate Rum");

        this.add(ModItems.RED_GRAPE_SEEDS.get(), "Red Grape Seeds");
        this.add(ModItems.RED_GRAPES.get(), "Red Grapes");
        this.add(ModItems.RED_GRAPE_JUICE.get(), "Red Grape Juice");
        this.add(ModItems.WHITE_GRAPE_SEEDS.get(), "White Grape Seeds");
        this.add(ModItems.WHITE_GRAPES.get(), "White Grapes");
        this.add(ModItems.WHITE_GRAPE_JUICE.get(), "White Grape Juice");
        this.add(ModItems.HOPS_SEEDS.get(), "Hops Seeds");
        this.add(ModItems.HOPS.get(), "Hops");
        this.add(ModItems.HOODED_MUSHROOM_MUCUS.get(), "Hooded Mushroom Mucus");
        this.add(ModItems.HOODED_MUSHROOM_JUICE.get(), "Hooded Mushroom Juice");
        this.add(ModItems.APPLE_JUICE.get(), "Apple Juice");
        this.add(ModItems.GLOW_BERRY_JUICE.get(), "Glow Berry Juice");

        this.add(ModItems.ELDERBERRIES.get(), "Elderberries");
        this.add(ModItems.ELDERBERRY_JUICE.get(), "Elderberry Juice");

        this.add(ModItems.BARLEY_SEEDS.get(), "Barley Seeds");
        this.add(ModItems.BARLEY.get(), "Barley");
        this.add(ModItems.MALTED_BARLEY.get(), "Malted Barley");

        this.add(ModItems.HANGOVER_CURE.get(), "Hangover Cure");

        this.addEffect(ModMobEffects.BULWARK, "Bulwark");
        this.addEffect(ModMobEffects.SIPHON, "Siphon");
        this.addEffect(ModMobEffects.HUSBANDRY, "Husbandry");
        this.addEffect(ModMobEffects.VITALITY, "Vitality");
        this.addEffect(ModMobEffects.PILFERING, "Pilfering");
        this.addEffect(ModMobEffects.TREASURE_HUNTER, "Treasure Hunter");
        this.addEffect(ModMobEffects.PIRACY, "Piracy");

        this.add("item.hops_and_barrels.alcohol_poisoning_warning", "You don't feel so good");

        this.add("container.hops_and_barrels.keg", "Brewing");
        this.add("container.hops_and_barrels.keg.water_tooltip", "Water: %1$s");
        this.add("container.hops_and_barrels.juicer", "Juicing");

        this.add("creativetab.hops_and_barrels.brewing_tab", "Brewing");

        this.add("death.attack.alcohol_poisoning", "%1$s died from alcohol poisoning");
        this.add("death.attack.alcohol_poisoning.player", "%1$s died from alcohol poisoning whilst fighting %2$s");

        this.add("hops_and_barrels.subtitles.effect.siphon", "Entity gets siphoned");
        this.add("hops_and_barrels.subtitles.effect.bulwark.protect", "Bulwark breaks");
        this.add("hops_and_barrels.subtitles.block.juicer.crush", "Juicer juices");
    }

    private void addAdvancement(ModAdvancement modAdvancement) {
        this.add(modAdvancement.titleKey(), modAdvancement.getTitle());
        this.add(modAdvancement.descriptionKey(), modAdvancement.getDescription());
    }


}
