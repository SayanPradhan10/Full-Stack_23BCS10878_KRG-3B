# Spring Boot Application - @Component, @Service, @Repository Demo

## ✅ Application Ready to Run

Your Spring Boot backend application is fully configured and ready to execute!

### Quick Start

#### **Option 1: Run with Maven** (Recommended)
```powershell
cd d:\question4
.\apache-maven-3.8.6\bin\mvn.cmd spring-boot:run
```

#### **Option 2: Build and Run JAR**
```powershell
cd d:\question4
.\apache-maven-3.8.6\bin\mvn.cmd clean install
java -jar target/user-management-1.0.0.jar
```

---

## 📁 Project Structure

```
d:\question4\
├── pom.xml                              (Maven Configuration)
├── src/main/java/com/example/
│   ├── Application.java                 (@SpringBootApplication)
│   ├── controller/
│   │   └── UserController.java         (REST Endpoints)
│   ├── service/
│   │   └── UserService.java            (@Service - Business Logic)
│   ├── repository/
│   │   └── UserRepository.java         (@Repository - Data Access)
│   ├── model/
│   │   └── User.java                   (JPA Entity)
│   └── util/
│       └── PasswordEncoder.java        (@Component - Utility)
├── src/main/resources/
│   └── application.properties           (Configuration)
├── apache-maven-3.8.6/                  (Maven Installation)
├── AnnotationExplanation.md             (Detailed Explanation)
└── QuickReference.md                    (Quick Lookup)
```

---

## 🚀 API Endpoints

Once running, the application will be available at `http://localhost:8080`

### User Registration
```bash
POST /api/users/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "MyPassword123"
}
```

### Get User
```bash
GET /api/users/{id}
```

### Update User
```bash
PUT /api/users/{id}
Content-Type: application/json

{
  "username": "jane_doe",
  "email": "jane@example.com",
  "active": true
}
```

### Delete User
```bash
DELETE /api/users/{id}
```

---

## 🗄️ Database Configuration

### H2 In-Memory Database
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (empty)
- **Console URL**: `http://localhost:8080/h2-console` (when running)

### Auto-generated Schema
The database schema is automatically created at startup with:
- `users` table with columns: `id`, `username`, `email`, `password`, `active`

---

## 🏗️ Architecture Layers

### Controller Layer
- **Class**: `UserController.java`
- **Annotation**: `@RestController`
- **Responsibility**: Handle HTTP requests/responses
- **Methods**: POST, GET, PUT, DELETE

### Service Layer
- **Class**: `UserService.java`
- **Annotation**: `@Service`
- **Responsibility**: Business logic & validation
- **Features**:
  - Email validation
  - Username uniqueness check
  - Password strength validation
  - Soft delete functionality

### Repository Layer
- **Class**: `UserRepository.java`
- **Annotation**: `@Repository`
- **Responsibility**: Database CRUD operations
- **Inheritance**: Extends `JpaRepository<User, Long>`
- **Methods**:
  - `findByUsername(String username)`
  - `findByEmail(String email)`
  - `existsByUsername(String username)`
  - `existsByEmail(String email)`

### Component (Utility) Layer
- **Class**: `PasswordEncoder.java`
- **Annotation**: `@Component`
- **Responsibility**: Reusable utility functions
- **Methods**:
  - Password encoding
  - Password strength validation
  - Token generation

---

## 🔑 Key Annotation Differences

| Aspect | @Component | @Service | @Repository |
|--------|-----------|----------|-------------|
| **Layer** | Generic | Business Logic | Data Access |
| **Purpose** | Helper/Utility | Business Operations | Database Operations |
| **Exception Handling** | Basic | Yes | Yes (DataAccessException) |
| **Transactional Support** | No | Yes (recommended) | Yes (partial) |
| **Semantics** | Generic | Clear Intent | Database-focused |
| **Use When** | Reusable utilities | Complex business logic | Database queries |

---

## 💡 Business Logic Examples in @Service

### User Registration Validation
```java
1. Username must not be empty
2. Username must be at least 3 characters
3. Username must be unique
4. Email must be valid format
5. Email must be unique
6. Password must be at least 6 characters
```

### Soft Delete Implementation
Instead of actual deletion, the `active` flag is set to `false`.

---

## 🧪 Testing the Application

### Using cURL or Postman

**1. Register a new user:**
```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","email":"alice@example.com","password":"password123"}'
```

**2. Retrieve user (replace {id} with returned ID):**
```bash
curl http://localhost:8080/api/users/1
```

**3. Update user:**
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"username":"alice_updated","email":"alice.new@example.com","active":true}'
```

**4. Delete user:**
```bash
curl -X DELETE http://localhost:8080/api/users/1
```

---

## 📋 Dependencies

### Spring Boot 2.7.14
- spring-boot-starter-web (REST API support)
- spring-boot-starter-data-jpa (Database access)
- h2 (In-memory database)
- spring-boot-devtools (Auto-reload during development)

### Java Version
- **Compiled for**: Java 11
- **Your System**: Java 25.0.1 LTS ✅

---

## ✨ Features Implemented

✅ **Layered Architecture** - Clean separation of concerns  
✅ **Dependency Injection** - Using @Autowired  
✅ **Business Validation** - Email, username, password checks  
✅ **Database Operations** - CRUD through JPA  
✅ **Error Handling** - Proper HTTP status codes  
✅ **Spring Annotations** - @Component, @Service, @Repository  
✅ **RESTful API** - Standard REST endpoints  
✅ **In-Memory Database** - H2 for easy testing  

---

## 🔧 Troubleshooting

### Port Already in Use
If port 8080 is in use, modify `src/main/resources/application.properties`:
```properties
server.port=8081
```

### Download Timeout
If Maven dependencies download slowly, try increasing timeout:
```powershell
$env:MAVEN_OPTS="-Dorg.slf4j.simpleLogger.defaultLogLevel=warn"
```

### Clear Maven Cache
```powershell
cd d:\question4
.\apache-maven-3.8.6\bin\mvn.cmd clean
```

---

## 📚 Additional Resources

- **Detailed Explanation**: See `AnnotationExplanation.md`
- **Quick Reference**: See `QuickReference.md`
- **Source Code**: Files in `src/main/java/com/example/`

---

## ✅ Status

- ✅ Maven installed and configured
- ✅ pom.xml created with all dependencies
- ✅ Java source files in correct directory structure
- ✅ application.properties configured
- ✅ H2 database configured
- ✅ All classes ready to compile and run
- ✅ API endpoints ready for testing

**Ready to execute!** 🚀
