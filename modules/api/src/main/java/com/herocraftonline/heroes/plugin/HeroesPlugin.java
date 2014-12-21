package com.herocraftonline.heroes.plugin;

import com.herocraftonline.heroes.characters.managers.CharacterDamageManager;
import com.herocraftonline.heroes.classes.CharacterClassManager;
import com.herocraftonline.heroes.effects.EffectManager;
import com.herocraftonline.heroes.characters.managers.CharacterManager;
import com.herocraftonline.heroes.characters.party.PartyManager;
import com.herocraftonline.heroes.command.CommandHandler;
import com.herocraftonline.heroes.configuration.ConfigManager;
import com.herocraftonline.heroes.io.StorageManager;
import com.herocraftonline.heroes.skills.SkillConfigManager;
import com.herocraftonline.heroes.skills.SkillManager;
import org.spongepowered.api.Game;

import java.util.logging.Logger;

public interface HeroesPlugin {

    /**
     * Gets the game instance that loaded this plugin
     * @return The active game instance
     */
    Game getGame();

    /**
     * <p>Gets the active {@link CharacterManager}, which is responsible for loading, managing, retrieving, and saving
     * {@link com.herocraftonline.heroes.characters.CharacterBase} instances</p>
     * @return The active character manager
     */
    CharacterManager getCharacterManager();

    CharacterClassManager getClassManager();

    CommandHandler getCommandHandler();

    ConfigManager getConfigManager();

    CharacterDamageManager getDamageManager();

    EffectManager getEffectManager();

    String getImplementation();

    PartyManager getPartyManager();

    SkillConfigManager getSkillConfigs();

    SkillManager getSkillManager();

    StorageManager getStorageManager();

    /**
     * @return The debug logger for this plugin
     */
    Logger getLogger();

    //getPartyChannelManager() TODO

}
