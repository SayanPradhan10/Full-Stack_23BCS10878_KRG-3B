package com.resumebuilder.repository;

import com.resumebuilder.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for UserRepository.
 * Uses @DataJpaTest for testing JPA repositories with H2 in-memory database.
 */
@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByEmail_WhenUserExists_ShouldReturnUser() {
        // Given
        User user = new User("john.doe@example.com", "John Doe");
        user.setPhone("123-456-7890");
        user.setAddress("123 Main St, City, State");
        entityManager.persistAndFlush(user);

        // When
        Optional<User> foundUser = userRepository.findByEmail("john.doe@example.com");

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("John Doe");
        assertThat(foundUser.get().getPhone()).isEqualTo("123-456-7890");
        assertThat(foundUser.get().getAddress()).isEqualTo("123 Main St, City, State");
    }

    @Test
    void testFindByEmail_WhenUserDoesNotExist_ShouldReturnEmpty() {
        // When
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        // Then
        assertThat(foundUser).isEmpty();
    }

    @Test
    void testSaveUser_ShouldPersistUser() {
        // Given
        User user = new User("jane.smith@example.com", "Jane Smith");

        // When
        User savedUser = userRepository.save(user);

        // Then
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("jane.smith@example.com");
        assertThat(savedUser.getName()).isEqualTo("Jane Smith");
        assertThat(savedUser.getCreatedDate()).isNotNull();
    }

    @Test
    void testFindById_WhenUserExists_ShouldReturnUser() {
        // Given
        User user = new User("test@example.com", "Test User");
        User savedUser = entityManager.persistAndFlush(user);

        // When
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("test@example.com");
        assertThat(foundUser.get().getName()).isEqualTo("Test User");
    }

    @Test
    void testDeleteUser_ShouldRemoveUser() {
        // Given
        User user = new User("delete@example.com", "Delete User");
        User savedUser = entityManager.persistAndFlush(user);

        // When
        userRepository.deleteById(savedUser.getId());
        entityManager.flush();

        // Then
        Optional<User> foundUser = userRepository.findById(savedUser.getId());
        assertThat(foundUser).isEmpty();
    }
}