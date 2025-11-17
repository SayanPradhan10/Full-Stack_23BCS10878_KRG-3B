package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Repository: Marks this as a Data Access Layer component
 * 
 * Purpose: Handle all database operations for User entity
 * - Performs CRUD operations
 * - Executes database queries
 * - Manages persistence logic
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Custom query method to find user by username
     */
    User findByUsername(String username);
    
    /**
     * Custom query method to find user by email
     */
    User findByEmail(String email);
    
    /**
     * Check if user exists by username
     */
    boolean existsByUsername(String username);
    
    /**
     * Check if user exists by email
     */
    boolean existsByEmail(String email);
}
