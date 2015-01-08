package com.herocraftonline.heroes.common.components;

import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.components.Component;
import com.herocraftonline.heroes.api.components.core.SkillTracker;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;
import com.herocraftonline.heroes.api.skills.SkillRequirement;
import com.herocraftonline.heroes.api.util.Combiner;
import org.spongepowered.api.service.persistence.data.DataView;

import java.util.Collection;

public class ComponentSkills implements Component, SkillTracker {

    @Override
    public boolean cloneOnLoad() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void onInit(HeroesPlugin plugin) {

    }

    @Override
    public void onAttach(CharacterBase character, DataView data) {

    }

    @Override
    public void onRemove(CharacterBase character) {

    }

    @Override
    public DataView onSave(Character character) {
        return null;
    }

    @Override
    public Combiner<DataView> getCombiner() {
        return null;
    }

    @Override
    public Component clone() {
        return new ComponentSkills();
    }

    @Override
    public void addSkill(String skill) {

    }

    @Override
    public boolean hasSkill(String skill) {
        return false;
    }

    @Override
    public void removeSkill(String skill) {

    }

    @Override
    public Collection<SkillRequirement> getRequirements(String skill) {
        return null;
    }
}
