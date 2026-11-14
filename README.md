# WebFlux Docker Demo

Focused Spring Boot WebFlux and Project Reactor demonstrations in a reproducible Docker image. Java **21**, Spring Boot **3.4.2**, and one service (no reactive database).

## Start from a clean clone

Prerequisites: Docker Engine/Desktop with Docker Compose v2. No host-side JDK or prebuilt JAR is needed for Docker.

```sh
git clone https://github.com/kakarot987/webflux-docker-demo.git
cd webflux-docker-demo
docker compose up --build -d
docker compose ps
curl -f http://localhost:8081/actuator/health
curl http://localhost:8081/api/hello
```

Open the embedded [dashboard](http://localhost:8081/dashboard.html). The dashboard uses same-origin `/api` requests, so it also works when accessed from a different computer through the server's hostname.

**Port mapping:** Docker Compose publishes host **8081** -> container **8080**. Local `./gradlew bootRun` listens on 8080 instead. For `docker run`, choose a port using `-p 8081:8080`.

## API

| Endpoint | Description |
| --- | --- |
| `/api/hello` | Mono greeting |
| `/api/info` | Application information |
| `/api/health` | Demonstration health response |
| `/api/async-delay/{seconds}` | Non-blocking response delay, integer 0–30 |
| `/api/reactive-chain/{number}` | Filter, map, flatMap; positive integer safe for doubling |
| `/api/stream` | Ten finite SSE events, about one per second |
| `/api/numbers` | One hundred finite SSE integer events |
| `/actuator/health` | Spring Boot Actuator health |
| `/actuator/metrics` | Actuator metrics index |

```sh
curl -N http://localhost:8081/api/stream
curl -N http://localhost:8081/api/numbers
curl -i http://localhost:8081/api/async-delay/-1  # HTTP 400
curl -i http://localhost:8081/api/reactive-chain/0 # HTTP 400
```

## Tests and build

```sh
./gradlew test                 # requires Java 21
./gradlew bootJar
docker build -t webflux-docker-demo .  # compiles the JAR inside Docker
docker compose down
```

Tests use WebTestClient and StepVerifier for happy paths, invalid inputs, Actuator health, and finite stream completion. The SSE tests take approximately ten seconds each in real time. GitHub Actions runs tests and a clean Docker Compose smoke check on pushes and pull requests.

## Configuration and design boundaries

The runtime runs as an unprivileged user; `JAVA_TOOL_OPTIONS` in Compose configures JVM memory without a shell entrypoint. The Docker/Compose health probe uses Actuator `/actuator/health` and the Alpine image's `wget`. CORS has one restrictive filter for optional local development clients; the embedded dashboard is same-origin.

This is **not** a performance benchmark, evidence of backpressure under load, reactive database access, or a production deployment. Reactor supports backpressure, but this demo does not measure throughput, concurrency, or memory advantages compared with Spring MVC. No image-size or performance savings are asserted without measurement.
