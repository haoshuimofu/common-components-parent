package com.demo.jdk8.supplier;

import java.util.function.Supplier;

/**
 * @author dewu.de
 * @date 2022-01-26 8:15 下午
 */
public class Lazy<T> {

    private Supplier<T> supplier;
    private T value;

    public T get() {
        if (value == null) {
            value = supplier.get();
        }
        return value;
    }

}
