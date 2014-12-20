package com.herocraftonline.heroes.characters;

import org.spongepowered.api.entity.player.Player;

public interface Hero extends CharacterBase {

    /**
     * Functionally the same as {@link #getEntity()} without up-casting
     * @return The player represented by this Hero object.
     */
    public Player getPlayer();

}
