package com.herocraftonline.heroes.common.characters.living;

import com.herocraftonline.heroes.api.characters.Hero;
import com.herocraftonline.heroes.api.components.core.ClassTracker;
import com.herocraftonline.heroes.api.components.core.CooldownTracker;
import com.herocraftonline.heroes.api.components.core.HealthTracker;
import com.herocraftonline.heroes.api.components.core.ManaTracker;
import com.herocraftonline.heroes.api.components.core.SkillTracker;
import com.herocraftonline.heroes.common.characters.CharacterCommon;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.service.persistence.data.DataView;
import org.spongepowered.api.world.Location;

import java.util.UUID;

public class HeroCommon extends CharacterCommon implements Hero {

    private final Player player;

    public HeroCommon(Player player) {
        this.player = player;
    }

    @Override
    public Entity getEntity() {
        return player;
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
    public Player getPlayer() {
        return player
                ;
    }

    @Override
    public UUID getUID() {
        return player.getUniqueId();
    }

    @Override
    public DataView getCharacterComponentData(String name) {
        return null;
    }


}
