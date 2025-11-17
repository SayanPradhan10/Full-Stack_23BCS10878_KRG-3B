# Spring Boot Annotations Demo

A simple demonstration of `@Component`, `@Service`, and `@Repository` annotations in Spring Boot.

## Annotations Explained

### @Component
- **Purpose**: Generic stereotype for any Spring-managed component
- **Use case**: Utility classes, helpers, validators, or any bean that doesn't fit other categories
- **Example**: `EmailValidator` - validates email format

### @Service
- **Purpose**: Business logic layer
- **Use case**: Contains business rules, validation, orchestration between repositories
- **Example**: `UserService` - handles user registration logic, validation

### @Repository
- **Purpose**: Data access layer (persistence)
- **Use case**: Interacts with database/storage, CRUD operations
- **Benefits**: Automatic exception translation (SQLException → DataAccessException)
- **Example**: `UserRepository` - manages user data storage

## How They Interact

```
[Main] → [UserService (@Service)]
              ↓
              ├─→ [EmailValidator (@Component)] - validates email
              └─→ [UserRepository (@Repository)] - persists data
```

## Project Structure

```
spring-annotations-demo/
├── src/main/java/com/example/demo/
│   ├── DemoApplication.java           # Main application
│   ├── component/
│   │   └── EmailValidator.java        # @Component
│   ├── service/
│   │   └── UserService.java           # @Service
│   ├── repository/
│   │   └── UserRepository.java        # @Repository
│   └── model/
│       └── User.java                  # POJO
└── pom.xml
```

## Running the Application

```bash
mvn spring-boot:run
```

## Expected Output

The application will:
1. Initialize all Spring beans (@Component, @Service, @Repository)
2. Register two users (Alice and Bob)
3. Retrieve and display all users
4. Find a user by email

You'll see console output showing the flow through each layer:
- `[@Component]` - Email validation
- `[@Service]` - Business logic execution
- `[@Repository]` - Database operations

## Key Differences

| Annotation    | Layer           | Purpose                          |
|---------------|-----------------|----------------------------------|
| @Component    | Generic         | Any Spring-managed bean          |
| @Service      | Business Logic  | Business rules & orchestration   |
| @Repository   | Data Access     | Database/persistence operations  |

**Note**: `@Service` and `@Repository` are specialized versions of `@Component` with additional semantics and benefits (like exception translation for repositories).
