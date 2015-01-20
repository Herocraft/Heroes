package com.herocraftonline.heroes.common.characters.living;

import com.herocraftonline.heroes.api.characters.Creature;
import com.herocraftonline.heroes.common.characters.CharacterCommon;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.service.persistence.data.DataView;
import org.spongepowered.api.world.Location;

import java.util.UUID;

public class CreatureCommon extends CharacterCommon implements Creature {

    private final Living entity;

    public CreatureCommon(Living entity) {
        this.entity = entity;
    }

    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public Location getLocation() {
        return entity.getLocation();
    }

    @Override
    public String getName() {
        return entity.getCustomName() == null ? entity.getCustomName() : entity.getType().getId();
    }

    @Override
    public UUID getUID() {
        return entity.getUniqueId();
    }

    @Override
    public DataView getCharacterComponentData(String name) {
        return null;
    }

    @Override
    public Living getCreature() {
        return entity;
    }
}
