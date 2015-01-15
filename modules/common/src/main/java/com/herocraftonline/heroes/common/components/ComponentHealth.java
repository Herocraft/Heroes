package com.herocraftonline.heroes.common.components;

import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.components.Component;
import com.herocraftonline.heroes.api.components.core.HealthTracker;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;
import com.herocraftonline.heroes.api.util.Combiner;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.service.persistence.data.DataQuery;
import org.spongepowered.api.service.persistence.data.DataView;

import java.util.Map.Entry;

public class ComponentHealth implements Component, HealthTracker {

    private static String BASE_HEALTH_KEY = "base-health";
    private static String HEALTH_PER_LEVEL_KEY = "health-per-level";
    private static String HEALTH_BOOST_KEY = "health-boosts";

    private Living living;
    private double maxHealthSetting;
    private double defaultHealth;


    @Override
    public boolean cloneOnLoad() {
        return true;
    }

    @Override
    public String getName() {
        return "health";
    }

    @Override
    public void onInit(HeroesPlugin plugin) {

    }

    @Override
    public void onAttach(CharacterBase character, DataView data) {
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
    public DataView onSave(Character character) {
        return null;
    }

    @Override
    public Combiner<DataView> getCombiner() {
        return HealthDataCombiner.instance;
    }

    @Override
    public Component clone() {
        return null;
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

    private static class HealthDataCombiner implements Combiner<DataView> {

        public static HealthDataCombiner instance;

        static {
            instance = new HealthDataCombiner();
        }

        @Override
        public DataView combine(DataView o1, DataView o2) {
            DataView ret = null; //TODO
            // Combine health boosts
            DataQuery boosts = new DataQuery(HEALTH_BOOST_KEY);
            DataView retBoosts = ret.createView(boosts);
            if (o2.contains(boosts)) {
                for(Entry<DataQuery, Object> entry : o2.getView(boosts).get().getValues(true).entrySet()) {
                    retBoosts.set(entry.getKey(), entry.getValue());
                }
            }
            if (o1.contains(boosts)) {
                for(Entry<DataQuery, Object> entry : o1.getView(boosts).get().getValues(true).entrySet()) {
                    retBoosts.set(entry.getKey(), entry.getValue());
                }
            }
            // Compare selected values only, and return config of the two TODO

            return ret;
        }
    }
}
