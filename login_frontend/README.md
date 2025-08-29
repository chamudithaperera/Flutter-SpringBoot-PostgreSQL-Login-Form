# Flutter Login App

A modern and beautiful Flutter application with login and registration functionality, designed to work with a Spring Boot backend and PostgreSQL database.

## 🚀 Features

- **Modern UI Design**: Clean, professional interface with Material Design 3
- **User Authentication**: Login and registration with form validation
- **Responsive Layout**: Works on all screen sizes and orientations
- **Form Validation**: Real-time input validation with helpful error messages
- **Loading States**: Smooth loading animations during API calls
- **Navigation**: Seamless navigation between screens
- **Theme System**: Consistent color scheme and styling throughout the app

## 📱 Screens

### 1. Login Screen (`/login`)
- Email and password input fields
- Form validation
- Password visibility toggle
- Forgot password link
- Navigation to registration
- Loading state during authentication

### 2. Registration Screen (`/register`)
- Full name, email, and password fields
- Password strength validation
- Password confirmation
- Form validation
- Loading state during registration
- Navigation back to login

### 3. Home Screen (`/home`)
- Welcome message for authenticated users
- Logout functionality
- Clean, minimal design

## 🏗️ Project Structure

```
lib/
├── main.dart                 # App entry point and routing
├── models/
│   └── user_model.dart      # User data model
├── screens/
│   ├── auth/
│   │   ├── login_screen.dart    # Login screen
│   │   └── register_screen.dart # Registration screen
│   └── home/
│       └── home_screen.dart     # Home screen after login
├── services/
│   └── auth_service.dart    # Authentication API service
├── utils/
│   └── app_theme.dart       # App theme and styling
└── widgets/
    ├── custom_button.dart       # Reusable button component
    └── custom_text_field.dart  # Reusable text field component
```

## 🎨 Design Features

- **Color Scheme**: Professional blue-based theme
- **Typography**: Clear, readable fonts with proper hierarchy
- **Spacing**: Consistent padding and margins throughout
- **Shadows**: Subtle elevation effects for depth
- **Icons**: Material Design icons for better UX
- **Animations**: Smooth transitions and loading states

## 🔧 Setup Instructions

### Prerequisites
- Flutter SDK (3.9.0 or higher)
- Dart SDK
- Android Studio / VS Code
- Android/iOS emulator or physical device

### Installation

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd login_frontend
   ```

2. **Install dependencies**
   ```bash
   flutter pub get
   ```

3. **Run the app**
   ```bash
   flutter run
   ```

### Dependencies

The app uses the following main dependencies:
- `flutter`: Core Flutter framework
- `http`: For making API calls to the backend
- `cupertino_icons`: iOS-style icons

## 🔌 Backend Integration

The app is designed to work with a Spring Boot backend. The `AuthService` class handles all API communication:

- **Base URL**: `http://localhost:8080/api/auth`
- **Endpoints**:
  - `POST /login` - User authentication
  - `POST /register` - User registration
  - `POST /logout` - User logout
  - `GET /profile` - Get user profile

## 📱 Platform Support

- ✅ Android
- ✅ iOS
- ✅ Web
- ✅ macOS
- ✅ Windows
- ✅ Linux

## 🚀 Future Enhancements

- [ ] Remember me functionality
- [ ] Biometric authentication
- [ ] Password reset functionality
- [ ] User profile management
- [ ] Dark mode support
- [ ] Multi-language support
- [ ] Push notifications
- [ ] Offline support

## 🐛 Troubleshooting

### Common Issues

1. **Dependencies not found**
   ```bash
   flutter clean
   flutter pub get
   ```

2. **Build errors**
   - Ensure Flutter SDK version compatibility
   - Check all imports are correct
   - Verify file paths in the project structure

3. **API connection issues**
   - Ensure backend server is running
   - Check network connectivity
   - Verify API endpoints in `AuthService`

## 📄 License

This project is licensed under the MIT License.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📞 Support

For support and questions, please open an issue in the repository or contact the development team.

---

**Happy Coding! 🎉**
