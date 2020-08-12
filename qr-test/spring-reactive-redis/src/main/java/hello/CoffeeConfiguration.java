package hello;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class CoffeeConfiguration {

    @Bean
    @Primary
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
//        RedisSentinelConfiguration redisconfig = new RedisSentinelConfiguration().master("redismaster").sentinel("10.22.7.111", 26379).sentinel("10.22.7.112", 26379);
        RedisStandaloneConfiguration redisconfig = new RedisStandaloneConfiguration("10.22.7.122", 6379);
        redisconfig.setPassword("testredis@123");
        redisconfig.setDatabase(11);
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisconfig);
        lettuceConnectionFactory.setDatabase(11);
        return lettuceConnectionFactory;
    }

    @Bean
    @Primary
    public ReactiveRedisOperations<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
        RedisSerializer<String> serializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(String.class);
        RedisSerializationContext serializationContext = RedisSerializationContext
                .<String, String>newSerializationContext()
                .key(serializer)
                .value(jackson2JsonRedisSerializer)
                .hashKey(serializer)
                .hashValue(jackson2JsonRedisSerializer)
                .build();

        return new ReactiveRedisTemplate<String, String>(connectionFactory, serializationContext);
    }

}
