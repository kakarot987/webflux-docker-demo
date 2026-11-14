# Compile from source: no host-built JAR is required.
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /build
COPY gradle/ gradle/
COPY gradlew settings.gradle build.gradle ./
COPY src/ src/
RUN chmod +x gradlew && ./gradlew --no-daemon clean bootJar -x test

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S app && adduser -S -G app app
COPY --from=builder --chown=app:app /build/build/libs/*.jar /app/app.jar
USER app
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
    CMD wget -q -O /dev/null http://localhost:8080/actuator/health || exit 1
ENTRYPOINT ["java", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=200", "-jar", "/app/app.jar"]
