package com.login.backend.repository;

import com.login.backend.entity.RefreshToken;
import com.login.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    /**
     * Find refresh token by token value
     * @param token refresh token value
     * @return Optional containing refresh token if found
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Find refresh token by user
     * @param user user entity
     * @return Optional containing refresh token if found
     */
    Optional<RefreshToken> findByUser(User user);

    /**
     * Delete all expired refresh tokens
     * @param now current timestamp
     */
    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.expiryDate < ?1")
    void deleteExpiredTokens(LocalDateTime now);

    /**
     * Revoke all refresh tokens for a user
     * @param user user entity
     */
    @Modifying
    @Query("UPDATE RefreshToken rt SET rt.isRevoked = true WHERE rt.user = ?1")
    void revokeAllTokensForUser(User user);

    /**
     * Delete all refresh tokens for a user
     * @param user user entity
     */
    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.user = ?1")
    void deleteAllTokensForUser(User user);
}
