package com.login.backend.service;

import com.login.backend.entity.RefreshToken;
import com.login.backend.entity.User;
import com.login.backend.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
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
    public RefreshToken createRefreshToken(User user) {
        // Revoke existing tokens for the user
        refreshTokenRepository.revokeAllTokensForUser(user);
        
        // Create new refresh token
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000));
        refreshToken.setIsRevoked(false);
        
        return refreshTokenRepository.save(refreshToken);
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
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired or revoked. Please make a new signin request");
        }
        return token;
    }

    /**
     * Revoke refresh token
     * @param token refresh token value
     */
    public void revokeToken(String token) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token);
        if (refreshToken.isPresent()) {
            RefreshToken rt = refreshToken.get();
            rt.setIsRevoked(true);
            refreshTokenRepository.save(rt);
        }
    }

    /**
     * Revoke all tokens for a user
     * @param user user entity
     */
    public void revokeAllTokensForUser(User user) {
        refreshTokenRepository.revokeAllTokensForUser(user);
    }

    /**
     * Delete expired refresh tokens
     */
    public void deleteExpiredTokens() {
        refreshTokenRepository.deleteExpiredTokens(LocalDateTime.now());
    }

    /**
     * Delete all tokens for a user
     * @param user user entity
     */
    public void deleteAllTokensForUser(User user) {
        refreshTokenRepository.deleteAllTokensForUser(user);
    }
}
