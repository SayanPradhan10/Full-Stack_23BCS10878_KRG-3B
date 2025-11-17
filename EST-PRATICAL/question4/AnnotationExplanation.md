# Spring Annotations: @Component, @Service, and @Repository

## Overview

These are Spring Framework annotations used for **dependency injection** and **component scanning**. They mark classes as Spring-managed beans.

---

## 1. @Component

**Purpose:** Generic marker for any Spring-managed component  
**Scope:** General-purpose classes that don't fit other categories  
**Use When:** You have a class that needs Spring to manage but isn't specifically a service or data access layer

```
@Component
public class MyUtilityClass {
    public void doSomething() { }
}
```

---

## 2. @Service

**Purpose:** Marks a class as a service layer component  
**Scope:** Business logic, calculations, operations  
**Use When:** Your class contains business logic, validates data, or orchestrates operations

```
@Service
public class UserService {
    public void registerUser(User user) { }
    public void validateUser(User user) { }
}
```

---

## 3. @Repository

**Purpose:** Marks a class as a data access/persistence layer component  
**Scope:** Database operations, queries, CRUD operations  
**Use When:** Your class handles database operations, queries, or persistence logic

```
@Repository
public class UserRepository {
    public User findById(Long id) { }
    public void save(User user) { }
}
```

---

## Key Differences

| Feature | @Component | @Service | @Repository |
|---------|-----------|----------|-------------|
| **Purpose** | Generic component | Business logic | Data access |
| **Exception Handling** | Basic | Yes | DataAccessException translation |
| **Semantics** | None | Clear intent | Database-focused |
| **When to Use** | Utility classes | Business operations | Database operations |

---

## Architecture Layer Structure

```
Controller Layer
      ↓ (uses)
Service Layer (@Service)
      ↓ (uses)
Repository Layer (@Repository)
      ↓ (uses)
Database

@Component - Can be used anywhere for utility/helper classes
```
