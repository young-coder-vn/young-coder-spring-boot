package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class CoffeeController {

    @Autowired
    private final ReactiveRedisOperations<String, String> coffeeOps;

    public CoffeeController(ReactiveRedisOperations<String, String> coffeeOps) {
        this.coffeeOps = coffeeOps;
    }

    @GetMapping("/coffees")
    public Flux all() {
        return coffeeOps.keys("*")
                .flatMap(coffeeOps.opsForValue()::get)
//                .map(x -> (Coffee) x)
                ;
    }
}
