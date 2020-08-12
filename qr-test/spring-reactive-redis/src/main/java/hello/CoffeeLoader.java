package hello;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Component
public class CoffeeLoader {
    @Autowired
    private ReactiveRedisConnectionFactory factory;
    @Autowired
    private ReactiveRedisOperations<String, String> coffeeOps;
    private static Gson GSON = new Gson();

    @PostConstruct
    public void loadData() {

        factory.getReactiveConnection().ping().thenMany(
                Flux.just("Nguyễn Hưng", "Nam Khánh", "Đăng Thanh")
                        .map(x -> Coffee.builder().name(x).id(UUID.randomUUID().toString()).build()).log()
                        .flatMap(x -> coffeeOps.opsForValue().set(x.getId(), GSON.toJson(x))).log())
                .thenMany(coffeeOps.keys("*")
                        .flatMap(x -> coffeeOps.opsForValue().get(x)))
                .subscribe(System.out::println)
        ;
    }
}
