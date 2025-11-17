package com.example.demo.component;

import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

/**
 * @Component: Generic stereotype for any Spring-managed component
 * - Used for utility classes, helpers, validators
 * - When class doesn't fit @Service, @Repository, or @Controller
 * - Most generic Spring-managed bean
 */
@Component
public class EmailValidator {

    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public EmailValidator() {
        System.out.println("[@Component] EmailValidator initialized");
    }

    public boolean isValid(String email) {
        boolean valid = email != null && EMAIL_PATTERN.matcher(email).matches();
        System.out.println("[@Component] Email validation for '" + email + "': " + valid);
        return valid;
    }
}
