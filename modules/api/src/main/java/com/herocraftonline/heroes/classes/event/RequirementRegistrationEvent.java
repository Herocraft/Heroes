package com.herocraftonline.heroes.classes.event;

import com.herocraftonline.heroes.classes.CharacterClassManager;
import org.spongepowered.api.util.event.Event;

/**
 * This event is called when Heroes is accepting registrations for requirement definitions, prior to loading Character
 * Classes. Requirement registration must be done when this event is called.
 */
public interface RequirementRegistrationEvent extends Event {
    /**
     * @return The CharacterClassManager where requirements can be registered
     */
    CharacterClassManager getClassManager();
}
