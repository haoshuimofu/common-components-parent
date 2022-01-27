package com.demo.jdk8.supplier;

import java.util.function.Supplier;

/**
 * @author dewu.de
 * @date 2022-01-26 7:59 下午
 */
public class SupplierDemo {

    public static void main(String[] args) {
        String msgA = "Hello ";
        String msgB = "World ";
        System.out.println(
                getString(
                        () -> msgA + msgB + "a"
                )
        );

        Supplier<Integer> supplier = () -> 1+3;
        System.out.println(supplier.get());

    }

    private static String getString(Supplier<String> stringSupplier) {
        return stringSupplier.get();
    }


}
