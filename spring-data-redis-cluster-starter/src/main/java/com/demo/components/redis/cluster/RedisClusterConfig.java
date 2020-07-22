package com.demo.components.redis.cluster;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * RedisCluster配置
 *
 * @Author wude
 * @Create 2019-04-17 18:07
 */
@Configuration
public class RedisClusterConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisClusterConfigurationProperties redisClusterConfigurationProperties() {
        return new RedisClusterConfigurationProperties();
    }

    @Bean
    public RedisClusterConfiguration redisClusterConfiguration(RedisClusterConfigurationProperties properties) {
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        List<RedisNode> redisNodes = Stream.of(properties.getClusterNodes().split(",")).map(hostAndPort -> {
            String[] redisNodeInfo = hostAndPort.split(":");
            return new RedisNode(redisNodeInfo[0], Integer.parseInt(redisNodeInfo[1]));
        }).collect(Collectors.toList());
        redisClusterConfiguration.setClusterNodes(redisNodes);
        if (properties.getMaxRedirects() > 0) {
            redisClusterConfiguration.setMaxRedirects(properties.getMaxRedirects());
        }
        return redisClusterConfiguration;
    }

    @Bean
    @ConfigurationProperties(prefix = "jedis.pool")
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }

    public @Bean
    RedisConnectionFactory connectionFactory(RedisClusterConfiguration redisClusterConfiguration, JedisPoolConfig jedisPoolConfig) {
        return new JedisConnectionFactory(redisClusterConfiguration, jedisPoolConfig);
    }

    @Bean
    @Primary
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // org.springframework.data.redis.serializer.SerializationException: Cannot serialize;
        // nested exception is org.springframework.core.serializer.support.SerializationFailedException:
        // Failed to serialize object using DefaultSerializer;
        // nested exception is java.lang.IllegalArgumentException: DefaultSerializer requires a Serializable payload but received an object of type [com.demo.boot.user.model.User]
        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }

    @Bean("stringRedisTemplate")
    @ConditionalOnBean(RedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    public CacheManager redisCacheManager(@Qualifier("stringRedisTemplate") RedisTemplate stringRedisTemplate, RedisTemplate redisTemplate) {
        return new CacheManager(stringRedisTemplate, redisTemplate);
    }

}