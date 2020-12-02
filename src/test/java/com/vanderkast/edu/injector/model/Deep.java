package com.vanderkast.edu.injector.model;

import com.vanderkast.edu.injector.Autowired;

public class Deep {
    private final Valued valued;

    @Autowired
    public Deep(Valued valued) {
        this.valued = valued;
    }

    public Valued getValued() {
        return valued;
    }
}
