package com.herocraftonline.heroes.common.components;

import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.components.Component;
import com.herocraftonline.heroes.api.components.core.Health;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;

public class ComponentHealth implements Component, Health {

    private Living living;
    private double maxHealthSetting;
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
        living = (Living)e;
        defaultHealth = living.getMaxHealth();
        double ratio = living.getHealth()/living.getMaxHealth();
        living.setMaxHealth(maxHealthSetting);
        living.setHealth(living.getMaxHealth() * ratio);
    }

    @Override
    public void onRemove(CharacterBase character) {
        if (living.getHealth() > 0) {
            double ratio = living.getHealth() / living.getMaxHealth();
            living.setMaxHealth(defaultHealth);
            living.setHealth(defaultHealth * ratio);
        }
        living = null;
    }

    @Override
    public double getMaxHealth() {
        return living.getMaxHealth();
    }

    @Override
    public double getHealth() {
        return living.getHealth();
    }

    @Override
    public void setHealth(double amount) {
        if (amount > living.getMaxHealth()) {
            throw new IllegalArgumentException("Health must be less than the set maximum health value of " + living
                    .getMaxHealth());
        }
        living.setHealth(amount);
    }

    @Override
    public void setMaxHealth(double amount) {
        living.setMaxHealth(amount);
    }
}
