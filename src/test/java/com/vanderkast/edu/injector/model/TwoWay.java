package com.vanderkast.edu.injector.model;

public class TwoWay {
    public static final String ANON = "Anonymous";

    private final String name;

    public TwoWay() {
        name = ANON;
    }

    public TwoWay(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
