package com.herocraftonline.heroes.common.components;

import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.components.Component;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;

public class ComponentMana implements Component {

    private long currMana;
    private long maxMana;

    @Override
    public Component getFromSettings(Object config) {
        return null; //TODO
    }

    @Override
    public String getName() {
        return "mana";
    }

    @Override
    public void onInit(HeroesPlugin plugin) {

    }

    @Override
    public void onAttach(CharacterBase character) {

    }

    @Override
    public void onRemove(CharacterBase character) {

    }
}
