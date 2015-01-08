package com.herocraftonline.heroes.api.components;

import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;
import com.herocraftonline.heroes.api.util.Combiner;
import org.spongepowered.api.GameState;
import org.spongepowered.api.service.persistence.data.DataView;

/**
 * <p>Components represent persistent metadata that can be attached to a Characters, generally through the usage of
 * character classes. Empty component instances are generally first registered with the component manager during
 * {@link GameState#INITIALIZATION}. Heroes will then call {@link #onAttach(CharacterBase, DataView)}
 * to create an appropriate instance for the character in question</p>
 *
 * <p>Component implementations should not use constructors with arguments/parameters - the Heroes plugin will generally
 * use reflection to initialize the component class with an empty constructor. Rather additional data can be passed in
 * at two points: {@link #onInit(HeroesPlugin)} for general/shared values, or
 * {@link #onAttach(CharacterBase, DataView)} for instance specific values.</p>
 */
public interface Component extends Cloneable {

    /**
     * @return Whether a new instance should be constructed for every setting load (required if data is, for instance,
     * unique from character to character and/or class to class) -
     * if true, {@link #onAttach(CharacterBase, DataView)} will be called on a new/clean instance from
     * {@link #clone()}, otherwise setting load will be called on the initial instance loaded by the component manager
     */
    boolean cloneOnLoad();

    /**
     * The name of the component - it is with this name that the component is registered with a Character and
     * is how settings for this component are identified in configuration.
     * @return An (ideally) unique name for this component
     */
    String getName();

    /**
     * Serves as an alternative to the constructor, called when Heroes first loads the component
     * @param plugin The loading plugin instance
     */
    void onInit(HeroesPlugin plugin);

    /**
     * Actions to take when a component is attached to a given Character - this can be done when a Character
     * is initially loaded from storage (in which the component is already registered with the Character)
     * or when a component is initially registered with a Character via
     * {@link CharacterBase#registerComponent(Component)}.
     * @param character The Character to which the component is registered
     * @param data A combined representation of all data stored under this component's identifier, combined
     *             via the combiner returned from {@link #getCombiner()}
     */
    void onAttach(CharacterBase character, DataView data);

    /**
     * Actions to take when a component is unregistered from the Character. Note that storage providers should
     * automatically remove configuration keys associated with this component if it has been unregistered
     * @param character The Character from which the component is being registered
     */
    void onRemove(CharacterBase character);

    /**
     * Actions to take when a component is saved. The guarantee is made that the given character has the component and
     * that this method is called before onRemove
     * @param character The Character to which the component is registered
     * @returns A {@link DataView} representation that can then be saved to the character
     */
    DataView onSave(Character character);

    /**
     * @return A combiner that combines multiple data views pertaining to this component
     */
    Combiner<DataView> getCombiner();

    /**
     * @return A new copy of the component that contains matching (cloned) data in all its fields
     */
    Component clone();

}
