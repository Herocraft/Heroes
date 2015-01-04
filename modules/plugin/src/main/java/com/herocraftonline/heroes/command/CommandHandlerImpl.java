package com.herocraftonline.heroes.command;

import com.google.common.base.Optional;
import com.herocraftonline.heroes.api.command.Command;
import com.herocraftonline.heroes.api.command.CommandHandler;
import com.herocraftonline.heroes.api.io.configuration.Messaging;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;
import com.herocraftonline.heroes.skills.SkillManagerImpl;
import com.herocraftonline.heroes.util.Commons;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CommandHandlerImpl implements CommandHandler {

    private final HeroesPlugin plugin;
    private HashMap<String, Command> commandsByIdentifier;
    private SkillManagerImpl skillManager;
    private HashSet<String> skillIdents;
    private Messaging messaging;

    public CommandHandlerImpl(HeroesPlugin plugin) {
        this.plugin = plugin;
        this.messaging = plugin.getConfigManager().getMessaging();
        this.skillIdents = new HashSet<>();
        for (String ident : Arrays.asList(messaging.getCommandIdentifiers("skill-execution",
                new String[] {"skill", "cast"}))) {
            this.skillIdents.add(ident.toLowerCase());
        }
        this.skillManager = (SkillManagerImpl) plugin.getSkillManager();
        this.commandsByIdentifier = new HashMap<>();
    }

    @Override
    public List<String> getSuggestions(CommandSource commandSource, String args) throws CommandException {
        return null;
    }

    @Override
    public boolean call(CommandSource sender, String args, List<String> commands) throws CommandException {
        String[] cmd = Commons.addAll((String[]) commands.toArray(), args.split(" "));
        return handle(sender, cmd);
    }

    private boolean handle(CommandSource sender, String[] cmd) {
        if (this.skillIdents.contains(cmd[0].toLowerCase())) {
            /*
             * Special case is handled by skill manager
             * We check for this first due to the relative importance of having skill commands execute asap
             * and cause a minimum of lag compared to other settings. This is also the reasoning behind having
             * the skill manager process skill identifiers (fewer possible collisions/one fewer iteration of
             * string building) as opposed to directly registering skills with the command handler.
             */
            return skillManager.execute(sender, Arrays.copyOfRange(cmd, 1, cmd.length));
        }
        // Match greatest length identifiers first
        for (int identifierLength = cmd.length; identifierLength >= 0; identifierLength--) {
            final StringBuilder identBuilder = new StringBuilder();
            for (int identIterator = 0; identIterator < identifierLength; identIterator++) {
                if (identIterator > 0) {
                    identBuilder.append(' ');
                }
                identBuilder.append(cmd[identIterator]);
            }
            final String ident = identBuilder.toString().toLowerCase();
            // Find command
            final Command command = commandsByIdentifier.get(ident);
            if (command == null) {
                continue;
            }
            // Check permissions
            if (sender instanceof Player) { //TODO
                sender.sendMessage(TextColors.RED + "You lack the required permission " + command.getPermission() +
                        " for this command.");
                return true;
            }
            // Remaining Arguments
            String[] args = Arrays.copyOfRange(cmd, identifierLength, cmd.length);
            // Check argument length
            int len = args.length;
            if (len < command.getMinArguments() || (command.getMaxArguments() != -1
                    && len > command.getMaxArguments())) {
                sender.sendMessage(TextColors.GRAY + "Invalid arguments - correct usage is " + command.getUsage());
                return true;
            }

            return command.execute(sender, args);
        }
        // No Match TODO: send message? pending verification of how new command system works
        return true;
    }

    @Override
    public boolean testPermission(CommandSource commandSource) {
        return true;
    }

    @Override
    public Optional<String> getShortDescription() {
        return Optional.of(plugin.getConfigManager().getMessaging().getMessage("main-command-short-desc","" +
                "Commands to control the Heroes RPG Plugin"));
    }

    @Override
    public Optional<String> getHelp() {
        return Optional.of(plugin.getConfigManager().getMessaging().getMessage("main-command-help","Commands " +
                "to control the Heroes RPG Plugin - use /hero help to view a full list of commands " +
                "available to you."));
    }

    @Override
    public String getUsage() {
        return "/hero help";
    }
}
