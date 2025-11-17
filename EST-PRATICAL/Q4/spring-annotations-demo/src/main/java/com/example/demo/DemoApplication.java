package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
        
        // Get the service bean and demonstrate the flow
        UserService userService = context.getBean(UserService.class);
        
        System.out.println("\n=== Demo: Creating and retrieving users ===\n");
        
        // Create users
        userService.registerUser("Alice", "alice@example.com");
        userService.registerUser("Bob", "bob@example.com");
        
        // Retrieve users
        System.out.println("\nAll users:");
        userService.getAllUsers().forEach(System.out::println);
        
        // Find specific user
        System.out.println("\nFinding user by email:");
        userService.findUserByEmail("alice@example.com")
                  .ifPresent(user -> System.out.println("Found: " + user));
    }
}
