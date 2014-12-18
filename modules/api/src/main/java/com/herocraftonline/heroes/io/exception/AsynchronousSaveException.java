package com.herocraftonline.heroes.io.exception;

import com.herocraftonline.heroes.characters.Hero;

public class AsynchronousSaveException extends RuntimeException {
    public AsynchronousSaveException(Hero hero, Exception cause) {
        super("A problem arose during asynchronous save of " + hero.getName(), cause);
    }
}
