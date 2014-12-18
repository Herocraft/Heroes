package com.herocraftonline.heroes.characters;

import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.player.Player;

import java.util.Collection;
import java.util.UUID;

/**
 * Responsible for loading, managing, retrieving, and saving {@link Character} instances
 */
public interface CharacterManager {

    /**
     * General method to get a character by its UID, use {@link #getHero(java.util.UUID)}
     * or {@link #getCreature(java.util.UUID)} for more specific variants.
     * @param character The {@link java.util.UUID} of the character to look for
     * @return Depending on nature of the character, the return value is equal to the return value of getHero(UUID) or
     * getCreature(UUID)
     */
    Character getCharacter(UUID character);

    /**
     * General method to get a character by its entity object. Use
     * {@link #getHero(org.spongepowered.api.entity.player.Player)} or
     * {@link #getCreature(org.spongepowered.api.entity.living.Living)} for more specific variants.
     * @param character The entity of the character to look for
     * @return Depending on the nature of the character, the return value is equal to the return value of
     * getHero(Player) or getCreature(Living)
     */
    Character getCharacter(Living character);

    /**
     * Gets a given Creature (non-human character) from its UUID
     * @param creature The UUID of the creature to get
     * @return The matching creature, will load the creature if it exists but is not loaded, otherwise returns null
     */
    Creature getCreature(UUID creature);

    /**
     * Gets a given Creature (non-human character) from its entity object
     * @param creature The {@link org.spongepowered.api.entity.living.Living} object representing the creature
     * @return The matching creature, will load the creature if none matching exists <b>Note:</b>This behaviour differs
     * from {@link #getCreature(java.util.UUID)}
     * @throws java.lang.IllegalArgumentException if creature is a Player object
     */
    Creature getCreature(Living creature);

    /**
     * Gets a given Hero by its UUID
     * @param hero The UUID of the player to get a Hero for
     * @return The player's associated Hero instance, will load from file if one does not exist, or will load from
     * defaults if that doesn't exist either
     */
    Hero getHero(UUID hero);

    /**
     * Gets a given Hero by its name
     * @param hero The name of the player to get a hero for, case sensitive
     * @return The player's associated Hero instance, will load from file if one does not exist, or will load from
     * defaults if that doesn't exist either
     */
    Hero getHero(String hero);

    /**
     * Gets a given Hero by its {@link org.spongepowered.api.entity.player.Player} object
     * @param hero The player object representing the Hero
     * @return The player's associated Hero instance, will load from file if one does not exist, or will load from
     * defaults if that doesn't exist either
     */
    Hero getHero(Player hero);

    /**
     * @return An immutable collection of currently loaded Heroes
     */
    Collection<Hero> getHeroes();

    /**
     * Triggers a save of the given hero via whatever storage solution is active for that specific Hero.
     * If the now parameter is set to true, the save is done immediately on the same server tick and thread.
     * Otherwise the save is done asynchronously assuming the storage solution supports it.
     * @param hero The hero to save
     * @param now Whether the save should be done immediately - this parameter may be ignored by some storage solutions
     * @return Whether the save was successful
     */
    boolean saveHero(Hero hero, boolean now);



}
