package com.vanderkast.edu.injector;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that is used to tell <code>Injector</code> to
 * load value for argument from properties.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Value {
    String value();

    class ValueNotFoundException extends RuntimeException {
        public ValueNotFoundException(String message) {
            super(message);
        }
    }
}
