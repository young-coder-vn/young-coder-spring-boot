package hello;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

@RestController
public class CoffeeController {
    @Autowired
    protected ReactiveRedisOperations<String, String> reactiveRedisTemplate;
    private static Gson GSON = new Gson();

    @GetMapping("/coffees")
    public Flux all() {

        ExecutorService service = Executors.newFixedThreadPool(10);
        Flux.just(1, 2, 3, 4)
                .log()
                .subscribeOn(Schedulers.fromExecutorService(service))
                .subscribe();

        Flux.just(5, 6, 7, 8)
                .log()
                .subscribeOn(Schedulers.fromExecutorService(service))
                .subscribe();

        ReactiveValueOperations<String, String> valueOps = reactiveRedisTemplate.opsForValue();
        ReactiveHashOperations<String, String, String> hashOperations = reactiveRedisTemplate.opsForHash();

        valueOps.set("hungv: " + UUID.randomUUID().toString(), UUID.randomUUID().toString())
                .subscribe();// phải có subcribe thì các lệnh mới được thực thi
        return reactiveRedisTemplate.keys("*")
                .subscribeOn(Schedulers.elastic())
                .flatMap(reactiveRedisTemplate.opsForValue()::get)
                .map(x -> GSON.fromJson(x, Coffee.class))
                ;
    }
}
