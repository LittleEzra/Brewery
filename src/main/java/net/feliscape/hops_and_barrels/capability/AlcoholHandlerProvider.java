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

public class AlcoholHandlerProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<AlcoholHandler> ALCOHOL_HANDLER =
            CapabilityManager.get(new CapabilityToken<AlcoholHandler>() {});

    private AlcoholHandler effectHandler = null;
    private final LazyOptional<AlcoholHandler> optional = LazyOptional.of(this::createAlcoholHandler);

    private AlcoholHandler createAlcoholHandler() {
        if (this.effectHandler == null){
            this.effectHandler = new AlcoholHandler();
        }

        return this.effectHandler;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ALCOHOL_HANDLER){
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createAlcoholHandler().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createAlcoholHandler().loadNBTData(nbt);
    }
}
