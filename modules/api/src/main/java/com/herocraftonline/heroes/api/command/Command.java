package com.herocraftonline.heroes.api.command;

import org.spongepowered.api.util.command.CommandSource;

public interface Command {

    /**
     * Called upon command execution
     * @param source The executor
     * @param args An array of words describing any additional arguments used, if present. Null otherwise
     * @return Whether execution of the command completed normally
     */
    boolean execute(CommandSource source, String[] args);

    /**
     * @return A help string to be displayed in the help menu if {@link #showInHelp()} returns true. In addition, if
     *         {@link #getHelp()} does not return null, then this will be displayed should
     *         {@link #execute(org.spongepowered.api.util.command.CommandSource, String[])} return false
     */
    String getHelp();

    /**
     * @return The permission node required to run this command, or null if none required
     */
    String getPermission();

    /**
     * @return The minimum length of the args parameter as described in {@link #execute(CommandSource, String[])},
     *         can be 0. Must be less than or equal to {@link #getMaxArguments()}
     */
    int getMinArguments();

    /**
     * @return The maximum length of the args parameter as described in {@link #execute(CommandSource, String[])},
     *         use -1 for no maximum, otherwise must be greater than or equal to {@link #getMinArguments()}.
     */
    int getMaxArguments();


    /**
     * @return Whether help for this command should be shown in the help menu
     */
    boolean showInHelp();

}
