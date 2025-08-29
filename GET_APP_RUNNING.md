# 🚀 Get Your App Running Perfectly - Step by Step

## 🎯 What You'll Have After Following This Guide

✅ **User Registration** - Data saved to PostgreSQL database  
✅ **User Login** - Authenticate against database with BCrypt  
✅ **JWT Tokens** - Access and refresh tokens displayed in Flutter  
✅ **Database Integration** - Real data persistence and retrieval  
✅ **Modern UI** - Clean, green-themed Flutter interface  

## 📋 Step-by-Step Setup

### **Step 1: Setup Database** 🗄️

```bash
# Connect to PostgreSQL
psql -h localhost -U root -d postgres

# Create the database
CREATE DATABASE logindb;

# Connect to the new database
\c logindb

# Run the initialization script
\i backend/database/init.sql

# Exit PostgreSQL
\q
```

**Expected Output:**
- Database `logindb` created
- Tables `users` and `refresh_tokens` created
- Indexes and triggers created

### **Step 2: Start Spring Boot Backend** 🔧

```bash
# Navigate to backend directory
cd backend

# Clean and install dependencies
mvn clean install

# Start the backend server
mvn spring-boot:run
```

**Expected Output:**
- Maven builds successfully
- Spring Boot starts on port 8080
- Database connection established
- JWT service initialized

**Look for this in logs:**
```
Started BackendApplication in X.XXX seconds
```

### **Step 3: Test Backend API** 🌐

```bash
# Open new terminal, stay in project root
cd /Users/chamudithakavishan/Documents/GitHub/Flutter-SpringBoot-PostgreSQL-Login-Form

# Test the backend
./test_app.sh
```

**Expected Output:**
- ✅ Health check successful
- ✅ Registration successful
- ✅ Login successful
- 🎉 App is working perfectly!

### **Step 4: Start Flutter App** 📱

```bash
# Open new terminal, navigate to Flutter directory
cd login_frontend

# Get dependencies
flutter pub get

# Run the app
flutter run
```

**Expected Output:**
- Flutter app launches
- Login screen appears
- Green theme applied
- No errors in console

## 🧪 Testing Your App

### **Test 1: User Registration**
1. **In Flutter app**: Tap "Sign Up"
2. **Enter details**:
   - Full Name: `Test User`
   - Email: `test@example.com`
   - Password: `TestPass123`
   - Confirm Password: `TestPass123`
3. **Tap**: "CREATE ACCOUNT"
4. **Expected**: Success message, redirected to home screen

### **Test 2: User Login**
1. **In Flutter app**: Go back to login screen
2. **Enter credentials**:
   - Email: `test@example.com`
   - Password: `TestPass123`
3. **Tap**: "SIGN IN"
4. **Expected**: Success message, redirected to home screen

### **Test 3: View User Data**
1. **On home screen**: You should see:
   - Welcome message
   - User Information section
   - Full Name: `Test User`
   - Email: `test@example.com`
   - User ID: `1` (or similar)

### **Test 4: View JWT Tokens**
1. **On home screen**: Look for JWT tokens section
2. **You should see**:
   - Access Token (long JWT string)
   - Refresh Token (UUID format)
   - Token Type: `Bearer`
   - Expires In: `900000` (15 minutes)

### **Test 5: Logout**
1. **Tap logout button** (top right)
2. **Expected**: Returned to login screen
3. **Check backend logs**: Token should be revoked

## 🔍 Verify Database Integration

### **Check Database Contents**
```bash
# Connect to database
psql -h localhost -U root -d logindb

# View users table
SELECT id, full_name, email, is_active, created_at FROM users;

# View refresh tokens
SELECT id, user_id, is_revoked, created_at FROM refresh_tokens;

# Exit
\q
```

**Expected Output:**
- Users table shows your registered user
- Refresh tokens table shows generated tokens
- Passwords are BCrypt hashed (start with `$2a$`)

## 🐛 Troubleshooting Common Issues

### **Issue 1: "Database connection failed"**
```bash
# Check PostgreSQL is running
pg_isready -h localhost -p 5432

# Start PostgreSQL if needed
brew services start postgresql
```

### **Issue 2: "Port 8080 already in use"**
```bash
# Find process using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>
```

### **Issue 3: "Flutter build failed"**
```bash
# Clean Flutter project
flutter clean
flutter pub get
flutter run
```

### **Issue 4: "JWT tokens not showing"**
- Check backend logs for errors
- Verify user registration was successful
- Check if home screen is receiving data correctly

## 📱 App Features You'll Have

### **Registration Screen**
- ✅ Full name, email, password inputs
- ✅ Password confirmation
- ✅ Form validation
- ✅ Modern green theme
- ✅ Loading states

### **Login Screen**
- ✅ Email and password inputs
- ✅ Form validation
- ✅ Forgot password link
- ✅ Sign up link
- ✅ Loading states

### **Home Screen**
- ✅ Welcome message
- ✅ User information display
- ✅ JWT token display
- ✅ Logout functionality
- ✅ Clean card layout

### **Backend Features**
- ✅ RESTful API endpoints
- ✅ JWT authentication
- ✅ BCrypt password hashing
- ✅ PostgreSQL integration
- ✅ Refresh token management
- ✅ CORS configuration

## 🎉 Success Indicators

Your app is working perfectly when:

1. **Backend**: Starts without errors on port 8080
2. **Database**: Tables created, user data persists
3. **Registration**: User can register, data saved to DB
4. **Login**: User can login with registered credentials
5. **Home Screen**: Shows user data and JWT tokens
6. **Logout**: Properly revokes tokens and navigates

## 🚀 Next Steps After Setup

1. **Customize UI**: Modify colors, fonts, layouts
2. **Add Features**: Profile editing, password reset
3. **Enhance Security**: Add rate limiting, input sanitization
4. **Deploy**: Move to production environment
5. **Monitor**: Add logging and analytics

---

**🎯 Follow these steps exactly and your authentication app will work perfectly! 🚀**

The app will have:
- ✅ Real database integration
- ✅ JWT token authentication
- ✅ BCrypt password security
- ✅ Modern Flutter UI
- ✅ Complete user flow

**Start with Step 1 (Database Setup) and work through each step. Your app will be running perfectly! 🎉**
