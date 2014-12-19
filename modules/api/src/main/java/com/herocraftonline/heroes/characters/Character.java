package com.herocraftonline.heroes.characters;

import com.herocraftonline.heroes.components.Component;
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

    // Effect Methods

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

    // Component Methods

    /**
     * Checks whether a component is attached/registered with this character
     * @param name The name of the component, corresponding to value of {@link Component#getName()}, case sensitive
     * @return True if the component is registered with this Hero, false otherwise
     */
    public boolean hasComponent(String name);

    /**
     * Retrieves a component instance attached/registered with this character, if it exists
     * @param name The name of the component, corresponding to value of {@link Component#getName()}, case sensitive
     * @return The corresponding component, or null if no matching component exists
     */
    public Component getComponent(String name);

    /**
     * Adds a component to this Hero, and calls {@link Component#onAttach(Character)} with this Hero as the parameter
     * @param component The component to register
     * @return False if an error occurs during attach, true otherwise
     */
    public boolean registerComponent(Component component);

    /**
     * Removes a component from this Hero, and calls {@link Component#onRemove(Character)} with this Hero as the parameter
     * @param name he name of the component, corresponding to value of {@link Component#getName()}, case sensitive
     * @return The removed component, or null no matching component exists
     */
    public Component unregisterComponent(String name);

}
