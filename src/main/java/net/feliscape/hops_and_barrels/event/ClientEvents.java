package net.feliscape.hops_and_barrels.event;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.block.entity.ModBlockEntities;
import net.feliscape.hops_and_barrels.client.SiphonHudOverlay;
import net.feliscape.hops_and_barrels.entity.client.ModModelLayers;
import net.feliscape.hops_and_barrels.particle.ModParticles;
import net.feliscape.hops_and_barrels.particle.custom.TreasureParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Brewery.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents{

    }

    @Mod.EventBusSubscriber(modid = Brewery.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents{

        @SubscribeEvent
        public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event){
            event.registerLayerDefinition(ModModelLayers.ELDERBERRY_BOAT_LAYER, BoatModel::createBodyModel);
            event.registerLayerDefinition(ModModelLayers.ELDERBERRY_CHEST_BOAT_LAYER, ChestBoatModel::createBodyModel);
        }

        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event){
            event.registerSpriteSet(ModParticles.TREASURE.get(), TreasureParticle.Provider::new);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event){
            event.registerAboveAll("siphon", SiphonHudOverlay.HUD_SIPHON);
        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
            event.registerBlockEntityRenderer(ModBlockEntities.MOD_SIGN.get(), SignRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.MOD_HANGING_SIGN.get(), HangingSignRenderer::new);
        }
    }
}
