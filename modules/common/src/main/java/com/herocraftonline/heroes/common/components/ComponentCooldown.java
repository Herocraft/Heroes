package com.herocraftonline.heroes.common.components;

import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.components.Component;
import com.herocraftonline.heroes.api.components.core.CooldownTracker;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;
import com.herocraftonline.heroes.api.util.Combiner;
import org.spongepowered.api.service.persistence.data.DataQuery;
import org.spongepowered.api.service.persistence.data.DataView;

import java.util.HashMap;

public class ComponentCooldown implements Component, CooldownTracker {

    private HashMap<String, Long> cooldowns;

    @Override
    public boolean cloneOnLoad() {
        return true;
    }

    @Override
    public String getName() {
        return "cooldowns";
    }

    @Override
    public void onInit(HeroesPlugin plugin) {

    }

    @Override
    public void onAttach(CharacterBase character, DataView data) {
        this.cooldowns = new HashMap<>();
        for (DataQuery skill : data.getKeys(false)) {
            cooldowns.put(skill.getParts().get(0), data.getLong(skill).or(-1L));
        }
        return;
    }

    @Override
    public void onRemove(CharacterBase character) {
        cooldowns = null;
    }

    @Override
    public DataView onSave(Character character) {
        return null; // TODO pending further persistence API
    }

    @Override
    //TODO
    public Combiner<DataView> getCombiner() {
        return null;
    }

    @Override
    public Component clone() {
        return new ComponentCooldown();
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
