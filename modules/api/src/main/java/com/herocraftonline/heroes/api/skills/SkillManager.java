package com.herocraftonline.heroes.api.skills;

import java.util.Collection;

public interface SkillManager {

    /**
     * Adds a skill to the manager
     * @param skill The skill to add
     * @throws java.lang.IllegalStateException if skill registration is attempted after registration lock is enabled
     *         (after plugin initialization)
     */
    void addSkill(Skill skill);

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
