package com.herocraftonline.heroes.command;

import com.google.common.base.Optional;
import com.herocraftonline.heroes.api.command.Command;
import com.herocraftonline.heroes.api.command.CommandHandler;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;
import com.herocraftonline.heroes.skills.SkillManagerImpl;
import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandSource;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommandHandlerImpl implements CommandHandler {

    private final HeroesPlugin plugin;
    private HashMap<String, Command> commandsByIdentifier;
    private SkillManagerImpl skillManager;

    public CommandHandlerImpl(HeroesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> getSuggestions(CommandSource commandSource, String args) throws CommandException {
        return null;
    }

    @Override
    public boolean call(CommandSource sender, String args, List<String> commands) throws CommandException {
        String[] cmd = addAll((String[]) commands.toArray(), args.split(" "));
        return handle(sender, cmd);
    }

    private boolean handle(CommandSource sender, String[] cmd) {
        if (cmd[0].equalsIgnoreCase("skill")) {
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
        return Optional.of("Commands to control the Heroes RPG Plugin");
    }

    @Override
    public Optional<String> getHelp() {
        return Optional.of("Commands to control the Heroes RPG Plugin - use /hero help to view a full list of commands " +
                "available to you.");
    }

    @Override
    public String getUsage() {
        return "/hero help";
    }

    // Apache Commons Methods for efficiency sake TODO: verify license allows us to use this, if not add commons as a
    // mvn dependency

    public static String[] addAll(String[] array1, String[] array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        String[] joinedArray = (String[]) Array.newInstance(array1.getClass().getComponentType(),
                array1.length + array2.length);
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        try {
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        } catch (ArrayStoreException ase) {
            // Check if problem was due to incompatible types
            /*
             * We do this here, rather than before the copy because:
             * - it would be a wasted check most of the time
             * - safer, in case check turns out to be too strict
             */
            final Class type1 = array1.getClass().getComponentType();
            final Class type2 = array2.getClass().getComponentType();
            if (!type1.isAssignableFrom(type2)){
                throw new IllegalArgumentException("Cannot store "+type2.getName()+" in an array of "+type1.getName());
            }
            throw ase; // No, so rethrow original
        }
        return joinedArray;
    }

    public static String[] clone(String[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }
}
