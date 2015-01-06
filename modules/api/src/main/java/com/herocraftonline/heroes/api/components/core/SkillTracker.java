package com.herocraftonline.heroes.api.components.core;

import com.herocraftonline.heroes.api.skills.SkillRequirement;

import java.util.Collection;

/**
 * Handles tracking what skills are available to the attached character. Information tracked by this component includes
 * what skills are available to a given character and what requirements are needed to execute said skill.
 */
public interface SkillTracker {

    /**
     * Adds a given skill to the accessible skills of this component. This is tracked independently on a character
     * by character basis and is independent and overrides character classes.
     * @param skill The name of the skill to add
     */
    void addSkill(String skill);

    /**
     * @param skill The name of the skill to check
     * @return Whether the skill is available in this component
     */
    boolean hasSkill(String skill);

    /**
     * Removes a given skill to the accessible skills of this component. This is tracked independently on a character
     * by character basis and is independent and overrides character classes.
     * @param skill The name of the skill to remove
     */
    void removeSkill(String skill);

    /**
     * @param skill The name of the skill to check
     * @return A collection of requirements that must be satisfied to
     */
    Collection<SkillRequirement> getRequirements(String skill);


}
