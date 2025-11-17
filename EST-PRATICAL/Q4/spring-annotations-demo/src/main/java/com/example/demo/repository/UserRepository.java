package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Repository: Used for Data Access Layer
 * - Handles database/data storage operations
 * - Provides exception translation (SQLException -> DataAccessException)
 * - Encapsulates data access logic
 */
@Repository
public class UserRepository {
    
    private final Map<Long, User> database = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
        }
        database.put(user.getId(), user);
        System.out.println("[@Repository] Saved user to database: " + user);
        return user;
    }

    public Optional<User> findById(Long id) {
        System.out.println("[@Repository] Finding user by id: " + id);
        return Optional.ofNullable(database.get(id));
    }

    public Optional<User> findByEmail(String email) {
        System.out.println("[@Repository] Finding user by email: " + email);
        return database.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    public List<User> findAll() {
        System.out.println("[@Repository] Retrieving all users from database");
        return new ArrayList<>(database.values());
    }

    public void deleteById(Long id) {
        System.out.println("[@Repository] Deleting user with id: " + id);
        database.remove(id);
    }
}
