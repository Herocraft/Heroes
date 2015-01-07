package com.herocraftonline.heroes.api.skills;

import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.util.Combiner;
import org.spongepowered.api.service.persistence.data.DataView;

/**
 * Represents a condition that must be met before a skill can be executed by a specific character. Implementations
 * must be registered with the skill manager and have an empty constructor
 */
public interface SkillRequirement {

    /**
     * Initializes the requirement.
     * @param data A DataView representation of the data for the character this requirement is being initialized for
     *             conflicts will be resolved by {@link #combiner()}
     */
    void init(DataView data);

    /**
     * @return A combiner that resolves multiple DataViews being present for the same requirement
     */
    Combiner<DataView> combiner();

    /**
     * Checks whether a given character satisfies this requirement
     * @param character The character being checked
     * @return A SkillResult representing requirement satisfaction. Values other than {@link SkillResult#NORMAL} will
     * result in a halt to skill execution.
     */
    SkillResult satisfiesRequirement(CharacterBase character);
}
