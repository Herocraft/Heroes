package com.herocraftonline.heroes.api.io.configuration;

/**
 * A separate part of the main configuration handler that specifically handles all user-facing aspects of
 * the overall plugin, for instance any/all instances where messages are sent to the player
 */
public interface Messaging {

    /**
     * @param name The name of the command, non case-sensitive
     * @param def A default help message should an entry not exist
     * @return A help string/description of the command set in config for this command
     */
    String getCommandHelp(String name, String def);

    /**
     * @param name The name of the command, non case-sensitive
     * @param def A default array of possible identifiers for this command
     * @return An array of identifier strings, which are used to define what commands can be used to execute the
     *         matching parameter command.
     */
    String[] getCommandIdentifiers(String name, String[] def);

    /**
     * Gets a user defined string for messaging with the given key identifier. these are retrieved
     * @param key The message key/identifier, non case-sensitive
     * @param def A default string to use/populate the configuration if no value exists under the parameter key
     * @return The string defined in messaging under the specified key, or the provided default
     */
    String getMessage(String key, String def);
}
