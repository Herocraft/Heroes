package com.herocraftonline.heroes.effects;

import com.herocraftonline.heroes.characters.CharacterBase;

/**
 * Repeatable implementations indicate that the effect repeats indefinitely
 */
public interface EffectRepeatable {
    /**
     * Called right before scheduling the next tick, to determine how long of a period to wait
     * @return The time till next tick, in milliseconds
     */
    long timeTillNextTick();

    /**
     * Called when the effect ticks, repeat ticks will automatically be scheduled and does not need to be done here
     * @param character The character to which the effect is applied
     */
    void tick(CharacterBase character);
}
