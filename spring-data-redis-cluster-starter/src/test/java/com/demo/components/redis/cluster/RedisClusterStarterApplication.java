package com.demo.components.redis.cluster;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author wude
 * @Create 2019-05-09 15:16
 */
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {RedisClusterStarterApplication.class})
public class RedisClusterStarterApplication {

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void test() {
    }
}