package com.resumebuilder.service;

import com.resumebuilder.dto.ResumeData;
import com.resumebuilder.entity.*;
import com.resumebuilder.exception.XMLProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class XMLProcessingServiceTest {
    
    private XMLProcessingService xmlProcessingService;
    
    @BeforeEach
    void setUp() {
        xmlProcessingService = new XMLProcessingService();
    }
    
    @Test
    void convertToXML_WithValidResume_ShouldReturnXMLString() {
        // Given
        Resume resume = createTestResume();
        
        // When
        String xmlResult = xmlProcessingService.convertToXML(resume);
        
        // Then
        assertNotNull(xmlResult);
        assertTrue(xmlResult.contains("<resume>"));
        assertTrue(xmlResult.contains("<title>Test Resume</title>"));
        assertTrue(xmlResult.contains("<templateType>CLASSIC</templateType>"));
        assertTrue(xmlResult.contains("<name>John Doe</name>"));
    }
    
    @Test
    void convertToXML_WithResumeData_ShouldReturnXMLString() {
        // Given
        ResumeData resumeData = createTestResumeData();
        
        // When
        String xmlResult = xmlProcessingService.convertToXML(resumeData);
        
        // Then
        assertNotNull(xmlResult);
        assertTrue(xmlResult.contains("<resume>"));
        assertTrue(xmlResult.contains("<title>Test Resume</title>"));
        assertTrue(xmlResult.contains("<templateType>MODERN</templateType>"));
    }
    
    @Test
    void convertToXML_WithNullResume_ShouldThrowException() {
        // When & Then
        XMLProcessingException exception = assertThrows(XMLProcessingException.class, 
            () -> xmlProcessingService.convertToXML((Resume) null));
        
        assertEquals("Resume cannot be null", exception.getMessage());
    }
    
    @Test
    void convertToXML_WithNullResumeData_ShouldThrowException() {
        // When & Then
        XMLProcessingException exception = assertThrows(XMLProcessingException.class, 
            () -> xmlProcessingService.convertToXML((ResumeData) null));
        
        assertEquals("ResumeData cannot be null", exception.getMessage());
    }
    
    @Test
    void parseFromXML_WithValidXML_ShouldReturnResumeData() {
        // Given
        ResumeData originalData = createTestResumeData();
        String xmlContent = xmlProcessingService.convertToXML(originalData);
        
        // When
        ResumeData parsedData = xmlProcessingService.parseFromXML(xmlContent);
        
        // Then
        assertNotNull(parsedData);
        assertEquals(originalData.getTitle(), parsedData.getTitle());
        assertEquals(originalData.getTemplateType(), parsedData.getTemplateType());
        assertNotNull(parsedData.getPersonalInfo());
        assertEquals(originalData.getPersonalInfo().getName(), parsedData.getPersonalInfo().getName());
    }
    
    @Test
    void parseFromXML_WithNullXML_ShouldThrowException() {
        // When & Then
        XMLProcessingException exception = assertThrows(XMLProcessingException.class, 
            () -> xmlProcessingService.parseFromXML(null));
        
        assertEquals("XML content cannot be null or empty", exception.getMessage());
    }
    
    @Test
    void parseFromXML_WithEmptyXML_ShouldThrowException() {
        // When & Then
        XMLProcessingException exception = assertThrows(XMLProcessingException.class, 
            () -> xmlProcessingService.parseFromXML(""));
        
        assertEquals("XML content cannot be null or empty", exception.getMessage());
    }
    
    @Test
    void parseFromXML_WithInvalidXML_ShouldThrowException() {
        // Given
        String invalidXml = "<invalid>xml content</invalid>";
        
        // When & Then
        assertThrows(XMLProcessingException.class, 
            () -> xmlProcessingService.parseFromXML(invalidXml));
    }
    
    @Test
    void validateXMLStructure_WithValidXML_ShouldReturnTrue() {
        // Given
        ResumeData resumeData = createTestResumeData();
        String validXml = xmlProcessingService.convertToXML(resumeData);
        
        // When
        boolean isValid = xmlProcessingService.validateXMLStructure(validXml);
        
        // Then
        assertTrue(isValid);
    }
    
    @Test
    void validateXMLStructure_WithInvalidXML_ShouldReturnFalse() {
        // Given
        String invalidXml = "<invalid>xml content</invalid>";
        
        // When
        boolean isValid = xmlProcessingService.validateXMLStructure(invalidXml);
        
        // Then
        assertFalse(isValid);
    }
    
    private Resume createTestResume() {
        User user = new User("john.doe@example.com", "John Doe");
        user.setPhone("123-456-7890");
        user.setAddress("123 Main St, City, State");
        
        Resume resume = new Resume(user, "Test Resume", TemplateType.CLASSIC);
        resume.setId(1L);
        resume.setCreatedDate(LocalDateTime.now());
        resume.setLastModified(LocalDateTime.now());
        
        // Add education
        Education education = new Education();
        education.setInstitution("Test University");
        education.setDegree("Bachelor of Science");
        education.setFieldOfStudy("Computer Science");
        education.setStartDate(LocalDate.of(2018, 9, 1));
        education.setEndDate(LocalDate.of(2022, 5, 15));
        education.setGpa(3.8);
        resume.addEducation(education);
        
        // Add work experience
        WorkExperience work = new WorkExperience();
        work.setCompany("Tech Company");
        work.setPosition("Software Developer");
        work.setStartDate(LocalDate.of(2022, 6, 1));
        work.setEndDate(LocalDate.of(2023, 12, 31));
        work.setDescription("Developed web applications");
        work.setIsInternship(false);
        resume.addWorkExperience(work);
        
        // Add skill
        Skill skill = new Skill();
        skill.setName("Java");
        skill.setType(SkillType.TECHNICAL);
        skill.setProficiencyLevel("Advanced");
        resume.addSkill(skill);
        
        return resume;
    }
    
    private ResumeData createTestResumeData() {
        ResumeData resumeData = new ResumeData();
        resumeData.setId(1L);
        resumeData.setTitle("Test Resume");
        resumeData.setTemplateType(TemplateType.MODERN);
        resumeData.setCreatedDate(LocalDateTime.now());
        resumeData.setLastModified(LocalDateTime.now());
        
        // Personal info
        ResumeData.PersonalInfo personalInfo = new ResumeData.PersonalInfo();
        personalInfo.setName("Jane Smith");
        personalInfo.setEmail("jane.smith@example.com");
        personalInfo.setPhone("987-654-3210");
        personalInfo.setAddress("456 Oak Ave, City, State");
        resumeData.setPersonalInfo(personalInfo);
        
        // Education
        ResumeData.EducationData education = new ResumeData.EducationData();
        education.setInstitution("Example University");
        education.setDegree("Master of Science");
        education.setFieldOfStudy("Software Engineering");
        education.setStartDate(LocalDate.of(2020, 9, 1));
        education.setEndDate(LocalDate.of(2022, 5, 15));
        education.setGpa(3.9);
        resumeData.setEducations(Arrays.asList(education));
        
        // Work experience
        ResumeData.WorkExperienceData work = new ResumeData.WorkExperienceData();
        work.setCompany("Software Solutions Inc");
        work.setPosition("Senior Developer");
        work.setStartDate(LocalDate.of(2022, 6, 1));
        work.setDescription("Led development team");
        work.setIsInternship(false);
        resumeData.setWorkExperiences(Arrays.asList(work));
        
        // Skills
        ResumeData.SkillData skill = new ResumeData.SkillData();
        skill.setName("Python");
        skill.setType("TECHNICAL");
        skill.setProficiencyLevel("Expert");
        resumeData.setSkills(Arrays.asList(skill));
        
        return resumeData;
    }
}