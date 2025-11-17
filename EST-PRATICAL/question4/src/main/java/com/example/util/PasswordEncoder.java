package com.example.util;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
    
    public String encodePassword(String password) {
        return "{encoded}" + password.hashCode();
    }
    
    public boolean isStrongPassword(String password) {
        return password != null &&
               password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*[0-9].*") &&
               password.matches(".*[!@#$%^&*()].*");
    }
    
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
