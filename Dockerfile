# Multi-stage build: Build stage
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /build
COPY build/libs/webflux-docker-0.0.1-SNAPSHOT.jar app.jar

# Extract layers for optimized layering
RUN java -Djarmode=layertools -jar app.jar extract

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy layers from builder
COPY --from=builder /build/spring-boot-loader/ ./
COPY --from=builder /build/dependencies/ ./
COPY --from=builder /build/snapshot-dependencies/ ./
COPY --from=builder /build/application/ ./

# Add health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/api/health || exit 1

EXPOSE 8080

# Run application with optimizations
ENTRYPOINT ["java", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=200", "-XX:+ParallelRefProcEnabled", "org.springframework.boot.loader.launch.JarLauncher"]
