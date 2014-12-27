package com.herocraftonline.heroes.skills;

import com.herocraftonline.heroes.command.Command;
import com.herocraftonline.heroes.plugin.HeroesPlugin;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.util.command.CommandSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class SkillManagerImpl implements SkillManager, Command {

    private HeroesPlugin plugin;
    protected HashMap<String, Skill> skillsByName;
    protected HashMap<String, Skill> skillsByIdentifiers;

    public SkillManagerImpl(HeroesPlugin plugin) {
        this.plugin = plugin;
        this.skillsByIdentifiers = new HashMap<String, Skill>();
        this.skillsByName = new HashMap<String, Skill>();
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
        return "View available skills with /skills";
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
    public boolean showInHelp() {
        return false;
    }

    @Override
    public void addSkill(Skill skill) {
        this.skillsByName.put(skill.getName().toLowerCase(), skill);
        for (String ident : skill.getIdentifiers()) {
            this.skillsByIdentifiers.put(ident.toLowerCase(), skill);
        }
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
