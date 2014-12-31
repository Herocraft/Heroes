package com.herocraftonline.heroes.api.io.configuration;

/**
 * The configuration manager is responsible for loading the (singular) configuration provider being used and storing
 * configuration settings in memory. Retrieval of the configuration settings themselves from storage is delegated to the
 * active {@link com.herocraftonline.heroes.api.io.configuration.ConfigurationProvider}
 */
public interface ConfigManager {

    /**
     * Gets a user defined string for messaging with the given key identifier
     * @param key The message key/identifier, non case-sensitive
     * @param def A default string to use/populate the configuration if no value exists under the parameter key
     * @return The string defined in messaging under the specified key, or the provided default
     */
    String getMessaging(String key, String def);
    //TODO
}
