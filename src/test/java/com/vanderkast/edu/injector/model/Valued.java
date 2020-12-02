package com.vanderkast.edu.injector.model;

import com.vanderkast.edu.injector.Autowired;
import com.vanderkast.edu.injector.Value;

public class Valued {
    private final Object info;

    @Autowired
    public Valued(@Value(value = "info") Object info) {
        this.info = info;
    }

    public Object getInfo() {
        return info;
    }
}
