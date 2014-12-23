package com.herocraftonline.heroes.io.configuration;

/**
 * Responsible for saving/ retrieving configuration settings from storage (general configuration, class configs,
 * and skill configs, amongst others), and converting them into DataView objects parseable
 * by the {@link ConfigManager} for storage into memory
 */
public interface ConfigurationProvider {

    /**
     * @param path The path of the configuration, delimited by the '.' character. Interpretation of path in relation to
     *             where data is stored is left to the implementing class
     * @return A DataView representation of the data located under said path
     */
    Object getConfiguration(String path);

    /**
     * Saves the given data to the given path
     * @param path The path of the configuration, delimited by the '.' character. Interpretation of path in relation to
     *             where data is stored is left to the implementing class
     * @param data A DataView representation of the data to save
     */
    void saveConfiguration(String path, Object data);
}
