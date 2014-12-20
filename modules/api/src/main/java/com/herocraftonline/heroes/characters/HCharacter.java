package com.herocraftonline.heroes.characters;

import com.herocraftonline.heroes.components.Component;
import com.herocraftonline.heroes.effects.Effect;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.world.Location;

import java.util.Collection;
import java.util.UUID;

/**
 * <p>Represents a character, which is Heroes' representation of any entities that can have effects
 * applied to them, or have components attached to them</p>
 * <p>To obtain a Character instance, use {@link com.herocraftonline.heroes.characters.managers.CharacterManager#getCharacter(java.util.UUID)},
 * {@link com.herocraftonline.heroes.characters.managers.CharacterManager#getCharacter(org.spongepowered.api.entity.living.Living)}
 * or an appropriate alternate method depending on entity type.</p>
 * <p>It is important to note that characters are not restricted to living entities - for instance the Beacon
 * character class in the com.herocraftonline.heroes:Heroes-Common maven module is designed for use with static
 * entities and/or that are immobile/persistent at a certain point in the map(</p>
 */
public interface HCharacter {

    /**
     * Gets the location of the character
     * @return A copy of the location of the character
     */
    Location getLocation();

    /**
     * @return Retrieves the name of the character: examples include the player name if the character is a player,
     * otherwise the set custom name of a living non-player, or the name of the entity type if said
     * custom name does not exist
     */
    String getName();

    /**
     * All characters must be convertible to some constant UID - that is to say so long as the objects they are
     * representing remain the same, the converted UUID should also be the same. Ideally, the UID should also match
     * that of the underlying object if it exists (e.g. from {@link Player#getUniqueId()} for Hero objects).
     * @return The UID of the character
     */
    UUID getUID();

    // Effect Methods

    /**
     * Adds the given effect to the Character, initiating its first tick and calling {@link Effect#apply(HCharacter)},
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
    Collection<Effect> getEffects();

    /**
     * Checks whether this character has a given Effect
     * @param name The name of the effect to check for, non case-sensitive
     * @return Whether the character has the effect
     */
    boolean hasEffect(String name);

    /**
     * Removes an effect from this character if it exists
     * @param name The name of the effect to remove, non case-sensitive
     * @return The removed effect, or null if none matching
     */
    Effect removeEffect(String name);

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
     * Adds a component to this Character, and calls {@link Component#onAttach(HCharacter)} with this character as the
     * parameter
     * @param component The component to register
     * @return False if an error occurs during attach, true otherwise
     */
    public boolean registerComponent(Component component);

    /**
     * Removes a component from this Character, and calls {@link Component#onRemove(HCharacter)} with this Character as
     * the parameter
     * @param name The name of the component, corresponding to value of {@link Component#getName()}, case sensitive
     * @return The removed component, or null no matching component exists
     */
    public Component unregisterComponent(String name);

}
