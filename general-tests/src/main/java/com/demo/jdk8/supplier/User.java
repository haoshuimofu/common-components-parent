package com.demo.jdk8.supplier;

/**
 * @author dewu.de
 * @date 2022-01-26 8:13 下午
 */
public class User {

    private String id;
    private Lazy<String> department;

    private String getDepartment() {
        // RPC

        return department.get();
    }

}
