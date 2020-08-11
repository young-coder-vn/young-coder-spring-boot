package hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class CoffeeConfiguration {

    @Bean
    @Primary
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
//        RedisSentinelConfiguration redisconfig = new RedisSentinelConfiguration().master("redismaster").sentinel("10.22.7.111", 26379).sentinel("10.22.7.112", 26379);
        RedisStandaloneConfiguration redisconfig = new RedisStandaloneConfiguration("10.22.7.122", 6379);
        redisconfig.setPassword("testredis@123");
        redisconfig.setDatabase(11);
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisconfig);
        return lettuceConnectionFactory;
    }

    @Bean
    @Primary
    public ReactiveRedisOperations<String, String> redisOperations(ReactiveRedisConnectionFactory factory) {
//        Jackson2JsonRedisSerializer<String> serializer = new Jackson2JsonRedisSerializer<>(String.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, String> builder = RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, String> context = builder.value(new StringRedisSerializer()).key(new StringRedisSerializer()).build();
        return new ReactiveRedisTemplate<>(factory, context);
    }

}
