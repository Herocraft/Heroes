package com.herocraftonline.heroes.components;

import com.herocraftonline.heroes.characters.CharacterBase;
import org.spongepowered.api.GameState;

/**
 * <p>Components represent persistent metadata that can be attached to a Characters, generally through the usage of
 * character classes. Empty component instances are generally first registered with the component manager during
 * {@link GameState#INITIALIZATION}. Heroes will then call {@link #getFromSettings(Object)} to create an appropriate
 * instance for the character in question</p>
 * //TODO update javadocs, pending persistence API
 */
public interface Component {

    /** TODO pending persistence API
     * <p>Constructs a new instance of this component based on settings found under a key with the value of
     * {@link #getName()}. Should there be no such settings set, this method will not be called
     * and rather the instance already registered with the component manager will be used</p>
     * <p>This method may be called anywhere from 0-2 times, depending on what data exists. The guarantee is made that
     * so long as the data exists in each sequential step, this method will be called first by any data within the
     * character class itself if it exists (this first iteration is what is stored with character class objects)
     * and then again with data contained within the character file itself (this second iteration is stored with the
     * character class itself). For each iteration, should data not exist, the instance used for that iteration
     * is the same as that in the previous iteration.</p>
     * @param config A DataView representation of the settings stored for this requirement
     * @return A new requirement instance specifically for the character class with the configuration passed to this
     *         method.
     */
    Component getFromSettings(Object config);

    /**
     * The name of the component - it is with this name that the component is registered with a Character and
     * is how settings for this component are identified in configuration.
     * @return An (ideally) unique name for this component
     */
    String getName();

    /**
     * Actions to take when a component is attached to a given Character - this can be done when a Character
     * is initially loaded from storage (in which the component is already registered with the Character)
     * or when a component is initially registered with a Character via
     * {@link CharacterBase#registerComponent(Component)}.
     * @param character The Character to which the component is registered
     */
    void onAttach(CharacterBase character);

    /**
     * Actions to take when a component is unregistered from the Character. Note that storage providers should
     * automatically remove configuration keys associated with this component if it has been unregistered
     * @param character The Character from which the component is being registered
     */
    void onRemove(CharacterBase character);

    /**
     * Actions to take when a component is saved. The guarantee is made that the given character has the component
     * TODO pending persistence API PR Merge
     * @param character The Character to which the component is registered
     * @returns A {@link DataView} representation that can then be saved to the character
     */
    //DataView onSave(Character character);


}
