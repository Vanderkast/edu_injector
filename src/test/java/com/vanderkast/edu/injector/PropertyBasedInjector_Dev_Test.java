package com.vanderkast.edu.injector;

import com.vanderkast.edu.injector.model.Valued;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Properties;

import static org.junit.Assert.*;

public class PropertyBasedInjector_Dev_Test {

    @Test
    public void provide_Empty() {
        var actual = construct().provide(Empty.class);
        assertNotNull(actual);
    }

    @Test
    public void provide_Dumb() {
        try {
            construct().provide(Dumb.class);
            fail();
        } catch (PropertyBasedInjector.NoImplementationDeclaredException e) {
            // intended behavior
        }
    }

    @Test
    public void provide_DeclaredDumb() {
        var actual = construct(Dumb.class, Empty.class);
        assertNotNull(actual);
    }

    @Test
    public void provide_Abs() {
        try {
            construct().provide(Abs.class);
            fail();
        } catch (PropertyBasedInjector.NoImplementationDeclaredException e) {
            // intended behavior
        }
    }

    public static abstract class Abs {
        Abs() {
        }
    }

    public static class Empty implements Dumb {

    }

    public interface Dumb {

    }

    @Test
    public void getValue() {
        var injector = construct();
        var actual = injector.getValue(Valued.class.getConstructors()[0].getParameterAnnotations()[0]);
        assertEquals("info", actual);
    }

    private PropertyBasedInjector construct(Object... properties) {
        assertEquals(0, properties.length % 2);
        var props = new Properties(properties.length / 2);
        for (int i = 0; i < properties.length; i++)
            props.put(properties[i], properties[++i]);

        return new PropertyBasedInjector(props);
    }

    @Test
    public void testConstruct() throws NoSuchFieldException, IllegalAccessException {
        var injector = construct("a", "a1", "b", "b1");
        Field field = injector.getClass().getDeclaredField("properties");
        field.setAccessible(true);
        var properties = (Properties) field.get(injector);
        assertEquals("a1", properties.get("a"));
        assertEquals("b1", properties.get("b"));
    }
}
