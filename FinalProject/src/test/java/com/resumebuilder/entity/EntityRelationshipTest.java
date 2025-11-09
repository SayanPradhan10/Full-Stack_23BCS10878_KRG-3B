package com.resumebuilder.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Entity Relationship Tests")
class EntityRelationshipTest {
    
    private Validator validator;
    
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    @DisplayName("User entity creation and validation")
    void testUserEntityCreationAndValidation() {
        // Test valid user creation
        User user = new User("john.doe@example.com", "John Doe");
        user.setPhone("123-456-7890");
        user.setAddress("123 Main St, City, State");
        
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Valid user should have no validation errors");
        
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("John Doe", user.getName());
        assertEquals("123-456-7890", user.getPhone());
        assertEquals("123 Main St, City, State", user.getAddress());
        assertNotNull(user.getCreatedDate());
        assertTrue(user.getResumes().isEmpty());
    }
    
    @Test
    @DisplayName("User validation constraints")
    void testUserValidationConstraints() {
        // Test invalid email
        User userInvalidEmail = new User("invalid-email", "John Doe");
        Set<ConstraintViolation<User>> violations = validator.validate(userInvalidEmail);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Email should be valid")));
        
        // Test null name
        User userNullName = new User("john@example.com", null);
        violations = validator.validate(userNullName);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Name is required")));
        
        // Test empty name
        User userEmptyName = new User("john@example.com", "");
        violations = validator.validate(userEmptyName);
        assertFalse(violations.isEmpty());
    }
    
    @Test
    @DisplayName("Resume entity creation and relationships")
    void testResumeEntityCreationAndRelationships() {
        // Create user
        User user = new User("jane.doe@example.com", "Jane Doe");
        
        // Create resume
        Resume resume = new Resume("Software Developer Resume", TemplateType.MODERN);
        resume.setUser(user);
        
        Set<ConstraintViolation<Resume>> violations = validator.validate(resume);
        assertTrue(violations.isEmpty(), "Valid resume should have no validation errors");
        
        assertEquals("Software Developer Resume", resume.getTitle());
        assertEquals(TemplateType.MODERN, resume.getTemplateType());
        assertEquals(user, resume.getUser());
        assertNotNull(resume.getCreatedDate());
        assertNotNull(resume.getLastModified());
        assertTrue(resume.getEducations().isEmpty());
        assertTrue(resume.getWorkExperiences().isEmpty());
        assertTrue(resume.getSkills().isEmpty());
    }
    
    @Test
    @DisplayName("User-Resume relationship management")
    void testUserResumeRelationship() {
        User user = new User("test@example.com", "Test User");
        Resume resume1 = new Resume("Resume 1", TemplateType.CLASSIC);
        Resume resume2 = new Resume("Resume 2", TemplateType.CREATIVE);
        
        // Test adding resumes to user
        user.addResume(resume1);
        user.addResume(resume2);
        
        assertEquals(2, user.getResumes().size());
        assertTrue(user.getResumes().contains(resume1));
        assertTrue(user.getResumes().contains(resume2));
        assertEquals(user, resume1.getUser());
        assertEquals(user, resume2.getUser());
        
        // Test removing resume from user
        user.removeResume(resume1);
        assertEquals(1, user.getResumes().size());
        assertFalse(user.getResumes().contains(resume1));
        assertNull(resume1.getUser());
    }
    
    @Test
    @DisplayName("Education entity creation and validation")
    void testEducationEntityCreationAndValidation() {
        Education education = new Education("University of Technology", "Bachelor of Science");
        education.setFieldOfStudy("Computer Science");
        education.setStartDate(LocalDate.of(2018, 9, 1));
        education.setEndDate(LocalDate.of(2022, 5, 15));
        education.setGpa(3.8);
        
        Set<ConstraintViolation<Education>> violations = validator.validate(education);
        assertTrue(violations.isEmpty(), "Valid education should have no validation errors");
        
        assertEquals("University of Technology", education.getInstitution());
        assertEquals("Bachelor of Science", education.getDegree());
        assertEquals("Computer Science", education.getFieldOfStudy());
        assertEquals(3.8, education.getGpa());
    }
    
    @Test
    @DisplayName("Education validation constraints")
    void testEducationValidationConstraints() {
        // Test invalid GPA (too high)
        Education educationHighGpa = new Education("University", "Degree");
        educationHighGpa.setGpa(5.0);
        Set<ConstraintViolation<Education>> violations = validator.validate(educationHighGpa);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("GPA must not exceed 4.0")));
        
        // Test invalid GPA (negative)
        Education educationNegativeGpa = new Education("University", "Degree");
        educationNegativeGpa.setGpa(-1.0);
        violations = validator.validate(educationNegativeGpa);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("GPA must be at least 0.0")));
    }
    
    @Test
    @DisplayName("WorkExperience entity creation and validation")
    void testWorkExperienceEntityCreationAndValidation() {
        WorkExperience workExp = new WorkExperience("Tech Corp", "Software Engineer");
        workExp.setStartDate(LocalDate.of(2022, 6, 1));
        workExp.setEndDate(LocalDate.of(2024, 5, 31));
        workExp.setDescription("Developed web applications using Java and Spring Boot");
        workExp.setIsInternship(false);
        
        Set<ConstraintViolation<WorkExperience>> violations = validator.validate(workExp);
        assertTrue(violations.isEmpty(), "Valid work experience should have no validation errors");
        
        assertEquals("Tech Corp", workExp.getCompany());
        assertEquals("Software Engineer", workExp.getPosition());
        assertEquals("Developed web applications using Java and Spring Boot", workExp.getDescription());
        assertFalse(workExp.getIsInternship());
        assertFalse(workExp.isCurrentPosition());
    }
    
    @Test
    @DisplayName("Skill entity creation and validation")
    void testSkillEntityCreationAndValidation() {
        Skill skill = new Skill("Java", SkillType.TECHNICAL);
        skill.setProficiencyLevel("Advanced");
        
        Set<ConstraintViolation<Skill>> violations = validator.validate(skill);
        assertTrue(violations.isEmpty(), "Valid skill should have no validation errors");
        
        assertEquals("Java", skill.getName());
        assertEquals(SkillType.TECHNICAL, skill.getType());
        assertEquals("Advanced", skill.getProficiencyLevel());
    }
    
    @Test
    @DisplayName("Resume-Education relationship management")
    void testResumeEducationRelationship() {
        Resume resume = new Resume("My Resume", TemplateType.CLASSIC);
        Education education1 = new Education("University A", "Bachelor's");
        Education education2 = new Education("University B", "Master's");
        
        // Test adding education to resume
        resume.addEducation(education1);
        resume.addEducation(education2);
        
        assertEquals(2, resume.getEducations().size());
        assertTrue(resume.getEducations().contains(education1));
        assertTrue(resume.getEducations().contains(education2));
        assertEquals(resume, education1.getResume());
        assertEquals(resume, education2.getResume());
        
        // Test removing education from resume
        resume.removeEducation(education1);
        assertEquals(1, resume.getEducations().size());
        assertFalse(resume.getEducations().contains(education1));
        assertNull(education1.getResume());
    }
    
    @Test
    @DisplayName("Resume-WorkExperience relationship management")
    void testResumeWorkExperienceRelationship() {
        Resume resume = new Resume("My Resume", TemplateType.MODERN);
        WorkExperience work1 = new WorkExperience("Company A", "Developer");
        WorkExperience work2 = new WorkExperience("Company B", "Senior Developer");
        
        // Test adding work experience to resume
        resume.addWorkExperience(work1);
        resume.addWorkExperience(work2);
        
        assertEquals(2, resume.getWorkExperiences().size());
        assertTrue(resume.getWorkExperiences().contains(work1));
        assertTrue(resume.getWorkExperiences().contains(work2));
        assertEquals(resume, work1.getResume());
        assertEquals(resume, work2.getResume());
        
        // Test removing work experience from resume
        resume.removeWorkExperience(work1);
        assertEquals(1, resume.getWorkExperiences().size());
        assertFalse(resume.getWorkExperiences().contains(work1));
        assertNull(work1.getResume());
    }
    
    @Test
    @DisplayName("Resume-Skill relationship management")
    void testResumeSkillRelationship() {
        Resume resume = new Resume("My Resume", TemplateType.CREATIVE);
        Skill skill1 = new Skill("Python", SkillType.TECHNICAL);
        Skill skill2 = new Skill("Communication", SkillType.SOFT_SKILL);
        
        // Test adding skills to resume
        resume.addSkill(skill1);
        resume.addSkill(skill2);
        
        assertEquals(2, resume.getSkills().size());
        assertTrue(resume.getSkills().contains(skill1));
        assertTrue(resume.getSkills().contains(skill2));
        assertEquals(resume, skill1.getResume());
        assertEquals(resume, skill2.getResume());
        
        // Test removing skill from resume
        resume.removeSkill(skill1);
        assertEquals(1, resume.getSkills().size());
        assertFalse(resume.getSkills().contains(skill1));
        assertNull(skill1.getResume());
    }
    
    @Test
    @DisplayName("Enum functionality")
    void testEnumFunctionality() {
        // Test TemplateType enum
        assertEquals("Classic", TemplateType.CLASSIC.getDisplayName());
        assertEquals("Modern", TemplateType.MODERN.getDisplayName());
        assertEquals("Creative", TemplateType.CREATIVE.getDisplayName());
        
        // Test SkillType enum
        assertEquals("Technical Skills", SkillType.TECHNICAL.getDisplayName());
        assertEquals("Soft Skills", SkillType.SOFT_SKILL.getDisplayName());
        assertEquals("Languages", SkillType.LANGUAGE.getDisplayName());
    }
}