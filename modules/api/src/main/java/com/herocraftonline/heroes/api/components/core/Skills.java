package com.herocraftonline.heroes.api.components.core;

import com.herocraftonline.heroes.api.skills.Skill;

/**
 * Handles tracking what skills are available to the attached character. Information tracked by this component includes
 * what skills are available to a given character and what requirements are needed to execute said skill.
 *
 * TODO: move requirement tracking here out of skill configs
 */
public interface Skills {
    /**
     * @param skill The skill to check
     * @return Whether the skill is available in this component
     */
    boolean hasSkill(Skill skill);
}
