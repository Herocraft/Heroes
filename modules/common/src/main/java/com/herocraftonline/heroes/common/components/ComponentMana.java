package com.herocraftonline.heroes.common.components;

import com.herocraftonline.heroes.characters.CharacterBase;
import com.herocraftonline.heroes.components.Component;

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
    public void onAttach(CharacterBase character) {

    }

    @Override
    public void onRemove(CharacterBase character) {

    }
}
