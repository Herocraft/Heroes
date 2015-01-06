package com.herocraftonline.heroes.api.components.core;

/**
 * Component that tracks health on a character and allows for definition of custom health values
 */
public interface HealthTracker {
    /**
     * @return The maximum health value for the character to which this component is attached
     */
    double getMaxHealth();

    /**
     * @return The current health value for the character to which this component is attached
     */
    double getHealth();

    /**
     * @param amount The amount to set current health to
     * @throws java.lang.IllegalArgumentException if amount exceeds {@link #getMaxHealth()}
     */
    void setHealth(double amount);

    /**
     * @param amount The amount to set maximum health to
     */
    void setMaxHealth(double amount);
}
