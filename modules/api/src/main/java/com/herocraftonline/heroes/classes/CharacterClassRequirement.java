package com.herocraftonline.heroes.classes;

import com.herocraftonline.heroes.classes.event.RequirementRegistrationEvent;

/**
 * <p>Implementations are identified by name and represent a condition check that must be met by a character prior to
 * being allowed to use a class.</p>
 *
 * <p>Requirements instances must be registered during {@link RequirementRegistrationEvent} by calling
 * {@link RequirementRegistrationEvent#getClassManager()#registerClassRequirement(CharacterClassRequirement)}</p>
 *
 * <p>The requirements required (as well as any relevant settings) for a class are defined in the class configuration
 * by the end user. </p>
 */
public interface CharacterClassRequirement {

    /**
     * @return A name by which this requirement can be registered/identified in configuration, non-case sensitive
     */
    String getName();

    /** TODO pending persistence API
     * <p>Constructs a new instance of this requirement based on settings found in a specific character class under a
     * key with the value of {@link #getName()}. Should there be no such settings set, this method will not be called
     * and rather the instance already registered with the class manager will be used</p>
     * @param config A DataView representation of the settings stored for this requirement
     * @return A new requirement instance specifically for the character class with the configuration passed to this
     *         method.
     */
    //CharacterClassRequirement loadFromSettings(DataView config);

}
