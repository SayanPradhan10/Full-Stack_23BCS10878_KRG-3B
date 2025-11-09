package com.resumebuilder.service;

import com.resumebuilder.dto.ResumeData;
import com.resumebuilder.entity.Resume;
import com.resumebuilder.entity.TemplateType;
import com.resumebuilder.entity.User;
import com.resumebuilder.exception.PDFGenerationException;
import com.resumebuilder.exception.ResumeNotFoundException;
import com.resumebuilder.exception.XMLProcessingException;
import com.resumebuilder.repository.ResumeRepository;
import com.resumebuilder.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResumeServiceTest {
    
    @Mock
    private ResumeRepository resumeRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private XMLProcessingService xmlProcessingService;
    
    @Mock
    private PDFGenerationService pdfGenerationService;
    
    @Mock
    private TemplateService templateService;
    
    private ResumeService resumeService;
    
    @BeforeEach
    void setUp() {
        resumeService = new ResumeService(
            resumeRepository,
            userRepository,
            xmlProcessingService,
            pdfGenerationService,
            templateService
        );
    }
    
    @Test
    void createResume_WithValidData_ShouldReturnCreatedResume() {
        // Given
        Long userId = 1L;
        User user = createTestUser();
        ResumeData resumeData = createTestResumeData();
        Resume savedResume = createTestResume();
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(templateService.isTemplateSupported(any(TemplateType.class))).thenReturn(true);
        when(xmlProcessingService.convertToXML(any(ResumeData.class))).thenReturn("<xml>content</xml>");
        when(resumeRepository.save(any(Resume.class))).thenReturn(savedResume);
        
        // When
        Resume result = resumeService.createResume(userId, resumeData);
        
        // Then
        assertNotNull(result);
        assertEquals(savedResume.getId(), result.getId());
        verify(userRepository).findById(userId);
        verify(xmlProcessingService).convertToXML(resumeData);
        verify(resumeRepository).save(any(Resume.class));
    }
    
    @Test
    void createResume_WithNullUserId_ShouldThrowException() {
        // Given
        ResumeData resumeData = createTestResumeData();
        
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> resumeService.createResume(null, resumeData));
        
        assertEquals("User ID and resume data cannot be null", exception.getMessage());
    }
    
    @Test
    void createResume_WithNonExistentUser_ShouldThrowException() {
        // Given
        Long userId = 999L;
        ResumeData resumeData = createTestResumeData();
        
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> resumeService.createResume(userId, resumeData));
        
        assertEquals("User not found with ID: " + userId, exception.getMessage());
    }
    
    @Test
    void updateResume_WithValidData_ShouldReturnUpdatedResume() {
        // Given
        Long resumeId = 1L;
        Resume existingResume = createTestResume();
        ResumeData updateData = createTestResumeData();
        updateData.setTitle("Updated Title");
        
        when(resumeRepository.findById(resumeId)).thenReturn(Optional.of(existingResume));
        when(templateService.isTemplateSupported(any(TemplateType.class))).thenReturn(true);
        when(xmlProcessingService.convertToXML(any(ResumeData.class))).thenReturn("<xml>updated</xml>");
        when(resumeRepository.save(any(Resume.class))).thenReturn(existingResume);
        
        // When
        Resume result = resumeService.updateResume(resumeId, updateData);
        
        // Then
        assertNotNull(result);
        verify(resumeRepository).findById(resumeId);
        verify(xmlProcessingService).convertToXML(updateData);
        verify(resumeRepository).save(existingResume);
    }
    
    @Test
    void updateResume_WithNonExistentResume_ShouldThrowException() {
        // Given
        Long resumeId = 999L;
        ResumeData updateData = createTestResumeData();
        
        when(resumeRepository.findById(resumeId)).thenReturn(Optional.empty());
        
        // When & Then
        ResumeNotFoundException exception = assertThrows(ResumeNotFoundException.class,
            () -> resumeService.updateResume(resumeId, updateData));
        
        assertEquals("Resume not found with ID: " + resumeId, exception.getMessage());
    }
    
    @Test
    void getResumeById_WithValidId_ShouldReturnResume() {
        // Given
        Long resumeId = 1L;
        Resume resume = createTestResume();
        
        when(resumeRepository.findById(resumeId)).thenReturn(Optional.of(resume));
        
        // When
        Resume result = resumeService.getResumeById(resumeId);
        
        // Then
        assertNotNull(result);
        assertEquals(resume.getId(), result.getId());
        verify(resumeRepository).findById(resumeId);
    }
    
    @Test
    void getResumeById_WithNonExistentId_ShouldThrowException() {
        // Given
        Long resumeId = 999L;
        
        when(resumeRepository.findById(resumeId)).thenReturn(Optional.empty());
        
        // When & Then
        ResumeNotFoundException exception = assertThrows(ResumeNotFoundException.class,
            () -> resumeService.getResumeById(resumeId));
        
        assertEquals("Resume not found with ID: " + resumeId, exception.getMessage());
    }
    
    @Test
    void getResumesByUserId_WithValidUserId_ShouldReturnResumeList() {
        // Given
        Long userId = 1L;
        List<Resume> resumes = Arrays.asList(createTestResume(), createTestResume());
        
        when(resumeRepository.findByUserIdOrderByCreatedDateDesc(userId)).thenReturn(resumes);
        
        // When
        List<Resume> result = resumeService.getResumesByUserId(userId);
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(resumeRepository).findByUserIdOrderByCreatedDateDesc(userId);
    }
    
    @Test
    void deleteResume_WithValidId_ShouldDeleteResume() {
        // Given
        Long resumeId = 1L;
        Resume resume = createTestResume();
        
        when(resumeRepository.findById(resumeId)).thenReturn(Optional.of(resume));
        
        // When
        resumeService.deleteResume(resumeId);
        
        // Then
        verify(resumeRepository).findById(resumeId);
        verify(resumeRepository).delete(resume);
    }
    
    @Test
    void createResumeVersion_WithValidId_ShouldReturnNewVersion() {
        // Given
        Long resumeId = 1L;
        Resume originalResume = createTestResume();
        Resume newVersion = createTestResume();
        newVersion.setId(2L);
        
        when(resumeRepository.findById(resumeId)).thenReturn(Optional.of(originalResume));
        when(resumeRepository.save(any(Resume.class))).thenReturn(newVersion);
        
        // When
        Resume result = resumeService.createResumeVersion(resumeId);
        
        // Then
        assertNotNull(result);
        verify(resumeRepository).findById(resumeId);
        verify(resumeRepository).save(any(Resume.class));
    }
    
    @Test
    void generatePDF_WithValidResumeId_ShouldReturnPDFBytes() {
        // Given
        Long resumeId = 1L;
        Resume resume = createTestResume();
        resume.setXmlContent("<xml>content</xml>");
        ResumeData resumeData = createTestResumeData();
        byte[] pdfBytes = new byte[]{1, 2, 3, 4};
        
        when(resumeRepository.findById(resumeId)).thenReturn(Optional.of(resume));
        when(templateService.isTemplateSupported(any(TemplateType.class))).thenReturn(true);
        when(xmlProcessingService.parseFromXML(anyString())).thenReturn(resumeData);
        when(templateService.applyTemplate(any(ResumeData.class), any(TemplateType.class))).thenReturn(resumeData);
        when(pdfGenerationService.generatePDF(any(ResumeData.class), any(TemplateType.class))).thenReturn(pdfBytes);
        
        // When
        byte[] result = resumeService.generatePDF(resumeId, TemplateType.CLASSIC);
        
        // Then
        assertNotNull(result);
        assertEquals(pdfBytes, result);
        verify(resumeRepository).findById(resumeId);
        verify(pdfGenerationService).generatePDF(resumeData, TemplateType.CLASSIC);
    }
    
    @Test
    void generateXML_WithValidResumeId_ShouldReturnXMLString() {
        // Given
        Long resumeId = 1L;
        Resume resume = createTestResume();
        resume.setXmlContent("<xml>existing content</xml>");
        
        when(resumeRepository.findById(resumeId)).thenReturn(Optional.of(resume));
        
        // When
        String result = resumeService.generateXML(resumeId);
        
        // Then
        assertNotNull(result);
        assertEquals("<xml>existing content</xml>", result);
        verify(resumeRepository).findById(resumeId);
    }
    
    @Test
    void isResumeOwnedByUser_WithValidOwnership_ShouldReturnTrue() {
        // Given
        Long userId = 1L;
        Long resumeId = 1L;
        Resume resume = createTestResume();
        resume.getUser().setId(userId);
        
        when(resumeRepository.findById(resumeId)).thenReturn(Optional.of(resume));
        
        // When
        boolean result = resumeService.isResumeOwnedByUser(userId, resumeId);
        
        // Then
        assertTrue(result);
        verify(resumeRepository).findById(resumeId);
    }
    
    @Test
    void isResumeOwnedByUser_WithInvalidOwnership_ShouldReturnFalse() {
        // Given
        Long userId = 1L;
        Long resumeId = 1L;
        Resume resume = createTestResume();
        resume.getUser().setId(2L); // Different user
        
        when(resumeRepository.findById(resumeId)).thenReturn(Optional.of(resume));
        
        // When
        boolean result = resumeService.isResumeOwnedByUser(userId, resumeId);
        
        // Then
        assertFalse(result);
        verify(resumeRepository).findById(resumeId);
    }
    
    private User createTestUser() {
        User user = new User("test@example.com", "Test User");
        user.setId(1L);
        return user;
    }
    
    private Resume createTestResume() {
        User user = createTestUser();
        Resume resume = new Resume(user, "Test Resume", TemplateType.CLASSIC);
        resume.setId(1L);
        resume.setCreatedDate(LocalDateTime.now());
        resume.setLastModified(LocalDateTime.now());
        return resume;
    }
    
    private ResumeData createTestResumeData() {
        ResumeData resumeData = new ResumeData();
        resumeData.setTitle("Test Resume");
        resumeData.setTemplateType(TemplateType.CLASSIC);
        
        ResumeData.PersonalInfo personalInfo = new ResumeData.PersonalInfo();
        personalInfo.setName("Test User");
        personalInfo.setEmail("test@example.com");
        resumeData.setPersonalInfo(personalInfo);
        
        return resumeData;
    }
}