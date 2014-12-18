package com.herocraftonline.heroes.characters;

import com.herocraftonline.heroes.effects.Effect;

import java.util.Set;

/**
 * Represents a character, which is Heroes' internal representation of living entities. To obtain a Character instance,
 * use {@link CharacterManager#getCharacter(java.util.UUID)},
 * {@link CharacterManager#getCharacter(org.spongepowered.api.entity.living.Living)}
 * or an appropriate alternate method depending on entity type.
 */
public interface Character {

    /**
     * @return Retrieves the name of the character: the player name if the character is a player,
     * otherwise the set custom name of a non-player, or the name of the entity type if said
     * custom name does not exist
     */
    String getName();

    /**
     * Adds the given effect to the Character, initiating its first tick and calling {@link Effect#apply(Character)},
     * which in turn calls {@link Effect#applyToHero(Hero)} or {@link Effect#applyToCreature(Creature)} as appropriate
     * @param effect The effect to add, will replace any preexisting effects of the same name
     */
    void addEffect(Effect effect);

    /**
     * Attempts to retrieve a given effect that is linked to the character
     * @param name The name of the effect to retrieve, non case-sensitive
     * @return The effect if it exists, or null if it does not
     */
    Effect getEffect(String name);

    /**
     * @return A copy of all the effects currently attached to the character
     */
    Set<Effect> getEffects();

    /**
     * Checks whether this character has a given Effect
     * @param effect The effect to check for
     * @return Whether the character has the effect
     */
    boolean hasEffect(Effect effect);

}
