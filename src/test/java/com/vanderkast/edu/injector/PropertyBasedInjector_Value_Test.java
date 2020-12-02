package com.vanderkast.edu.injector;

import com.vanderkast.edu.injector.model.Valued;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class PropertyBasedInjector_Value_Test {
    private final String johny = "here is Johny!";
    private PropertyBasedInjector injector;

    @Before
    public void setUp() {
        var properties = new Properties(2);
        properties.put("info", johny);
        properties.put(Valued.class.getName(), Valued.class.getName());

        injector = new PropertyBasedInjector(properties);
    }

    @Test
    public void provideValue() {
        var constructor = Valued.class.getConstructors()[0];
        var result = injector.provide(constructor.getParameterTypes()[0],
                constructor.getParameterAnnotations()[0]);
        assertEquals(johny, result);
    }

    @Test
    public void provide() {
        var mock = injector.provide(Valued.class);
        assertNotNull(mock);
        assertEquals(johny, mock.getInfo());
    }

    @Ignore
    public void constructorDeconstruction() {
        var constructors = Valued.class.getConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println("Constructor of: " + constructor.getName());
            for (Annotation[] annotations : constructor.getParameterAnnotations()) {
                System.out.println("Annotations: {");
                var i = 0;
                for (Annotation annotation : annotations) {
                    System.out.print((i++) + ": " + annotation.toString());
                    if (annotation instanceof Value)
                        System.out.println("; value: " + ((Value) annotation).value());
                }
                System.out.println("}");
            }
        }
    }
}
