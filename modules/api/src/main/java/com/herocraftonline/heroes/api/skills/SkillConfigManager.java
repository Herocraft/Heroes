package com.herocraftonline.heroes.api.skills;

import com.herocraftonline.heroes.api.characters.CharacterBase;

import java.util.Comparator;

/**
 * Responsible for loading and retrieving data and settings from configuration and characters
 */
public interface SkillConfigManager {

    /**
     * The running skill config manager instance, provided for convenience
     */
    static SkillConfigManager i = null;

    /**
     * @param skill The skill to get configuration for
     * @param setting The setting to check for
     * @param def A default value to use/store if no setting is present, can be null
     * @return The global value of the parameter setting for the parameter skill, or the provided default if no such
     * setting exists
     */
    String getSkillConfig(Skill skill, String setting, String def);

    /**
     * @param character The character to get skill setting for
     * @param skill The skill to get configuration for
     * @param setting The setting to check for
     * @param def A default value to use/store if no setting is present, can be null
     * @param comparator A comparator to resolve conflicting values should they be present in multiple locations for
     *                   this character. A null comparator will result in the first matching setting to be used. The
     *                   global value will always be overridden if a different setting exists and will not be compared
     * @return The user-specific value (identical to global value unless overridden in the character's settings or the
     * settings of the character's class) of the parameter setting for the parameter skill, or the provided default if
     * no such setting exists
     */
    String getCharacterSkillConfig(CharacterBase character, Skill skill, String setting, String def,
                                   Comparator<String> comparator);
    /**
     * @param character The character to get skill setting for
     * @param skill The skill to get configuration for
     * @param path The path to get the DataView from
     * @param def A default value to use/store if no setting is present, can be null
     * @param comparator A comparator to resolve conflicting values should they be present in multiple locations for
     *                   this character. A null comparator will result in the first matching path to be used. The
     *                   global value will always be overridden if a different setting exists and will not be compared
     * @return A DataView (identical to global value unless overridden in the character's settings or the
     * settings of the character's class) located  for the parameter skill, or the provided default if
     * no such setting exists
     * TODO: Object -> DataView
     */
    Object getCharacterSkillSection(CharacterBase character, Skill skill, String path, Object def,
                                    Comparator<Object> comparator);

    /**
     * Saves a given data value to a given path within a character's skill settings
     * @param character The character to save the skill setting for
     * @param skill The skill to save the setting for
     * @param setting The name of the setting
     * @param data The data to save, null will cause removal of setting if it existed
     * @return The previous value if present, or null
     */
    String setCharacterSkillConfig(CharacterBase character, Skill skill, String setting, String data);

    /**
     * Saves the given data view to a given path within a character's skill settings
     * @param character The character to save the skill setting for
     * @param skill The skill to save the setting for
     * @param path The path of the DataView
     * @param data The data to save, null will cause removal of setting if it existed
     * @return The previous value if present, or null
     * TODO: Object->DataView
     */
    Object setCharacterSkillSection(CharacterBase character, Skill skill, String path, Object data);


}
