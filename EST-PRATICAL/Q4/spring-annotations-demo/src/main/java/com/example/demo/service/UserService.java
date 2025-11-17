package com.example.demo.service;

import com.example.demo.component.EmailValidator;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Service: Used for Business Logic Layer
 * - Contains business logic and validation
 * - Orchestrates between repositories and controllers
 * - Manages transactions (when configured)
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailValidator emailValidator;

    // Constructor injection (recommended approach)
    public UserService(UserRepository userRepository, EmailValidator emailValidator) {
        this.userRepository = userRepository;
        this.emailValidator = emailValidator;
        System.out.println("[@Service] UserService initialized with dependencies");
    }

    public User registerUser(String name, String email) {
        System.out.println("\n[@Service] Processing user registration for: " + name);
        
        // Business logic: validate email using component
        if (!emailValidator.isValid(email)) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }

        // Business logic: check if user already exists
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User with email already exists: " + email);
        }

        // Create and save user via repository
        User user = new User(null, name, email);
        return userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        System.out.println("\n[@Service] Looking up user by email: " + email);
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        System.out.println("\n[@Service] Fetching all users");
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        System.out.println("\n[@Service] Deleting user with id: " + id);
        userRepository.deleteById(id);
    }
}
