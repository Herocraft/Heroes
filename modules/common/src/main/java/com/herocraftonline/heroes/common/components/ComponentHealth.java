package com.herocraftonline.heroes.common.components;

import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.components.Component;
import com.herocraftonline.heroes.api.components.core.Health;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;

public class ComponentHealth implements Component, Health {

    private double maxHealth;
    private double defaultHealth;


    @Override
    public Component getFromSettings(Object config) { //TODO

        return null;
    }

    @Override
    public String getName() {
        return "health";
    }

    @Override
    public void onInit(HeroesPlugin plugin) {

    }

    @Override
    public void onAttach(CharacterBase character) {
        Entity e = character.getEntity();
        if (e == null || !(e instanceof Living)) {
            throw new UnsupportedOperationException("Cannot modify health of an inanimate character");
        }
        Living l = (Living)e;
        defaultHealth = l.getMaxHealth();
        double ratio = l.getHealth()/l.getMaxHealth();
        l.setMaxHealth(maxHealth);
        l.setHealth(l.getMaxHealth()*ratio);
    }

    @Override
    public void onRemove(CharacterBase character) {
        Living l = (Living)character.getEntity();
        double ratio = l.getHealth()/l.getMaxHealth();
        l.setMaxHealth(defaultHealth);
        l.setHealth(defaultHealth * ratio);
    }
}
