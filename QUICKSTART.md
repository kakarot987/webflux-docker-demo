# Quick Start Guide

## ⚡ 30-Second Setup

### Prerequisites
- Java 23+
- Docker & Docker Compose
- Git

### Start Application

```bash
# Navigate to project
cd webflux-docker-demo

# Build and start with Docker Compose
docker-compose up -d

# Wait 10 seconds and test
curl http://localhost:8080/api/hello
```

## 🧪 Test Endpoints

### Using PowerShell (Windows)
```powershell
.\test-api.ps1
```

### Using Bash (Linux/Mac)
```bash
chmod +x test-api.sh
./test-api.sh
```

### Using curl
```bash
# Basic endpoints
curl http://localhost:8080/api/hello
curl http://localhost:8080/api/info
curl http://localhost:8080/api/health

# Reactive examples
curl http://localhost:8080/api/async-delay/2
curl http://localhost:8080/api/reactive-chain/10

# Streaming (Server-Sent Events)
curl http://localhost:8080/api/stream
```

## 🐳 Docker Commands

```bash
# Start
docker-compose up -d

# View logs
docker-compose logs -f webflux-app

# Stop
docker-compose down

# Stop and remove data
docker-compose down -v
```

## 📊 Available Endpoints

| Endpoint | Method | Description | Example |
|----------|--------|-------------|---------|
| `/api/hello` | GET | Simple greeting | `curl localhost:8080/api/hello` |
| `/api/info` | GET | App info | `curl localhost:8080/api/info` |
| `/api/health` | GET | Health check | `curl localhost:8080/api/health` |
| `/api/stream` | GET | SSE stream | `curl localhost:8080/api/stream` |
| `/api/numbers` | GET | Number stream | `curl localhost:8080/api/numbers` |
| `/api/async-delay/{sec}` | GET | Async delay | `curl localhost:8080/api/async-delay/3` |
| `/api/reactive-chain/{num}` | GET | Reactive chain | `curl localhost:8080/api/reactive-chain/10` |

## 🚀 Local Development

```bash
# Build
./gradlew.bat build

# Run locally
./gradlew.bat bootRun

# Run tests
./gradlew.bat test
```

## 📈 View Metrics

```bash
# Health endpoint
curl http://localhost:8080/health

# Metrics
curl http://localhost:8080/metrics
```

## 🛑 Stop & Cleanup

```bash
# Stop containers
docker-compose down

# Remove everything
docker-compose down -v
docker image rm webflux-docker-demo:latest
```

## ❓ Troubleshooting

### Port 8080 already in use
```bash
# Find what's using port 8080
netstat -ano | findstr :8080  # Windows
lsof -i :8080  # Mac/Linux

# Change port in docker-compose.yml: "8081:8080"
```

### Docker not running
```bash
# Start Docker Desktop and retry
docker-compose up -d
```

### Build fails
```bash
# Clean and rebuild
./gradlew.bat clean build
docker-compose up --build -d
```

## 📚 Documentation

- **README.md** - Complete documentation
- **ENHANCEMENTS.md** - Detailed list of all changes
- **Dockerfile** - Container configuration
- **docker-compose.yml** - Service orchestration

## 💡 What's Demonstrated

✅ **WebFlux Reactive Programming**
- Mono (single value)
- Flux (stream of values)
- Reactive operators
- Non-blocking I/O
- Server-Sent Events

✅ **Docker Best Practices**
- Multi-stage builds
- Alpine base images
- Health checks
- Container optimization
- Docker Compose

✅ **Spring Boot Features**
- Actuator monitoring
- Health endpoints
- Metrics collection
- Application properties configuration

---

**Happy coding! 🚀**

