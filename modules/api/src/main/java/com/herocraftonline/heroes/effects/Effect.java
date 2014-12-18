package com.herocraftonline.heroes.effects;

import com.herocraftonline.heroes.characters.Character;
import com.herocraftonline.heroes.characters.Creature;
import com.herocraftonline.heroes.characters.Hero;

public interface Effect {

    /**
     *
     * @param character
     */
    void apply(Character character);
    void applyToHero(Hero hero);
    void applyToCreature(Creature creature);
}
