package com.herocraftonline.heroes.components;

import com.herocraftonline.heroes.characters.CharacterBase;
import com.herocraftonline.heroes.classes.CharacterClass;

/**
 * <p>Components represent persistent metadata that can be attached to a Character object via
 * {@link CharacterBase#registerComponent(Component)}. </p>
 * <p>Initial component settings are designed to be loaded from a {@link CharacterClass} that is attached to a given
 * character, while additional overriding settings/customizations are stored with the character itself in storage</p>
 * <p>Components must implement the following:
 * {@code public static Component deserialize(org.spongepowered.api.service.persistence.data.DataView)} that deserializes
 * a Component from preexisting stored settings</p>
 */
public interface Component {
    /**
     * The name of the component - it is with this name that the component is registered with a Character
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
     * @returns A {@link DataView} representation that can then be saved to the Hero
     */
    //DataView onSave(Hero hero);


}
