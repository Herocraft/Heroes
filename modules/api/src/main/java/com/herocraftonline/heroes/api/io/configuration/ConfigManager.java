package com.herocraftonline.heroes.api.io.configuration;

/**
 * The configuration manager is responsible for loading the (singular) configuration provider being used and storing
 * configuration settings in memory. Retrieval of the configuration settings themselves from storage is delegated to the
 * active {@link com.herocraftonline.heroes.api.io.configuration.ConfigurationProvider}
 */
public interface ConfigManager {

    /**
     * @return The {@link Messaging} instance associated with this configuration manager, which is responsible for
     *         any/all Strings that may be sent/retrieved from the end user
     */
    Messaging getMessaging();

    /**
     * @param key The configuration key, case-sensitive
     * @return A string representation of the value stored in configuration
     */
    String getConfig(String key);

}
