package com.herocraftonline.heroes.components.event;

import com.herocraftonline.heroes.components.ComponentManager;
import org.spongepowered.api.util.event.Event;

/**
 * This event is called when Heroes is accepting registrations for component definitions, prior to loading Character
 * Classes. Component registration must be done when this event is called - component registrations are not accepted at
 * any other point within the plugin's runtime for consistency reasons.
 */
public interface ComponentRegistrationEvent extends Event {
    /**
     * @return The component manager with which components can be registered
     */
    ComponentManager getComponentManager();
}
