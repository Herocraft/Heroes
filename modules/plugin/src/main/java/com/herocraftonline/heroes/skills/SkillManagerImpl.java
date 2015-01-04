package com.herocraftonline.heroes.skills;

import com.herocraftonline.heroes.api.command.Command;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;
import com.herocraftonline.heroes.api.skills.Skill;
import com.herocraftonline.heroes.api.skills.SkillManager;
import com.herocraftonline.heroes.util.LoaderUtil;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandSource;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;

public class SkillManagerImpl implements SkillManager, Command {

    private HeroesPlugin plugin;
    private boolean registrationLock;

    protected HashMap<String, Skill> skillsByName;
    protected HashMap<String, Skill> skillsByIdentifiers;

    public SkillManagerImpl(HeroesPlugin plugin) {
        this.plugin = plugin;
        this.skillsByIdentifiers = new HashMap<>();
        this.skillsByName = new HashMap<>();
        this.registrationLock = false;
    }

    public void init() { // During server initialization
        loadSkills();
    }

    public void postInit() { // Post server initialization
        this.registrationLock = true;
    }

    private void loadSkills() {
        File skillDir = new File("Mock File"); //TODO
        skillDir.mkdirs();
        for (Class<? extends Skill> clazz : LoaderUtil.instance().loadJARsFromDir(skillDir, Skill.class)) {
            try {
                registerSkill(clazz);
            } catch (Exception e) {
                plugin.getLogger().log(Level.WARNING, "Failed to load component " + clazz.getName(), e);
                continue;
            }
        }
    }

    @Override
    public boolean execute(CommandSource source, String[] args) {
        if (!(source instanceof Player)) {
            //TODO Player execution only
        }
        if (args == null || args.length == 0) {
            //TODO messaging send must have skill argument or use /skills
            return true;
        }
        // Match greatest length identifiers first
        for (int identifierLength = args.length; identifierLength >= 0; identifierLength--) {
            final StringBuilder identBuilder = new StringBuilder();
            for (int identIterator = 0; identIterator < identifierLength; identIterator++) {
                if (identIterator > 0) {
                    identBuilder.append(' ');
                }
                identBuilder.append(args[identIterator]);
            }
            final String ident = identBuilder.toString().toLowerCase();
            Skill skill = this.skillsByIdentifiers.get(ident);
            if (skill == null) {
                continue;
            }
            // Remaining Arguments
            String[] skillArgs = Arrays.copyOfRange(args, identifierLength, args.length);
            skill.execute(plugin.getCharacterManager().getCharacter((Living)source), skillArgs);
            return true;
        }
        //TODO messaging send no skill found
        return true;
    }

    @Override
    public String getHelp() {
        return plugin.getConfigManager().getMessaging().getCommandHelp("skills", "View available skills with /skills");
    }

    @Override
    public String getPermission() {
        return "heroes.skills";
    }

    @Override
    public int getMinArguments() {
        return 1; // /skill skillname
    }

    @Override
    public int getMaxArguments() {
        return -1;
    }

    @Override
    public String[] getIdentifiers() {
        return new String[0];  // Does not matter since we are not registering this as a traditional command
    }

    @Override
    public String getUsage() {
        return "/skill skillname";
    }

    @Override
    public boolean showInHelp() {
        return false;
    }

    @Override
    public void registerSkill(Class<? extends Skill> clazz) {
        if (registrationLock) {
            throw new IllegalStateException("Skill registration must be done during server initialization state");
        }
        Skill skill = null;
        try {
            skill = clazz.getConstructor(new Class<?>[]{}).newInstance(new Object[]{});
            skill.onInit(plugin);
            this.skillsByName.put(skill.getName().toLowerCase(), skill);
            for (String ident : skill.getIdentifiers()) {
                this.skillsByIdentifiers.put(ident.toLowerCase(), skill);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("The skill " + skill.getClass().getName() + " failed to initialize.", e);
        }
        return;
    }

    @Override
    public Collection<Skill> getSkills() {
        return Collections.unmodifiableCollection(skillsByName.values());
    }

    @Override
    public Skill removeSkill(String name) {
        Skill match = skillsByName.remove(name.toLowerCase());
        if (match != null) {
            for (String ident : new ArrayList<String>(skillsByIdentifiers.keySet())) {
                if (skillsByIdentifiers.get(ident).equals(match)) {
                    skillsByIdentifiers.remove(ident);
                }
            }
        }
        return match;
    }
}
