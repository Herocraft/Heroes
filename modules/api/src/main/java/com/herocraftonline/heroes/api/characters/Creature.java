package com.herocraftonline.heroes.api.characters;

import org.spongepowered.api.entity.living.Living;

public interface Creature extends CharacterBase {
    /**
     * Functionally the same as {@link #getEntity()} without up-casting
     * @return The living entity represented by this Creature object.
     */
    public Living getCreature();
}
