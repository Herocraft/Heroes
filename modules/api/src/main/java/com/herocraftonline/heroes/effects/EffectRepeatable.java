package com.herocraftonline.heroes.effects;

/**
 * Repeatable implementations indicate that the effect repeats indefinitely
 */
public interface EffectRepeatable extends EffectTickable {
    /**
     * Called right before scheduling the next tick, to determine how long of a period to wait
     * @return The time till next tick, in milliseconds
     */
    long timeTillNextTick();
}
