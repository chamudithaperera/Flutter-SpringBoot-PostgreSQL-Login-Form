#!/bin/bash

echo "🚀 Setting up Perfect Authentication App"
echo "========================================"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to check if command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Check Java
echo "🔍 Checking Java..."
if ! command_exists java; then
    echo -e "${RED}❌ Java is not installed. Please install Java 17+ first.${NC}"
    exit 1
fi
echo -e "${GREEN}✅ Java found: $(java -version 2>&1 | head -n 1)${NC}"

# Check Maven
echo "🔍 Checking Maven..."
if ! command_exists mvn; then
    echo -e "${YELLOW}📦 Installing Maven...${NC}"
    if command_exists brew; then
        brew install maven
    else
        echo -e "${RED}❌ Homebrew not found. Please install Maven manually.${NC}"
        exit 1
    fi
fi
echo -e "${GREEN}✅ Maven found: $(mvn -version 2>&1 | head -n 1)${NC}"

# Check PostgreSQL
echo "🔍 Checking PostgreSQL..."
if ! pg_isready -h localhost -p 5432 >/dev/null 2>&1; then
    echo -e "${RED}❌ PostgreSQL is not running. Please start PostgreSQL first.${NC}"
    echo "   On macOS: brew services start postgresql"
    echo "   On Ubuntu: sudo systemctl start postgresql"
    exit 1
fi
echo -e "${GREEN}✅ PostgreSQL is running${NC}"

# Check Flutter
echo "🔍 Checking Flutter..."
if ! command_exists flutter; then
    echo -e "${RED}❌ Flutter is not installed. Please install Flutter first.${NC}"
    echo "   Visit: https://docs.flutter.dev/get-started/install"
    exit 1
fi
echo -e "${GREEN}✅ Flutter found: $(flutter --version 2>&1 | head -n 1)${NC}"

echo ""
echo -e "${GREEN}🎯 All prerequisites are ready!${NC}"
echo ""
echo "📋 Setup Instructions:"
echo "====================="
echo ""
echo "1. 🗄️  Database Setup:"
echo "   psql -h localhost -U root -d postgres"
echo "   CREATE DATABASE logindb;"
echo "   \\c logindb"
echo "   \\i backend/database/init.sql"
echo ""
echo "2. 🔧 Backend Setup:"
echo "   cd backend"
echo "   mvn clean install"
echo "   mvn spring-boot:run"
echo ""
echo "3. 📱 Frontend Setup:"
echo "   cd login_frontend"
echo "   flutter pub get"
echo "   flutter run"
echo ""
echo "4. 🌐 Test API:"
echo "   curl http://localhost:8080/api/auth/health"
echo ""
echo -e "${GREEN}🎯 The app will be available at:${NC}"
echo "   - Backend: http://localhost:8080"
echo "   - Flutter: Run on your device/emulator"
echo ""
echo -e "${GREEN}Happy Coding! 🚀${NC}"
