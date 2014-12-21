package com.herocraftonline.heroes.classes;

/**
 * Represents a class - classes store component settings and will automatically apply relevant components with
 * initial settings when it is attached. In addition classes have levels and experience sources.
 * TODO: write better javadocs here
 */
public interface CharacterClass {

    //Experience related methods

    /**
     * @param character The character to get experience for
     * @return A numerical representation of the number of experience points accumulated by the character
     * @see #getLevelExperience(Character)
     */
    long getExperience(Character character);

    /**
     * @param character The character to get experience for
     * @return The amount of experience the character needs to reach the next level ({@link #getLevel(Character)} + 1)
     *         with 0 exp.
     */
    long getExperienceToNextLevel(Character character);

    /**
     * @param character The character to get experience for
     * @return The amount of experience the character has accumulated in its current level
     * @see #getExperience(Character)
     */
    long getLevelExperience(Character character);

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
     * @return The name of the class, ideally unique
     */
    String getName();

    /**
     * Sets the amount of experience a given character has in this class
     * @param character The character to set the experience of
     * @param experience The experience amount, valid range being [0, {@link #getMaxExperience()}]
     * @throws java.lang.IllegalArgumentException if {@code experience < 0} or {@code experience > getMaxExperience()}
     */
    void setExperience(Character character, long experience);

    /**
     * Sets the level (with 0 exp towards the next level) a given character has in this class
     * @param character The character to set the level of
     * @param level The level to set, valid range being [0, {@link #getMaxLevel()}]
     * @throws java.lang.IllegalArgumentException if {@code level < 0} or {@code level > getMaxLevel()}
     */
    void setLevel(Character character, int level);

    //Component Methods

    /**
     * Retrieves the component classes attached to this class, as well as associated settings such that they may be
     * instantiated
     * @return A map of component classes and associated settings //TODO
     */
    //Map<Class<? extends Component>, DataView> getComponents();

}
