package net.feliscape.hops_and_barrels.sound;

import net.feliscape.hops_and_barrels.Brewery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Brewery.MOD_ID);

    public static final RegistryObject<SoundEvent> BULWARK_PROTECT = registerSoundEvent("effect.bulwark.protect");
    public static final RegistryObject<SoundEvent> SIPHON = registerSoundEvent("effect.siphon");
    public static final RegistryObject<SoundEvent> JUICER_CRUSH = registerSoundEvent("block.juicer.crush");

    public static void register(IEventBus eventBus)
    {
        SOUND_EVENTS.register(eventBus);
    }

    private static RegistryObject<SoundEvent> registerSoundEvent(String name)
    {
        return registerSoundEvent(name, 4f);
    }
    private static RegistryObject<SoundEvent> registerSoundEvent(String name, float pRange)
    {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(Brewery.MOD_ID, name), pRange));
    }
}
