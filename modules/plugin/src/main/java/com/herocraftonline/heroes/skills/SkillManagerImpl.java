package com.herocraftonline.heroes.skills;

import com.herocraftonline.heroes.command.Command;
import com.herocraftonline.heroes.plugin.HeroesPlugin;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.util.command.CommandSource;

import java.util.Arrays;
import java.util.HashMap;

public class SkillManagerImpl implements SkillManager, Command {

    private HeroesPlugin plugin;
    protected HashMap<String, Skill> identifiers;

    public SkillManagerImpl(HeroesPlugin plugin) {
        this.plugin = plugin;
        this.identifiers = new HashMap<String, Skill>();
    }

    @Override
    public boolean execute(CommandSource source, String[] args) {
        if (args == null || args.length == 0) {
            //TODO messaging send must have skill argument or use /skills
            return true;
        }
        Skill skill = findSkillFromArgs(args);
        if (skill == null) {
            //TODO messaging send no skill found
            return true;
        } else {
            skill.execute(plugin.getCharacterManager().getCharacter((Living)source),
                    Arrays.copyOfRange(args, 1, args.length));
            return true;
        }

    }

    private Skill findSkillFromArgs(String[] args) {
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
            Skill skill = this.identifiers.get(ident);
            if (skill == null) {
                continue;
            }
            return skill;
        }
        return null;
    }

    @Override
    public String getHelp() {
        return "Use /skills to view available skills";
    }

    @Override
    public String getPermission() {
        return "heroes.skills";
    }

    @Override
    public int getMinArguments() {
        return 0;
    }

    @Override
    public int getMaxArguments() {
        return -1;
    }

    @Override
    public boolean showInHelp() {
        return false;
    }
}
