package com.login.backend.repository;

import com.login.backend.entity.RefreshToken;
import com.login.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query("SELECT rt FROM RefreshToken rt WHERE rt.token = :token")
    Optional<RefreshToken> findByToken(@Param("token") String token);

    /**
     * Find refresh token by user
     * @param user user entity
     * @return Optional containing refresh token if found
     */
    @Query("SELECT rt FROM RefreshToken rt WHERE rt.user = :user")
    Optional<RefreshToken> findByUser(@Param("user") User user);

    /**
     * Delete all expired refresh tokens
     * @param now current timestamp
     */
    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.expiryDate < :now")
    void deleteExpiredTokens(@Param("now") LocalDateTime now);

    /**
     * Revoke all refresh tokens for a user
     * @param user user entity
     */
    @Modifying
    @Query("UPDATE RefreshToken rt SET rt.isRevoked = true WHERE rt.user = :user")
    void revokeAllTokensForUser(@Param("user") User user);

    /**
     * Delete all refresh tokens for a user
     * @param user user entity
     */
    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.user = :user")
    void deleteAllTokensForUser(@Param("user") User user);
}
