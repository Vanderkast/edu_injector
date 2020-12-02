package com.vanderkast.edu.injector;

import java.lang.annotation.*;

/**
 * Constructor that is used to mark constructor
 *      that should be used to get instance of class.
 *
 * If no constructors marked with <code>@Autowired</code>
 *      then default constructor will be used
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.CONSTRUCTOR)
public @interface Autowired {

    class ConstructorNotFoundException extends RuntimeException {
        public ConstructorNotFoundException(String message) {
            super(message);
        }
    }
}
