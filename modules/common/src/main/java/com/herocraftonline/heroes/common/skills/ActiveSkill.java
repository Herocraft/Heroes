package com.herocraftonline.heroes.common.skills;

import com.herocraftonline.heroes.characters.CharacterBase;
import com.herocraftonline.heroes.plugin.HeroesPlugin;
import com.herocraftonline.heroes.skills.SkillResult;

public abstract class ActiveSkill extends BaseSkill {

    public ActiveSkill(HeroesPlugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    public void init(CharacterBase character) {

    }

    @Override
    public String[] getIdentifiers() {
        return new String[0];
    }

    @Override
    public String getDescription(CharacterBase character) {
        return null;
    }

    @Override
    public SkillResult execute(CharacterBase executor, String[] args) {
        return null;
    }
}
