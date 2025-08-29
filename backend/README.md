# Spring Boot Backend - Authentication System

A complete Spring Boot backend implementation for user authentication with JWT tokens, PostgreSQL database, and BCrypt password hashing.

## 🚀 Features

- **User Registration & Login**: Secure user authentication system
- **JWT Authentication**: Access tokens and refresh tokens
- **BCrypt Password Hashing**: Secure password storage
- **PostgreSQL Database**: Robust data persistence
- **Spring Security**: Comprehensive security framework
- **CORS Support**: Cross-origin resource sharing enabled
- **Input Validation**: Request data validation with Bean Validation
- **Error Handling**: Proper error responses and status codes

## 🏗️ Architecture

```
backend/
├── src/main/java/com/login/backend/
│   ├── BackendApplication.java          # Main Spring Boot application
│   ├── config/
│   │   └── SecurityConfig.java         # Spring Security configuration
│   ├── controller/
│   │   ├── AuthController.java         # Authentication endpoints
│   │   └── UserController.java         # User profile endpoints
│   ├── dto/
│   │   ├── AuthResponse.java           # Authentication response DTO
│   │   ├── LoginRequest.java           # Login request DTO
│   │   └── RegisterRequest.java        # Registration request DTO
│   ├── entity/
│   │   ├── User.java                   # User entity
│   │   └── RefreshToken.java           # Refresh token entity
│   ├── repository/
│   │   ├── UserRepository.java         # User data access
│   │   └── RefreshTokenRepository.java # Refresh token data access
│   ├── security/
│   │   └── JwtAuthenticationFilter.java # JWT authentication filter
│   └── service/
│       ├── AuthenticationService.java   # Authentication business logic
│       ├── CustomUserDetailsService.java # User details service
│       ├── JwtService.java             # JWT token operations
│       ├── RefreshTokenService.java    # Refresh token management
│       └── UserService.java            # User management
├── src/main/resources/
│   └── application.properties          # Application configuration
└── database/
    └── init.sql                        # Database initialization script
```

## 🛠️ Technologies Used

- **Spring Boot 3.5.5**: Modern Java framework
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: Database operations
- **PostgreSQL**: Relational database
- **JWT (JSON Web Tokens)**: Stateless authentication
- **BCrypt**: Password hashing
- **Maven**: Dependency management
- **Java 17**: Modern Java features

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## 🚀 Quick Start

### 1. Database Setup

```bash
# Connect to PostgreSQL
psql -h localhost -U root -d postgres

# Create database
CREATE DATABASE logindb;

# Connect to the new database
\c logindb

# Run the initialization script
\i backend/database/init.sql
```

### 2. Configuration

Update `src/main/resources/application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/logindb
spring.datasource.username=your_username
spring.datasource.password=your_password

# JWT Secret (generate a secure secret for production)
jwt.secret=your-super-secret-jwt-key-here-make-it-very-long-and-secure
```

### 3. Run the Application

```bash
# Navigate to backend directory
cd backend

# Clean and install dependencies
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 🔌 API Endpoints

### Authentication Endpoints

| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| `POST` | `/api/auth/register` | User registration | `RegisterRequest` |
| `POST` | `/api/auth/login` | User login | `LoginRequest` |
| `POST` | `/api/auth/refresh` | Refresh access token | `refreshToken` (param) |
| `POST` | `/api/auth/logout` | User logout | `refreshToken` (param) |
| `GET` | `/api/auth/health` | Health check | None |

### User Profile Endpoints

| Method | Endpoint | Description | Request Body | Authentication |
|--------|----------|-------------|--------------|----------------|
| `GET` | `/api/user/profile` | Get user profile | None | Required |
| `PUT` | `/api/user/profile` | Update user profile | `User` | Required |
| `DELETE` | `/api/user/profile` | Deactivate account | None | Required |

## 📝 Request/Response Examples

### User Registration

**Request:**
```json
POST /api/auth/register
{
  "fullName": "John Doe",
  "email": "john@example.com",
  "password": "SecurePass123"
}
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "550e8400-e29b-41d4-a716-446655440000",
  "tokenType": "Bearer",
  "expiresIn": 900000,
  "user": {
    "id": 1,
    "fullName": "John Doe",
    "email": "john@example.com",
    "profilePicture": null,
    "createdAt": "2024-01-01T10:00:00",
    "updatedAt": "2024-01-01T10:00:00"
  }
}
```

### User Login

**Request:**
```json
POST /api/auth/login
{
  "email": "john@example.com",
  "password": "SecurePass123"
}
```

**Response:** Same as registration response

### Refresh Token

**Request:**
```
POST /api/auth/refresh?refreshToken=550e8400-e29b-41d4-a716-446655440000
```

**Response:** New access and refresh tokens

## 🔐 Security Features

- **JWT Tokens**: Stateless authentication
- **BCrypt Hashing**: Secure password storage
- **Token Expiration**: Configurable token lifetimes
- **Refresh Token Rotation**: New refresh token on each refresh
- **CORS Configuration**: Cross-origin support
- **Input Validation**: Request data validation
- **Error Handling**: Secure error responses

## ⚙️ Configuration

### JWT Configuration

```properties
# JWT Secret Key (256-bit minimum recommended)
jwt.secret=your-super-secret-jwt-key-here-make-it-very-long-and-secure

# Access Token Expiration (15 minutes)
jwt.access-token.expiration=900000

# Refresh Token Expiration (7 days)
jwt.refresh-token.expiration=604800000
```

### Database Configuration

```properties
# PostgreSQL Connection
spring.datasource.url=jdbc:postgresql://localhost:5432/logindb
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA/Hibernate Settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## 🧪 Testing

### Using cURL

```bash
# Health Check
curl http://localhost:8080/api/auth/health

# Register User
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"fullName":"Test User","email":"test@example.com","password":"TestPass123"}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"TestPass123"}'
```

### Using Postman

1. Import the collection from `postman/` directory
2. Set the base URL to `http://localhost:8080`
3. Use the provided requests for testing

## 🚨 Security Considerations

- **JWT Secret**: Use a strong, unique secret key
- **Token Expiration**: Set appropriate expiration times
- **HTTPS**: Use HTTPS in production
- **Input Validation**: Always validate input data
- **Error Messages**: Don't expose sensitive information
- **Password Policy**: Enforce strong password requirements

## 🔧 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify PostgreSQL is running
   - Check database credentials
   - Ensure database exists

2. **JWT Errors**
   - Verify JWT secret is set
   - Check token expiration times
   - Ensure proper token format

3. **CORS Issues**
   - Check CORS configuration
   - Verify frontend origin
   - Test with Postman first

### Logs

Enable debug logging in `application.properties`:

```properties
logging.level.com.login.backend=DEBUG
logging.level.org.springframework.security=DEBUG
```

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [JWT.io](https://jwt.io/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.

---

**Happy Coding! 🎉**
