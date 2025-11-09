package com.resumebuilder.repository;

import com.resumebuilder.entity.Resume;
import com.resumebuilder.entity.TemplateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Resume entity operations.
 * Extends JpaRepository to provide basic CRUD operations and custom query methods.
 */
@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    
    /**
     * Find all resumes for a specific user, ordered by creation date in descending order.
     * 
     * @param userId the ID of the user
     * @return List of resumes ordered by creation date (newest first)
     */
    List<Resume> findByUserIdOrderByCreatedDateDesc(Long userId);
    
    /**
     * Find all resumes for a specific user with a specific template type.
     * 
     * @param userId the ID of the user
     * @param templateType the template type to filter by
     * @return List of resumes matching the criteria
     */
    List<Resume> findByUserIdAndTemplateType(Long userId, TemplateType templateType);
    
    /**
     * Find all resumes for a specific user, ordered by creation date in ascending order.
     * 
     * @param userId the ID of the user
     * @return List of resumes ordered by creation date (oldest first)
     */
    List<Resume> findByUserIdOrderByCreatedDateAsc(Long userId);
}