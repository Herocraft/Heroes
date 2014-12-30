package com.herocraftonline.heroes;

import com.herocraftonline.heroes.api.characters.managers.CharacterDamageManager;
import com.herocraftonline.heroes.api.characters.managers.CharacterManager;
import com.herocraftonline.heroes.api.characters.party.PartyManager;
import com.herocraftonline.heroes.api.classes.CharacterClassManager;
import com.herocraftonline.heroes.api.command.CommandHandler;
import com.herocraftonline.heroes.api.effects.EffectManager;
import com.herocraftonline.heroes.api.io.configuration.ConfigManager;
import com.herocraftonline.heroes.api.io.storage.StorageManager;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;
import com.herocraftonline.heroes.api.skills.SkillConfigManager;
import com.herocraftonline.heroes.api.skills.SkillManager;
import com.herocraftonline.heroes.command.CommandHandlerImpl;
import com.herocraftonline.heroes.component.ComponentManagerImpl;
import com.herocraftonline.heroes.effects.EffectManagerImpl;
import com.herocraftonline.heroes.skills.SkillManagerImpl;
import com.herocraftonline.heroes.util.LoaderUtil;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.state.InitializationEvent;
import org.spongepowered.api.event.state.PostInitializationEvent;
import org.spongepowered.api.event.state.PreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.util.event.Subscribe;

import java.util.logging.Logger;

@Plugin(id="heroes", name="Heroes RPG Plugin", version="1.0.0-SNAPSHOT")
public class HeroesMain implements HeroesPlugin {

    private Game game;
    private EffectManager effectManager;
    private SkillManagerImpl skillManager;
    private ComponentManagerImpl componentManager;
    private CommandHandlerImpl commandHandler;

    @Subscribe
    public void onPreInit(PreInitializationEvent event) {
        game = event.getGame();

        // Initialize utility classes first
        new LoaderUtil(this);

        // Initialize Managers
        effectManager = new EffectManagerImpl(this);
        skillManager = new SkillManagerImpl(this);
        componentManager = new ComponentManagerImpl(this);
        commandHandler = new CommandHandlerImpl(this);
    }

    @Subscribe
    public void onInit(InitializationEvent event) {
        skillManager.init();
    }

    @Subscribe
    public void onPostInit(PostInitializationEvent event) {
        skillManager.postInit();
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
        return commandHandler;
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
        return effectManager;
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
        return skillManager;
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
