#!/bin/bash

# WebFlux Docker Demo - API Test Script
# This script tests all available endpoints

BASE_URL="http://localhost:8080/api"
TIMEOUT=10

echo "======================================"
echo "WebFlux Docker Demo - API Testing"
echo "======================================"
echo ""

# Function to test endpoint
test_endpoint() {
    local method=$1
    local endpoint=$2
    local description=$3

    echo "Testing: $description"
    echo "Endpoint: $method $BASE_URL$endpoint"

    if [ "$method" = "GET" ]; then
        response=$(curl -s --max-time $TIMEOUT "$BASE_URL$endpoint")
        status=$(curl -s --max-time $TIMEOUT -o /dev/null -w "%{http_code}" "$BASE_URL$endpoint")
    fi

    echo "Status Code: $status"
    echo "Response: $response"
    echo ""
}

# Check if server is running
echo "Checking if server is running..."
if ! curl -s --max-time 2 "$BASE_URL/hello" > /dev/null 2>&1; then
    echo "ERROR: Server is not running on $BASE_URL"
    echo "Please start the application first:"
    echo "  - Local: ./gradlew bootRun"
    echo "  - Docker: docker-compose up"
    exit 1
fi

echo "✓ Server is running!"
echo ""

# Test all endpoints
test_endpoint "GET" "/hello" "Simple Hello Endpoint"
test_endpoint "GET" "/info" "Application Info Endpoint"
test_endpoint "GET" "/health" "Health Status Endpoint"
test_endpoint "GET" "/async-delay/2" "Async Delay (2 seconds)"
test_endpoint "GET" "/reactive-chain/5" "Reactive Chain Processing"

echo "======================================"
echo "Testing Stream Endpoints (first 5 events)"
echo "======================================"
echo ""

echo "Testing: Server-Sent Events Stream"
echo "Command: curl http://localhost:8080/api/stream"
echo "Events:"
curl -s "$BASE_URL/stream" | head -5
echo ""
echo "(Showing first 5 events...)"
echo ""

echo "======================================"
echo "All tests completed!"
echo "======================================"

