package com.herocraftonline.heroes.api.io.storage;

import com.google.common.base.Function;
import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.characters.Creature;
import com.herocraftonline.heroes.api.characters.Hero;
import com.herocraftonline.heroes.api.characters.Inanimate;
import com.herocraftonline.heroes.api.io.exception.AsynchronousSaveException;
import org.spongepowered.api.entity.Entity;

import java.util.UUID;

/**
 * Implementations of this interface handle various IO functions (namely loading and saving {@link CharacterBase}
 * objects and related settings). Saves and record retrieval should be done via UID: that is to say there is a guarantee
 * that {@link CharacterBase#getUID()} will not be null and will remain consistent
 * in representations of the same object.
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
     * the {@link StorageManager} attempts to load a Character object from an {@link Entity}. The function will return
     * true when the Entity in question matches a given storage hook. <br />
     * <br />
     * Load hooks are sequential in the order that they are registered - as such there is no guarantee that
     * there will not be conflicting load hooks whereby a Entity can be loaded by multiple storage solutions. To
     * minimize conflicts, Load hooks should follow the logic of only returning true if certain conditions are met,
     * and not the logic of returning false if certain conditions are met. <br />
     * <br />
     * When processing load hooks sequentially, upon returning true for any hook, the Character is loaded using
     * that storage provider and subsequent storage hooks are not checked. In the event that no registered load
     * hook returns true, then the storage manager defaults to using whatever storage provider is defined as default
     * within the configuration. <br />
     * <br />
     * A storage provider without a load hook (where this function returns null) will not be used to load
     * anything unless it is defined as the default storage provider within the configuration<br />
     * <br />
     *
     * @return A Function<Entity, Boolean> representation of a load hook which returns true when a Entity being
     *         passed in is eligible to be loaded by this storage provider, or false otherwise.
     */
    Function<Entity, Boolean> getLoadHook();

    /**
     * Storage providers can optionally define a save hook {@link com.google.common.base.Function}. <br />
     * Save hooks are processed sequentially by registration order to determine whether a storage provider
     * ought to be used when the {@link StorageManager} attempts to save a {@link Character} object. <br />
     * While similar to load hooks (see: {@link #getLoadHook()}), save hooks differ in the following ways:
     * <ul>
     *     <li>
     *         Save Hooks are not exclusive: if multiple save hooks return true, the Character will be saved using all
     *         of their corresponding storage providers. (Exception: when {@link #isExclusiveSave()} returns true) See
     *         below for conflict resolution description.
     *     </li>
     *     <li>
     *         The default storage provider will always be used to save a Character (same exception as above).
     *     </li>
     * </ul>
     * <br />
     * If a load hook returns true for a given entity, then a save hook should always return true for a given
     * corresponding Character. Note that the reverse is not true - a save hook returning true does not necessarily
     * mean that a load hook will return true. <br />
     * <br />
     * In the event that multiple save hooks return true and one or more have isExclusiveSave() returning true,
     * the first processed hook that is an exclusive saver will be used to save. Should multiple exclusive savers
     * exist in the above scenario, a warning will optionally be printed to the debug log
     *
     * @return A Function<CharacterBase, Boolean> representation of a save hook which returns true when the Character
     *         being passed in is eligable to be saved by this storage provider, or false otherwise
     */
    Function<CharacterBase, Boolean> getSaveHook();

    /**
     * @return True if this storage provider should be the only one used to save a character assuming save-hooks return
     * true, false otherwise
     */
    boolean isExclusiveSave();

    /**
     * Loads a (non-Hero) character from storage
     * @param uid The UUID of the the creature being loaded, should match the UID from calling save methods
     * @return The loaded character, or null if no such character exists
     */
    Creature loadCharacter(UUID uid);

    /**
     * Loads a Hero from storage, including stored components
     * @param uid The unique identifier of the Hero being loaded, should match the UID from calling save methods
     * @return The loaded Hero from storage, or null if no matching Hero found
     */
    Hero loadHero(UUID uid);

    /**
     * Loads an inanimate character from storage, including stored components
     * @param uid The unique identifier of the inanimate character, should match the UID from calling save methods
     * @return The loaded Inanimate from storage, or null if no matching Inanimate found
     */
    Inanimate loadInanimate(UUID uid);

    /**
     * Saves the given {@link Creature}, including any attached components and respective {@link DataView}
     * objects
     * @param creature The Creature to save
     * @param now Whether this save should be done immediately/synchronously - note that implementation/respect of this
     *            parameter is optional depending on implementation, although generally that means that
     * @return The UID of the saved record, or null if save not successful or save is asynchronous
     * @throws AsynchronousSaveException when the method respects the asynchronous parameter and an error occurs during
     *         asynchronous saving
     */
    UUID saveCreature(Creature creature, boolean now);

    /**
     * Saves the given {@link Hero}, including any attached components and respective {@link DataView} objects
     * @param hero The Hero to save
     * @param now Whether this save should be done immediately/synchronously - note that implementation/respect of this
     *            parameter is optional depending on implementation, although generally that means that
     * @return The UID of the saved record, or null if save not successful or save is asynchronous
     * @throws AsynchronousSaveException when the method respects the asynchronous parameter and an error occurs during
     *         asynchronous saving
     */
    UUID saveHero(Hero hero, boolean now);

    /**
     * Saves the given {@link Inanimate}, including any attached components and respective {@link DataView} objects
     * @param inanimate The Inanimate to save
     * @param now Whether this save should be done immediately/synchronously - note that implementation/respect of this
     *            parameter is optional depending on implementation, although generally that means that
     * @return The UID of the saved record, or null if save not successful or save is asynchronous
     * @throws AsynchronousSaveException when the method respects the asynchronous parameter and an error occurs during
     *         asynchronous saving
     */
    UUID saveInanimate(Inanimate inanimate, boolean now);

    /**
     * Removes a given {@link Creature} from storage
     * @param record The UUID of the record to remove
     * @return The removed creature, or null if none removed
     */
    Creature removeCreature(UUID record);

    /**
     * Removes a given Hero from storage
     * @param record The record of the UID to remove
     * @return The removed Hero, or null if none removed
     */
    Hero removeHero(UUID record);

    /**
     * Removes a given Inanimate from storage
     * @param record The UUID of the record to remove
     * @return the removed Inanimate, or null if none removed
     */
    Inanimate removeInanimate(UUID record);
}
