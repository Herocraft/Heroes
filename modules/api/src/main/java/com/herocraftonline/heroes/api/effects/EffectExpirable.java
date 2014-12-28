package com.herocraftonline.heroes.api.effects;

/**
 * Expirable implementations indicate that the effect is finite in nature - that is to say it will expire and be removed
 * from the character at some point.
 */
public interface EffectExpirable extends EffectTickable {

    /**
     * @return The total duration of the effect in milliseconds - that is to say the sum of the elapsed time since the
     *         effect was applied and the current value of {@link #getRemainingDuration()}
     */
    long getDuration();

    /**
     * @return The current time at which the effect will expire in milliseconds
     */
    long getExpireTime();

    /**
     * @return The original duration of the effect when the effect was instantiated in milliseconds. Should be the same
     *         as {@link #getDuration()} provided {@link #setRemainingDuration(long)} is never called.
     */
    long getOriginalDuration();

    /**
     * @return The original time at which the effect would have expired in milliseconds. Should be the same as
     *         {@link #getExpireTime()} provided {@link #setRemainingDuration(long)} is never called.
     */
    long getOriginalExpireTime();

    /**
     * @return The remaining duration on the effect in milliseconds
     */
    long getRemainingDuration();

    /**
     * @return The time (in milliseconds) that the effect was applied
     */
    long getStartTime();

    /**
     * Sets the remaining duration, if negative terminates effect immediately
     * @param duration The remaining duration to set in milliseconds
     */
    void setRemainingDuration(long duration);
}
