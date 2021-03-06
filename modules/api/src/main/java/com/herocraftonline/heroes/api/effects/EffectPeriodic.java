package com.herocraftonline.heroes.api.effects;

/**
 * Periodic implementations indicate that the effect is periodic in nature - that is to say its effects repeat on some
 * constant period ({@link #timeTillNextTick()} will return a constant value).
 */
public interface EffectPeriodic extends EffectTickable {
    /**
     * @return The period between ticks in milliseconds, same value as {@link #timeTillNextTick()}
     */
    long getPeriod();
}
