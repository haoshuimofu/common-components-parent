package com.demo.components.guava;

import com.demo.components.caffeine.CachedUser;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import java.util.concurrent.TimeUnit;

/**
 * @author dewu.de
 * @date 2022-09-28 7:42 下午
 */
public class GuavaTest {

    public static void main(String[] args) {
        Cache<String, CachedUser> users = CacheBuilder.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build(new CacheLoader<String, CachedUser>() {
                    @Override
                    public CachedUser load(String key) throws Exception {
                        return createUser(key);
                    }
                });

        CachedUser user = users.getIfPresent("wangbo");
        System.err.println(user == null);

        users.put("wangbo", new CachedUser());
        user = users.getIfPresent("wangbo");
        System.err.println(user == null);

        user = users.getIfPresent("wangbo");
        System.err.println(user == null);
    }

    private static CachedUser createUser(Object name) {
        System.err.println("createUser: " + name);
        return null;
    }

}
