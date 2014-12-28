package com.herocraftonline.heroes.util;

import com.herocraftonline.heroes.api.plugin.HeroesPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;

public class LoaderUtil {

    private static LoaderUtil INSTANCE;
    private URLClassLoader loader;
    private Method addURL;

    public LoaderUtil(HeroesPlugin plugin) {
        INSTANCE = this;
        loader = (URLClassLoader) plugin.getClass().getClassLoader();
        try {
            addURL = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] {URL.class});
            addURL.setAccessible(true);
        } catch (Exception e) {
            INSTANCE = null;
            plugin.getLogger().log(Level.SEVERE, "Problem initializing class loading, JAR loading will not " +
                    "function", e);
            return;
        }
        return;
    }

    public static LoaderUtil instance() {
        return INSTANCE;
    }

    public URLClassLoader getClassLoader() {
        return loader;
    }

    /**
     * Perhaps slightly inappropriately named, this method searches all JAR files within the top level of a directory
     * and adds them to the classpath. In addition, it will return a collection of any classes within said JAR files
     * that extends or implements the parameter class.
     * @param dir The directory to search
     * @param search The class file type to search for
     * @param <T> The specific type extended or implemented by the target class
     * @return A collection of loaded classes that match the search parameter
     */
    public <T> Collection<Class<? extends T>> loadJARsFromDir(File dir, Class<T> search) {

        // Sanitize Inputs - Only load JARs
        List<File> toLoad = new LinkedList<>();
        for (final String fileName : dir.list()) {
            if (fileName.toLowerCase().endsWith(".jar")) {
                toLoad.add(new File(dir, fileName));
            }
        }
        // Load from JAR
        Collection<Class<? extends T>> searchResults = new LinkedList<>();
        for (final File file : toLoad) {
            // Add to class loader
            try {
                addURL(file.toURI().toURL());
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            // Obtain matching class
            try {
                JarFile jar = new JarFile(file);
                String mainClass = null;
                final Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    final JarEntry entry = entries.nextElement();
                    if (entry.getName().endsWith(".class")) {
                        String clazzName = entry.getName().replace("/",".").replace("\\",".").substring(0, entry.getName().lastIndexOf("."));
                        final Class<?> clazz = Class.forName(clazzName, true, loader);
                        if (search.isAssignableFrom(clazz)) {
                            // This is class we are looking for, add
                            searchResults.add((Class<? extends T>) clazz);
                            break;
                        }
                    }
                }
            } catch (IllegalArgumentException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
                continue;
            }
        }
        return searchResults;
    }

    // Use reflection to add to active class loader due to how finicky children classloaders can be
    private void addURL(URL url) throws IOException {
        try {
            addURL.invoke(loader, new Object[]{url});
        } catch (Exception e) {
            throw new IOException("Error adding URL to ClassLoader", e);
        }
    }

}
