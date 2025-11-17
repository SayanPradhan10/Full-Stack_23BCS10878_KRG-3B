package com.example.util;

import org.springframework.stereotype.Component;

/**
 * @Component: Marks this as a generic Spring-managed component
 * 
 * Purpose: Utility/Helper classes that don't fit Service or Repository
 * - Reusable utilities
 * - Helper functions
 * - Formatting, conversion, validation helpers
 * - NOT business logic
 * - NOT database operations
 */
@Component
public class PasswordEncoder {
    
    /**
     * Utility method: Encode password (simplified example)
     * In real applications, use BCryptPasswordEncoder
     */
    public String encodePassword(String password) {
        // Simplified encoding - use Spring Security in production
        return "{encoded}" + password.hashCode();
    }
    
    /**
     * Utility method: Validate password strength
     */
    public boolean isStrongPassword(String password) {
        // Rules: at least 8 chars, 1 uppercase, 1 number, 1 special char
        return password != null &&
               password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*[0-9].*") &&
               password.matches(".*[!@#$%^&*()].*");
    }
    
    /**
     * Utility method: Generate random token
     */
    public String generateToken(int length) {
        String characters = 
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder token = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            token.append(characters.charAt(index));
        }
        
        return token.toString();
    }
}
