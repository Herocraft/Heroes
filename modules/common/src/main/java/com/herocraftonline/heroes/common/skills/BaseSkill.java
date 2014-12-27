package com.herocraftonline.heroes.common.skills;

import com.herocraftonline.heroes.plugin.HeroesPlugin;
import com.herocraftonline.heroes.skills.Skill;

public abstract class BaseSkill implements Skill {

    private String name;
    private HeroesPlugin plugin;
    private int minArgs;
    private int maxArgs;

    public BaseSkill(HeroesPlugin plugin, String name) {
        this.name = name;
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return name;
    }
}
