package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Application Entry Point
 * 
 * @SpringBootApplication enables:
 * - Component scanning (finds @Component, @Service, @Repository)
 * - Auto-configuration
 * - Dependency injection
 */
@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
