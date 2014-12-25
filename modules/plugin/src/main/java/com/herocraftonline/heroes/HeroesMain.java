package com.herocraftonline.heroes;

import com.herocraftonline.heroes.characters.managers.CharacterDamageManager;
import com.herocraftonline.heroes.characters.managers.CharacterManager;
import com.herocraftonline.heroes.characters.party.PartyManager;
import com.herocraftonline.heroes.classes.CharacterClassManager;
import com.herocraftonline.heroes.command.CommandHandler;
import com.herocraftonline.heroes.effects.EffectManager;
import com.herocraftonline.heroes.effects.EffectManagerImpl;
import com.herocraftonline.heroes.io.configuration.ConfigManager;
import com.herocraftonline.heroes.io.storage.StorageManager;
import com.herocraftonline.heroes.plugin.HeroesPlugin;
import com.herocraftonline.heroes.skills.SkillConfigManager;
import com.herocraftonline.heroes.skills.SkillManager;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.util.event.Subscribe;

import java.util.logging.Logger;

@Plugin(id="heroes", name="Heroes RPG Plugin", version="1.0.0-SNAPSHOT")
public class HeroesMain implements HeroesPlugin {

    private Game game;
    private EffectManager effectManager;

    @Subscribe
    public void onServerInit(PreInitializationEvent event) {
        game = event.getGame();
        this.effectManager = new EffectManagerImpl(this);
    }

    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public CharacterManager getCharacterManager() {
        return null;
    }

    @Override
    public CharacterClassManager getClassManager() {
        return null;
    }

    @Override
    public CommandHandler getCommandHandler() {
        return null;
    }

    @Override
    public ConfigManager getConfigManager() {
        return null;
    }

    @Override
    public CharacterDamageManager getDamageManager() {
        return null;
    }

    @Override
    public EffectManager getEffectManager() {
        return null;
    }

    @Override
    public String getImplementation() {
        return null;
    }

    @Override
    public PartyManager getPartyManager() {
        return null;
    }

    @Override
    public SkillConfigManager getSkillConfigs() {
        return null;
    }

    @Override
    public SkillManager getSkillManager() {
        return null;
    }

    @Override
    public StorageManager getStorageManager() {
        return null;
    }

    @Override
    public Logger getLogger() {
        return Logger.getLogger("Minecraft"); //TODO
    }
}
