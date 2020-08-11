package com.young.coder.spring.redis;
/*******************************************************
 * For Vietnamese readers:
 *    Các bạn thân mến, mình rất vui nếu project này giúp 
 * ích được cho các bạn trong việc học tập và công việc. Nếu 
 * bạn sử dụng lại toàn bộ hoặc một phần source code xin để 
 * lại dường dẫn tới github hoặc tên tác giá.
 *    Xin cảm ơn!
 *******************************************************/

import io.lettuce.core.RedisURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2019 {@author Loda} (https://loda.me).
 * This project is licensed under the MIT license.
 *
 * @since 5/4/2019
 * Github: https://github.com/loda-kun
 */
@Configuration
public class RedisConfig {
//    @Value("${spring.redis.port:Data Null}")
//    private int port;
//
//    @Value("${spring.redis.host:Data Null}")
//    private String host;
//
//    @Value("${spring.redis.password:Data Null}")
//    private String password;
//
//    @Value("${spring.redis.database:Data Null}")
//    private int database;
//
//    @Value("${spring.redis.jedis.pool.max-idle:Data Null}")
//    private int maxIdle;
//
//    @Value("${spring.redis.jedis.pool.max-active:Data Null}")
//    private int maxActive;
//
//    @Value("${spring.redis.sentinel.master:Data Null}")
//    private String master;
//
//    @Value("${spring.redis.sentinel.nodes:Data Null}")
//    private List<String> nodes;
//
//    @Value("${spring.redis.timeout:Data Null}")
//    private int timeout;

    @Bean
    @Primary
    public RedisConnectionFactory lettuceConnectionFactory() {
//        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration().master("redismaster").sentinel("10.22.7.111", 26379).sentinel("10.22.7.112", 26379);
        RedisStandaloneConfiguration sentinelConfig = new RedisStandaloneConfiguration("10.22.7.111", 6379);
        sentinelConfig.setPassword("testredis@123");
        sentinelConfig.setDatabase(11);
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(sentinelConfig);
        return lettuceConnectionFactory;
    }

    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(serializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
