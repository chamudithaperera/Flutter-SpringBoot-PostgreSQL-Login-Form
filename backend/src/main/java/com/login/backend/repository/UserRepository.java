package com.login.backend.repository;

import com.login.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by email
     * @param email user's email
     * @return Optional containing user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if user exists by email
     * @param email user's email
     * @return true if user exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Find user by email and isActive status
     * @param email user's email
     * @param isActive user's active status
     * @return Optional containing user if found
     */
    Optional<User> findByEmailAndIsActive(String email, Boolean isActive);
}
