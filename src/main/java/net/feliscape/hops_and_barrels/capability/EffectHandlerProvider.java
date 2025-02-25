package net.feliscape.hops_and_barrels.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EffectHandlerProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<EffectHandler> EFFECT_HANDLER =
            CapabilityManager.get(new CapabilityToken<EffectHandler>() {});

    private EffectHandler effectHandler = null;
    private final LazyOptional<EffectHandler> optional = LazyOptional.of(this::createEffectHandler);

    private EffectHandler createEffectHandler() {
        if (this.effectHandler == null){
            this.effectHandler = new EffectHandler();
        }

        return this.effectHandler;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == EFFECT_HANDLER){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createEffectHandler().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createEffectHandler().loadNBTData(nbt);
    }
}
