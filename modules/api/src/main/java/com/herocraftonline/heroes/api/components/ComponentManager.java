package com.herocraftonline.heroes.api.components;

import org.spongepowered.api.GameState;

/**
 * Acts as a base repository from which component instances can be registered/created/cloned as appropriate.
 */
public interface ComponentManager {
    /**
     * Registers the component with this component manager, must be called during {@link GameState#INITIALIZATION}
     * Registration is required for a component to be attached/identifiable if said registration is not located
     * within a JAR file inside the component data folder as defined in the configuration
     * @param component The class of the component to register
     * @throws java.lang.IllegalStateException if this method is called during any other GameState than INITIALIZATION
     * @throws java.lang.IllegalArgumentException if the parameter class fails instantiation tests or does not meet
     *         other requirements
     */
    void registerComponent(Class<? extends Component> component);

    /**
     * Gets a base component instance with no data values passed in
     * @param name The name of the component, non case-sensitive
     * @param <T> The specific class of the component being retrieved
     * @return The component, or null if no component with a matching name is registered.
     */
    <T extends Component> T getComponent(String name);

    /**
     * Removes the component from this component manager, must be called during {@link GameState#INITIALIZATION}
     * @param name The name of the component, non case-sensitive
     * @param <T> The specific class of the component being removed
     * @return The removed component, or null if no component found
     * @throws java.lang.IllegalStateException if this method is called during any other GameState than INITIALIZATION
     */
    <T extends Component> T removeComponent(String name);
}
