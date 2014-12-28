package com.herocraftonline.heroes.component;

import com.herocraftonline.heroes.api.components.Component;
import com.herocraftonline.heroes.api.components.ComponentManager;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;
import com.herocraftonline.heroes.util.LoaderUtil;

import java.io.File;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.logging.Level;

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
        File componentDir = new File("Mock File"); //TODO
        componentDir.mkdirs();
        for (Class<? extends Component> clazz : LoaderUtil.instance().loadJARsFromDir(componentDir, Component.class)) {
            try {
                registerComponent(clazz);
            } catch (Exception e) {
                plugin.getLogger().log(Level.WARNING, "Failed to load component " + clazz.getName(), e);
                continue;
            }
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
