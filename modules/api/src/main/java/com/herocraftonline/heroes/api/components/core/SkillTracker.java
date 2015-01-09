package com.herocraftonline.heroes.api.components.core;

import com.herocraftonline.heroes.api.skills.SkillRequirement;
import org.spongepowered.api.service.persistence.data.DataView;

import java.util.Collection;

/**
 * Handles tracking what skills are available to the attached character. Information tracked by this component includes
 * what skills are available to a given character and what requirements are needed to execute said skill. It is
 * important to note that {@link #addSkill(String, org.spongepowered.api.service.persistence.data.DataView)} and
 * {@link #removeSkill(String)} operate independently from character class configuration
 * and directly on the character to which the tracker is attached - that is to say while the other methods in this
 */
public interface SkillTracker {

    /**
     * Adds a given skill to the accessible skills of this component. This is tracked independently on a character
     * by character basis and is independent and overrides character classes.
     * @param skill The name of the skill to add
     * @param config Any associated configuration for the skill
     */
    void addSkill(String skill, DataView config);

    /**
     * @param skill The name of the skill to check
     * @return Whether the skill is available in this component
     */
    boolean hasSkill(String skill);

    /**
     * Removes a given skill from the accessible skills of this component. Does NOT affect skills inherited from class
     * configurations, use {@link #negateSkill(String)} instead.
     * @param skill The name of the skill to remove
     */
    void removeSkill(String skill);

    /**
     * Marks a given skill as inaccessible to the character to which this tracker is attached, overriding class
     * configuration
     * @param skill The name of the skill to negate
     */
    void negateSkill(String skill);

    /**
     * Removes the negation on a skill from {@link #negateSkill(String)}, if present
     * @param skill The name of the skill to un-negate
     */
    void unnegateSkill(String skill);

    /**
     * @param skill The name of the skill to check
     * @return Whether the skill is negated via {@link #negateSkill(String)}
     */
    boolean isNegated(String skill);

    /**
     * @param skill The name of the skill to check
     * @return A collection of requirements that must be satisfied by the given character to execute the skill
     */
    Collection<SkillRequirement> getRequirements(String skill);


}
