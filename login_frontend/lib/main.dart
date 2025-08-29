import 'package:flutter/material.dart';
import 'package:login_frontend/screens/auth/login_screen.dart';
import 'package:login_frontend/screens/auth/register_screen.dart';
import 'package:login_frontend/screens/home/home_screen.dart';
import 'package:login_frontend/utils/app_theme.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Login App',
      debugShowCheckedModeBanner: false,
      theme: AppTheme.lightTheme,
      initialRoute: '/login',
      onGenerateRoute: (settings) {
        if (settings.name == '/home') {
          return MaterialPageRoute(
            builder: (context) => const HomeScreen(),
            settings: settings,
          );
        }
        return null;
      },
      routes: {
        '/login': (context) => const LoginScreen(),
        '/register': (context) => const RegisterScreen(),
      },
    );
  }
}
