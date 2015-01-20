package com.herocraftonline.heroes.common.characters.inanimate;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.service.persistence.data.DataView;
import org.spongepowered.api.world.Location;

import java.util.UUID;

public class BeaconEntity extends Beacon {


    private final Entity entity;

    public BeaconEntity(Entity entity) {
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
        return entity.getType().getId();
    }

    @Override
    public UUID getUID() {
        return entity.getUniqueId();
    }

    //TODO
    @Override
    public DataView getCharacterComponentData(String name) {
        return null;
    }
}
