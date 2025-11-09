package com.resumebuilder.service;

import com.resumebuilder.entity.Resume;
import com.resumebuilder.entity.User;
import com.resumebuilder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing user operations including registration, profile management, and authentication.
 */
@Service
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final ResumeService resumeService;
    
    @Autowired
    public UserService(UserRepository userRepository, ResumeService resumeService) {
        this.userRepository = userRepository;
        this.resumeService = resumeService;
    }
    
    /**
     * Creates a new user account.
     * 
     * @param user the user to create
     * @return the created User entity
     * @throws IllegalArgumentException if user data is invalid or email already exists
     */
    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        
        validateUserData(user);
        
        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }
        
        // Set creation date if not already set
        if (user.getCreatedDate() == null) {
            user.setCreatedDate(LocalDateTime.now());
        }
        
        return userRepository.save(user);
    }
    
    /**
     * Updates an existing user's profile information.
     * 
     * @param userId the ID of the user to update
     * @param updatedUser the updated user data
     * @return the updated User entity
     * @throws IllegalArgumentException if user not found or data is invalid
     */
    public User updateUser(Long userId, User updatedUser) {
        if (userId == null || updatedUser == null) {
            throw new IllegalArgumentException("User ID and updated user data cannot be null");
        }
        
        User existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        
        // Check if email is being changed and if new email already exists
        if (!existingUser.getEmail().equals(updatedUser.getEmail())) {
            Optional<User> userWithNewEmail = userRepository.findByEmail(updatedUser.getEmail());
            if (userWithNewEmail.isPresent() && !userWithNewEmail.get().getId().equals(userId)) {
                throw new IllegalArgumentException("Email " + updatedUser.getEmail() + " is already in use");
            }
        }
        
        // Update user fields
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setAddress(updatedUser.getAddress());
        
        validateUserData(existingUser);
        
        return userRepository.save(existingUser);
    }
    
    /**
     * Retrieves a user by ID.
     * 
     * @param userId the ID of the user to retrieve
     * @return the User entity
     * @throws IllegalArgumentException if user not found
     */
    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
    }
    
    /**
     * Retrieves a user by email address.
     * 
     * @param email the email address to search for
     * @return Optional containing the user if found, empty otherwise
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return Optional.empty();
        }
        
        return userRepository.findByEmail(email.trim().toLowerCase());
    }
    
    /**
     * Gets dashboard data for a user including all their resumes.
     * 
     * @param userId the ID of the user
     * @return DashboardData containing user info and resumes
     * @throws IllegalArgumentException if user not found
     */
    @Transactional(readOnly = true)
    public DashboardData getDashboardData(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        
        User user = getUserById(userId);
        List<Resume> resumes = resumeService.getResumesByUserId(userId);
        
        return new DashboardData(user, resumes);
    }
    
    /**
     * Authenticates a user by email (simplified authentication for demo purposes).
     * In a real application, this would include password verification.
     * 
     * @param email the user's email
     * @return the authenticated User entity
     * @throws IllegalArgumentException if authentication fails
     */
    @Transactional(readOnly = true)
    public User authenticateUser(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required for authentication");
        }
        
        return userRepository.findByEmail(email.trim().toLowerCase())
            .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }
    
    /**
     * Deletes a user account and all associated resumes.
     * 
     * @param userId the ID of the user to delete
     * @throws IllegalArgumentException if user not found
     */
    public void deleteUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        
        userRepository.delete(user);
    }
    
    /**
     * Validates user data for business rules.
     * 
     * @param user the user to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateUserData(User user) {
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        
        // Basic email format validation
        String email = user.getEmail().trim().toLowerCase();
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        // Update email to lowercase for consistency
        user.setEmail(email);
        
        // Validate name length
        if (user.getName().length() > 255) {
            throw new IllegalArgumentException("Name cannot exceed 255 characters");
        }
        
        // Validate phone length if provided
        if (user.getPhone() != null && user.getPhone().length() > 20) {
            throw new IllegalArgumentException("Phone number cannot exceed 20 characters");
        }
        
        // Validate address length if provided
        if (user.getAddress() != null && user.getAddress().length() > 500) {
            throw new IllegalArgumentException("Address cannot exceed 500 characters");
        }
    }
    
    /**
     * Data class for dashboard information.
     */
    public static class DashboardData {
        private final User user;
        private final List<Resume> resumes;
        
        public DashboardData(User user, List<Resume> resumes) {
            this.user = user;
            this.resumes = resumes;
        }
        
        public User getUser() { return user; }
        public List<Resume> getResumes() { return resumes; }
        public int getResumeCount() { return resumes != null ? resumes.size() : 0; }
    }
}