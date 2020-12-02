package com.vanderkast.edu.injector;

import com.vanderkast.edu.injector.model.TwoWay;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Properties;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class PropertyBasedInjector_TwoWay_Test {
    private Injector injector;

    @Before
    public void setUp() throws Exception {
        var props = new Properties(1);
        props.put(TwoWay.class.getName(), TwoWay.class.getName());

        injector = new PropertyBasedInjector(props);
    }

    @Test
    public void provide() {
        var actual = injector.provide(TwoWay.class);
        assertNotNull(actual);
        assertEquals(TwoWay.ANON, actual.getName());
    }
}
