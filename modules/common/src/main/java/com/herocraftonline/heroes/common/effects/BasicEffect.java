package com.herocraftonline.heroes.common.effects;

import com.herocraftonline.heroes.characters.CharacterBase;
import com.herocraftonline.heroes.effects.EffectBase;
import com.herocraftonline.heroes.plugin.HeroesPlugin;

public class BasicEffect implements EffectBase {

    protected HeroesPlugin plugin;
    protected String name;

    public BasicEffect(HeroesPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }

    @Override
    public void apply(CharacterBase character) {
        return;
    }

    @Override
    public void remove(CharacterBase character) {
        return;
    }

    @Override
    public String getName() {
        return name;
    }
}
