package com.vanderkast.edu.car;

import com.vanderkast.edu.car.model.Car;
import com.vanderkast.edu.injector.PropertyBasedInjector;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        var properties = new Properties();

        try (var input = new FileInputStream(args[0])) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        var injector = new PropertyBasedInjector(properties);

        try {
            var car = injector.provide(Car.class);
            System.out.println("Finally, you are in " + car.description());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
