package com.herocraftonline.heroes.common.effects;

import com.herocraftonline.heroes.characters.CharacterBase;
import com.herocraftonline.heroes.effects.EffectPeriodic;
import com.herocraftonline.heroes.plugin.HeroesPlugin;

public abstract class PeriodicEffect extends TickableEffect implements EffectPeriodic {

    protected long period;

    public PeriodicEffect(HeroesPlugin plugin, String name, long period) {
        super(plugin, name);
        this.period = period;
    }

    @Override
    public void apply(final CharacterBase character) { // Use a simple repeating tickTask for performance
        super.apply(character);
    }

    @Override
    public void remove(final CharacterBase character) {
        super.apply(character);
    }

    @Override
    public long getPeriod() {
        return period;
    }

    @Override
    public long timeTillNextTick() {
        return period;
    }

}
