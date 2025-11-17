package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @Service: Marks this as a Business Logic Layer component
 * 
 * Purpose: Contain business logic, validation, and orchestration
 * - Validates user data
 * - Implements business rules
 * - Coordinates between controller and repository
 * - Handles exceptions and error scenarios
 */
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    private static final String EMAIL_REGEX = 
        "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile(EMAIL_REGEX);
    
    /**
     * Business logic: Register a new user with validation
     */
    public User registerUser(User user) {
        // Business rule: Validate username
        if (user.getUsername() == null || 
            user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Username cannot be empty");
        }
        
        // Business rule: Username must be at least 3 characters
        if (user.getUsername().length() < 3) {
            throw new IllegalArgumentException(
                "Username must be at least 3 characters");
        }
        
        // Business rule: Check if username already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException(
                "Username already exists");
        }
        
        // Business rule: Validate email format
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException(
                "Invalid email format");
        }
        
        // Business rule: Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException(
                "Email already exists");
        }
        
        // Business rule: Password must be at least 6 characters
        if (user.getPassword().length() < 6) {
            throw new IllegalArgumentException(
                "Password must be at least 6 characters");
        }
        
        // Save user to database (delegates to repository)
        return userRepository.save(user);
    }
    
    /**
     * Business logic: Get user by ID
     */
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> 
            new IllegalArgumentException(
                "User not found with id: " + id));
    }
    
    /**
     * Business logic: Get user by username
     */
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException(
                "User not found with username: " + username);
        }
        return user;
    }
    
    /**
     * Business logic: Update user information
     */
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        
        // Business rule: Only allow email updates if not already used
        if (!user.getEmail().equals(userDetails.getEmail())) {
            if (userRepository.existsByEmail(
                userDetails.getEmail())) {
                throw new IllegalArgumentException(
                    "Email already exists");
            }
        }
        
        user.setEmail(userDetails.getEmail());
        user.setUsername(userDetails.getUsername());
        user.setActive(userDetails.isActive());
        
        return userRepository.save(user);
    }
    
    /**
     * Business logic: Delete user (soft delete)
     */
    public void deleteUser(Long id) {
        User user = getUserById(id);
        user.setActive(false);
        userRepository.save(user);
    }
    
    /**
     * Business logic: Email validation helper
     */
    private boolean isValidEmail(String email) {
        return email != null && 
               EMAIL_PATTERN.matcher(email).matches();
    }
}
