# Quick Reference: When to Use Each Annotation

## @Component
**Best For:**
- Utility classes
- Helper classes
- Formatter classes
- Converter classes
- Generic beans that don't fit other categories

**Example:**
```java
@Component
public class EmailValidator {
    public boolean validate(String email) { }
}
```

---

## @Service
**Best For:**
- Business logic
- Calculations
- Validation rules
- Orchestration between layers
- Transaction management
- Exception handling for business rules

**Example:**
```java
@Service
public class PaymentService {
    public boolean processPayment(Payment payment) {
        // Complex business logic
    }
}
```

---

## @Repository
**Best For:**
- Database CRUD operations
- Query methods
- Data persistence
- Data access logic
- Database transactions

**Example:**
```java
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
}
```

---

## Architecture Flow

```
HTTP Request
    ↓
@RestController (UserController)
    ↓
@Service (UserService) - Business Logic & Validation
    ↓
@Repository (UserRepository) - Database Operations
    ↓
Database
```

Each layer has a specific responsibility:
1. **Controller** - Handles HTTP requests/responses
2. **Service** - Contains business logic
3. **Repository** - Handles data persistence
4. **Component** - Utility/helper classes used by any layer

---

## Key Points

| Aspect | @Component | @Service | @Repository |
|--------|-----------|----------|-------------|
| **Scope** | Generic | Business | Data Access |
| **Exception Handling** | No | Yes | Yes (DataAccessException) |
| **Transactional Support** | No | Yes | Partial |
| **Semantic Meaning** | None | "This is business logic" | "This accesses database" |
| **Should be Reused** | Yes | Yes | Yes |
| **Business Logic Inside** | No | Yes | No |
