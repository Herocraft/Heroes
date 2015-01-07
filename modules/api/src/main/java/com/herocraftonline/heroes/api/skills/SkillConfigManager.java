package com.herocraftonline.heroes.api.skills;

import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.util.Combiner;
import org.spongepowered.api.service.persistence.data.DataView;

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
     * @param combiner A combiner to resolve conflicting values should they be present in multiple locations for
     *                   this character. A null combiner will result in the first matching setting to be used. The
     *                   global value will always be overridden if a different setting exists and will not be compared
     * @return The user-specific value (identical to global value unless overridden in the character's settings or the
     * settings of the character's class) of the parameter setting for the parameter skill, or the provided default if
     * no such setting exists
     */
    String getCharacterSkillConfig(CharacterBase character, Skill skill, String setting, String def,
                                   Combiner<String> combiner);
    /**
     * @param character The character to get skill setting for
     * @param skill The skill to get configuration for
     * @param path The path to get the DataView from
     * @param def A default value to use/store if no setting is present, can be null
     * @param combiner A combiner to resolve conflicting values should they be present in multiple locations for
     *                   this character. A null combiner will result in the first matching path to be used. The
     *                   global value will always be overridden if a different setting exists and will not be compared
     * @return A DataView (identical to global value unless overridden in the character's settings or the
     * settings of the character's class) located  for the parameter skill, or the provided default if
     * no such setting exists
     */
    DataView getCharacterSkillSection(CharacterBase character, Skill skill, String path, Object def,
                                    Combiner<DataView> combiner);

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
     */
    DataView setCharacterSkillSection(CharacterBase character, Skill skill, String path, DataView data);


}
