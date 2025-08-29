package com.login.backend.service;

import com.login.backend.dto.AuthResponse;
import com.login.backend.dto.LoginRequest;
import com.login.backend.dto.RegisterRequest;
import com.login.backend.entity.RefreshToken;
import com.login.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    /**
     * Register a new user
     * @param request registration request
     * @return authentication response with tokens
     */
    public AuthResponse register(RegisterRequest request) {
        // Register user
        User user = userService.registerUser(request);
        
        // Create user details for JWT
        UserDetails userDetails = createUserDetails(user);
        
        // Generate tokens
        String accessToken = jwtService.generateAccessToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        
        return new AuthResponse(
            accessToken,
            refreshToken.getToken(),
            jwtService.getAccessTokenExpiration(),
            user
        );
    }

    /**
     * Authenticate user and generate tokens
     * @param request login request
     * @return authentication response with tokens
     */
    public AuthResponse login(LoginRequest request) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByEmail(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Generate tokens
        String accessToken = jwtService.generateAccessToken(userDetails);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        
        return new AuthResponse(
            accessToken,
            refreshToken.getToken(),
            jwtService.getAccessTokenExpiration(),
            user
        );
    }

    /**
     * Refresh access token using refresh token
     * @param refreshToken refresh token value
     * @return new authentication response
     */
    public AuthResponse refreshToken(String refreshToken) {
        // Find and validate refresh token
        RefreshToken token = refreshTokenService.findByToken(refreshToken)
            .orElseThrow(() -> new RuntimeException("Refresh token not found"));
        
        token = refreshTokenService.verifyExpiration(token);
        
        // Get user details
        User user = token.getUser();
        UserDetails userDetails = createUserDetails(user);
        
        // Generate new tokens
        String newAccessToken = jwtService.generateAccessToken(userDetails);
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);
        
        return new AuthResponse(
            newAccessToken,
            newRefreshToken.getToken(),
            jwtService.getAccessTokenExpiration(),
            user
        );
    }

    /**
     * Logout user and revoke tokens
     * @param refreshToken refresh token to revoke
     */
    public void logout(String refreshToken) {
        refreshTokenService.revokeToken(refreshToken);
    }

    /**
     * Create UserDetails from User entity
     * @param user user entity
     * @return UserDetails object
     */
    private UserDetails createUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .authorities("USER")
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .disabled(!user.getIsActive())
            .build();
    }
}
