package com.herocraftonline.heroes.effects;

/**
 * Keeps track of tickable effects and ticks them as appropriate
 */
public interface EffectManager {
    /**
     * Adds the effect to the effect manager, causing it to be ticked
     * @param effect The effect to add
     */
    public void registerEffect(EffectTickable effect);

    /**
     * Removes the effect from the effect manager, ceasing ticks. This may be automatically called if
     * {@link EffectTickable#tick(com.herocraftonline.heroes.characters.CharacterBase)} returns false
     * @param effect The effect to remove
     */
    public void removeEffect(EffectTickable effect);
}
