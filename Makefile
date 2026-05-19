.PHONY: help build test clean docker-build docker-run docker-stop docker-compose-up docker-compose-down docker-logs

help:
	@echo "WebFlux Docker Demo - Available Commands"
	@echo "========================================"
	@echo "make build              - Build the application with Gradle"
	@echo "make test               - Run all tests"
	@echo "make clean              - Clean build artifacts"
	@echo "make run                - Run application locally"
	@echo "make docker-build       - Build Docker image"
	@echo "make docker-run         - Run Docker container"
	@echo "make docker-stop        - Stop Docker container"
	@echo "make docker-compose-up  - Start with Docker Compose"
	@echo "make docker-compose-down- Stop Docker Compose"
	@echo "make docker-logs        - View Docker Compose logs"
	@echo "make docker-clean       - Remove all Docker containers and images"

build:
	./gradlew build

test:
	./gradlew test

clean:
	./gradlew clean

run:
	./gradlew bootRun

docker-build: build
	docker build -t webflux-docker-demo:latest .

docker-run: docker-build
	docker run -p 8080:8080 --name webflux-demo webflux-docker-demo:latest

docker-stop:
	docker stop webflux-demo || true
	docker rm webflux-demo || true

docker-compose-up: build
	docker-compose up -d

docker-compose-down:
	docker-compose down

docker-logs:
	docker-compose logs -f webflux-app

docker-clean: docker-compose-down
	docker rmi webflux-docker-demo:latest || true

test-api:
	@echo "Testing API Endpoints..."
	@echo "========================"
	@echo "\n1. Hello Endpoint:"
	curl -s http://localhost:8080/api/hello && echo "\n"
	@echo "2. Info Endpoint:"
	curl -s http://localhost:8080/api/info && echo "\n"
	@echo "3. Health Endpoint:"
	curl -s http://localhost:8080/api/health && echo "\n"
	@echo "4. Reactive Chain (value=10):"
	curl -s http://localhost:8080/api/reactive-chain/10 && echo "\n"

