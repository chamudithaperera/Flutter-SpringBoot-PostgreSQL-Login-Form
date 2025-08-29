package com.login.backend.controller;

import com.login.backend.entity.User;
import com.login.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Get current user profile
     * @return user profile
     */
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            
            User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Update user profile
     * @param user updated user data
     * @return updated user profile
     */
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody User user) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            
            User currentUser = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Update allowed fields only
            currentUser.setFullName(user.getFullName());
            if (user.getProfilePicture() != null) {
                currentUser.setProfilePicture(user.getProfilePicture());
            }
            
            User updatedUser = userService.updateUser(currentUser);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Deactivate user account
     * @return success message
     */
    @DeleteMapping("/profile")
    public ResponseEntity<?> deactivateAccount() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            
            User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            userService.deactivateUser(user.getId());
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Account deactivated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
