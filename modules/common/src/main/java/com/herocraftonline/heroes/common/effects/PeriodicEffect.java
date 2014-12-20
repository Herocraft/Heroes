package com.herocraftonline.heroes.common.effects;

import com.herocraftonline.heroes.characters.CharacterBase;
import com.herocraftonline.heroes.effects.EffectBase;
import com.herocraftonline.heroes.effects.EffectPeriodic;
import com.herocraftonline.heroes.plugin.HeroesPlugin;

public abstract class PeriodicEffect implements EffectPeriodic, EffectBase {

    public PeriodicEffect(HeroesPlugin plugin, long period) {

    }


    @Override
    public String getName() {
        return null;
    }

    @Override
    public long getPeriod() {
        return 0;
    }

    @Override
    public long timeTillNextTick() {
        return 0;
    }

    @Override
    public void tick(CharacterBase character) {

    }
}
