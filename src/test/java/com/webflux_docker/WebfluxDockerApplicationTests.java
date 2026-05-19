package com.webflux_docker;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
public class WebfluxDockerApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void testHelloEndpoint() {
		webTestClient.get()
			.uri("/api/hello")
			.exchange()
			.expectStatus().isOk()
			.expectBody(String.class)
			.isEqualTo("Hello from a Reactive WebFlux App running in Docker!");
	}

	@Test
	public void testInfoEndpoint() {
		webTestClient.get()
			.uri("/api/info")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.name").isEqualTo("WebFlux Docker Demo")
			.jsonPath("$.version").isEqualTo("1.0.0")
			.jsonPath("$.javaVersion").exists();
	}

	@Test
	public void testHealthEndpoint() {
		webTestClient.get()
			.uri("/api/health")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.status").isEqualTo("UP")
			.jsonPath("$.message").isEqualTo("Application is running")
			.jsonPath("$.timestamp").exists();
	}

	@Test
	public void testAsyncDelayEndpoint() {
		webTestClient.get()
			.uri("/api/async-delay/1")
			.exchange()
			.expectStatus().isOk()
			.expectBody(String.class)
			.isEqualTo("Delayed response after 1 seconds");
	}

	@Test
	public void testReactiveChainEndpoint() {
		webTestClient.get()
			.uri("/api/reactive-chain/5")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$.value").isEqualTo(10)
			.jsonPath("$.message").isEqualTo("Processed value: 10");
	}

	@Test
	public void testReactiveChainWithInvalidInput() {
		webTestClient.get()
			.uri("/api/reactive-chain/-5")
			.exchange()
			.expectStatus().is5xxServerError();
	}

	@Test
	public void testStreamEndpoint() {
		webTestClient.get()
			.uri("/api/stream")
			.exchange()
			.expectStatus().isOk()
			.expectHeader()
			.contentType(MediaType.TEXT_EVENT_STREAM)
			.expectBodyList(String.class)
			.hasSize(10);
	}

	@Test
	public void testNumbersStream() {
		webTestClient.get()
			.uri("/api/numbers")
			.exchange()
			.expectStatus().isOk()
			.expectHeader()
			.contentType(MediaType.TEXT_EVENT_STREAM)
			.expectBodyList(Integer.class)
			.hasSize(100);
	}

}


