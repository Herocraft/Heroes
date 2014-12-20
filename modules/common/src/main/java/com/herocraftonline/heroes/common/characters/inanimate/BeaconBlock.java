package com.herocraftonline.heroes.common.characters.inanimate;

import org.spongepowered.api.block.BlockLoc;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.UUID;

public class BeaconBlock extends Beacon {

    private final BlockLoc block;

    public BeaconBlock(BlockLoc block) {
        this.block = block;
    }

    @Override
    public Location getLocation() {
        return block.getLocation();
    }

    @Override
    public String getName() {
        return block.getType().getId();
    }

    @Override
    public UUID getUID() {
        Location loc = getLocation();
        World w = (World)loc.getExtent();  //TODO check to make sure this is a world first(?)
        String s = new StringBuilder().append(w.getUniqueID()).append(":").append(block.getPosition().toString())
                .toString();
        return UUID.nameUUIDFromBytes(s.getBytes());
    }
}
