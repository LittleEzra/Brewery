package net.feliscape.hops_and_barrels.content.capability;

import net.feliscape.hops_and_barrels.registry.ModDamageSources;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class AlcoholHandler {

    public static final int RECOVERY_PERIOD = 900;
    public static final int LETHAL_POISONING = 6;
    public static final int MAX_POISONING = 10;

    private int alcoholPoisoning;
    private int recovery;
    private boolean displayedWarning;

    public void tick(LivingEntity entity){
        if (this.alcoholPoisoning > 0){
            this.recovery++;
        } else{
            this.recovery = 0;
        }
        if (this.recovery >= RECOVERY_PERIOD){
            reduceAlcoholPoisoning(1);
            this.recovery = 0;
        }

        if (this.alcoholPoisoning >= LETHAL_POISONING && this.recovery % ((MAX_POISONING + 1 - this.alcoholPoisoning) * 4) == 0){
            entity.hurt(ModDamageSources.alcoholPoisoning(entity.level().registryAccess()), 1f);
        }
    }

    public void setRecovery(int recovery){
        this.recovery = recovery;
    }
    public int getRecovery(){
        return recovery;
    }

    public int getAlcoholPoisoning() {
        return alcoholPoisoning;
    }

    public void setAlcoholPoisoning(int alcoholPoisoning) {
        this.alcoholPoisoning = alcoholPoisoning;
    }

    public void drinkAlcohol(int severity){
        this.alcoholPoisoning = Mth.clamp(this.alcoholPoisoning + severity, 0, MAX_POISONING);
        this.recovery = 0;
    }
    public void reduceAlcoholPoisoning(int amount){
        this.alcoholPoisoning = Mth.clamp(this.alcoholPoisoning - amount, 0, MAX_POISONING);
        this.setDisplayedWarning(false);
    }

    public void copyFrom(AlcoholHandler source){
        setAlcoholPoisoning(source.getAlcoholPoisoning());
        setRecovery(source.getRecovery());
        setDisplayedWarning(source.hasDisplayedWarning());
    }

    public void setDisplayedWarning(boolean displayedWarning){
        this.displayedWarning = displayedWarning;
    }

    public boolean hasDisplayedWarning() {
        return this.displayedWarning;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("alcoholPoisoning", this.getAlcoholPoisoning());
        nbt.putInt("recovery", this.getRecovery());
    }

    public void loadNBTData(CompoundTag nbt){
        setAlcoholPoisoning(nbt.getInt("alcoholPoisoning"));
        setRecovery(nbt.getInt("recovery"));
    }
}
