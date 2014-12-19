package com.herocraftonline.heroes.io.exception;

import com.herocraftonline.heroes.characters.Hero;

/**
 * Thrown when asynchronous saving fails, for whatever reason. If the failure was due to an exception,
 * said exception is included as the cause.
 */
public final class AsynchronousSaveException extends RuntimeException {
    public AsynchronousSaveException(Hero hero, Exception cause) {
        super("A problem arose during asynchronous save of " + hero.getName(), cause);
    }
}
