package com.demo.clazz;

/**
 * @author eleme
 * @datetime 2021-08-17 下午4:55
 */
public class User {

    private String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static User duser = new User("default");
    public static void main(String[] args) {
        User user = new User("wude");
        handle(user);
        System.err.println(user.getUsername());
    }

    public static void handle(User user) {
        // 把引用指向的内容变更了
        user.setUsername("handle");
        // 把执行变了，但外部的引用没变
        user = duser;
        System.err.println(user.getUsername());
    }
}
