# 🔐 Perfect Authentication App

A complete authentication system with Flutter frontend, Spring Boot backend, and PostgreSQL database.

## ✨ Features

- **User Registration**: Store user data in PostgreSQL with BCrypt-hashed passwords
- **User Login**: Authenticate users and return JWT tokens
- **JWT Authentication**: Access and refresh tokens for secure authentication
- **Database Integration**: Real PostgreSQL database with proper data persistence
- **Modern UI**: Clean, green-themed Flutter interface
- **Token Display**: Show JWT tokens after successful authentication

## 🏗️ Architecture

```
Flutter App ←→ Spring Boot Backend ←→ PostgreSQL Database
     ↓              ↓                        ↓
  UI Screens    REST APIs              User Data
  JWT Display   JWT Generation         BCrypt Hashes
  Navigation    Authentication         Refresh Tokens
```

## 🚀 Quick Start

### 1. **Prerequisites**
- Java 17+
- Maven
- PostgreSQL
- Flutter

### 2. **Setup Database**
```bash
# Connect to PostgreSQL
psql -h localhost -U root -d postgres

# Create database
CREATE DATABASE logindb;

# Connect to the database
\c logindb

# Run initialization script
\i backend/database/init.sql
```

### 3. **Start Backend**
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### 4. **Start Flutter App**
```bash
cd login_frontend
flutter pub get
flutter run
```

### 5. **Test the App**
```bash
# Test backend API
./test_app.sh

# Or test manually
curl http://localhost:8080/api/auth/health
```

## 📱 App Flow

### **Registration**
1. User fills registration form
2. Data sent to Spring Boot backend
3. Password hashed with BCrypt
4. User saved to PostgreSQL
5. JWT tokens generated
6. User redirected to home screen

### **Login**
1. User enters email/password
2. Credentials validated against database
3. BCrypt password verification
4. JWT tokens generated
5. User data retrieved from database
6. User redirected to home screen

### **Home Screen**
1. Displays user information from database
2. Shows JWT access and refresh tokens
3. Provides logout functionality
4. Token revocation on logout

## 🗄️ Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,  -- BCrypt hashed
    profile_picture VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

### Refresh Tokens Table
```sql
CREATE TABLE refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    is_revoked BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

## 🔧 API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/refresh` - Refresh access token
- `POST /api/auth/logout` - User logout
- `GET /api/auth/health` - Health check

### User Management
- `GET /api/user/profile` - Get user profile
- `PUT /api/user/profile` - Update user profile
- `DELETE /api/user/profile` - Deactivate account

## 🎨 UI Components

### Screens
- **Login Screen**: Email/password input with validation
- **Register Screen**: Full name, email, password with validation
- **Home Screen**: User data display and JWT tokens

### Widgets
- **CustomTextField**: Consistent input styling
- **CustomButton**: Loading states and modern design
- **AppTheme**: Green color scheme with modern aesthetics

## 🔒 Security Features

- **BCrypt Password Hashing**: Secure password storage
- **JWT Tokens**: Stateless authentication
- **Refresh Token Rotation**: Enhanced security
- **Input Validation**: Server-side validation
- **CORS Configuration**: Cross-origin support

## 🧪 Testing

### Backend Testing
```bash
# Run the test script
./test_app.sh

# Manual testing
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"fullName":"Test User","email":"test@example.com","password":"TestPass123"}'
```

### Flutter Testing
1. Register a new user
2. Login with credentials
3. View user data on home screen
4. Check JWT tokens are displayed
5. Test logout functionality

## 🐛 Troubleshooting

### Common Issues

#### 1. **Database Connection**
```bash
# Check PostgreSQL is running
pg_isready -h localhost -p 5432

# Verify database exists
psql -h localhost -U root -l
```

#### 2. **Backend Issues**
```bash
# Check Java version
java -version

# Check Maven
mvn -version

# Check backend logs
tail -f backend/logs/spring.log
```

#### 3. **Flutter Issues**
```bash
# Clean and rebuild
flutter clean
flutter pub get
flutter run
```

### Error Messages

- **"Executing an update/delete query"**: Fixed with proper repository annotations
- **"Connection refused"**: Check if backend is running on port 8080
- **"Database not found"**: Ensure logindb database exists

## 📁 Project Structure

```
├── backend/                          # Spring Boot backend
│   ├── src/main/java/
│   │   └── com/login/backend/
│   │       ├── config/              # Security configuration
│   │       ├── controller/          # REST controllers
│   │       ├── dto/                 # Data transfer objects
│   │       ├── entity/              # JPA entities
│   │       ├── repository/          # Data repositories
│   │       ├── security/            # JWT filters
│   │       └── service/             # Business logic
│   ├── database/                    # SQL scripts
│   └── pom.xml                      # Maven dependencies
├── login_frontend/                   # Flutter app
│   ├── lib/
│   │   ├── models/                  # Data models
│   │   ├── screens/                 # UI screens
│   │   ├── services/                # API services
│   │   ├── utils/                   # Theme and utilities
│   │   └── widgets/                 # Reusable widgets
│   └── pubspec.yaml                 # Flutter dependencies
├── setup_app.sh                      # Setup script
├── test_app.sh                       # Test script
└── README.md                         # This file
```

## 🚀 Deployment

### Production Considerations
- Change JWT secret in `application.properties`
- Use environment variables for database credentials
- Enable HTTPS
- Configure proper CORS origins
- Set up database connection pooling
- Enable logging and monitoring

### Environment Variables
```bash
export DB_URL=jdbc:postgresql://localhost:5432/logindb
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
export JWT_SECRET=your_super_secret_key
```

## 📚 Dependencies

### Backend (Maven)
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- PostgreSQL Driver
- JWT Library
- BCrypt

### Frontend (Flutter)
- Flutter 3.x
- HTTP package
- Material Design

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.

---

**🎉 Your authentication app is now ready to run perfectly! 🚀**

Follow the setup instructions above and you'll have a fully functional authentication system with database integration and JWT tokens.
