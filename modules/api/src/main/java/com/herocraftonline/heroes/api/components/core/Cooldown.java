package com.herocraftonline.heroes.api.components.core;

/**
 * Component that tracks cooldowns on a character - which in essence maps an identifier to a specific timestamp
 */
public interface Cooldown {

    /**
     * Adds a new cooldown for the character to which this component is attached. Will replace any cooldowns for the
     * character that already exists
     * @param identifier The identifier for this cooldown, case-sensitive
     * @param duration The length of the cooldown
     * @return The expiry time of the cooldown in milliseconds, equal to current time + duration
     */
    long addCooldown(String identifier, long duration);

    /**
     * Clears the given cooldown from the tracker
     * @param identifier The identifier for this cooldown, case-sensitive
     * @return The previously associated cooldown expiry, or -1 if none exists.
     */
    long clearCooldown(String identifier);

    /**
     * Gets the expiry time of a given cooldown
     * @param identifier The identifier for this cooldown, case-sensitive
     * @return The cooldown's expiry time in milliseconds, or -1 if no cooldown exists
     */
    long getCooldown(String identifier);

    /**
     * @param identifier The identifier for this cooldown, case-sensitive
     * @return true if cooldown is not expired, false otherwise
     */
    boolean isOnCooldown(String identifier);

    /**
     * Sets the cooldown for a specified identifier to a timestamp, replacing any previous values if present
     * @param identifier The identifier for this cooldown, case-sensitive
     * @param timestamp The time in milliseconds for this cooldown's expiry
     */
    void setCooldown(String identifier, long timestamp);

    /**
     * Changes the cooldown mapping by a specified amount, or behaves similarly to {@link #addCooldown(String, long)}
     * should a mapping not yet exist for the identifier
     * @param identifier The identifier for this cooldown, case-sensitive
     * @param amount The amount to modify the cooldown by, can e negative
     * @return The modified expiry time, or -1 if the modified expiry is less than the current system time
     */
    long modifyCooldown(String identifier, long amount);


}
