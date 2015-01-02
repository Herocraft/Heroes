package com.herocraftonline.heroes.api.components.core;


/**
 * Component that tracks mana on a character and allows for definition of custom health values
 */
public interface Mana {
    /**
     * @return The maximum mana value for the character to which this component is attached
     */
    double getMaxMana();

    /**
     * @return The current mana value for the character to which this component is attached
     */
    double getMana();

    /**
     * @param amount The amount to set current mana to
     * @throws java.lang.IllegalArgumentException if amount exceeds {@link #getMaxMana()}
     */
    void setMana(double amount);

    /**
     * @param amount The amount to set maximum mana to
     */
    void setMaxMana(double amount);
}