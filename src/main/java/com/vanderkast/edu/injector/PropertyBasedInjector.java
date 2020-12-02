package com.vanderkast.edu.injector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * Injector that uses Properties
 */
@SuppressWarnings("unchecked")
public class PropertyBasedInjector implements Injector {
    private final Properties properties;

    public PropertyBasedInjector(Properties properties) {
        this.properties = properties;
    }

    @Override
    public <T, G extends T> G provide(Class<T> tClass) {
        var constructor = getConstructor(getImplementation(tClass));
        var params = getParameters(constructor);
        return (G) build(constructor, params);
    }

    protected <T, G extends T> G provide(Class<T> tClass, Annotation[] annotations) {
        var key = getValue(annotations);
        if (key != null) {
            var value = properties.get(key);
            if (value == null)
                throw new Value.ValueNotFoundException("No value found for key: " + key);
            if (tClass == String.class)
                return (G) value;
            if (tClass == int.class)
                return (G) Integer.valueOf((String) value);
            if (tClass == long.class)
                return (G) Long.valueOf((String) value);
            if (tClass == boolean.class)
                return (G) Boolean.valueOf((String) value);
            if (tClass == float.class)
                return (G) Float.valueOf((String) value);
            if (tClass == double.class)
                return (G) Double.valueOf((String) value);
            return (G) value;
        }
        return provide(tClass);
    }

    protected <T, G extends T> Class<G> getImplementation(Class<T> tClass) {
        var declared = (String) properties.getOrDefault(tClass.getName(), null);
        if (declared != null) {
            try {
                return (Class<G>) Class.forName(declared);
            } catch (ClassNotFoundException e) {
                throw new DeclaredImplementationNotFoundException("Class not found for implementation: " + declared, e);
            }
        } else if (tClass.getConstructors().length != 0)
            return (Class<G>) tClass;
        throw new NoImplementationDeclaredException("No implementation declared for Class: " + tClass.getName());
    }

    protected Constructor<?> getConstructor(Class<?> implementation) {
        Constructor<?> noArgs = null;
        for (var constructor : implementation.getConstructors()) {
            if (constructor.isAnnotationPresent(Autowired.class))
                return constructor;
            if (constructor.getParameterTypes().length == 0) {
                noArgs = constructor;
            }
        }
        if (noArgs == null)
            throw new Autowired.ConstructorNotFoundException(
                    "@Autowired annotated constructor not found for class: " + implementation.getName());
        return noArgs;
    }

    protected Object[] getParameters(Constructor<?> constructor) {
        Object[] types = constructor.getParameterTypes();
        Object[] params = new Object[types.length];
        var annotations = constructor.getParameterAnnotations();
        for (int i = 0; i < types.length; i++)
            params[i] = provide((Class<?>) types[i], annotations[i]);
        return params;
    }

    protected Object build(Constructor<?> constructor, Object[] parameters) {
        try {
            return constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new InstanceCreationException("Cannot create instance via constructor of " + constructor.getName(), e);
        }
    }

    protected String getValue(Annotation[] annotations) {
        for (var annotation : annotations) {
            if (annotation instanceof Value)
                return ((Value) annotation).value();
        }
        return null;
    }

    public static class NoImplementationDeclaredException extends RuntimeException {
        public NoImplementationDeclaredException(String message) {
            super(message);
        }
    }

    public static class DeclaredImplementationNotFoundException extends RuntimeException {
        public DeclaredImplementationNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class InstanceCreationException extends RuntimeException {
        public InstanceCreationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
