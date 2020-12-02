package com.vanderkast.edu.car.model;

import com.vanderkast.edu.injector.Autowired;
import com.vanderkast.edu.injector.Value;

public class EngineImpl implements Engine {
    private final String power;

    @Autowired
    public EngineImpl(@Value("power") int power) {
        this.power = power + " hp";
    }

    @Override
    public String power() {
        return power;
    }
}
