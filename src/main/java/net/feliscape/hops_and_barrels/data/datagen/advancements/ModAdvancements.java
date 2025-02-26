package net.feliscape.hops_and_barrels.data.datagen.advancements;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.data.advancements.ModAdvancement;
import net.feliscape.hops_and_barrels.data.advancements.criterion.LethalBulwarkProtectionTrigger;
import net.feliscape.hops_and_barrels.registry.ModBlocks;
import net.feliscape.hops_and_barrels.registry.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class ModAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {

    public static final List<ModAdvancement> ENTRIES = new ArrayList<>();

    public static final List<ItemLike> ALCOHOL = List.of(
            ModItems.BEER.get(),
            ModItems.RED_WINE.get(),
            ModItems.WHITE_WINE.get(),
            ModItems.STARSHINE.get(),
            ModItems.HARDY_BREW.get(),
            ModItems.BLOODY_MARY.get(),
            ModItems.RUM.get(),
            ModItems.VODKA.get(),
            ModItems.MEAD.get(),
            ModItems.SUNSET_ROSE.get(),
            ModItems.WHISKEY.get(),
            ModItems.OLD_WINE.get()
    );

    public static final ModAdvancement
        ROOT = create("root", b -> b.icon(ModBlocks.KEG.get())
            .title("Brewing")
            .description("The art of creating alcohol")
            .when(ConsumeItemTrigger.TriggerInstance.usedItem())
            .special(ModAdvancement.TaskType.SILENT)),

        JUICER = create("juicer", b -> b.icon(ModBlocks.JUICER.get())
            .title("Squishcraft")
            .description("Craft a Juicer")
            .whenGetIcon()
            .after(ROOT)),

        BEER = create("beer", b -> b.icon(ModItems.BEER.get())
            .title("Cheers!") // Or Hoppy Times Ahead
            .description("Brew a mug of beer")
            .whenGetIcon()
            .after(ROOT)),

        EAT_GRAPES = create("eat_red_grapes", b -> b.icon(ModItems.RED_GRAPES.get())
            .title("Like a Greek God")
            .description("Eat some grapes")
            .whenConsume(ModItems.RED_GRAPES.get(), ModItems.WHITE_GRAPES.get())
            .after(JUICER)),
        RED_WINE = create("red_wine", b -> b.icon(ModItems.RED_WINE.get())
            .title("Cult of Dionysus")
            .description("Brew a bottle of Red Wine")
            .whenGetIcon()
            .after(EAT_GRAPES)),
        WHITE_WINE = create("white_wine", b -> b.icon(ModItems.WHITE_WINE.get())
            .title("Gift of Bacchus")
            .description("Brew a bottle of White Wine")
            .whenGetIcon()
            .after(EAT_GRAPES)),

        ELDERBERRIES = create("elderberries", b -> b.icon(ModItems.ELDERBERRIES.get())
            .title("Smells Like Your Father")
            .description("Collect some Elderberries")
            .whenGetIcon()
            .after(JUICER)),
        OLD_WINE = create("old_wine", b -> b.icon(ModItems.OLD_WINE.get())
            .title("Like Fine Wine")
            .description("Brew some wine using a VERY old recipe")
            .whenGetIcon()
            .after(ELDERBERRIES)),


        HOODED_MUSHROOM_MUCUS = create("hooded_mushroom_mucus", b -> b.icon(ModItems.HOODED_MUSHROOM_MUCUS.get())
            .title("Might Give You Superpowers")
            .description("Harvest the mucus off of Giant Hooded Mushrooms")
            .whenGetIcon()
            .after(JUICER)),
        STARSHINE = create("starshine", b -> b.icon(ModItems.STARSHINE.get())
            .title("Celestial Bootlegger")
            .description("Brew some Starshine")
            .whenGetIcon()
            .after(HOODED_MUSHROOM_MUCUS)),

        RUM = create("rum", b -> b.icon(ModItems.RUM.get())
            .title("I Smell PENNIES!")
            .description("Brew a bottle of Rum")
            .whenGetIcon()
            .after(ROOT)),

        PIRATE_RUM = create("pirate_rum", b -> b.icon(ModItems.PIRATE_RUM.get())
            .title("Up She Rises")
            .description("Obtain a bottle of Pirate Rum")
            .whenGetIcon()
            .special(ModAdvancement.TaskType.SECRET)
            .after(ROOT)),


        HARDY_BREW = create("hardy_brew", b -> b.icon(ModItems.HARDY_BREW.get())
            .title("Oktoberfest")
            .description("Brew a Hardy Brew")
            .whenGetIcon()
            .after(ROOT)),
        LETHAL_BULWARK_PROTECTION = create("lethal_bulwark_protection", b -> b.icon(ModItems.HARDY_BREW.get())
            .title("'Tis But A Scratch!")
            .description("Get protected from a lethal attack by the Bulwark effect")
            .when(LethalBulwarkProtectionTrigger.TriggerInstance.any())
            .after(HARDY_BREW)),

        MEAD = create("mead", b -> b.icon(ModItems.MEAD.get())
            .title("Bees Love Him!")
            .description("Brew some Mead from Honey")
            .whenGetIcon()
            .after(ROOT)),

        ALCOHOL_ANTIDOTE = create("alcohol_antidote", b -> b.icon(ModItems.HANGOVER_CURE.get())
            .title("Sobering") // TODO: Change Name
            .description("Drink a Hangover Cure to reduce your alcohol poisoning")
            .whenConsume(ModItems.HANGOVER_CURE.get())
            .after(ROOT)),

        MIXOLOGY = create("mixology", b -> b.icon(ModItems.BLOODY_MARY.get())
            .title("Amateur Mixologist")
            .description("Create your first cocktail")
            .whenGet(ModItems.SUNSET_ROSE.get(), ModItems.BLOODY_MARY.get())
            .after(ROOT)),

        ALL_ALCOHOL = create("all_alcohol", b -> b.icon(ModItems.BEER.get())
            .title("Master Brewer")
            .description("Make and drink every type of alcohol there is")
            .whenConsumedAll(ALCOHOL)
            .special(ModAdvancement.TaskType.CHALLENGE)
            .rewards(AdvancementRewards.Builder.experience(100))
            .after(ROOT)),

        END = null;


    private static ModAdvancement create(String id, UnaryOperator<ModAdvancement.Builder> b) {
        return new ModAdvancement(id, b);
    }

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> pWriter, ExistingFileHelper existingFileHelper) {
        /*Advancement breweryRoot = Advancement.Builder.advancement()
                .display(ModBlocks.KEG.get(),
                        title("root"), description("root"), new ResourceLocation(Brewery.MOD_ID, "textures/gui/advancements/backgrounds/brewery.png"),
                        FrameType.TASK, false, false, false)
                .addCriterion("brewed_alcohol", BrewingTrigger.TriggerInstance.anyItem())
                .save(pWriter, advLocation("root"), existingFileHelper);

        getItemAdvancement(ModItems.BEER.get(), breweryRoot, FrameType.TASK, pWriter, existingFileHelper);*/

        for (ModAdvancement advancement : ENTRIES)
            advancement.save(pWriter);
    }

    /*protected Advancement getItemAdvancement(ItemLike pItem, Advancement parent, FrameType frameType, Consumer<Advancement> pWriter, ExistingFileHelper existingFileHelper){
        String name = "get_" + ForgeRegistries.ITEMS.getKey(pItem.asItem()).getPath();
        return Advancement.Builder.advancement().parent(parent).display(pItem, title(name), description(name),
                        (ResourceLocation)null, frameType, true, false, false)
                .addCriterion(name, InventoryChangeTrigger.TriggerInstance.hasItems(pItem))
                .save(pWriter, advLocation(name), existingFileHelper);
    }*/

    protected Component title(String advancementName){
        return Component.translatable("advancements.tenebria." + advancementName + ".title");
    }
    protected Component description(String advancementName){
        return Component.translatable("advancements.tenebria." + advancementName + ".description");
    }
    protected ResourceLocation advLocation(String advancementName){
        return new ResourceLocation(Brewery.MOD_ID, "tenebria/" + advancementName);
    }

    protected String nameOf(ItemLike itemLike){
        return ForgeRegistries.ITEMS.getKey(itemLike.asItem()).getPath();
    }
}
