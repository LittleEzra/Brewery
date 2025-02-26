package net.feliscape.hops_and_barrels.content.event;

import net.feliscape.hops_and_barrels.Brewery;
import net.feliscape.hops_and_barrels.data.advancements.criterion.ModCriteriaTriggers;
import net.feliscape.hops_and_barrels.content.capability.AlcoholHandler;
import net.feliscape.hops_and_barrels.content.capability.AlcoholHandlerProvider;
import net.feliscape.hops_and_barrels.content.capability.EffectHandler;
import net.feliscape.hops_and_barrels.content.capability.EffectHandlerProvider;
import net.feliscape.hops_and_barrels.data.datagen.loot.ModBuiltInLootTables;
import net.feliscape.hops_and_barrels.registry.ModMobEffects;
import net.feliscape.hops_and_barrels.networking.ModMessages;
import net.feliscape.hops_and_barrels.networking.packets.SiphonDataSyncS2CPacket;
import net.feliscape.hops_and_barrels.registry.ModSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = Brewery.MOD_ID)
    public static class ForgeEvents{

        @SubscribeEvent
        public static void changeTargetEvent(LivingChangeTargetEvent event){
            LivingEntity entity = event.getEntity();
            LivingEntity target = event.getNewTarget();

            if (entity == null || target == null) return;

            if (entity.getType() == EntityType.BEE && target.hasEffect(ModMobEffects.HUSBANDRY.get())){
                event.setCanceled(true);
            }
        }

        /*
        @SubscribeEvent
        public static void breakBlockEvent(BlockEvent.BreakEvent event){
            Player player = event.getPlayer();
            BlockState blockState = event.getState();
            Block block = blockState.getBlock();
            BlockPos pos = event.getPos();

            if (block instanceof CropBlock cropBlock){
                if (cropBlock.getAge(event.getState()) >= cropBlock.getMaxAge() && player.hasEffect(ModMobEffects.HUSBANDRY.get())){ // Make crops drop twice when the player has Husbandry
                    Block.dropResources(event.getState(), event.getLevel(), pos, null);
                    LevelAccessor level = event.getLevel();
                    if (level instanceof ServerLevel) {
                        Block.getDrops(blockState, (ServerLevel)level, pos, null).forEach((itemStack) -> {
                            if (!itemStack.is(cropBlock.getCloneItemStack(level, pos, blockState).getItem())) // Don't duplicate the seed item (this should be fine)
                                Block.popResource((ServerLevel)level, pos, itemStack);
                        });
                        blockState.spawnAfterBreak((ServerLevel)level, pos, ItemStack.EMPTY, true);
                    }
                }
            }


        }*/

        @SubscribeEvent
        public static void livingHurtEvent(LivingHurtEvent event){

            LivingEntity entity = event.getEntity();
            //DamageSource source = event.getSource();

            //  Bulwark (Should be at the end because losing it unnecessarily is annoying)

            entity.getCapability(EffectHandlerProvider.EFFECT_HANDLER).ifPresent(effectHandler -> {
                if (entity.hasEffect(ModMobEffects.BULWARK.get()) && effectHandler.bulwark.getBulwarkCooldown() <= 0){
                    if (event.getAmount() > EffectHandler.Bulwark.BULWARK_MIN_ABSORPTION || event.getAmount() >= entity.getHealth() + entity.getAbsorptionAmount()){
                        if (entity instanceof ServerPlayer && event.getAmount() >= entity.getHealth() + entity.getAbsorptionAmount()){
                            ModCriteriaTriggers.LETHAL_BULWARK_PROTECTION.trigger(((ServerPlayer) entity));
                        }
                        event.setAmount(Math.max(0, event.getAmount() - EffectHandler.Bulwark.BULWARK_MAX_ABSORPTION));

                        entity.level().playSound(null,
                                entity.position().x,
                                entity.position().y,
                                entity.position().z,
                                ModSounds.BULWARK_PROTECT.get(), SoundSource.MASTER, 1f, 1f);

                        effectHandler.bulwark.setBulwarkCooldown(EffectHandler.Bulwark.getDelay(entity.getEffect(ModMobEffects.BULWARK.get()).getAmplifier()));
                        EffectHandler.playBurst(entity);
                    }
                }
            });
        }

        @SubscribeEvent
        public static void livingAttackEvent(LivingAttackEvent event){
            LivingEntity entity = event.getEntity();
            DamageSource source = event.getSource();
            Entity attacker = source.getDirectEntity();

            if (attacker instanceof LivingEntity livingAttacker){
                // Pilfering
                if (livingAttacker.hasEffect(ModMobEffects.PILFERING.get())){
                    if (entity.getActiveEffects().stream().toList().size() > 0){
                        MobEffectInstance effect = entity.getActiveEffects().stream().toList().get(0);
                        livingAttacker.addEffect(effect);
                        entity.removeEffect(effect.getEffect());
                    }
                }
                // Siphon
                if (livingAttacker.hasEffect(ModMobEffects.SIPHON.get())){
                    livingAttacker.getCapability(EffectHandlerProvider.EFFECT_HANDLER).ifPresent(effectHandler -> {
                        effectHandler.siphon.addSiphonedHearts(Mth.ceil(event.getAmount()));
                        if (livingAttacker instanceof ServerPlayer player){
                            ModMessages.sendToPlayer(new SiphonDataSyncS2CPacket(effectHandler.siphon.getSiphonedHearts()), player);
                        }
                        livingAttacker.level().playSound(null, livingAttacker.position().x, livingAttacker.position().y, livingAttacker.position().z,
                                ModSounds.SIPHON.get(), SoundSource.MASTER, 2f, 1f);
                    });
                }
            }
        }

        @SubscribeEvent
        public static void livingDeathEvent(LivingDeathEvent event){
            Brewery.printDebug("LivingEntity died");

            LivingEntity entity = event.getEntity();
            DamageSource source = event.getSource();
            Entity attacker = source.getEntity();
            if (attacker == null) attacker = event.getSource().getDirectEntity();

            if (entity instanceof Monster && attacker instanceof LivingEntity livingAttacker && livingAttacker.hasEffect(ModMobEffects.PIRACY.get())){
                LootTable loottable = entity.level().getServer().getLootData().getLootTable(ModBuiltInLootTables.PIRACY_LOOT);
                LootParams.Builder lootparams$builder = (new LootParams.Builder((ServerLevel)entity.level()))
                        .withParameter(LootContextParams.THIS_ENTITY, entity)
                        .withParameter(LootContextParams.ORIGIN, entity.position())
                        .withParameter(LootContextParams.DAMAGE_SOURCE, source)
                        .withOptionalParameter(LootContextParams.KILLER_ENTITY, source.getEntity())
                        .withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, source.getDirectEntity());

                LootParams lootparams = lootparams$builder.create(LootContextParamSets.ENTITY);
                loottable.getRandomItems(lootparams, entity.getLootTableSeed(), entity::spawnAtLocation);
            }
        }

        @SubscribeEvent
        public static void livingHealEvent(LivingHealEvent event){
            LivingEntity entity = event.getEntity();

            if (entity.hasEffect(ModMobEffects.VITALITY.get())){
                float amount = (float) Math.ceil(event.getAmount() * 0.5f * (entity.getEffect(ModMobEffects.VITALITY.get()).getAmplifier() + 1));
                event.setAmount(event.getAmount() + amount);
            }
        }

        @SubscribeEvent
        public static void onAttachCapabilitiesLiving(AttachCapabilitiesEvent<Entity> event){
            if (event.getObject() instanceof LivingEntity){
                if (!event.getObject().getCapability(AlcoholHandlerProvider.ALCOHOL_HANDLER).isPresent()){
                    event.addCapability(new ResourceLocation(Brewery.MOD_ID, "alcohol_handler"), new AlcoholHandlerProvider());
                }
                if (!event.getObject().getCapability(EffectHandlerProvider.EFFECT_HANDLER).isPresent()){
                    event.addCapability(new ResourceLocation(Brewery.MOD_ID, "effect_handler"), new EffectHandlerProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event){
            if (event.isWasDeath()){
                event.getOriginal().getCapability(AlcoholHandlerProvider.ALCOHOL_HANDLER).ifPresent(oldStore -> {
                    event.getOriginal().getCapability(AlcoholHandlerProvider.ALCOHOL_HANDLER).ifPresent(newStore ->{
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(EffectHandlerProvider.EFFECT_HANDLER).ifPresent(oldStore -> {
                    event.getOriginal().getCapability(EffectHandlerProvider.EFFECT_HANDLER).ifPresent(newStore ->{
                        newStore.copyFrom(oldStore);
                        newStore.siphon.removeSiphonedHearts();
                    });
                });
            }
        }

        @SubscribeEvent
        public static void onPlayerJoinWorld(EntityJoinLevelEvent event){
            if (!event.getLevel().isClientSide()){
                if (event.getEntity() instanceof ServerPlayer player){
                    player.getCapability(EffectHandlerProvider.EFFECT_HANDLER).ifPresent(effectHandler ->{
                        ModMessages.sendToPlayer(new SiphonDataSyncS2CPacket(effectHandler.siphon.getSiphonedHearts()), player);
                    });
                }
            }
        }

        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event){
            event.register(AlcoholHandler.class);
            event.register(EffectHandler.class);
        }

        @SubscribeEvent
        public static void onLivingTick(LivingEvent.LivingTickEvent event){
            LivingEntity entity = event.getEntity();
            entity.getCapability(AlcoholHandlerProvider.ALCOHOL_HANDLER).ifPresent(alcoholHandler -> {
                alcoholHandler.tick(entity);
            });
            entity.getCapability(EffectHandlerProvider.EFFECT_HANDLER).ifPresent(effectHandler -> {
                effectHandler.tick(entity);
            });
        }
        @SubscribeEvent
        public static void onPlayerSleep(PlayerSleepInBedEvent event){
            Player player = event.getEntity();
            player.getCapability(AlcoholHandlerProvider.ALCOHOL_HANDLER).ifPresent(alcoholHandler -> {
                alcoholHandler.reduceAlcoholPoisoning(3);
            });
        }
    }
    @Mod.EventBusSubscriber(modid = Brewery.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents{

    }
}
