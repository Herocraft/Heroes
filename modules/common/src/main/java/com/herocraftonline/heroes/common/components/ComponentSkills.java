package com.herocraftonline.heroes.common.components;

import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.components.Component;
import com.herocraftonline.heroes.api.components.core.SkillTracker;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;
import com.herocraftonline.heroes.api.skills.SkillRequirement;
import com.herocraftonline.heroes.api.util.Combiner;
import org.spongepowered.api.service.persistence.data.DataView;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class ComponentSkills implements Component, SkillTracker {

    private HashMap<String, DataView> classSkills;
    private HashMap<String, DataView> overrideSkills;
    private HashSet<String> negatedSkills;

    private HeroesPlugin plugin;

    @Override
    public boolean cloneOnLoad() {
        return true;
    }

    @Override
    public String getName() {
        return "skills";
    }

    @Override
    public void onInit(HeroesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onAttach(CharacterBase character, DataView data) {
        this.classSkills = new HashMap<>();
        this.overrideSkills = new HashMap<>();
        this.negatedSkills = new HashSet<>();
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
    public void addSkill(String skill, DataView config) {

    }

    @Override
    public boolean hasSkill(String skill) {
        return false;
    }

    @Override
    public void removeSkill(String skill) {

    }

    @Override
    public void negateSkill(String skill) {

    }

    @Override
    public void unnegateSkill(String skill) {
        if (negatedSkills.contains(skill.toLowerCase())) {
            negatedSkills.remove(skill.toLowerCase());
            recalculateClassSkills();
        }
    }

    private void recalculateClassSkills() {

    }

    @Override
    public boolean isNegated(String skill) {
        return false;
    }

    @Override
    public Collection<SkillRequirement> getRequirements(String skill) {
        return null;
    }
}
