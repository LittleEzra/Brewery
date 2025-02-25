package net.feliscape.hops_and_barrels.capability;

import net.feliscape.hops_and_barrels.effect.ModMobEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class EffectHandler {

    public class Bulwark{
        public static final int BULWARK_MIN_DELAY = 60;
        public static final int BULWARK_MAX_DELAY = 240;
        public static final float BULWARK_MAX_ABSORPTION = 15f;
        public static final float BULWARK_MIN_ABSORPTION = 2f;

        private int bulwarkCooldown;

        public static int getDelay(int amplifier){
            return  Math.max((BULWARK_MAX_DELAY - (10 * amplifier)), BULWARK_MIN_DELAY);
        }

        public void tick(LivingEntity entity){
            if (!entity.hasEffect(ModMobEffects.BULWARK.get())) {
                bulwarkCooldown = -1;
                return;
            }
            if (bulwarkCooldown > 0) bulwarkCooldown--;
            if (bulwarkCooldown == 0){
                bulwarkCooldown = -1;
                playBurst(entity);
            }

            if (bulwarkCooldown <= 0){
                playParticles(entity);
            }
        }

        public int getBulwarkCooldown() {
            return bulwarkCooldown;
        }

        public void setBulwarkCooldown(int bulwarkCooldown) {
            this.bulwarkCooldown = bulwarkCooldown;
        }
    }
    public class Siphon{
        public static final int SIPHON_MAX_HEARTS = 40;

        private int siphonedHearts;

        public void addSiphonedHearts(int amount){
            siphonedHearts = Math.min(SIPHON_MAX_HEARTS, siphonedHearts + amount);
        }

        public int getSiphonedHearts() {
            return siphonedHearts;
        }

        public void setSiphonedHearts(int siphonedHearts) {
            this.siphonedHearts = siphonedHearts;
        }

        public void applySiphonedHearts(LivingEntity pLivingEntity) {
            pLivingEntity.setAbsorptionAmount(pLivingEntity.getAbsorptionAmount() + siphonedHearts);
            setSiphonedHearts(0);
        }
        public void removeSiphonedHearts() {
            setSiphonedHearts(0);
        }
    }

    public Bulwark bulwark = new Bulwark();
    public Siphon siphon = new Siphon();

    public void tick(LivingEntity entity){
        bulwark.tick(entity);
    }

    public static void playBurst(LivingEntity entity){
        Level level = entity.level();

        if (level instanceof ServerLevel serverLevel)
        {
            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                    entity.position().x,
                    entity.position().y + entity.getBbHeight() * 0.5D,
                    entity.position().z,
                    30,
                    .2d,
                    .2d,
                    .2d,
                    3d);
        }
    }
    public static void playParticles(LivingEntity entity){
        Level level = entity.level();

        if (level instanceof ServerLevel serverLevel)
        {
            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                    entity.position().x,
                    entity.position().y + entity.getBbHeight() * 0.5D,
                    entity.position().z,
                    1,
                    .2d,
                    .2d,
                    .2d,
                    3d);
        }
    }




    public void copyFrom(EffectHandler source){
        bulwark.setBulwarkCooldown(source.bulwark.getBulwarkCooldown());
        siphon.setSiphonedHearts(source.siphon.getSiphonedHearts());
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("bulwark.cooldown", this.bulwark.getBulwarkCooldown());
        nbt.putInt("siphon.hearts", this.siphon.getSiphonedHearts());
    }

    public void loadNBTData(CompoundTag nbt){
        bulwark.setBulwarkCooldown(nbt.getInt("bulwark.cooldown"));
        siphon.setSiphonedHearts(nbt.getInt("siphoned.hearts"));
    }
}
