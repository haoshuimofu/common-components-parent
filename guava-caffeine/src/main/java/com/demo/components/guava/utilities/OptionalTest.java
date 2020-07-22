package com.demo.components.guava.utilities;

import com.google.common.base.Optional;
import com.google.common.base.Strings;

/**
 * Optional工具类测试
 *
 * @author wude
 * @date 2019/11/21 11:27
 */
public class OptionalTest {

    public static void main(String[] args) {
        Optional<Integer> possible = Optional.of(5); // checkNotNull
//        possible = Optional.absent();
//        possible.isPresent(); // returns true
//        possible.get(); // returns 5

        if (possible.isPresent()) {
            System.out.println("isPresent: " + possible.get());
        } else {
            System.err.println("absent!");
        }

        Integer a = null;
        possible = Optional.fromNullable(a); // guava api
        if (possible.isPresent()) {
            System.out.println("guava api: fromNullable isPresent: " + possible.get());
        } else {
            System.err.println("guava api: fromNullable absent!");
        }

        // java api
        java.util.Optional optional = java.util.Optional.ofNullable(a);
//        System.err.println(optional.get());
        System.out.println(optional.orElse(1000));

        System.out.println(optional.orElseGet(() -> {
            int x = 5;
            int y = 8;
            return x * y;
        }));

        // MoreObjects 都为null则报错
//        System.err.println(Optional.of(MoreObjects.firstNonNull(null, null)).or(" sss"));

        System.out.println("Strings method: " + Strings.isNullOrEmpty(" "));
    }

}