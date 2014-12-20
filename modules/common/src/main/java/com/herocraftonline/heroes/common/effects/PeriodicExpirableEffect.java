package com.herocraftonline.heroes.common.effects;

import com.herocraftonline.heroes.characters.CharacterBase;
import com.herocraftonline.heroes.effects.EffectPeriodic;
import com.herocraftonline.heroes.plugin.HeroesPlugin;

public abstract class PeriodicExpirableEffect extends ExpirableEffect implements EffectPeriodic {


    private long period;
    private long nextTick;

    public PeriodicExpirableEffect(HeroesPlugin plugin, String name, long duration, long period) {
        super(plugin, name, duration);
        this.period = period;
    }

    @Override
    public void apply(CharacterBase character) {
        super.apply(character);
        nextTick = System.currentTimeMillis();
    }

    @Override
    public long getPeriod() {
        return period;
    }

    @Override
    public long timeTillNextTick() {
        return period;
    }

    @Override
    public boolean canTick(long currTime) {
        return currTime >= nextTick;
    }

    @Override
    public void tick(CharacterBase character) {
        if (System.currentTimeMillis() >= expireTime) {
            super.tick(character);
        } else {
            nextTick += timeTillNextTick();
        }
    }
}
