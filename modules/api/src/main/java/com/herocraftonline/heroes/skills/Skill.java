package com.herocraftonline.heroes.skills;

import com.herocraftonline.heroes.characters.CharacterBase;

/**
 * Skills serve as character metadata that generally performs some sort of action upon the character and its
 * surroundings upon attachment and/or execution.
 */
public interface Skill {
    /**
     * @return The name of the skill
     */
    String getName();

    /**
     * Called when a skill is attached to a character and/or when a character gains access to a skill
     * @param character The character the skill is being attached to
     */
    void init(CharacterBase character);

    /**
     * @param character The character to get a description for
     * @return A description of a given skill specifically for the parameter character
     */
    String getDescription(CharacterBase character);

    /**
     * Called when the skill is executed
     * @param executor The character executing the skill
     * @param args (optional) Any additional arguments
     * @return The {@link com.herocraftonline.heroes.skills.SkillResult} from execution
     */
    SkillResult execute(CharacterBase executor, String[] args);
}
