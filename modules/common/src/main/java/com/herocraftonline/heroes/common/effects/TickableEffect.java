package com.herocraftonline.heroes.common.effects;

import com.herocraftonline.heroes.characters.CharacterBase;
import com.herocraftonline.heroes.effects.EffectTickable;
import com.herocraftonline.heroes.plugin.HeroesPlugin;

public abstract class TickableEffect extends BasicEffect implements EffectTickable {
    protected long nextTick;
    protected boolean removed;

    public TickableEffect(HeroesPlugin plugin, String name) {
        super(plugin, name);
        removed = false;
    }

    @Override
    public void apply(CharacterBase character) {
        super.apply(character);
        nextTick = System.currentTimeMillis();
    }

    @Override
    public void remove(CharacterBase character) {
        super.remove(character);
        removed = true;
    }

    @Override
    public boolean canTick(long currTime) {
        return currTime >= nextTick;
    }

    @Override
    public boolean shouldStopTicking(long currTime) {
        return removed;
    }

    @Override
    public void tick(CharacterBase character) {
        nextTick += timeTillNextTick();
    }
}
