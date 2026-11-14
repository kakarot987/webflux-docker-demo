package com.webflux_docker;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
class WebfluxDockerApplicationTests {

    @Autowired
    private WebTestClient client;

    @Test
    void hello() {
        client.get().uri("/api/hello").exchange().expectStatus().isOk()
            .expectBody(String.class)
            .isEqualTo("Hello from a Reactive WebFlux App running in Docker!");
    }

    @Test
    void info() {
        client.get().uri("/api/info").exchange().expectStatus().isOk()
            .expectBody().jsonPath("$.name").isEqualTo("WebFlux Docker Demo")
            .jsonPath("$.javaVersion").exists();
    }

    @Test
    void customHealth() {
        client.get().uri("/api/health").exchange().expectStatus().isOk()
            .expectBody().jsonPath("$.status").isEqualTo("UP");
    }

    @Test
    void actuatorHealth() {
        client.get().uri("/actuator/health").exchange().expectStatus().isOk()
            .expectBody().jsonPath("$.status").isEqualTo("UP");
    }

    @Test
    void asyncDelay() {
        client.mutate().responseTimeout(Duration.ofSeconds(5)).build()
            .get().uri("/api/async-delay/1").exchange().expectStatus().isOk()
            .expectBody(String.class).isEqualTo("Delayed response after 1 seconds");
    }

    @Test
    void invalidDelaysReturnBadRequest() {
        client.get().uri("/api/async-delay/-1").exchange().expectStatus().isBadRequest();
        client.get().uri("/api/async-delay/31").exchange().expectStatus().isBadRequest();
    }

    @Test
    void reactiveChain() {
        client.get().uri("/api/reactive-chain/5").exchange().expectStatus().isOk()
            .expectBody().jsonPath("$.value").isEqualTo(10)
            .jsonPath("$.message").isEqualTo("Processed value: 10");
    }

    @Test
    void invalidChainsReturnBadRequest() {
        client.get().uri("/api/reactive-chain/-5").exchange().expectStatus().isBadRequest();
        client.get().uri("/api/reactive-chain/0").exchange().expectStatus().isBadRequest();
        client.get().uri("/api/reactive-chain/2147483647").exchange().expectStatus().isBadRequest();
    }

    @Test
    void eventStreamCompletes() {
        WebTestClient streamingClient = client.mutate().responseTimeout(Duration.ofSeconds(20)).build();
        var result = streamingClient.get().uri("/api/stream").exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.TEXT_EVENT_STREAM)
            .returnResult(String.class);
        StepVerifier.create(result.getResponseBody())
            .expectNextCount(10)
            .verifyComplete();
    }

    @Test
    void numberStreamCompletes() {
        WebTestClient streamingClient = client.mutate().responseTimeout(Duration.ofSeconds(20)).build();
        var result = streamingClient.get().uri("/api/numbers").exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.TEXT_EVENT_STREAM)
            .returnResult(Integer.class);
        StepVerifier.create(result.getResponseBody())
            .expectNextCount(100)
            .verifyComplete();
    }
}
