#!/bin/bash

echo "🔍 Testing Database Connection"
echo "=============================="

# Database connection details
DB_HOST="localhost"
DB_PORT="5432"
DB_NAME="logindb"
DB_USER="root"
DB_PASSWORD="root"

echo "Testing connection to PostgreSQL..."
echo "Host: $DB_HOST"
echo "Port: $DB_PORT"
echo "Database: $DB_NAME"
echo "User: $DB_USER"
echo ""

# Test 1: Check if PostgreSQL is running
echo "1. Checking if PostgreSQL is running..."
if pg_isready -h $DB_HOST -p $DB_PORT >/dev/null 2>&1; then
    echo "✅ PostgreSQL is running"
else
    echo "❌ PostgreSQL is not running"
    echo "   Start it with: brew services start postgresql"
    exit 1
fi

# Test 2: Test connection with psql
echo ""
echo "2. Testing database connection..."
if PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -c "SELECT version();" >/dev/null 2>&1; then
    echo "✅ Database connection successful"
else
    echo "❌ Database connection failed"
    echo "   Checking if database exists..."
    
    # Check if database exists
    if PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d postgres -c "\l" | grep -q $DB_NAME; then
        echo "✅ Database '$DB_NAME' exists"
    else
        echo "❌ Database '$DB_NAME' does not exist"
        echo "   Create it with:"
        echo "   psql -h $DB_HOST -U $DB_USER -d postgres"
        echo "   CREATE DATABASE $DB_NAME;"
        echo "   \\c $DB_NAME"
        echo "   \\i backend/database/init.sql"
        exit 1
    fi
fi

# Test 3: Check tables
echo ""
echo "3. Checking database tables..."
if PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -c "\dt" >/dev/null 2>&1; then
    echo "✅ Tables exist"
    
    # Show table details
    echo ""
    echo "Table details:"
    PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -c "\dt+"
    
    # Check users table
    echo ""
    echo "Users table structure:"
    PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -c "\d users"
    
    # Check refresh_tokens table
    echo ""
    echo "Refresh tokens table structure:"
    PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -c "\d refresh_tokens"
    
else
    echo "❌ Tables do not exist"
    echo "   Run the initialization script:"
    echo "   psql -h $DB_HOST -U $DB_USER -d $DB_NAME -f backend/database/init.sql"
    exit 1
fi

# Test 4: Check if there are any existing users
echo ""
echo "4. Checking existing users..."
USER_COUNT=$(PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -t -c "SELECT COUNT(*) FROM users;" | xargs)
echo "Current users in database: $USER_COUNT"

if [ "$USER_COUNT" -gt 0 ]; then
    echo "Existing users:"
    PGPASSWORD=$DB_PASSWORD psql -h $DB_HOST -p $DB_PORT -U $DB_USER -d $DB_NAME -c "SELECT id, full_name, email, is_active FROM users;"
fi

echo ""
echo "🎯 Database connection test completed!"
echo ""
echo "📋 Next steps:"
echo "1. If all tests passed, your database is ready"
echo "2. Start the backend: cd backend && mvn spring-boot:run"
echo "3. Test the API: ./test_app.sh"
echo "4. Start Flutter app: cd login_frontend && flutter run"
