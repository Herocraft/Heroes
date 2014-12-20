package com.herocraftonline.heroes.effects;

import com.herocraftonline.heroes.characters.CharacterBase;

/**
 * Tickable effects are effects that can be registered with the EffectManager to be ticked - that is to say they perform
 * a certain action (or possibly multiple) at some point in the future
 */
public interface EffectTickable extends EffectBase {

    /**
     * @param currTime The time in milliseconds when method is called
     * @return True if effect is ready to be ticked
     */
    boolean canTick(long currTime);

    /**
     * @param currTime The time in milliseconds when method is called
     * @return Whether the effect should be removed and stop ticking (i.e. effect not valid, duration expired, etc)
     */
    boolean shouldStopTicking(long currTime);

    /**
     * Called right before scheduling the next tick, to determine how long of a period to wait (if necessary)
     * @return The time till next tick, in milliseconds
     */
    long timeTillNextTick();

    /**
     * Called when the effect ticks
     * @param character The character to which the effect is applied
     */
    void tick(CharacterBase character);

}
