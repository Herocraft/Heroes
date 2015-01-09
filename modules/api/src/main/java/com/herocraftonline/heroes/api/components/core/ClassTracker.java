package com.herocraftonline.heroes.api.components.core;

import com.herocraftonline.heroes.api.classes.CharacterClass;
import com.herocraftonline.heroes.api.classes.CharacterClassTrait;
import com.herocraftonline.heroes.api.components.Component;

import java.util.Collection;

public interface ClassTracker {

    /**
     * @return A collection of all active classes on the character to which this component is attached
     */
    Collection<CharacterClass> getActiveClasses();

    /**
     * @return A collection of all classes for which the character the component is attached satisfies all
     *         {@link com.herocraftonline.heroes.api.classes.CharacterClassRequirement}
     */
    Collection<CharacterClass> getAvailableClasses();

    /**
     * @param component The component to have
     * @return A collection of active classes on the character that have the specified component
     */
    Collection<CharacterClass> getClassesWithComponent(Component component);

    /**
     * @param trait The trait to have
     * @return A collection of active classes on the character that have the specified trait
     */
    Collection<CharacterClass> getClassesWithTrait(CharacterClassTrait trait);

    /**
     * Utility method that gets the highest level out of a collection of character classes
     * @param classes A collection of classes to check
     * @return The highest level out of all the parameter classes
     */
    int getHighestLevel(Collection<CharacterClass> classes);

    /**
     * Utility method that gets the highest level out of multiple character classes, equivalent to
     * {@link #getHighestLevel(java.util.Collection)}
     * @param classes The classes to check
     * @return The highest level out of all the parameter classes
     */
    int getHighestLevel(CharacterClass... classes);

}
