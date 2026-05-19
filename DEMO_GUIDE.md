# WebFlux Docker Demo - Demonstration Guide

## 🚀 Quick Start (2 minutes)

### Step 1: Build & Start
```bash
cd webflux-docker-demo

# Build the application
./gradlew.bat build

# Start with Docker Compose
docker-compose up -d

# Wait 10 seconds for startup
```

### Step 2: Verify It's Running
```bash
curl http://localhost:8080/api/hello
```

You should see:
```
Hello from a Reactive WebFlux App running in Docker!
```

---

## 📚 Demo Flows

### **Flow 1: Basic API Testing** (5 minutes)
Great for: Showing basic API functionality and WebFlux concepts

#### Using PowerShell (Windows):
```powershell
# Run the test script
.\test-api.ps1
```

#### Using Command Prompt:
```bash
# Test each endpoint individually
curl http://localhost:8080/api/hello
curl http://localhost:8080/api/info
curl http://localhost:8080/api/health
curl http://localhost:8080/api/reactive-chain/10
```

**What it shows:**
- ✅ API is responding
- ✅ JSON responses working
- ✅ Application metadata
- ✅ Health monitoring

---

### **Flow 2: Real-Time Streaming Demo** (3 minutes)
Great for: Demonstrating Server-Sent Events (SSE) and real-time data

#### Terminal 1 - Start streaming:
```bash
curl http://localhost:8080/api/stream
```

You'll see:
```
Event 1 at 1716129600000
Event 2 at 1716129601000
Event 3 at 1716129602000
...
```

**What it shows:**
- ✅ Non-blocking streaming
- ✅ Server-Sent Events support
- ✅ Real-time data delivery
- ✅ WebFlux Flux capability

---

### **Flow 3: Async Operations Demo** (2 minutes)
Great for: Showing non-blocking async operations

#### Test async delay:
```bash
# This will respond after 5 seconds WITHOUT blocking
curl http://localhost:8080/api/async-delay/5

# Output after 5 seconds:
# Delayed response after 5 seconds
```

#### In another terminal, while the above is running:
```bash
# This responds immediately - proving non-blocking!
curl http://localhost:8080/api/hello
```

**What it shows:**
- ✅ Non-blocking operations
- ✅ Can handle multiple concurrent requests
- ✅ Efficient resource usage (uses event loops, not threads)
- ✅ Reactive Mono capability

---

### **Flow 4: Docker Benefits Demo** (3 minutes)
Great for: Showing Docker containerization

#### Check Docker status:
```bash
# See running containers
docker ps

# View container logs in real-time
docker-compose logs -f webflux-app

# See resource usage
docker stats
```

#### Health checks:
```bash
# Docker automatically monitors health
curl http://localhost:8080/health
```

**What it shows:**
- ✅ Application running in container
- ✅ Easy deployment and management
- ✅ Health monitoring working
- ✅ Resource efficiency

---

### **Flow 5: Reactive Chain Processing** (2 minutes)
Great for: Demonstrating reactive operators and processing

#### Single requests:
```bash
# Process number 5 (doubles to 10 after 500ms)
curl http://localhost:8080/api/reactive-chain/5

# Response:
# {"value":10,"message":"Processed value: 10"}
```

#### Test error handling:
```bash
# Negative numbers are rejected
curl http://localhost:8080/api/reactive-chain/-5

# Returns error
```

**What it shows:**
- ✅ Reactive operators (map, filter, flatMap)
- ✅ Async processing with delays
- ✅ Error handling in reactive streams
- ✅ Data transformation pipeline

---

### **Flow 6: Load/Performance Demo** (5 minutes)
Great for: Showing scalability

#### Install Apache Bench (if not available):
```bash
# Windows - download from Apache Bench for Windows
# Or use any load testing tool

# Mac: brew install httpd
# Linux: apt-get install apache2-utils
```

#### Run load test:
```bash
# Send 1000 requests with 10 concurrent
ab -n 1000 -c 10 http://localhost:8080/api/hello

# You'll see:
# Requests per second (very high!)
# Complete requests: 1000
# Failed requests: 0
```

#### Monitor during load:
```bash
# In another terminal:
docker stats
```

**What it shows:**
- ✅ High throughput
- ✅ Low resource usage under load
- ✅ Container efficiency
- ✅ Reactive advantage over servlet

---

## 🎯 Complete Demo Scenario (10 minutes)

| Time | Action | Demo Goal |
|------|--------|-----------|
| 0:00 | Show docker-compose up | Easy deployment |
| 1:00 | curl /api/hello | Basic API works |
| 2:00 | curl /api/info | Show metadata |
| 3:00 | Run test-api.ps1 / bash | All endpoints working |
| 4:00 | curl /api/stream | Streaming/SSE |
| 5:00 | curl /api/async-delay/3 & curl /api/hello | Non-blocking demo |
| 6:00 | docker ps / docker stats | Docker containerization |
| 7:00 | curl /api/reactive-chain/10 | Reactive operators |
| 8:00 | docker-compose logs | Log monitoring |
| 9:00 | docker-compose down | Easy cleanup |

---

## 🧪 Best Tools for Each Task

### **For REST Client GUI (Recommended):**
- **Postman** - Full-featured, import ready
- **Insomnia** - Lightweight, user-friendly
- **Thunder Client** - VS Code extension
- **REST Client** - VS Code extension (use requests.http)

### **For Command Line:**
- **curl** - Built-in on most systems
- **PowerShell** - Built into Windows (use test-api.ps1)
- **Bash** - Linux/Mac (use test-api.sh)
- **wget** - Alternative to curl

### **For Load Testing:**
- **Apache Bench (ab)** - Simple, command-line
- **wrk** - High-performance
- **JMeter** - Full-featured GUI
- **locust** - Python-based

---

## 📊 REST Client Setup (Postman Example)

### Import endpoints in Postman:

```json
{
  "info": {
    "name": "WebFlux Docker Demo",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "Hello",
      "request": {
        "method": "GET",
        "url": "http://localhost:8080/api/hello"
      }
    },
    {
      "name": "Info",
      "request": {
        "method": "GET",
        "url": "http://localhost:8080/api/info"
      }
    },
    {
      "name": "Health",
      "request": {
        "method": "GET",
        "url": "http://localhost:8080/api/health"
      }
    },
    {
      "name": "Stream Events",
      "request": {
        "method": "GET",
        "url": "http://localhost:8080/api/stream",
        "header": [
          {"key": "Accept", "value": "text/event-stream"}
        ]
      }
    },
    {
      "name": "Async Delay",
      "request": {
        "method": "GET",
        "url": "http://localhost:8080/api/async-delay/3"
      }
    },
    {
      "name": "Reactive Chain",
      "request": {
        "method": "GET",
        "url": "http://localhost:8080/api/reactive-chain/10"
      }
    }
  ]
}
```

---

## 📈 Key Points to Highlight During Demo

### **WebFlux Advantages:**
1. **Non-blocking**: All operations are truly asynchronous
2. **Efficient**: Uses fewer threads than traditional servlet apps
3. **Scalable**: Can handle thousands of concurrent connections
4. **Reactive**: Built on reactive streams (Mono/Flux)
5. **Perfect for Microservices**: Low resource footprint

### **Docker Advantages:**
1. **Containerization**: Runs anywhere (laptop, cloud, server)
2. **Easy Deployment**: One command to deploy
3. **Monitoring**: Built-in health checks
4. **Isolation**: Separate from host system
5. **Multi-stage Builds**: Optimized images (~400MB vs 800MB+)
6. **Easy Scaling**: Run multiple instances easily

### **What Makes This Special:**
- ✅ Non-blocking I/O with Netty
- ✅ Server-Sent Events (real-time streaming)
- ✅ Reactive operators (map, filter, flatMap, etc.)
- ✅ Alpine Linux (lightweight)
- ✅ JVM optimizations for containers
- ✅ Health check integration
- ✅ Actuator monitoring

---

## 🔧 Troubleshooting During Demo

### Port already in use:
```bash
# Change port in docker-compose.yml: "8081:8080"
docker-compose down
docker-compose up -d
```

### Application not responding:
```bash
# Check logs
docker-compose logs webflux-app

# Restart
docker-compose restart
```

### Want to rebuild after code changes:
```bash
# Clean and rebuild
./gradlew.bat clean build
docker-compose up --build -d
```

### View Java process inside container:
```bash
docker exec webflux-demo ps aux
```

---

## 🎬 Scripted Demo (Copy & Paste)

### PowerShell Demo Script:
```powershell
Write-Host "=== WebFlux Docker Demo ===" -ForegroundColor Cyan
Write-Host ""

Write-Host "1. Starting application..." -ForegroundColor Yellow
docker-compose up -d
Start-Sleep -Seconds 5

Write-Host "2. Testing basic endpoint..." -ForegroundColor Yellow
curl http://localhost:8080/api/hello
Write-Host ""

Write-Host "3. Getting application info..." -ForegroundColor Yellow
curl http://localhost:8080/api/info
Write-Host ""

Write-Host "4. Health check..." -ForegroundColor Yellow
curl http://localhost:8080/api/health
Write-Host ""

Write-Host "5. Testing async operation (5 second delay)..." -ForegroundColor Yellow
curl http://localhost:8080/api/async-delay/5
Write-Host ""

Write-Host "6. Docker status..." -ForegroundColor Yellow
docker ps
Write-Host ""

Write-Host "7. Stopping application..." -ForegroundColor Yellow
docker-compose down

Write-Host ""
Write-Host "Demo complete!" -ForegroundColor Green
```

---

## 💡 What Audiences Will See

### **For Developers:**
- Clean reactive code
- Proper use of Mono/Flux
- Error handling patterns
- Docker best practices

### **For DevOps/Containers:**
- Multi-stage Dockerfile
- Alpine base optimization
- Health check integration
- Docker Compose orchestration
- Image size optimization

### **For Business/Managers:**
- Fast deployment (docker-compose up)
- Scalable architecture
- Health monitoring
- Production-ready code

---

## 🎉 Success Indicators

Your demo is successful when:
- ✅ All endpoints return 200 status codes
- ✅ Streaming endpoint continuously sends events
- ✅ Async operations don't block other requests
- ✅ Docker container starts in < 10 seconds
- ✅ Health checks pass
- ✅ Application handles load efficiently

---

## 📝 Demo Notes Template

**For your reference:**

```
Demo Date: ____________
Audience: _____________

Key Points Covered:
- [ ] WebFlux reactive programming
- [ ] Docker containerization
- [ ] Non-blocking I/O benefits
- [ ] Server-Sent Events
- [ ] Health monitoring
- [ ] Easy deployment

Questions Asked:
1. ___________________
2. ___________________
3. ___________________

Feedback:
___________________
___________________
```

---

**Ready to demonstrate! 🚀**

