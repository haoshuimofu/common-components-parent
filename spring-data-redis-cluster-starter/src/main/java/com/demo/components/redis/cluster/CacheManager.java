package com.demo.components.redis.cluster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;

/**
 * Redis缓存
 *
 * @Author ddmc
 * @Create 2019-04-25 15:55
 */
@Service
public class CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(CacheManager.class);

    private String lockScript = "";
    private String lockReleaseScript = "";

    //    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;

    public CacheManager(StringRedisTemplate stringRedisTemplate) {
        Assert.notNull(stringRedisTemplate, "stringRedisTemplate is null");
        this.stringRedisTemplate = stringRedisTemplate;
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("redis-lock.lua");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line).append(" ");
            }
            lockScript = sb.toString();
            System.out.println(lockScript);
            br.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sb.setLength(0);
        is = this.getClass().getClassLoader().getResourceAsStream("lock-release.lua");
        br = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line).append(" ");
            }
            lockReleaseScript = sb.toString();
            System.out.println(lockReleaseScript);
            br.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("redis-loc.lua=" + lockScript);

    }
//
//    public RedisTemplate<String, Object> getRedisTemplate() {
//        return redisTemplate;
//    }

    public StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    public boolean tryLock(String key, String value, long ttlSeconds) {
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>(lockScript, String.class);
        String result = getStringRedisTemplate().execute(redisScript, Collections.singletonList(key), value, String.valueOf(ttlSeconds));
        return "1".equals(result);
    }

    public boolean releaseLock(String key, String value) {
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>(lockReleaseScript, String.class);
        String result = getStringRedisTemplate().execute(redisScript, Collections.singletonList(key), value);
        return "1".equals(result);
    }

    public long bitCount(final String key) {
        return getStringRedisTemplate().execute((RedisCallback<Long>) connection -> connection.bitCount(key.getBytes()));
    }

    public Long bitCount(String key, int start, int end) {
        return getStringRedisTemplate().execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes(), start, end));
    }

    public Long bitOp(RedisStringCommands.BitOperation op, String saveKey, String... desKey) {
        byte[][] bytes = new byte[desKey.length][];
        for (int i = 0; i < desKey.length; i++) {
            bytes[i] = desKey[i].getBytes();
        }
        return getStringRedisTemplate().execute((RedisCallback<Long>) con -> con.bitOp(op, saveKey.getBytes(), bytes));
    }

}