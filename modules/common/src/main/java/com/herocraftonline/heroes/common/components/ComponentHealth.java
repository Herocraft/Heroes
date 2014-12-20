package com.herocraftonline.heroes.common.components;

import com.herocraftonline.heroes.characters.CharacterBase;
import com.herocraftonline.heroes.components.Component;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;

public class ComponentHealth implements Component {

    private double maxHealth;
    private double defaultHealth;

    //public ComponentHealth(DataView root) {

    //}

    @Override
    public String getName() {
        return "HEROES_health";
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
