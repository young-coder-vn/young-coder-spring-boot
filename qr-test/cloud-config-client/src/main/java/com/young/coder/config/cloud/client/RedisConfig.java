package com.young.coder.config.cloud.client;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Configuration
public class RedisConfig {

    @Value("${spring.redis.port:Data Null}")
    private int port;

    @Value("${spring.redis.host:Data Null}")
    private String host;

    @Value("${spring.redis.password:Data Null}")
    private String password;

    @Value("${spring.redis.database:Data Null}")
    private String database;

    @Value("${spring.redis.jedis.pool.max-idle:Data Null}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-active:Data Null}")
    private int maxActive;

    @Value("${spring.redis.sentinel.master:Data Null}")
    private String master;

    @Value("${spring.redis.sentinel.nodes:Data Null}")
    private List<String> nodes;

    @Value("${spring.redis.timeout:Data Null}")
    private int timeout;

    @Bean
    public RedisConfig initRedisConfig() {
        return RedisConfig.builder()
                .port(port)
                .host(host)
                .password(password)
                .database(database)
                .master(master)
                .maxActive(maxActive)
                .maxIdle(maxIdle)
                .timeout(timeout)
                .nodes(nodes)
                .build();
    }
}
