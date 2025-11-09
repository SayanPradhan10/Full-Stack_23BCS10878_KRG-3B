package com.resumebuilder.service;

import com.resumebuilder.dto.ResumeData;
import com.resumebuilder.entity.TemplateType;
import com.resumebuilder.exception.PDFGenerationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PDFGenerationServiceTest {
    
    @Mock
    private TemplateService templateService;
    
    private PDFGenerationService pdfGenerationService;
    
    @BeforeEach
    void setUp() {
        pdfGenerationService = new PDFGenerationService(templateService);
        
        // Setup mock template service
        TemplateService.TemplateConfiguration classicConfig = new TemplateService.TemplateConfiguration();
        classicConfig.setDescription("Classic template");
        classicConfig.setPrimaryColor("#2C3E50");
        classicConfig.setFontFamily("Times New Roman");
        
        lenient().when(templateService.getTemplateConfiguration(any(TemplateType.class)))
            .thenReturn(classicConfig);
    }
    
    @Test
    void generatePDF_WithValidData_ShouldReturnPDFBytes() {
        // Given
        ResumeData resumeData = createTestResumeData();
        
        // When
        byte[] pdfBytes = pdfGenerationService.generatePDF(resumeData, TemplateType.CLASSIC);
        
        // Then
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
        // Check PDF header (PDF files start with %PDF)
        String pdfHeader = new String(pdfBytes, 0, Math.min(4, pdfBytes.length));
        assertEquals("%PDF", pdfHeader);
    }
    
    @Test
    void generatePDF_WithModernTemplate_ShouldReturnPDFBytes() {
        // Given
        ResumeData resumeData = createTestResumeData();
        
        // When
        byte[] pdfBytes = pdfGenerationService.generatePDF(resumeData, TemplateType.MODERN);
        
        // Then
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
    }
    
    @Test
    void generatePDF_WithCreativeTemplate_ShouldReturnPDFBytes() {
        // Given
        ResumeData resumeData = createTestResumeData();
        
        // When
        byte[] pdfBytes = pdfGenerationService.generatePDF(resumeData, TemplateType.CREATIVE);
        
        // Then
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
    }
    
    @Test
    void generatePDF_WithNullResumeData_ShouldThrowException() {
        // When & Then
        PDFGenerationException exception = assertThrows(PDFGenerationException.class,
            () -> pdfGenerationService.generatePDF(null, TemplateType.CLASSIC));
        
        assertEquals("Resume data cannot be null", exception.getMessage());
    }
    
    @Test
    void generatePDF_WithNullTemplateType_ShouldThrowException() {
        // Given
        ResumeData resumeData = createTestResumeData();
        
        // When & Then
        PDFGenerationException exception = assertThrows(PDFGenerationException.class,
            () -> pdfGenerationService.generatePDF(resumeData, null));
        
        assertEquals("Template type cannot be null", exception.getMessage());
    }
    
    @Test
    void generatePDF_WithMinimalData_ShouldReturnPDFBytes() {
        // Given
        ResumeData resumeData = new ResumeData();
        resumeData.setTitle("Minimal Resume");
        
        ResumeData.PersonalInfo personalInfo = new ResumeData.PersonalInfo();
        personalInfo.setName("John Doe");
        personalInfo.setEmail("john@example.com");
        resumeData.setPersonalInfo(personalInfo);
        
        // When
        byte[] pdfBytes = pdfGenerationService.generatePDF(resumeData, TemplateType.CLASSIC);
        
        // Then
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
    }
    
    @Test
    void generatePDF_WithCompleteData_ShouldReturnPDFBytes() {
        // Given
        ResumeData resumeData = createCompleteResumeData();
        
        // When
        byte[] pdfBytes = pdfGenerationService.generatePDF(resumeData, TemplateType.MODERN);
        
        // Then
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
    }
    
    @Test
    void generatePDF_ShouldCompleteWithinTimeLimit() {
        // Given
        ResumeData resumeData = createTestResumeData();
        long startTime = System.currentTimeMillis();
        
        // When
        byte[] pdfBytes = pdfGenerationService.generatePDF(resumeData, TemplateType.CLASSIC);
        long endTime = System.currentTimeMillis();
        
        // Then
        assertNotNull(pdfBytes);
        long duration = endTime - startTime;
        assertTrue(duration < 10000, "PDF generation should complete within 10 seconds, took: " + duration + "ms");
    }
    
    private ResumeData createTestResumeData() {
        ResumeData resumeData = new ResumeData();
        resumeData.setId(1L);
        resumeData.setTitle("Software Developer Resume");
        resumeData.setTemplateType(TemplateType.CLASSIC);
        resumeData.setCreatedDate(LocalDateTime.now());
        resumeData.setLastModified(LocalDateTime.now());
        
        // Personal info
        ResumeData.PersonalInfo personalInfo = new ResumeData.PersonalInfo();
        personalInfo.setName("John Smith");
        personalInfo.setEmail("john.smith@example.com");
        personalInfo.setPhone("555-123-4567");
        personalInfo.setAddress("123 Main St, Anytown, ST 12345");
        resumeData.setPersonalInfo(personalInfo);
        
        return resumeData;
    }
    
    private ResumeData createCompleteResumeData() {
        ResumeData resumeData = createTestResumeData();
        
        // Education
        ResumeData.EducationData education = new ResumeData.EducationData();
        education.setInstitution("University of Technology");
        education.setDegree("Bachelor of Science");
        education.setFieldOfStudy("Computer Science");
        education.setStartDate(LocalDate.of(2018, 9, 1));
        education.setEndDate(LocalDate.of(2022, 5, 15));
        education.setGpa(3.7);
        resumeData.setEducations(Arrays.asList(education));
        
        // Work experience
        ResumeData.WorkExperienceData work1 = new ResumeData.WorkExperienceData();
        work1.setCompany("Tech Solutions Inc");
        work1.setPosition("Junior Developer");
        work1.setStartDate(LocalDate.of(2022, 6, 1));
        work1.setEndDate(LocalDate.of(2023, 12, 31));
        work1.setDescription("Developed web applications using Java and Spring Boot");
        work1.setIsInternship(false);
        
        ResumeData.WorkExperienceData work2 = new ResumeData.WorkExperienceData();
        work2.setCompany("StartUp Co");
        work2.setPosition("Software Intern");
        work2.setStartDate(LocalDate.of(2021, 6, 1));
        work2.setEndDate(LocalDate.of(2021, 8, 31));
        work2.setDescription("Assisted in mobile app development");
        work2.setIsInternship(true);
        
        resumeData.setWorkExperiences(Arrays.asList(work1, work2));
        
        // Skills
        ResumeData.SkillData skill1 = new ResumeData.SkillData();
        skill1.setName("Java");
        skill1.setType("TECHNICAL");
        skill1.setProficiencyLevel("Advanced");
        
        ResumeData.SkillData skill2 = new ResumeData.SkillData();
        skill2.setName("Spring Boot");
        skill2.setType("TECHNICAL");
        skill2.setProficiencyLevel("Intermediate");
        
        ResumeData.SkillData skill3 = new ResumeData.SkillData();
        skill3.setName("Communication");
        skill3.setType("SOFT_SKILL");
        skill3.setProficiencyLevel("Expert");
        
        resumeData.setSkills(Arrays.asList(skill1, skill2, skill3));
        
        return resumeData;
    }
}