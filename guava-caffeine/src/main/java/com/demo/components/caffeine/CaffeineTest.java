package com.demo.components.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * @author ddmc
 * @date 2019/11/22 9:32
 */
public class CaffeineTest {

    public static void main(String[] args) {
        LoadingCache<String, CachedUser> users = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .executor(null)
//                .buildAsync()
                .build(key -> createUser(key));


        long freeMemory = Runtime.getRuntime().freeMemory();
        CachedUser user = users.get("wangbo");
        System.err.println(user == null);

        users.put("wangbo", new CachedUser());
        user = users.get("wangbo");
        System.err.println(user == null);

        user = users.get("wangbo");
        System.err.println(user == null);
    }

    private static CachedUser createUser(String name) {
        System.err.println("createUser: " + name);
        return null;
    }
}