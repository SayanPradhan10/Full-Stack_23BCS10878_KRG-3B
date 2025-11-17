/**
 * Spring Boot Application - Annotation Demonstration
 * 
 * This is a complete backend application showing the use of:
 * @Component, @Service, and @Repository annotations
 * 
 * APPLICATION STATUS: READY TO RUN
 * 
 * Project Structure:
 * ├── pom.xml                         (Maven Configuration)
 * ├── src/main/java/com/example/
 * │   ├── Application.java            (@SpringBootApplication)
 * │   ├── controller/
 * │   │   └── UserController.java    (REST endpoints)
 * │   ├── service/
 * │   │   └── UserService.java       (@Service - Business Logic)
 * │   ├── repository/
 * │   │   └── UserRepository.java    (@Repository - Data Access)
 * │   ├── model/
 * │   │   └── User.java              (Entity)
 * │   └── util/
 * │       └── PasswordEncoder.java   (@Component - Utility)
 * └── src/main/resources/
 *     └── application.properties      (Configuration)
 * 
 * TO RUN THIS APPLICATION:
 * 
 * Option 1 (Using Maven):
 * .\apache-maven-3.8.6\bin\mvn.cmd spring-boot:run
 * 
 * Option 2 (Using Java directly):
 * .\apache-maven-3.8.6\bin\mvn.cmd clean install
 * java -jar target/user-management-1.0.0.jar
 * 
 * ENDPOINTS AVAILABLE:
 * 
 * POST   http://localhost:8080/api/users/register
 * GET    http://localhost:8080/api/users/{id}
 * PUT    http://localhost:8080/api/users/{id}
 * DELETE http://localhost:8080/api/users/{id}
 * 
 * H2 DATABASE CONSOLE:
 * http://localhost:8080/h2-console
 * JDBC URL: jdbc:h2:mem:testdb
 * Username: sa
 * Password: (leave empty)
 * 
 * ANNOTATION USAGE SUMMARY:
 * 
 * 1. @Repository (Data Access Layer)
 *    - UserRepository: Manages database operations
 *    - Extends JpaRepository for CRUD functionality
 *    - Custom query methods for finding users
 * 
 * 2. @Service (Business Logic Layer)
 *    - UserService: Contains all business logic
 *    - Validates user data before persistence
 *    - Implements complex business rules
 *    - Coordinates between controller and repository
 * 
 * 3. @Component (Generic Component)
 *    - PasswordEncoder: Utility class for password operations
 *    - Can be injected into any Spring bean
 *    - Provides reusable helper methods
 * 
 * DEPENDENCY INJECTION FLOW:
 * 
 * UserController
 *     ↓ (injects @Service)
 * UserService
 *     ↓ (injects @Repository)
 * UserRepository
 *     ↓ (injects @Component)
 * PasswordEncoder
 * 
 * KEY DIFFERENCES AT A GLANCE:
 * 
 * @Repository
 * - Purpose: Data Access Layer
 * - Typically extends: JpaRepository, CrudRepository
 * - Exceptions: Translated to DataAccessException
 * - When to use: Database queries and CRUD operations
 * 
 * @Service
 * - Purpose: Business Logic Layer
 * - Contains: Validation, calculations, workflows
 * - Transactional: Often marked with @Transactional
 * - When to use: Complex business rules and operations
 * 
 * @Component
 * - Purpose: Generic Spring-managed Bean
 * - Contains: Utility functions, helpers
 * - Special variants: @Service, @Repository, @Controller, @RestController
 * - When to use: Reusable utility classes
 */

package com.example.demo;

public class AnnotationDocumentation {
    /*
     * The application demonstrates best practices for:
     * 
     * 1. Layered Architecture
     *    - Controller layer for HTTP handling
     *    - Service layer for business logic
     *    - Repository layer for data access
     *    - Component layer for utilities
     * 
     * 2. Dependency Injection
     *    - All dependencies injected via @Autowired
     *    - Loose coupling between layers
     *    - Easy to test and maintain
     * 
     * 3. Separation of Concerns
     *    - Each layer has a specific responsibility
     *    - Business logic separated from database logic
     *    - Utilities separated from business logic
     * 
     * 4. Spring Framework Best Practices
     *    - Use appropriate annotations for semantics
     *    - Follow naming conventions
     *    - Use interfaces for repositories
     *    - Implement validation in service layer
     */
}
