package com.demo.components.redis.cluster;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RedisCluster配置
 *
 * @Author wude
 * @Create 2019-04-17 18:07
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisClusterConfig {

//    @Bean("customRedisProperties")
//    @ConfigurationProperties(prefix = "spring.redis.custom")
//    public RedisProperties customRedisProperties() {
//        return new RedisProperties();
//    }
//
//    @Bean("customRedisClusterConfiguration")
//    public RedisClusterConfiguration redisClusterConfiguration(@Qualifier("customRedisProperties") RedisProperties redisProperties) {
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
//        List<RedisNode> redisNodes = redisProperties.getCluster().getNodes().stream().map(node -> {
//            String[] nodeInfo = node.split(":");
//            return new RedisNode(nodeInfo[0], Integer.parseInt(nodeInfo[1]));
//        }).collect(Collectors.toList());
//        redisClusterConfiguration.setClusterNodes(redisNodes);
//        if (redisProperties.getPassword() != null && redisProperties.getPassword().length() > 0) {
//            redisClusterConfiguration.setPassword(redisProperties.getPassword());
//        }
//        if (redisProperties.getCluster().getMaxRedirects() != null && redisProperties.getCluster().getMaxRedirects() > 0) {
//            redisClusterConfiguration.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());
//        }
//        return redisClusterConfiguration;
//    }
//
//    @Bean("customRedisConnectionFactory")
//    public RedisConnectionFactory connectionFactory(@Qualifier("customRedisClusterConfiguration") RedisClusterConfiguration redisClusterConfiguration,
//                                                    @Qualifier("customRedisProperties") RedisProperties redisProperties) {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        RedisProperties.Pool jedisPool = redisProperties.getJedis().getPool();
//        jedisPoolConfig.setMaxTotal(jedisPool.getMaxActive());
//        jedisPoolConfig.setMaxIdle(jedisPool.getMaxIdle());
//        jedisPoolConfig.setMinIdle(jedisPool.getMinIdle());
//        jedisPoolConfig.setMaxWaitMillis(jedisPool.getMaxWait().toMillis());
//        return new JedisConnectionFactory(redisClusterConfiguration, jedisPoolConfig);
//    }
//
//    @Bean
//    @Primary
//    public RedisTemplate<String, Object> redisTemplate(@Qualifier("customRedisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new FastJsonRedisSerializer(Object.class));
//        redisTemplate.setHashKeySerializer(redisTemplate.getKeySerializer());
//        redisTemplate.setHashValueSerializer(redisTemplate.getValueSerializer());
//        return redisTemplate;
//    }

    @Bean
    public RedisConnectionFactory connectionFactory(RedisProperties redisProperties) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        RedisProperties.Pool jedisPool = redisProperties.getJedis().getPool();
        jedisPoolConfig.setMaxTotal(jedisPool.getMaxActive());
        jedisPoolConfig.setMaxIdle(jedisPool.getMaxIdle());
        jedisPoolConfig.setMinIdle(jedisPool.getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(jedisPool.getMaxWait().toMillis());
        return new JedisConnectionFactory(jedisPoolConfig);
    }

    @Bean("stringRedisTemplate")
    @ConditionalOnBean(RedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    public CacheManager redisCacheManager(@Qualifier("stringRedisTemplate") StringRedisTemplate stringRedisTemplate) {
        return new CacheManager(stringRedisTemplate);
    }

}