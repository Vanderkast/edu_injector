package com.vanderkast.edu.injector;

public interface Injector {
    <T, G extends T> T provide(Class<T> tClass);
}
