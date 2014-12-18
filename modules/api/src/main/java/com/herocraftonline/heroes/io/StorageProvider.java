package com.herocraftonline.heroes.io;

import com.google.common.base.Function;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.io.exception.AsynchronousSaveException;
import org.spongepowered.api.entity.player.Player;

import java.util.UUID;

/**
 * Implementations of this interface handle various IO functions (namely loading and saving {@link Hero} objects
 * and related settings).
 */
public interface StorageProvider {
    /**
     * @return A (ideally) unique human-readable identifier that can be used to identify this particular storage
     * solution within human-readable mediums, such as within configuration settings.
     */
    String getStorageIdentifier();
    /**
     * Storage providers can optionally define a load hook {@link com.google.common.base.Function}. <br />
     * Load hooks are processed sequentially to determine whether a storage provider ought to be used when
     * the {@link StorageManager} attempts to load a Hero object from a {@link Player}. The function will return
     * true when the Player in question matches a given storage hook. <br />
     * <br />
     * Load hooks are sequential in the order that they are registered - as such there is no guarantee that
     * there will not be conflicting load hooks whereby a Player can be loaded by multiple storage solutions. To
     * minimize conflicts, Load hooks should follow the logic of only returning true if certain conditions are met,
     * and not the logic of returning false if certain conditions are met. <br />
     * <br />
     * When processing load hooks sequentially, upon returning true for any hook, the Hero is loaded using
     * that storage provider and subsequent storage hooks are not checked. In the event that no registered load
     * hook returns true, then the storage manager defaults to using whatever storage provider is defined as default
     * within the configuration. <br />
     * <br />
     * A storage provider without a load hook (where this function returns null) will not be used to load
     * anything unless it is defined as the default storage provider within the configuration<br />
     * <br />
     *
     * @return A Function<Player, Boolean> representation of a load hook which returns true when a Player being
     *         passed in is eligible to be loaded by this storage provider, or false otherwise.
     */
    Function<Player, Boolean> getLoadHook();

    /**
     * Storage providers can optionally define a save hook {@link com.google.common.base.Function}. <br />
     * Save hooks are processed sequentially by registration order to determine whether a storage provider
     * ought to be used when the {@link StorageManager} attempts to save a {@link Hero} object. <br />
     * While similar to load hooks (see: {@link #getLoadHook()}), save hooks differ in the following ways:
     * <ul>
     *     <li>
     *         Save Hooks are not exclusive: if multiple save hooks return true, the Hero will be saved using all of
     *         their corresponding storage providers. (Exception: when {@link #isExclusiveSave()} returns true) See
     *         below for conflict resolution description.
     *     </li>
     *     <li>
     *         The default storage provider will always be used to save a Hero (same exception as above).
     *     </li>
     * </ul>
     * <br />
     * If a load hook returns true for a given player, then a save hook should always return true for a given
     * corresponding Hero. Note that the reverse is not true - a save hook returning true does not necessarily
     * mean that a load hook will return true. <br />
     * <br />
     * In the event that multiple save hooks return true and one or more have isExclusiveSave() returning true,
     * the first processed hook that is an exclusive saver will be used to save. Should multiple exclusive savers
     * exist in the above scenario, a warning will optionally be printed to the debug log
     * @return
     */
    Function<Hero, Boolean> getSaveHook();

    /**
     * @return True if this storage provider should be the only one used to save a Hero, false otherwise
     */
    boolean isExclusiveSave();
    /**
     * Loads a Hero from storage
     * @param uid The unique identifier of the {@link org.spongepowered.api.entity.player.Player} represented by the
     *            Hero being loaded
     * @return The loaded Hero from storage, or null if no matching Hero found
     */
    Hero loadHero(UUID uid);
    /**
     * Saves the given {@link Hero}
     * @param hero The Hero to save
     * @param now Whether this save should be done immediately/synchronously - note that implementation/respect of this
     *            parameter is optional depending on implementation, although generally that means that
     * @return Whether the save was successful
     * @throws AsynchronousSaveException when the method respects the asynchronous parameter and an error occurs during
     *         asynchronous saving
     */
    boolean saveHero(Hero hero, boolean now);
}
