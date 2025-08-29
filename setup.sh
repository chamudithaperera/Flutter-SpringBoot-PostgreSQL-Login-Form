#!/bin/bash

echo "🚀 Setting up Complete Authentication App"
echo "=========================================="

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 17+ first."
    exit 1
fi

echo "✅ Java found: $(java -version 2>&1 | head -n 1)"

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "📦 Installing Maven..."
    if command -v brew &> /dev/null; then
        brew install maven
    else
        echo "❌ Homebrew not found. Please install Maven manually."
        exit 1
    fi
fi

echo "✅ Maven found: $(mvn -version 2>&1 | head -n 1)"

# Check if PostgreSQL is running
if ! pg_isready -h localhost -p 5432 &> /dev/null; then
    echo "❌ PostgreSQL is not running. Please start PostgreSQL first."
    echo "   On macOS: brew services start postgresql"
    echo "   On Ubuntu: sudo systemctl start postgresql"
    exit 1
fi

echo "✅ PostgreSQL is running"

# Check if Flutter is installed
if ! command -v flutter &> /dev/null; then
    echo "❌ Flutter is not installed. Please install Flutter first."
    echo "   Visit: https://docs.flutter.dev/get-started/install"
    exit 1
fi

echo "✅ Flutter found: $(flutter --version 2>&1 | head -n 1)"

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
echo "🎯 The app will be available at:"
echo "   - Backend: http://localhost:8080"
echo "   - Flutter: Run on your device/emulator"
echo ""
echo "📚 For detailed instructions, see README.md"
echo ""
echo "Happy Coding! 🚀"
