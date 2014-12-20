package com.herocraftonline.heroes.common.characters.living;

import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.common.characters.CharacterCommon;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.world.Location;

import java.util.UUID;

public class HeroCommon extends CharacterCommon implements Hero {

    private final Player player;

    public HeroCommon(Player player) {
        this.player = player;
    }

    @Override
    public Location getLocation() {
        return player.getLocation();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public UUID getUID() {
        return player.getUniqueId();
    }
}
