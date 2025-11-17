package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    private static final String EMAIL_REGEX = 
        "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile(EMAIL_REGEX);
    
    public User registerUser(User user) {
        if (user.getUsername() == null || 
            user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Username cannot be empty");
        }
        
        if (user.getUsername().length() < 3) {
            throw new IllegalArgumentException(
                "Username must be at least 3 characters");
        }
        
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException(
                "Username already exists");
        }
        
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException(
                "Invalid email format");
        }
        
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException(
                "Email already exists");
        }
        
        if (user.getPassword().length() < 6) {
            throw new IllegalArgumentException(
                "Password must be at least 6 characters");
        }
        
        return userRepository.save(user);
    }
    
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> 
            new IllegalArgumentException(
                "User not found with id: " + id));
    }
    
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException(
                "User not found with username: " + username);
        }
        return user;
    }
    
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        
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
    
    public void deleteUser(Long id) {
        User user = getUserById(id);
        user.setActive(false);
        userRepository.save(user);
    }
    
    private boolean isValidEmail(String email) {
        return email != null && 
               EMAIL_PATTERN.matcher(email).matches();
    }
}
