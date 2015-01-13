package com.herocraftonline.heroes.api.util;

/**
 * Implementations combine multiple objects of the same type together, resolving any potentially conflicting information
 */
public interface Combiner<T> {
    /**
     * The guarantee to implementations is made that - should the objects in question have some sort of priority over
     * the other, the higher priority object will be in the first argument.
     * @param o1 The first object
     * @param o2 The second object
     * @return The result of combining the two objects
     */
    public T combine(T o1, T o2);
}
