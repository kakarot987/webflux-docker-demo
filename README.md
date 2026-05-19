# WebFlux Docker Demo

A comprehensive Spring Boot WebFlux application demonstrating reactive programming and Docker containerization.

## Features

### WebFlux Capabilities
- **Reactive Endpoints**: Full non-blocking, event-driven architecture
- **Streaming**: Server-Sent Events (SSE) for real-time data streaming
- **Async Operations**: Demonstrates async delays and reactive chains
- **Flux & Mono**: Examples of both single-value and multi-value reactive types
- **Operators**: Using operators like `map`, `filter`, `flatMap`, `delayElement`, etc.

### Docker Enhancements
- **Multi-Stage Build**: Optimized Dockerfile with builder and runtime stages
- **Alpine Base Image**: Lightweight image for reduced footprint
- **Health Checks**: Docker health checks to monitor application status
- **Docker Compose**: Easy deployment and orchestration
- **Optimized JVM Settings**: G1GC and memory optimization for containers

### Actuator & Monitoring
- Health check endpoints
- Metrics exposure
- Application info endpoints

## API Endpoints

### Core Endpoints
- **`GET /api/hello`** - Simple greeting message
- **`GET /api/info`** - Application information
- **`GET /api/health`** - Health status check

### WebFlux Examples
- **`GET /api/stream`** - Server-Sent Events stream (10 events at 1 second intervals)
- **`GET /api/numbers`** - Server-Sent Events with numbered stream
- **`GET /api/async-delay/{seconds}`** - Delayed response demonstration
- **`GET /api/reactive-chain/{number}`** - Complex reactive chain with processing

### Monitoring
- **`GET /health`** - Spring Boot health endpoint (via Actuator)
- **`GET /metrics`** - Application metrics (via Actuator)

## Prerequisites

- Java 23+
- Docker & Docker Compose
- Gradle (or use `./gradlew` wrapper)

## Building

### Build the application:
```bash
./gradlew build
```

### Build Docker image:
```bash
docker build -t webflux-docker-demo:latest .
```

## Running

### Using Docker Compose (Recommended):
```bash
docker-compose up -d
```

### Using Docker directly:
```bash
docker build -t webflux-docker-demo:latest .
docker run -p 8080:8080 webflux-docker-demo:latest
```

### Running locally:
```bash
./gradlew bootRun
```

## Testing the Endpoints

### Using curl:
```bash
# Simple greeting
curl http://localhost:8080/api/hello

# Application info
curl http://localhost:8080/api/info

# Health check
curl http://localhost:8080/api/health

# Stream events
curl http://localhost:8080/api/stream

# Async operation (5 second delay)
curl http://localhost:8080/api/async-delay/5

# Reactive chain
curl http://localhost:8080/api/reactive-chain/10

# Get numbers stream
curl http://localhost:8080/api/numbers
```

### Using WebUI tools:
- Use PostMan, Insomnia, or Thunder Client
- For streaming endpoints, ensure client supports chunked transfer encoding
- Content-Type for streams: `text/event-stream`

## Docker Compose Commands

```bash
# Start the application
docker-compose up -d

# View logs
docker-compose logs -f webflux-app

# Check service status
docker-compose ps

# Stop the application
docker-compose down

# Remove volumes and containers
docker-compose down -v
```

## Docker Features Demonstrated

1. **Multi-Stage Build**: Reduces final image size by separating build and runtime
2. **Alpine Linux**: Uses JRE Alpine for minimal footprint (~200MB vs 500MB+ with full JDK)
3. **Health Checks**: Automatic monitoring and restart on failure
4. **Optimized JVM**: G1GC settings optimized for containerized environments
5. **Environment Variables**: Configurable Java options via Docker environment
6. **Networks**: Docker Compose network isolation

## WebFlux Features Demonstrated

1. **Mono<T>**: Single value async operations
2. **Flux<T>**: Stream of values with backpressure support
3. **Operators**: 
   - `map()` - Transform values
   - `filter()` - Filter values
   - `flatMap()` - Async transformation
   - `delayElement()` - Add latency
   - `take()` - Limit items
4. **Error Handling**: `switchIfEmpty()` for fallback handling
5. **Server-Sent Events**: Real-time client notifications
6. **Non-blocking I/O**: Efficient resource utilization

## Performance Characteristics

- **Non-blocking**: Uses Netty event loop (default in WebFlux)
- **Efficient**: Lower memory footprint compared to traditional servlet containers
- **Scalable**: Handles high concurrency with minimal threads
- **Container-optimized**: G1GC settings for predictable garbage collection

## Configuration

See `src/main/resources/application.properties` for:
- Server port (8080)
- Compression settings
- Logging levels
- Actuator exposure
- WebFlux base path

## Architecture

```
webflux-docker-demo/
├── Dockerfile (multi-stage build)
├── docker-compose.yml
├── build.gradle (dependencies and build config)
└── src/
    ├── main/java/com/webflux_docker/
    │   ├── WebfluxDockerApplication.java (main entry point)
    │   ├── ReactiveController.java (API endpoints)
    │   ├── AppInfo.java (model)
    │   ├── ProcessedData.java (model)
    │   └── HealthStatus.java (model)
    └── resources/
        └── application.properties
```

## Next Steps for Enhancement

1. Add database integration with R2DBC (reactive database)
2. Implement error handling with global exception handlers
3. Add request/response logging with WebFlux filters
4. Include integration tests with WebTestClient
5. Add security with Spring Security WebFlux
6. Implement circuit breaker pattern with Resilience4j

## License

MIT License - Feel free to use and modify

