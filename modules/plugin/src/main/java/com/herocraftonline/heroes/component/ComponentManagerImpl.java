package com.herocraftonline.heroes.component;

import com.herocraftonline.heroes.api.components.Component;
import com.herocraftonline.heroes.api.components.ComponentManager;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ComponentManagerImpl implements ComponentManager {

    private HeroesPlugin plugin;
    private boolean registrationLock;
    private HashMap<String, Component> components;

    public ComponentManagerImpl(HeroesPlugin plugin) {
        this.plugin = plugin;
        this.components = new HashMap<>();
        this.registrationLock = false;
    }

    public void init() {
        loadComponents();
    }

    public void postinit() {
        this.registrationLock = true;
    }

    private void loadComponents() {
        URLClassLoader loader = (URLClassLoader) plugin.getClass().getClassLoader();
        File componentDir = new File("Mock File"); //TODO
        componentDir.mkdirs();

        // Sanitize Inputs - Only load JARs
        List<File> toLoad = new LinkedList<File>();
        for (final String fileName : componentDir.list()) {
            if (fileName.toLowerCase().endsWith(".jar")) {
                toLoad.add(new File(componentDir, fileName));
            }
        }

        // Load from JAR
        for (final File file : toLoad) {
            // Add to class loader
            try {
                addURL(file.toURI().toURL(), loader);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            // Obtain component class
            try {
                JarFile jar = new JarFile(file);
                String mainClass = null;
                final Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    final JarEntry entry = entries.nextElement();
                    if (entry.getName().endsWith(".class")) {
                        String clazzName = entry.getName().replace("/",".").replace("\\",".").substring(0, entry.getName().lastIndexOf("."));
                        final Class<?> clazz = Class.forName(clazzName, true, loader);
                        if (Component.class.isAssignableFrom(clazz)) {
                            // This is main class - register
                            registerComponent((Class<? extends Component>) clazz);
                            break;
                        }
                    }
                }
            } catch (IllegalArgumentException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
                continue;
            }
        }

    }

    // Use reflection to add to active class loader due to how finicky children classloaders can be
    private void addURL(URL url, URLClassLoader loader) throws IOException {
        try {
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] {URL.class});
            method.setAccessible(true);
            method.invoke(loader, new Object[]{url});
        } catch (Exception e) {
            throw new IOException("Error adding URL to ClassLoader", e);
        }

    }


    @Override
    public void registerComponent(Class<? extends Component> clazz) throws IllegalArgumentException {
        if (registrationLock) {
            throw new IllegalStateException("Component registration must be done during server initialization state");
        }
        Component component = null;
        try {
            component = clazz.getConstructor(new Class<?>[]{}).newInstance(new Object[]{});
            component.onInit(plugin);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        if (components.containsKey(component.getName().toLowerCase())) {
            throw new IllegalArgumentException("Duplicate component with name " + component.getName().toLowerCase());
        }
        components.put(component.getName().toLowerCase(), component);
        return;
    }

    @Override
    public Component getComponent(String name) {
        return components.get(name.toLowerCase());
    }

    @Override
    public Component removeComponent(String name) {
        return components.remove(name.toLowerCase());
    }
}
