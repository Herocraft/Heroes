package com.herocraftonline.heroes.characters;

public interface Hero extends Character {
    /**
     * @return The name of the Hero, typically matches the name of the underlying Player object
     */
    public String getName();

}
