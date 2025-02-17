package com.webflux_docker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ReactiveController {

    @GetMapping("/hello")
    public Mono<String> sayHello() {
        return Mono.just("Hello from a Reactive Serverless App running in Docker!");
    }
}
