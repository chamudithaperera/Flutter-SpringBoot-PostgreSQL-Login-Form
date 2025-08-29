package com.login.backend.repository;

import com.login.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by email
     * @param email user's email
     * @return Optional containing user if found
     */
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    /**
     * Check if user exists by email
     * @param email user's email
     * @return true if user exists, false otherwise
     */
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    /**
     * Find user by email and isActive status
     * @param email user's email
     * @param isActive user's active status
     * @return Optional containing user if found
     */
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.isActive = :isActive")
    Optional<User> findByEmailAndIsActive(@Param("email") String email, @Param("isActive") Boolean isActive);
}
