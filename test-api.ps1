# WebFlux Docker Demo - API Test Script (PowerShell)
# This script tests all available endpoints

$BASE_URL = "http://localhost:8080/api"
$TIMEOUT = 10

Write-Host "======================================" -ForegroundColor Cyan
Write-Host "WebFlux Docker Demo - API Testing" -ForegroundColor Cyan
Write-Host "======================================" -ForegroundColor Cyan
Write-Host ""

# Function to test endpoint
function Test-Endpoint {
    param(
        [string]$Method,
        [string]$Endpoint,
        [string]$Description
    )

    Write-Host "Testing: $Description" -ForegroundColor Yellow
    Write-Host "Endpoint: $Method $BASE_URL$Endpoint"

    try {
        $response = Invoke-WebRequest -Uri "$BASE_URL$Endpoint" -Method $Method -TimeoutSec $TIMEOUT
        Write-Host "Status Code: $($response.StatusCode)" -ForegroundColor Green
        Write-Host "Response: $($response.Content)"
    }
    catch {
        Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
    }

    Write-Host ""
}

# Check if server is running
Write-Host "Checking if server is running..." -ForegroundColor Cyan

try {
    $null = Invoke-WebRequest -Uri "$BASE_URL/hello" -Method GET -TimeoutSec 2 -ErrorAction Stop
    Write-Host "✓ Server is running!" -ForegroundColor Green
    Write-Host ""
}
catch {
    Write-Host "ERROR: Server is not running on $BASE_URL" -ForegroundColor Red
    Write-Host "Please start the application first:" -ForegroundColor Yellow
    Write-Host "  - Local: .\gradlew.bat bootRun" -ForegroundColor White
    Write-Host "  - Docker: docker-compose up" -ForegroundColor White
    exit 1
}

# Test all endpoints
Test-Endpoint "GET" "/hello" "Simple Hello Endpoint"
Test-Endpoint "GET" "/info" "Application Info Endpoint"
Test-Endpoint "GET" "/health" "Health Status Endpoint"
Test-Endpoint "GET" "/async-delay/2" "Async Delay (2 seconds)"
Test-Endpoint "GET" "/reactive-chain/5" "Reactive Chain Processing"

Write-Host "======================================" -ForegroundColor Cyan
Write-Host "Testing Stream Endpoint" -ForegroundColor Cyan
Write-Host "======================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "Testing: Server-Sent Events Stream" -ForegroundColor Yellow
Write-Host "Command: Invoke-WebRequest $BASE_URL/stream"
Write-Host "Events (first 5):" -ForegroundColor White

try {
    $response = Invoke-WebRequest -Uri "$BASE_URL/stream" -Method GET -TimeoutSec 15
    $lines = $response.Content -split "`n" | Select-Object -First 5
    Write-Host $lines -ForegroundColor Green
}
catch {
    Write-Host "Error fetching stream: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "======================================" -ForegroundColor Cyan
Write-Host "All tests completed!" -ForegroundColor Cyan
Write-Host "======================================" -ForegroundColor Cyan

