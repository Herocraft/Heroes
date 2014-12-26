package com.herocraftonline.heroes.skills;

import com.herocraftonline.heroes.command.Command;
import com.herocraftonline.heroes.plugin.HeroesPlugin;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.player.Player;
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
            Skill skill = this.identifiers.get(ident);
            if (skill == null) {
                continue;
            }
            // Remaining Arguments
            String[] skillArgs = Arrays.copyOfRange(args, identifierLength, args.length);
            skill.execute(plugin.getCharacterManager().getCharacter((Living)source), skillArgs);
            return true;
        }
        //TODO messaging send no skill found
        return false;
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
