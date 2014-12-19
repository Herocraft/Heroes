package com.herocraftonline.heroes.effects;

import com.herocraftonline.heroes.characters.Character;

/**
 * <p>Represents additional information that can be attached to Character objects. Unlike components, effects are not
 * designed to necessarily be persistent - in fact one of the most common intended uses for the Effect class,
 * as (temporary) markers for skill effects, demonstrates that this is not the case at all. </p>
 * <p>Common implementations of Effect classes and subclasses can be found under the maven module
 * {@code com.herocraftonline.heroes:heroes-common}, although no guarantees are made as to code persistence</p>
 */
public interface Effect {

    /**
     * <p>Called when an effect is applied (added) to a Character</p>
     * @param character The character to which the effect is being applied
     */
    void apply(Character character);
}
