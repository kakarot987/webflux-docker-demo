# Quick start — WebFlux Docker Demo

**Requirements:** Docker and Compose v2. For host development/tests, use Java 21.

```sh
docker compose up --build -d
docker compose ps
curl -f http://localhost:8081/actuator/health
curl http://localhost:8081/api/hello
curl -N http://localhost:8081/api/stream
curl http://localhost:8081/actuator/metrics
```

Dashboard: http://localhost:8081/dashboard.html

Container port 8080 is mapped to host port **8081**. When running `./gradlew bootRun` outside Docker, use `http://localhost:8080` instead. See [README.md](README.md) for the API table, test commands, validation rules, and scope.

```sh
./gradlew test
docker compose logs -f webflux-app
docker compose down
```
