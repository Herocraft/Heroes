package com.herocraftonline.heroes.common.effects;

import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.effects.EffectBase;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;

public class BasicEffect implements EffectBase {

    protected HeroesPlugin plugin;
    protected String name;
    protected CharacterBase character;

    public BasicEffect(HeroesPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }

    @Override
    public void apply(CharacterBase character) {
        this.character = character;
        return;
    }

    @Override
    public void remove(CharacterBase character) {
        this.character = null;
        return;
    }

    @Override
    public CharacterBase getCharacter() {
        return this.character;
    }

    @Override
    public String getName() {
        return name;
    }
}
