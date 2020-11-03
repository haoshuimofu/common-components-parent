package com.demo.components.redis.cluster;

/**
 * @author wude
 * @date 2020/11/3 14:03
 */
public class CacheObject {


    private String name;
    private Integer age;


    public CacheObject() {
    }

    public CacheObject(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}