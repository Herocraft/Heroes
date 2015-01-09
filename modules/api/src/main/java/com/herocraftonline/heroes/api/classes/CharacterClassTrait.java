package com.herocraftonline.heroes.api.classes;

import com.herocraftonline.heroes.api.components.core.ClassTracker;

/**
 * Represents a trait, additional tags that may be attached to a character class in configuration that can be used
 * to identify certain classes from a collection of active classes on a given character
 *
 * @see ClassTracker#getClassesWithTrait(CharacterClassTrait)
 */
public class CharacterClassTrait {

    /**
     * Legacy trait that exists to provide similar functionality to the Bukkit version of Heroes, denotes a
     * primary class, of which only one can exist on a character at any given time
     */
    public static final CharacterClassTrait PRIMARY = new CharacterClassTrait("PRIMARY");

    /**
     * Legacy trait that exists to provide similar functionality to the Bukkit version of Heroes, denotes a
     * secondary class, of which only one can exist on a character at any given time
     */
    public static final CharacterClassTrait SECONDARY = new CharacterClassTrait("SECONDARY");

    public static CharacterClassTrait valueOf(String name) {
        return new CharacterClassTrait(name);
    }

    private String name;

    public CharacterClassTrait(String name) {
        this.name = name.toUpperCase();
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof CharacterClassTrait)) {
            return false;
        }
        return name.equals(((CharacterClassTrait)obj).name);
    }


}
