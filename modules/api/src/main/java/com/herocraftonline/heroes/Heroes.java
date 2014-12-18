package com.herocraftonline.heroes;

import com.herocraftonline.heroes.plugin.HeroesPlugin;

public class Heroes {

    private static HeroesPlugin INSTANCE = null;

    public static HeroesPlugin getInstance() {
        return INSTANCE;
    }

    public static void init(HeroesPlugin instance) {
        INSTANCE = instance;
    }
}
