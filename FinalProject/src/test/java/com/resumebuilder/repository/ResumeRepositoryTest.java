package com.resumebuilder.repository;

import com.resumebuilder.entity.Resume;
import com.resumebuilder.entity.TemplateType;
import com.resumebuilder.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for ResumeRepository.
 * Uses @DataJpaTest for testing JPA repositories with H2 in-memory database.
 */
@DataJpaTest
@ActiveProfiles("test")
class ResumeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ResumeRepository resumeRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("test@example.com", "Test User");
        testUser = entityManager.persistAndFlush(testUser);
    }

    @Test
    void testFindByUserIdOrderByCreatedDateDesc_ShouldReturnResumesInDescendingOrder() {
        // Given
        Resume resume1 = new Resume(testUser, "Resume 1", TemplateType.CLASSIC);
        resume1.setCreatedDate(LocalDateTime.now().minusDays(2));
        
        Resume resume2 = new Resume(testUser, "Resume 2", TemplateType.MODERN);
        resume2.setCreatedDate(LocalDateTime.now().minusDays(1));
        
        Resume resume3 = new Resume(testUser, "Resume 3", TemplateType.CREATIVE);
        resume3.setCreatedDate(LocalDateTime.now());

        entityManager.persistAndFlush(resume1);
        entityManager.persistAndFlush(resume2);
        entityManager.persistAndFlush(resume3);

        // When
        List<Resume> resumes = resumeRepository.findByUserIdOrderByCreatedDateDesc(testUser.getId());

        // Then
        assertThat(resumes).hasSize(3);
        assertThat(resumes.get(0).getTitle()).isEqualTo("Resume 3"); // Most recent
        assertThat(resumes.get(1).getTitle()).isEqualTo("Resume 2");
        assertThat(resumes.get(2).getTitle()).isEqualTo("Resume 1"); // Oldest
    }

    @Test
    void testFindByUserIdAndTemplateType_ShouldReturnFilteredResumes() {
        // Given
        Resume classicResume1 = new Resume(testUser, "Classic Resume 1", TemplateType.CLASSIC);
        Resume classicResume2 = new Resume(testUser, "Classic Resume 2", TemplateType.CLASSIC);
        Resume modernResume = new Resume(testUser, "Modern Resume", TemplateType.MODERN);

        entityManager.persistAndFlush(classicResume1);
        entityManager.persistAndFlush(classicResume2);
        entityManager.persistAndFlush(modernResume);

        // When
        List<Resume> classicResumes = resumeRepository.findByUserIdAndTemplateType(testUser.getId(), TemplateType.CLASSIC);
        List<Resume> modernResumes = resumeRepository.findByUserIdAndTemplateType(testUser.getId(), TemplateType.MODERN);

        // Then
        assertThat(classicResumes).hasSize(2);
        assertThat(classicResumes).allMatch(resume -> resume.getTemplateType() == TemplateType.CLASSIC);
        
        assertThat(modernResumes).hasSize(1);
        assertThat(modernResumes.get(0).getTemplateType()).isEqualTo(TemplateType.MODERN);
        assertThat(modernResumes.get(0).getTitle()).isEqualTo("Modern Resume");
    }

    @Test
    void testFindByUserIdOrderByCreatedDateAsc_ShouldReturnResumesInAscendingOrder() {
        // Given
        Resume resume1 = new Resume(testUser, "Resume 1", TemplateType.CLASSIC);
        resume1.setCreatedDate(LocalDateTime.now().minusDays(2));
        
        Resume resume2 = new Resume(testUser, "Resume 2", TemplateType.MODERN);
        resume2.setCreatedDate(LocalDateTime.now().minusDays(1));

        entityManager.persistAndFlush(resume1);
        entityManager.persistAndFlush(resume2);

        // When
        List<Resume> resumes = resumeRepository.findByUserIdOrderByCreatedDateAsc(testUser.getId());

        // Then
        assertThat(resumes).hasSize(2);
        assertThat(resumes.get(0).getTitle()).isEqualTo("Resume 1"); // Oldest first
        assertThat(resumes.get(1).getTitle()).isEqualTo("Resume 2"); // Most recent
    }

    @Test
    void testFindByUserIdOrderByCreatedDateDesc_WhenNoResumes_ShouldReturnEmptyList() {
        // Given
        User anotherUser = new User("another@example.com", "Another User");
        anotherUser = entityManager.persistAndFlush(anotherUser);

        // When
        List<Resume> resumes = resumeRepository.findByUserIdOrderByCreatedDateDesc(anotherUser.getId());

        // Then
        assertThat(resumes).isEmpty();
    }

    @Test
    void testSaveResume_ShouldPersistResume() {
        // Given
        Resume resume = new Resume(testUser, "Test Resume", TemplateType.CREATIVE);
        resume.setXmlContent("<resume><title>Test Resume</title></resume>");

        // When
        Resume savedResume = resumeRepository.save(resume);

        // Then
        assertThat(savedResume.getId()).isNotNull();
        assertThat(savedResume.getTitle()).isEqualTo("Test Resume");
        assertThat(savedResume.getTemplateType()).isEqualTo(TemplateType.CREATIVE);
        assertThat(savedResume.getXmlContent()).isEqualTo("<resume><title>Test Resume</title></resume>");
        assertThat(savedResume.getUser()).isEqualTo(testUser);
        assertThat(savedResume.getCreatedDate()).isNotNull();
        assertThat(savedResume.getLastModified()).isNotNull();
    }

    @Test
    void testDeleteResume_ShouldRemoveResume() {
        // Given
        Resume resume = new Resume(testUser, "Delete Resume", TemplateType.MODERN);
        Resume savedResume = entityManager.persistAndFlush(resume);

        // When
        resumeRepository.deleteById(savedResume.getId());
        entityManager.flush();

        // Then
        List<Resume> resumes = resumeRepository.findByUserIdOrderByCreatedDateDesc(testUser.getId());
        assertThat(resumes).isEmpty();
    }
}