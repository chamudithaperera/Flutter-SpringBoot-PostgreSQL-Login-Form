package com.login.backend.service;

import com.login.backend.dto.RegisterRequest;
import com.login.backend.entity.User;
import com.login.backend.exception.UserAlreadyExistsException;
import com.login.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Register a new user
     * @param request registration request
     * @return registered user
     * @throws UserAlreadyExistsException if email already exists
     */
    public User registerUser(RegisterRequest request) {
        try {
            // Check if user already exists
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
            }

            // Create new user
            User user = new User();
            user.setFullName(request.getFullName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setIsActive(true);

            return userRepository.save(user);
        } catch (Exception e) {
            // Log the error for debugging
            System.err.println("Error registering user: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Find user by email
     * @param email user's email
     * @return Optional containing user if found
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmailAndIsActive(email, true);
    }

    /**
     * Find user by ID
     * @param id user's ID
     * @return Optional containing user if found
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Check if user exists by email
     * @param email user's email
     * @return true if user exists, false otherwise
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Update user profile
     * @param user user entity to update
     * @return updated user
     */
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Deactivate user account
     * @param userId user's ID
     */
    public void deactivateUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setIsActive(false);
            userRepository.save(user);
        }
    }

    /**
     * Validate user credentials
     * @param email user's email
     * @param rawPassword raw password to validate
     * @return true if credentials are valid, false otherwise
     */
    public boolean validateCredentials(String email, String rawPassword) {
        Optional<User> userOpt = findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return passwordEncoder.matches(rawPassword, user.getPassword());
        }
        return false;
    }
}
