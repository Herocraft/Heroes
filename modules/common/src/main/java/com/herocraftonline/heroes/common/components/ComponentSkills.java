package com.herocraftonline.heroes.common.components;

import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.components.Component;
import com.herocraftonline.heroes.api.components.core.SkillTracker;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;
import com.herocraftonline.heroes.api.skills.SkillRequirement;
import com.herocraftonline.heroes.api.util.Combiner;
import org.spongepowered.api.service.persistence.data.DataQuery;
import org.spongepowered.api.service.persistence.data.DataView;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class ComponentSkills implements Component, SkillTracker {

    // Top level keys
    private static String CHARACTER_SPECIFIC_KEY = "character-specific-skills"; // Specific/overriding from character
    private static String GRANT_SKILLS_KEY = "grant"; // Grants the following skills
    private static String REVOKE_SKILLS_KEY = "revoke"; // Revokes the skills contained under the key if inherited

    // Skill level keys
    private static String SKILL_REQUIREMENTS_KEY = "requirements"; // Adds the requirements to the given skill
    private static String SKILL_REMOVE_REQUIREMENTS_KEY = "remove-requirements"; // Removes the requirements under this
                                                                                 // key if inherited
    private static String SKILL_CONFIGURATION_KEY = "config"; // Skill configuration settings

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
        return SkillDataCombiner.instance;
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

    private static class SkillDataCombiner implements Combiner<DataView> {

        public static SkillDataCombiner instance;

        static {
            instance = new SkillDataCombiner();
        }

        /*
         * Overall logic flow
         * 1. Make clone of lower priority (being inherited from)
         * 2. Copy higher priority granted skills, inheriting settings from lower priority if not present
         * 3. Remove higher priority revoked skills
         * 4. Remove higher priority removed requirements
         * 5. Process (but do not remove keys of) character-specific override skills if present
         *
         * Redundant if calls are made below for the sake of workflow clarity and code legibility - do not merge
         */
        @Override
        public DataView combine(DataView o1, DataView o2) {
            DataView ret = null; //TODO
            // Define global queries/keys
            DataQuery skillRemoval = new DataQuery(REVOKE_SKILLS_KEY);
            DataQuery skillGrant = new DataQuery(GRANT_SKILLS_KEY);
            DataQuery removeRequirementsQuery = new DataQuery(SKILL_REMOVE_REQUIREMENTS_KEY);
            DataQuery requirementsQuery = new DataQuery(SKILL_REQUIREMENTS_KEY);
            /*
             * Populate with lower priority first - It is assumed that lower priority views will not have removal/revoke
             * keys as the combiner should have processed it on a previous iteration and subsequently removed it
             * and a top level configuration would not contain any revoke/removal clauses
             */
            for (Entry<DataQuery, Object> entry : o2.getValues(true).entrySet()) {
                ret.set(entry.getKey(), entry.getValue());
            }
            // Copy in higher priority values, overwriting any previous values
            for (Entry<DataQuery, Object> entry : o1.getValues(true).entrySet()) {
                ret.set(entry.getKey(), entry.getValue());
            }
            // Process skill removals
            if (ret.contains(skillRemoval) && ret.contains(skillGrant)) {
                DataView grantView = ret.getView(skillGrant).get();
                DataView removalView = ret.getView(skillRemoval).get();
                for (DataQuery skillQuery : removalView.getKeys(false)) {
                    grantView.remove(skillQuery);
                }
            }
            ret.remove(skillRemoval);
            // Process requirement removals
            if (ret.contains(skillGrant)) {
                DataView grantView = ret.getView(skillGrant).get();
                for (DataQuery skillQuery : grantView.getKeys(false)) {
                    DataView skillView = grantView.getView(skillQuery).get();
                    if (skillView.contains(requirementsQuery) && skillView.contains(removeRequirementsQuery)) {
                        DataView requirementsView = skillView.getView(requirementsQuery).get();
                        for (DataQuery removalQuery : skillView.getView(removeRequirementsQuery).get().getKeys(false)) {
                            requirementsView.remove(removalQuery);
                        }
                    }
                }
            }
            // Cleanup finished removals
            ret.remove(skillRemoval);
            if (ret.contains(skillGrant)) {
                DataView grantView = ret.getView(skillGrant).get();
                for (DataQuery skillQuery : grantView.getKeys(false)) {
                    DataView skillView = grantView.getView(skillQuery).get();
                    if (skillView.contains(removeRequirementsQuery)) {
                        skillView.remove(removeRequirementsQuery);
                    }
                }
            }
            // TODO process character specific overrides
            return ret;
        }
    }
}
