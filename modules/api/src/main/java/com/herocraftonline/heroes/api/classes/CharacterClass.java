package com.herocraftonline.heroes.api.classes;

import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.components.Component;
import org.spongepowered.api.service.persistence.data.DataView;

import java.util.Collection;
import java.util.Set;

/**
 * Represents a Character Class. Character Classes fulfill several
 */
public interface CharacterClass {

    // Generic Methods

    /**
     * @return The name of the class, ideally unique
     */
    String getName();

    // Class Requirement Methods

    /**
     * @param name The requirement to get, non case-sensitive, as denoted by the return value from
     *             {@link CharacterClassRequirement#getName()} of the corresponding requirement
     * @param <T> The corresponding requirement subclass registered with the CharacterClassManager by the given name
     * @return The corresponding requirement instance attached to this class if it exists, null otherwise
     */
    <T extends CharacterClassRequirement> T getRequirement(String name);

    /**
     * @return An immutable copy of the class requirement instances associated with this character class
     */
    Collection<CharacterClassRequirement> getClassRequirements();

    // Class Parenting Methods TODO: move this to a CharacterClassRequirement

    /**
     * Gets all possible parents for this class. Unlike {@link #getStrongParents()} and {@link #getWeakParents()},
     * which only gets direct parents, this method will also
     * @return A collection of all parent classes
     */
    Collection<CharacterClass> getAllParents();

    /**
     * @return Strong parents for this class - All of these classes must be mastered before this class can be chosen
     *         by a character
     */
    Set<CharacterClass> getStrongParents();

    /**
     * @return Weak parents for this class - At least one of these classes must be mastered before this class can be
     *         chosen by a character
     */
    Set<CharacterClass> getWeakParents();

    // Experience related methods

    /**
     * @param character The character to get experience for
     * @return A numerical representation of the number of experience points accumulated by the character
     * @see #getLevelExperience(CharacterBase)
     */
    long getExperience(CharacterBase character);

    /**
     * @param character The character to get experience for
     * @return The amount of experience the character needs to reach the next level ({@link #getLevel(Character)} + 1)
     *         with 0 exp.
     */
    long getExperienceToNextLevel(CharacterBase character);

    /**
     * @param character The character to get experience for
     * @return The amount of experience the character has accumulated in its current level
     * @see #getExperience(CharacterBase)
     */
    long getLevelExperience(CharacterBase character);

    /**
     * @param character The character to check the level of
     * @return The character's level in the class
     */
    int getLevel(Character character);

    /**
     * @return The maximum obtainable experience points of this class.
     */
    long getMaxExperience();

    /**
     * @return The maximum obtainable level of this class.
     */
    int getMaxLevel();

    /**
     * @param character The character to check
     * @return Whether the provided character has mastered (is between 0-99% of the maximum level depending on settings)
     *         the class
     */
    boolean isMastered(CharacterBase character);

    /**
     * Sets the amount of experience a given character has in this class
     * @param character The character to set the experience of
     * @param experience The experience amount, valid range being [0, {@link #getMaxExperience()}]
     * @throws java.lang.IllegalArgumentException if {@code experience < 0} or {@code experience > getMaxExperience()}
     */
    void setExperience(CharacterBase character, long experience);

    /**
     * Sets the level (with 0 exp towards the next level) a given character has in this class
     * @param character The character to set the level of
     * @param level The level to set, valid range being [0, {@link #getMaxLevel()}]
     * @throws java.lang.IllegalArgumentException if {@code level < 0} or {@code level > getMaxLevel()}
     */
    void setLevel(CharacterBase character, int level);

    /**
     * @param trait The trait to check for
     * @return Whether the class possesses the parameter trait
     */
    boolean hasTrait(CharacterClassTrait trait);

    /**
     * @return A collection of traits possessed by the given class
     */
    Collection<CharacterClassTrait> getTraits();

    // Component Methods

    /**
     * Retrieves the components attached to this class, with class level settings already instantiated.
     * The returned components have <b>not</b> processed character level settings.
     * @return An immutable collection of components attached to this class.
     */
    Collection<Component> getComponents();

    /**
     * @param name The component to get, case-sensitive, as denoted by the return value from
     *             {@link Component#getName()} of the corresponding component
     * @param <T> The corresponding component subclass registered with the ComponentManager by the given name
     * @return The corresponding component instance attached to this class if it exists, null otherwise
     */
    <T extends Component> T getComponent(String name);

    /**
     * Gets the settings stored on a character class configuration for a specific component
     * @param name The matching return value of {@link Component#getName()}
     * @return A DataView representation of the settings for said component, or an empty data view if no such settings
     * exist
     */
    DataView getComponentSettings(String name);

}
