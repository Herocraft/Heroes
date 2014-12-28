package com.herocraftonline.heroes.api.skills;

import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;

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
     * Called when a skill is instantiated by the main plugin
     * @param plugin The heroes plugin instance instantiating the skill
     */
    void onInit(HeroesPlugin plugin);

    /**
     * Called when a skill is attached to a character and/or when a character gains access to a skill
     * @param character The character the skill is being attached to
     */
    void onAttach(CharacterBase character);

    /**
     * <p>Identifiers are used to determine what skill should be executed when {@code /skill <identifier>}
     * is used.</p>
     * <p>For example, a return value of {@code {"fireball","fball","fire ball"}}</p> would allow
     * {@code /skill fireball}, {@code /skill fball}, and {@code /skill fire ball} to be used to execute this skill
     *
     * @return An array of all identifiers for this skill, non case-sensitive, can be multiple words
     */
    String[] getIdentifiers();

    /**
     * @param character The character to get a description for
     * @return A description of a given skill specifically for the parameter character
     */
    String getDescription(CharacterBase character);

    /**
     * Called when the skill is executed
     * @param executor The character executing the skill
     * @param args (optional) Any additional arguments
     * @return The {@link com.herocraftonline.heroes.api.skills.SkillResult} from execution
     */
    SkillResult execute(CharacterBase executor, String[] args);


}
