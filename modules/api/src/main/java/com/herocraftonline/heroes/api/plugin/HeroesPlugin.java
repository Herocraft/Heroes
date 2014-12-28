package com.herocraftonline.heroes.api.plugin;

import com.herocraftonline.heroes.api.characters.managers.CharacterDamageManager;
import com.herocraftonline.heroes.api.classes.CharacterClassManager;
import com.herocraftonline.heroes.api.effects.EffectManager;
import com.herocraftonline.heroes.api.characters.managers.CharacterManager;
import com.herocraftonline.heroes.api.characters.party.PartyManager;
import com.herocraftonline.heroes.api.command.CommandHandler;
import com.herocraftonline.heroes.api.io.configuration.ConfigManager;
import com.herocraftonline.heroes.api.io.storage.StorageManager;
import com.herocraftonline.heroes.api.skills.SkillConfigManager;
import com.herocraftonline.heroes.api.skills.SkillManager;
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
     * {@link com.herocraftonline.heroes.api.characters.CharacterBase} instances</p>
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
