package com.herocraftonline.heroes.common.characters;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.classes.CharacterClass;
import com.herocraftonline.heroes.api.classes.CharacterClassTrait;
import com.herocraftonline.heroes.api.components.Component;
import com.herocraftonline.heroes.api.components.core.ClassTracker;
import com.herocraftonline.heroes.api.components.core.CooldownTracker;
import com.herocraftonline.heroes.api.components.core.HealthTracker;
import com.herocraftonline.heroes.api.components.core.ManaTracker;
import com.herocraftonline.heroes.api.components.core.SkillTracker;
import com.herocraftonline.heroes.api.effects.EffectBase;
import com.herocraftonline.heroes.api.util.Combiner;
import org.spongepowered.api.service.persistence.data.DataView;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public abstract class CharacterCommon implements CharacterBase {
    private final Map<String, Component> components;
    private final Map<String, EffectBase> effects;

    public CharacterCommon() {
        components = new HashMap<>();
        effects = new HashMap<>();
    }

    @Override
    public void addEffect(EffectBase effect) {
        effect.apply(this);
        effects.put(effect.getName().toLowerCase(), effect);
    }

    @Override
    public EffectBase getEffect(String name) {
        return effects.get(name.toLowerCase());
    }

    @Override
    public Collection<EffectBase> getEffects() {
        return Collections.unmodifiableCollection(effects.values());
    }

    @Override
    public boolean hasEffect(String name) {
        return effects.containsKey(name.toLowerCase());
    }

    @Override
    public EffectBase removeEffect(String name) {
        return effects.remove(name.toLowerCase());
    }

    @Override
    public boolean hasComponent(String name) {
        return components.containsKey(name);
    }

    @Override
    public Component getComponent(String name) {
        return components.get(name);
    }

    @Override
    public boolean registerComponent(Component component) {
        if (!components.containsKey(component.getName())) {
            component.onAttach(this, getCombinedDataView(component));
            components.put(component.getName(), component);
            return true;
        } else {
            Heroes.getInstance().getLogger().log(Level.WARNING, "Duplicate component registration "
                    + component.getName() + " attempted, skipping!");
            return false;
        }
    }

    protected DataView getCombinedDataView(Component component) {
        ClassTracker tracker = getClassTracker();
        DataView ret = null; // TODO empty data view
        // Legacy support for primary/secondary class hierarchy
        Collection<CharacterClass> primaryClasses = tracker.getClassesWithTrait(CharacterClassTrait.PRIMARY);
        Collection<CharacterClass> secondaryClasses = tracker.getClassesWithTrait(CharacterClassTrait.SECONDARY);
        Combiner<DataView> combiner = component.getCombiner();
        boolean flag = false;
        if (!primaryClasses.isEmpty()) {
            // The guarantee is made that there will be only one active
            CharacterClass[] primary = primaryClasses.toArray(new CharacterClass[1]);
            DataView primaryView = primary[0].getComponentSettings(component.getName());
            // Secondary classes are only checked with a given priority if a primary class exists
            if (!secondaryClasses.isEmpty()) {
                CharacterClass[] secondary = secondaryClasses.toArray(new CharacterClass[1]);
                DataView secondaryView = secondary[0].getComponentSettings(component.getName());
                ret = combiner.combine(primaryView, secondaryView);
            }
            flag = true;
        }
        // Process/combine settings for everything else
        for (CharacterClass cClass : tracker.getActiveClasses()) {
            if (flag) {
                if (cClass.hasTrait(CharacterClassTrait.PRIMARY) || cClass.hasTrait(CharacterClassTrait.SECONDARY)) {
                    continue; // Skip - processed already
                }
            }
            ret = combiner.combine(ret, cClass.getComponentSettings(component.getName()));
            // ret first so primary/secondary are marked as higher priority
        }
        // Combine character specific data
        return combiner.combine(getCharacterComponentData(component.getName()), ret);
    }


    @Override
    public Component unregisterComponent(String name) {
        Component removed = components.remove(name);
        if (removed != null) {
            removed.onRemove(this);
        }
        return removed;
    }

    //TODO
    @Override
    public HealthTracker getHealthTracker() {
        return null;
    }

    @Override
    public CooldownTracker getCooldownTracker() {
        return null;
    }

    @Override
    public ManaTracker getManaTracker() {
        return null;
    }

    @Override
    public SkillTracker getSkillTracker() {
        return null;
    }

    @Override
    public ClassTracker getClassTracker() {
        return null;
    }
}
