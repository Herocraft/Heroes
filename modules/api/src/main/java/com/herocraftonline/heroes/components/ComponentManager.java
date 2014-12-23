package com.herocraftonline.heroes.components;

/**
 * Acts as a base repository from which component instances can be registered/created/cloned as appropriate.
 */
public interface ComponentManager {
    /**
     * Registers the component with this component manager
     * @param component The component to register
     */
    void registerComponent(Component component);

    /**
     * Gets a base component instance with no data values passed in
     * @param name The name of the component, case-sensitive
     * @param <T> The specific class of the component being retrieved
     * @return The component, or null if no component with a matching name is registered.
     */
    <T extends Component> T getComponent(String name);
}
