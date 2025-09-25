package org.example.uberproject_location.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("localhost");
        jedisConnectionFactory.setPort(6379);
        return jedisConnectionFactory;

    }
    @Bean   //Spring will call this method once at startup, and the returned object will be managed by the Spring IoC container.
    public RedisTemplate<String , String > redisTemplate() {    // RedisTemplate<K, V> is a Spring Data Redis class that provides high-level abstraction for Redis operations (get, set, hash operations, list, etc.).
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());    // how to connect to the Redis server.
        redisTemplate.setKeySerializer(new StringRedisSerializer());  //StringRedisSerializer ensures that keys are stored as human-readable strings in Redis.
          redisTemplate.setValueSerializer(new StringRedisSerializer());
          return redisTemplate;
    }
}
