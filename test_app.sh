#!/bin/bash

echo "🧪 Testing Authentication App"
echo "============================="

BASE_URL="http://localhost:8080"

# Test 1: Health check
echo "1. Testing health endpoint..."
HEALTH_RESPONSE=$(curl -s "$BASE_URL/api/auth/health")
echo "Response: $HEALTH_RESPONSE"
echo ""

# Test 2: Register a new user
echo "2. Testing user registration..."
REGISTER_RESPONSE=$(curl -s -X POST "$BASE_URL/api/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Test User",
    "email": "test@example.com",
    "password": "TestPass123"
  }')

echo "Registration Response:"
echo "$REGISTER_RESPONSE"
echo ""

# Check if registration was successful
if echo "$REGISTER_RESPONSE" | grep -q "accessToken"; then
    echo "✅ Registration successful!"
    echo ""
    
    # Test 3: Login with the registered user
    echo "3. Testing user login..."
    LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/api/auth/login" \
      -H "Content-Type: application/json" \
      -d '{
        "email": "test@example.com",
        "password": "TestPass123"
      }')
    
    echo "Login Response:"
    echo "$LOGIN_RESPONSE"
    echo ""
    
    if echo "$LOGIN_RESPONSE" | grep -q "accessToken"; then
        echo "✅ Login successful!"
        echo "🎉 App is working perfectly!"
    else
        echo "❌ Login failed!"
        echo "Error: $LOGIN_RESPONSE"
    fi
else
    echo "❌ Registration failed!"
    echo "Error: $REGISTER_RESPONSE"
fi

echo ""
echo "📱 Next steps:"
echo "1. Test Flutter app: flutter run"
echo "2. Register a new user in the app"
echo "3. Login with the registered user"
echo "4. View user data on home screen"
