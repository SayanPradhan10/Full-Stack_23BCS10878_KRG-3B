package com.resumebuilder.service;

import com.resumebuilder.dto.ResumeData;
import com.resumebuilder.entity.TemplateType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TemplateServiceTest {
    
    private TemplateService templateService;
    
    @BeforeEach
    void setUp() {
        templateService = new TemplateService();
    }
    
    @Test
    void getAvailableTemplates_ShouldReturnAllTemplateTypes() {
        // When
        List<TemplateType> templates = templateService.getAvailableTemplates();
        
        // Then
        assertNotNull(templates);
        assertEquals(3, templates.size());
        assertTrue(templates.contains(TemplateType.CLASSIC));
        assertTrue(templates.contains(TemplateType.MODERN));
        assertTrue(templates.contains(TemplateType.CREATIVE));
    }
    
    @Test
    void getTemplateConfiguration_WithValidTemplate_ShouldReturnConfiguration() {
        // When
        TemplateService.TemplateConfiguration config = templateService.getTemplateConfiguration(TemplateType.CLASSIC);
        
        // Then
        assertNotNull(config);
        assertEquals("Traditional and professional layout with clean typography", config.getDescription());
        assertEquals("#2C3E50", config.getPrimaryColor());
        assertEquals("Times New Roman", config.getFontFamily());
        assertEquals("single-column", config.getLayout());
        assertNotNull(config.getFeatures());
        assertTrue(config.getFeatures().contains("Clean typography"));
    }
    
    @Test
    void getTemplateConfiguration_WithModernTemplate_ShouldReturnModernConfiguration() {
        // When
        TemplateService.TemplateConfiguration config = templateService.getTemplateConfiguration(TemplateType.MODERN);
        
        // Then
        assertNotNull(config);
        assertEquals("Contemporary design with modern typography and subtle colors", config.getDescription());
        assertEquals("#3498DB", config.getPrimaryColor());
        assertEquals("Arial", config.getFontFamily());
        assertEquals("two-column", config.getLayout());
    }
    
    @Test
    void getTemplateConfiguration_WithCreativeTemplate_ShouldReturnCreativeConfiguration() {
        // When
        TemplateService.TemplateConfiguration config = templateService.getTemplateConfiguration(TemplateType.CREATIVE);
        
        // Then
        assertNotNull(config);
        assertEquals("Bold and creative design with unique layout and vibrant colors", config.getDescription());
        assertEquals("#E74C3C", config.getPrimaryColor());
        assertEquals("Helvetica", config.getFontFamily());
        assertEquals("creative-grid", config.getLayout());
    }
    
    @Test
    void applyTemplate_WithValidData_ShouldReturnFormattedData() {
        // Given
        ResumeData resumeData = createTestResumeData();
        
        // When
        ResumeData formattedData = templateService.applyTemplate(resumeData, TemplateType.MODERN);
        
        // Then
        assertNotNull(formattedData);
        assertEquals(TemplateType.MODERN, formattedData.getTemplateType());
        assertEquals(resumeData.getTitle(), formattedData.getTitle());
    }
    
    @Test
    void applyTemplate_WithNullData_ShouldThrowException() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> templateService.applyTemplate(null, TemplateType.CLASSIC));
        
        assertEquals("ResumeData and TemplateType cannot be null", exception.getMessage());
    }
    
    @Test
    void applyTemplate_WithNullTemplateType_ShouldThrowException() {
        // Given
        ResumeData resumeData = createTestResumeData();
        
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> templateService.applyTemplate(resumeData, null));
        
        assertEquals("ResumeData and TemplateType cannot be null", exception.getMessage());
    }
    
    @Test
    void previewTemplate_WithValidTemplate_ShouldReturnDescription() {
        // When
        String preview = templateService.previewTemplate(TemplateType.CLASSIC);
        
        // Then
        assertNotNull(preview);
        assertEquals("Traditional and professional layout with clean typography", preview);
    }
    
    @Test
    void previewTemplate_WithNullTemplate_ShouldReturnDefaultMessage() {
        // When
        String preview = templateService.previewTemplate(null);
        
        // Then
        assertEquals("Template preview not available", preview);
    }
    
    @Test
    void getTemplateMetadata_WithValidTemplate_ShouldReturnMetadata() {
        // When
        Map<String, Object> metadata = templateService.getTemplateMetadata(TemplateType.MODERN);
        
        // Then
        assertNotNull(metadata);
        assertEquals("Modern", metadata.get("name"));
        assertEquals("Contemporary design with modern typography and subtle colors", metadata.get("description"));
        assertEquals("#3498DB", metadata.get("primaryColor"));
        assertEquals("Arial", metadata.get("fontFamily"));
        assertEquals("two-column", metadata.get("layout"));
        assertNotNull(metadata.get("features"));
    }
    
    @Test
    void getTemplateMetadata_WithNullTemplate_ShouldReturnEmptyMetadata() {
        // When
        Map<String, Object> metadata = templateService.getTemplateMetadata(null);
        
        // Then
        assertNotNull(metadata);
        assertTrue(metadata.isEmpty());
    }
    
    @Test
    void isTemplateSupported_WithValidTemplate_ShouldReturnTrue() {
        // When & Then
        assertTrue(templateService.isTemplateSupported(TemplateType.CLASSIC));
        assertTrue(templateService.isTemplateSupported(TemplateType.MODERN));
        assertTrue(templateService.isTemplateSupported(TemplateType.CREATIVE));
    }
    
    @Test
    void isTemplateSupported_WithNullTemplate_ShouldReturnFalse() {
        // When & Then
        assertFalse(templateService.isTemplateSupported(null));
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