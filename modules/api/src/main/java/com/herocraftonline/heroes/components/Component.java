package com.herocraftonline.heroes.components;

import com.herocraftonline.heroes.characters.Hero;

/**
 * Components represent persistent metadata that can be attached to a Hero object via
 * {@link Hero#registerComponent(Component)}. <br />
 * Additionally, components have the capability of loading settings/saving settings from class configurations, as well
 * as Hero files. <br />
 * <br />
 * Components must implement the following:
 * {@code public static Component deserialize(org.spongepowered.api.util.config.ConfigFile)} that deserializes
 * a Component from preexisting stored settings
 */
public interface Component {
    /**
     * The name of the component - it is with this name that the component is registered with a Hero
     * @return An (ideally) unique name for this component
     */
    String getName();

    /**
     * Actions to take when a component is attached to a given Hero - this can be done when a Hero
     * is initially loaded from storage (in which the component is already registered with the Hero)
     * or when a component is initially registered with a Hero via {@link Hero#registerComponent()}.<br />
     * @param hero The Hero to which the component is registered
     */
    void onAttach(Hero hero);

    /**
     * Actions to take when a component is unregistered from the Hero. Note that storage providers should
     * automatically remove configuration keys associated with this component if it has been unregistered
     * @param hero The Hero from which the component is being registered
     */
    void onRemove(Hero hero);

    /**
     * Actions to take when a component is saved. The guarantee is made that the given hero has the component
     * @param hero The Hero to which the component is registered
     */
    void onSave(Hero hero);

}
