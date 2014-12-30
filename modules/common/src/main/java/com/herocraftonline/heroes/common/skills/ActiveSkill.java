package com.herocraftonline.heroes.common.skills;

import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;
import com.herocraftonline.heroes.api.skills.Skill;
import com.herocraftonline.heroes.api.skills.SkillResult;

/**
 * Active skills are skills that can be executed - i.e. that perform a certain series of actions upon execution.
 * This class will do such things as cooldown/requirement checks and call appropriate events - extensions of this class
 * define what actions to take upon execution in {@link #run(CharacterBase, String[])}
 */
public abstract class ActiveSkill implements Skill {
    protected HeroesPlugin plugin;

    @Override
    public void onInit(HeroesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public SkillResult execute(CharacterBase character, String[] args) {
        //TODO
        return SkillResult.NORMAL;
    }

    /**
     * Define what to do when the active skill is executed
     * @param character The character running the command
     * @param args Skill arguments used
     * @return The result from skill execution
     */
    public abstract SkillResult run(CharacterBase character, String[] args);
}
