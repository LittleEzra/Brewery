package net.feliscape.hops_and_barrels.block.entity;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Brewery.MOD_ID);

    public static final RegistryObject<BlockEntityType<KegBlockEntity>> KEG_BE =
            BLOCK_ENTITIES.register("keg_be", () -> BlockEntityType.Builder.of(KegBlockEntity::new,
                    ModBlocks.KEG.get()).build(null));

    public static final RegistryObject<BlockEntityType<JuicerBlockEntity>> JUICER_BE =
            BLOCK_ENTITIES.register("juicer_be", () -> BlockEntityType.Builder.of(JuicerBlockEntity::new,
                    ModBlocks.JUICER.get()).build(null));

    public static final RegistryObject<BlockEntityType<ModSignBlockEntity>> MOD_SIGN =
            BLOCK_ENTITIES.register("mod_sign", () -> BlockEntityType.Builder.of(ModSignBlockEntity::new,
                    ModBlocks.ELDERBERRY_SIGN.get(), ModBlocks.ELDERBERRY_WALL_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModHangingSignBlockEntity>> MOD_HANGING_SIGN =
            BLOCK_ENTITIES.register("mod_hanging_sign", () -> BlockEntityType.Builder.of(ModHangingSignBlockEntity::new,
                    ModBlocks.ELDERBERRY_HANGING_SIGN.get(), ModBlocks.ELDERBERRY_WALL_HANGING_SIGN.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
