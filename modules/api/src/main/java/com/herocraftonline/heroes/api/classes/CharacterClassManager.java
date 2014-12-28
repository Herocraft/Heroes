package com.herocraftonline.heroes.api.classes;

import org.spongepowered.api.GameState;

/**
 *
 */
public interface CharacterClassManager {
    /**
     * Registers a class requirement with the class manager, must be done during {@link GameState#INITIALIZATION}.
     * Registration is required for a requirement to be attached to a class if said registration is not located
     * within a JAR file inside the requirement data folder as defined in the configuration
     * @param requirement The class of the  requirement to register
     * @throws java.lang.IllegalStateException if registration is done at any other state than INITIALIZATION
     * @throws java.lang.IllegalArgumentException if the parameter class fails instantiation tests or does not meet
     *         other requirements
     */
    void registerRequirement(Class<? extends CharacterClassRequirement> requirement);

    /**
     * Gets a base requirement instance with no data values passed in
     * @param name The name of the requirement, non case-sensitive
     * @param <T> The specific class of the requirement being retrieved
     * @return The component, or null if no requirement with a matching name is registered.
     */
    <T extends CharacterClassRequirement> T getRequirement(String name);

    /**
     * Unregisters a class requirement from the class manager, must be done during {@link GameState#INITIALIZATION}.
     * @param name The name of the requirement to unregister, non case-sensitive
     * @param <T> The specific CharacterClassRequirement subclass
     * @return The unregistered requirement, or null if not found
     */
    <T extends CharacterClassRequirement> T unregisterRequirement(String name);
}
