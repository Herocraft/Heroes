package com.herocraftonline.heroes.common.components;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.components.Component;
import com.herocraftonline.heroes.api.components.core.HealthTracker;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;
import com.herocraftonline.heroes.api.util.Combiner;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.service.persistence.data.DataQuery;
import org.spongepowered.api.service.persistence.data.DataView;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ComponentHealth implements Component, HealthTracker {

    private static String COMBINE_MODE = "health-combine-mode";

    private static String BASE_HEALTH_KEY = "base-health";
    private static String HEALTH_PER_LEVEL_KEY = "health-per-level";
    private static String HEALTH_BOOST_KEY = "health-boosts";
    private static String TIER = "tier";

    private static String BOOST_AMOUNT = "amount";
    private static String BOOST_EXPIRY = "expires";

    private Living living;
    private Double baseHealth;
    private Double healthPerLevel;
    private Map<String, BoostData> boosts;

    private HeroesPlugin plugin;


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
        this.plugin = plugin;
    }

    @Override
    public void onAttach(CharacterBase character, DataView data) {
        Entity e = character.getEntity();
        if (e == null || !(e instanceof Living)) {
            throw new UnsupportedOperationException("Cannot modify health of an inanimate character");
        }
        // Populate fields with data
        baseHealth = data.getDouble(new DataQuery(BASE_HEALTH_KEY)).orNull();
        if (baseHealth == null) {
            throw new IllegalArgumentException("A base health must be defined for the health component");
        }
        healthPerLevel = data.getDouble(new DataQuery(HEALTH_PER_LEVEL_KEY)).or(0.0);
        boosts = new HashMap<>();
        DataView view = data.getView(new DataQuery(HEALTH_BOOST_KEY)).orNull();
        if (view != null) {
            for (DataQuery boostQuery : view.getKeys(false)) {
                String boostName = boostQuery.asString(".");
                DataView boostView = view.getView(boostQuery).get();
                double amount = boostView.getDouble(new DataQuery(BOOST_AMOUNT)).or(0.0);
                long expiry = boostView.getLong(new DataQuery(BOOST_EXPIRY)).or(System.currentTimeMillis());
                boosts.put(boostName, new BoostData(amount, expiry));
            }
        }
        double health = baseHealth;
        health += character.getClassTracker().getHighestLevel(character.getClassTracker().getActiveClasses())
                * healthPerLevel;
        long currSysTime = System.currentTimeMillis();
        for (Entry<String, BoostData> entry : boosts.entrySet()) {
            BoostData bData = entry.getValue();
            if (bData.getExpiry() > currSysTime) {
                health += bData.getAmount();
            }
        }
        if (health <= 0) {
            throw new IllegalArgumentException("Health component cannot have a calculated maximum health <= 0");
        }
        living = (Living)e;
        double defaultHealth = living.getMaxHealth();
        // TODO check defaultHealth against vanilla values to prevent overwrites -- compatibility with other plugins
        // Make sure we are always overriding players
        double ratio = living.getHealth()/living.getMaxHealth();
        living.setMaxHealth(health);
        living.setHealth(living.getMaxHealth() * ratio);
    }

    @Override
    public void onRemove(CharacterBase character) {
        // At the moment we don't clean up after ourselves upon removal - does this matter as it is only likely to
        // be called upon plugin shutdown? Would be considered something that might be essential for successfully
        // uninstalling the plugin anyways
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

    private static class BoostData {
        private Double amount;
        private Long expiry;

        public BoostData(Double amount, Long expiry) {
            this.amount = amount;
            this.expiry = expiry;
        }

        public Double getAmount() {
            return amount;
        }

        public Long getExpiry() {
            return expiry;
        }
    }

    private static class HealthDataCombiner implements Combiner<DataView> {

        private enum CombineMode {
            LARGER,
            LOWER,
            TIER,
            COMBINE;
        }

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
            // Compare selected values only, and return config of the two
            CombineMode mode = CombineMode.valueOf(Heroes.getInstance().getConfigManager().getConfig(COMBINE_MODE));
            if (mode == null) { // Silent default to combine
                mode = CombineMode.COMBINE;
            }
            switch (mode) {
                case LARGER: {
                    DataQuery base = new DataQuery(BASE_HEALTH_KEY);
                    double health1 = o1.getDouble(base).or(0D);
                    double health2 = o2.getDouble(base).or(0D);
                    ret.set(base, health1 > health2 ? health1 : health2);
                    DataQuery perLevel = new DataQuery(HEALTH_PER_LEVEL_KEY);
                    double pLevel1 = o1.getDouble(perLevel).or(0D);
                    double pLevel2 = o2.getDouble(perLevel).or(0D);
                    ret.set(perLevel, pLevel1 > pLevel2 ? pLevel1 : pLevel2);
                    break;
                }
                case LOWER: {
                    DataQuery base = new DataQuery(BASE_HEALTH_KEY);
                    double health1 = o1.getDouble(base).or(0D);
                    double health2 = o2.getDouble(base).or(0D);
                    ret.set(base, health1 < health2 ? health1 : health2);
                    DataQuery perLevel = new DataQuery(HEALTH_PER_LEVEL_KEY);
                    double pLevel1 = o1.getDouble(perLevel).or(0D);
                    double pLevel2 = o2.getDouble(perLevel).or(0D);
                    ret.set(perLevel, pLevel1 < pLevel2 ? pLevel1 : pLevel2);
                    break;
                }
                case TIER: {
                    DataQuery tier = new DataQuery(TIER);
                    int tier1 = o1.getInt(tier).or(-1);
                    int tier2 = o2.getInt(tier).or(-1);
                    ret.set(tier, tier1 > tier2 ? tier1 : tier2);
                    DataQuery base = new DataQuery(BASE_HEALTH_KEY);
                    DataQuery perLevel = new DataQuery(HEALTH_PER_LEVEL_KEY);
                    if (tier1 >= tier2) { // If tiers match we use what heroes internally determines to be higher
                        double health1 = o1.getDouble(base).or(0D);
                        ret.set(base, health1);
                        double pLevel1 = o1.getDouble(perLevel).or(0D);
                        ret.set(perLevel, pLevel1);
                    } else {
                        double health2 = o2.getDouble(base).or(0D);
                        ret.set(base, health2);
                        double pLevel2 = o2.getDouble(perLevel).or(0D);
                        ret.set(perLevel, pLevel2);
                    }
                    break;
                }
                case COMBINE: {
                    DataQuery base = new DataQuery(BASE_HEALTH_KEY);
                    double health1 = o1.getDouble(base).or(0D);
                    double health2 = o2.getDouble(base).or(0D);
                    ret.set(base, health1 + health2);
                    DataQuery perLevel = new DataQuery(HEALTH_PER_LEVEL_KEY);
                    double pLevel1 = o1.getDouble(perLevel).or(0D);
                    double pLevel2 = o2.getDouble(perLevel).or(0D);
                    ret.set(perLevel, pLevel1 + pLevel2);
                    break;
                }
            }
            return ret;
        }
    }
}
