package com.herocraftonline.heroes.api.util;

/**
 * Implementations combine multiple objects of the same type together, resolving any potentially conflicting information
 */
public interface Combiner<T> {
    /**
     * @param o1 The first object - should be the object with the higher priority if ordering matters
     *           i.e. a combiner for skill data would have this be data defined in the character class
     *           with the second parameter being data defined in general configuration, likewise
     *           with character file vs. character class
     * @param o2 The second object
     * @return The result of combining the two objects
     */
    public T combine(T o1, T o2);
}
