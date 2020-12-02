package com.vanderkast.edu.car.model;

import com.vanderkast.edu.injector.Autowired;
import com.vanderkast.edu.injector.Value;

public class Car {
    private final Body body;
    private final Engine engine;
    private final String number;

    @Autowired
    public Car(Body body,
               Engine engine,
               @Value(value = "car-number") String number) {
        this.body = body;
        this.engine = engine;
        this.number = number;
    }

    public String description() {
        return String.format("%s car with engine of %s and number %s",
                body.color(),
                engine.power(),
                number);
    }
}
