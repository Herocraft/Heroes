package com.herocraftonline.heroes.api.io.storage;


import java.util.Map;

/**
 * The Storage Manager handles loading one or more {@link StorageProvider} objects, and determines which
 * storage providers to use when loading and saving specific Player/Hero objects using
 * {@link StorageProvider#getLoadHook()} and {@link StorageProvider#getSaveHook()} respectively, as well as the storage
 * provider defined in defaults
 */
public interface StorageManager {
    /**
     * @return An immutable map of registered storage providers, sorted by identifiers obtained from
     * {@link StorageProvider#getStorageIdentifier()}
     */
    Map<String, StorageProvider> getRegisteredStorageProviders();
}
