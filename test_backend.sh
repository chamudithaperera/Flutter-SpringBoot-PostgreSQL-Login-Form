#!/bin/bash

echo "🧪 Testing Spring Boot Backend API"
echo "=================================="

BASE_URL="http://localhost:8080"

# Test health endpoint
echo "1. Testing health endpoint..."
curl -s "$BASE_URL/api/auth/health" | jq '.' 2>/dev/null || curl -s "$BASE_URL/api/auth/health"
echo ""

# Test user registration
echo "2. Testing user registration..."
REGISTER_RESPONSE=$(curl -s -X POST "$BASE_URL/api/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Test User",
    "email": "test@example.com",
    "password": "TestPass123"
  }')

echo "Registration Response:"
echo "$REGISTER_RESPONSE" | jq '.' 2>/dev/null || echo "$REGISTER_RESPONSE"
echo ""

# Extract tokens if registration was successful
if echo "$REGISTER_RESPONSE" | grep -q "accessToken"; then
  echo "✅ Registration successful!"
  
  # Test user login
  echo "3. Testing user login..."
  LOGIN_RESPONSE=$(curl -s -X POST "$BASE_URL/api/auth/login" \
    -H "Content-Type: application/json" \
    -d '{
      "email": "test@example.com",
      "password": "TestPass123"
    }')
  
  echo "Login Response:"
  echo "$LOGIN_RESPONSE" | jq '.' 2>/dev/null || echo "$LOGIN_RESPONSE"
  echo ""
  
  if echo "$LOGIN_RESPONSE" | grep -q "accessToken"; then
    echo "✅ Login successful!"
    
    # Extract access token for profile test
    ACCESS_TOKEN=$(echo "$LOGIN_RESPONSE" | grep -o '"accessToken":"[^"]*"' | cut -d'"' -f4)
    
    if [ ! -z "$ACCESS_TOKEN" ]; then
      echo "4. Testing user profile access..."
      PROFILE_RESPONSE=$(curl -s -X GET "$BASE_URL/api/user/profile" \
        -H "Authorization: Bearer $ACCESS_TOKEN")
      
      echo "Profile Response:"
      echo "$PROFILE_RESPONSE" | jq '.' 2>/dev/null || echo "$PROFILE_RESPONSE"
      echo ""
      
      if echo "$PROFILE_RESPONSE" | grep -q "fullName"; then
        echo "✅ Profile access successful!"
      else
        echo "❌ Profile access failed!"
      fi
    fi
  else
    echo "❌ Login failed!"
  fi
else
  echo "❌ Registration failed!"
fi

echo ""
echo "🎯 Backend API testing completed!"
echo ""
echo "📱 Next steps:"
echo "1. Ensure backend is running: mvn spring-boot:run"
echo "2. Test Flutter app: flutter run"
echo "3. Register a new user in the Flutter app"
echo "4. Login with the registered user"
echo "5. View user data on the home screen"
