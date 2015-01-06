package com.herocraftonline.heroes.common.components;

import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.components.Component;
import com.herocraftonline.heroes.api.components.core.CooldownTracker;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;

import java.util.HashMap;

public class ComponentCooldown implements Component, CooldownTracker {

    private HashMap<String, Long> cooldowns;

    @Override
    public Component getFromSettings(Object config) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void onInit(HeroesPlugin plugin) {
        // Do nothing here, we don't want to allocate memory until necessary
    }

    @Override
    public void onAttach(CharacterBase character) {
        cooldowns = new HashMap<String, Long>();
    }

    @Override
    public void onRemove(CharacterBase character) {
        cooldowns = null;
    }

    @Override
    public long addCooldown(String identifier, long duration) {
        long expiry = System.currentTimeMillis() + duration;
        cooldowns.put(identifier, expiry);
        return expiry;
    }

    @Override
    public long clearCooldown(String identifier) {
        return cooldowns.containsKey(identifier) ? cooldowns.remove(identifier) : -1;
    }

    @Override
    public long getCooldown(String identifier) {
        return cooldowns.containsKey(identifier) ? cooldowns.get(identifier) : -1;
    }

    @Override
    public boolean isOnCooldown(String identifier) {
        return cooldowns.containsKey(identifier);
    }

    @Override
    public void setCooldown(String identifier, long timestamp) {
        cooldowns.put(identifier, timestamp);
    }

    @Override
    public long modifyCooldown(String identifier, long amount) {
        long expiry = getCooldown(identifier);
        if (expiry == -1) {
            expiry = System.currentTimeMillis();
        }
        expiry += amount;
        if (expiry > System.currentTimeMillis()) {
            cooldowns.put(identifier, expiry);
            return expiry;
        } else {
            return -1;
        }
    }
}
