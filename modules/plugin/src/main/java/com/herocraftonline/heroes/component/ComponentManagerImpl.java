package com.herocraftonline.heroes.component;

import com.herocraftonline.heroes.api.components.Component;
import com.herocraftonline.heroes.api.components.ComponentManager;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;

public class ComponentManagerImpl implements ComponentManager {

    public ComponentManagerImpl(HeroesPlugin plugin) {

    }

    @Override
    public void registerComponent(Class<? extends Component> component) {

    }

    @Override
    public <T extends Component> T getComponent(String name) {
        return null;
    }

    @Override
    public <T extends Component> T removeComponent(String name) {
        return null;
    }
}
