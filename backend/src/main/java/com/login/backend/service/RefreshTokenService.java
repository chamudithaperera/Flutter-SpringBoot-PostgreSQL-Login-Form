package com.login.backend.service;

import com.login.backend.entity.RefreshToken;
import com.login.backend.entity.User;
import com.login.backend.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;

    /**
     * Create a new refresh token for user
     * @param user user entity
     * @return refresh token
     */
    @Transactional
    public RefreshToken createRefreshToken(User user) {
        try {
            // Revoke existing tokens for the user (but don't delete them)
            revokeExistingTokensForUser(user);
            
            // Create new refresh token
            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setUser(user);
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000));
            refreshToken.setIsRevoked(false);
            
            return refreshTokenRepository.save(refreshToken);
        } catch (Exception e) {
            // Log the error for debugging
            System.err.println("Error creating refresh token: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Find refresh token by token value
     * @param token refresh token value
     * @return Optional containing refresh token if found
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Verify if refresh token is valid
     * @param token refresh token value
     * @return refresh token if valid
     * @throws RuntimeException if token is invalid or expired
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(LocalDateTime.now()) < 0 || token.getIsRevoked()) {
            throw new RuntimeException("Refresh token was expired or revoked. Please make a new signin request");
        }
        return token;
    }

    /**
     * Revoke refresh token
     * @param token refresh token value
     */
    @Transactional
    public void revokeToken(String token) {
        try {
            Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token);
            if (refreshToken.isPresent()) {
                RefreshToken rt = refreshToken.get();
                rt.setIsRevoked(true);
                refreshTokenRepository.save(rt);
            }
        } catch (Exception e) {
            System.err.println("Error revoking token: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Revoke existing tokens for a user (mark as revoked but don't delete)
     * @param user user entity
     */
    @Transactional
    private void revokeExistingTokensForUser(User user) {
        try {
            // Find existing active tokens and mark them as revoked
            Optional<RefreshToken> existingToken = refreshTokenRepository.findByUser(user);
            if (existingToken.isPresent()) {
                RefreshToken token = existingToken.get();
                if (!token.getIsRevoked()) {
                    token.setIsRevoked(true);
                    refreshTokenRepository.save(token);
                }
            }
        } catch (Exception e) {
            // Log the error but don't fail the login process
            System.err.println("Warning: Could not revoke existing tokens: " + e.getMessage());
            // Don't throw the exception to avoid breaking the login process
        }
    }

    /**
     * Delete expired refresh tokens
     */
    @Transactional
    public void deleteExpiredTokens() {
        try {
            refreshTokenRepository.deleteExpiredTokens(LocalDateTime.now());
        } catch (Exception e) {
            System.err.println("Warning: Could not delete expired tokens: " + e.getMessage());
        }
    }

    /**
     * Delete all tokens for a user
     * @param user user entity
     */
    @Transactional
    public void deleteAllTokensForUser(User user) {
        try {
            refreshTokenRepository.deleteAllTokensForUser(user);
        } catch (Exception e) {
            System.err.println("Warning: Could not delete tokens for user: " + e.getMessage());
        }
    }
}
