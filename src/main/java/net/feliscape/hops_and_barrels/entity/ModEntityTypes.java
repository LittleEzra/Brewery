package net.feliscape.hops_and_barrels.entity;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.entity.custom.ModBoat;
import net.feliscape.hops_and_barrels.entity.custom.ModChestBoat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Brewery.MOD_ID);

    /* Registering an Entity:
    public static final RegistryObject<EntityType<ENTITY>> ENTITY_NAME = ENTITY_TYPES.register("ENTITY",
            () -> EntityType.Builder.of(ENTITY::new, MobCategory.AMBIENT).sized(1.0f, 1.0f)
                    .build(new ResourceLocation(Template.MOD_ID, "ENTITY").toString()));
                    */

    public static final RegistryObject<EntityType<ModBoat>> MOD_BOAT = ENTITY_TYPES.register("mod_boat",
            () -> EntityType.Builder.<ModBoat>of(ModBoat::new, MobCategory.MISC).sized(1.375f, 0.5625f)
                    .build(new ResourceLocation(Brewery.MOD_ID, "mod_boat").toString()));
    public static final RegistryObject<EntityType<ModChestBoat>> MOD_CHEST_BOAT = ENTITY_TYPES.register("mod_chest_boat",
            () -> EntityType.Builder.<ModChestBoat>of(ModChestBoat::new, MobCategory.MISC).sized(1.375f, 0.5625f)
                    .build(new ResourceLocation(Brewery.MOD_ID, "mod_chest_boat").toString()));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
