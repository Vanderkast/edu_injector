package com.vanderkast.edu.injector;

import com.vanderkast.edu.injector.model.Deep;
import com.vanderkast.edu.injector.model.Valued;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.*;

public class PropertyBasedInjector_Deep_Test {
    private int info = 33;

    private Injector injector;

    @Before
    public void setUp() throws Exception {
        var props = new Properties(3);
        props.put("info", info);
        props.put(Valued.class.getName(), Valued.class.getName());
        props.put(Deep.class.getName(), Deep.class.getName());

        injector = new PropertyBasedInjector(props);
    }

    @Test
    public void provide() {
        var deep = injector.provide(Deep.class);
        assertNotNull(deep);
        assertNotNull(deep.getValued());
        assertEquals(info, deep.getValued().getInfo());
    }
}
