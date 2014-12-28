package com.herocraftonline.heroes.common.effects;

import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.effects.EffectExpirable;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;

public class ExpirableEffect extends BasicEffect implements EffectExpirable {

    protected long applyTime;
    protected long originalExpireTime;
    protected long expireTime;
    private long length;
    private boolean removed;

    public ExpirableEffect(HeroesPlugin plugin, String name, long duration) {
        super(plugin, name);
        length = duration;
        removed = false;
    }

    @Override
    public void apply(final CharacterBase character) {
        super.apply(character);
        applyTime = System.currentTimeMillis();
        expireTime = applyTime + length;
        originalExpireTime = expireTime;
        plugin.getEffectManager().registerEffect(this);
    }

    @Override
    public void remove(final CharacterBase character) {
        super.remove(character);
        removed = true;
    }

    @Override
    public long getDuration() {
        return System.currentTimeMillis() - applyTime + getRemainingDuration();
    }

    @Override
    public long getExpireTime() {
        return expireTime;
    }

    @Override
    public long getOriginalDuration() {
        return originalExpireTime - applyTime;
    }

    @Override
    public long getOriginalExpireTime() {
        return originalExpireTime;
    }

    @Override
    public long getRemainingDuration() {
        return expireTime - System.currentTimeMillis();
    }

    @Override
    public long getStartTime() {
        return applyTime;
    }

    @Override
    public void setRemainingDuration(long duration) {
        expireTime = System.currentTimeMillis() + duration;
    }

    @Override
    public boolean canTick(long currTime) {
        return currTime >= expireTime;
    }

    @Override
    public  boolean shouldStopTicking(long currTime) {
        return removed;
    }

    @Override
    public long timeTillNextTick() {
        return 0;
    }

    @Override
    public void tick(CharacterBase character) {
        character.removeEffect(name);
    }
}
