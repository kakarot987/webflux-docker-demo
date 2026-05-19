package com.webflux_docker;

import java.time.Duration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ReactiveController {

    @GetMapping("/hello")
    public Mono<String> sayHello() {
        return Mono.just("Hello from a Reactive WebFlux App running in Docker!");
    }

    @GetMapping("/info")
    public Mono<AppInfo> getInfo() {
        return Mono.just(new AppInfo(
            "WebFlux Docker Demo",
            "1.0.0",
            "Demonstrating Spring WebFlux with Docker containerization",
            System.getProperty("java.version")
        ));
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getStream() {
        return Flux.interval(Duration.ofSeconds(1))
            .map(index -> "Event " + (index + 1) + " at " + System.currentTimeMillis())
            .take(10);
    }

    @GetMapping("/async-delay/{seconds}")
    public Mono<String> asyncDelay(@PathVariable long seconds) {
        return Mono.delay(Duration.ofSeconds(seconds))
            .map(x -> "Delayed response after " + seconds + " seconds");
    }

    @GetMapping(value = "/numbers", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> getNumbers() {
        return Flux.range(1, 100)
                .delayElements(Duration.ofMillis(100))
            .log();
    }

    @GetMapping("/reactive-chain/{number}")
    public Mono<ProcessedData> reactiveChain(@PathVariable int number) {
        return Mono.just(number)
            .filter(n -> n > 0)
            .map(n -> n * 2)
            .flatMap(n -> Mono.delay(Duration.ofMillis(500))
                .map(x -> new ProcessedData(n, "Processed value: " + n)))
            .switchIfEmpty(Mono.error(new IllegalArgumentException("Number must be positive")));
    }

    @GetMapping("/health")
    public Mono<HealthStatus> health() {
        return Mono.just(new HealthStatus("UP", "Application is running", System.currentTimeMillis()));
    }
}
