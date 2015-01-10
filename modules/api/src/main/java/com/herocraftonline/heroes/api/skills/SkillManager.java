package com.herocraftonline.heroes.api.skills;

import org.spongepowered.api.GameState;

import java.util.Collection;

public interface SkillManager {

    /**
     * Registers the skill with this skill manager, must be called during {@link GameState#INITIALIZATION}
     * Registration is required for a skill to be usable/identifiable if said registration is not located
     * within a JAR file inside the skill data folder as defined in the configuration
     * @param skill The class of the skill to register
     * @throws java.lang.IllegalStateException if this method is called during any other GameState than INITIALIZATION
     * @throws java.lang.IllegalArgumentException if the parameter class fails instantiation tests or does not meet
     *         other requirements
     */
    void registerSkill(Class<? extends Skill> skill);

    /**
     * @param name The name of the skill
     * @return true if a skill matching the provided name exists and is registered with this manager
     */
    boolean isSkill(String name);

    /**
     * @param name The name of the skill
     * @return The skill, if it exists
     */
    Skill getSkill(String name);

    /**
     * @return An immutable copy of currently registered skills
     */
    Collection<Skill> getSkills();

    /**
     * Removes a skill from this manager (and all associated identifiers)
     * @param name Name of the skill to remove
     * @return The removed skill, or null if not found
     */
    Skill removeSkill(String name);

}
