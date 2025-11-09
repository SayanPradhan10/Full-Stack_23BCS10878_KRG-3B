package com.resumebuilder.controller;

import com.resumebuilder.entity.Resume;
import com.resumebuilder.entity.TemplateType;
import com.resumebuilder.entity.User;
import com.resumebuilder.exception.PDFGenerationException;
import com.resumebuilder.exception.ResumeNotFoundException;
import com.resumebuilder.service.ResumeService;
import com.resumebuilder.service.TemplateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for TemplateController endpoints.
 * Tests template selection, preview, and generation functionality.
 */
@WebMvcTest(TemplateController.class)
class TemplateControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private TemplateService templateService;
    
    @MockBean
    private ResumeService resumeService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private User testUser;
    private Resume testResume;
    private MockHttpSession session;
    
    @BeforeEach
    void setUp() {
        testUser = new User("test@example.com", "Test User");
        testUser.setId(1L);
        
        testResume = new Resume();
        testResume.setId(1L);
        testResume.setUser(testUser);
        testResume.setTitle("Test Resume");
        testResume.setTemplateType(TemplateType.CLASSIC);
        testResume.setCreatedDate(LocalDateTime.now());
        testResume.setLastModified(LocalDateTime.now());
        
        session = new MockHttpSession();
        session.setAttribute("userId", 1L);
        session.setAttribute("currentUser", testUser);
    }
    
    @Test
    void showTemplateSelection_WithAuthenticatedUser_ShouldReturnTemplateSelectView() throws Exception {
        List<TemplateType> templates = Arrays.asList(TemplateType.CLASSIC, TemplateType.MODERN, TemplateType.CREATIVE);
        Map<String, Object> classicMetadata = createTemplateMetadata("Classic", "Traditional layout");
        Map<String, Object> modernMetadata = createTemplateMetadata("Modern", "Contemporary design");
        Map<String, Object> creativeMetadata = createTemplateMetadata("Creative", "Bold and creative");
        
        when(templateService.getAvailableTemplates()).thenReturn(templates);
        when(templateService.getTemplateMetadata(TemplateType.CLASSIC)).thenReturn(classicMetadata);
        when(templateService.getTemplateMetadata(TemplateType.MODERN)).thenReturn(modernMetadata);
        when(templateService.getTemplateMetadata(TemplateType.CREATIVE)).thenReturn(creativeMetadata);
        
        mockMvc.perform(get("/template/select").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("template/select"))
                .andExpect(model().attributeExists("templates"))
                .andExpect(model().attributeExists("classicMetadata"))
                .andExpect(model().attributeExists("modernMetadata"))
                .andExpect(model().attributeExists("creativeMetadata"));
        
        verify(templateService).getAvailableTemplates();
    }
    
    @Test
    void showTemplateSelection_WithResumeId_ShouldVerifyOwnership() throws Exception {
        List<TemplateType> templates = Arrays.asList(TemplateType.CLASSIC);
        
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(true);
        when(templateService.getAvailableTemplates()).thenReturn(templates);
        when(templateService.getTemplateMetadata(any())).thenReturn(new HashMap<>());
        
        mockMvc.perform(get("/template/select")
                .param("resumeId", "1")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("template/select"))
                .andExpect(model().attribute("resumeId", 1L));
        
        verify(resumeService).isResumeOwnedByUser(1L, 1L);
    }
    
    @Test
    void showTemplateSelection_WithoutAuthentication_ShouldRedirectToLogin() throws Exception {
        mockMvc.perform(get("/template/select"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }
    
    @Test
    void applyTemplate_WithValidResumeAndOwnership_ShouldRedirectToResumeView() throws Exception {
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(true);
        when(resumeService.getResumeById(1L)).thenReturn(testResume);
        when(resumeService.generateXML(1L)).thenReturn("<resume><title>Test</title></resume>");
        when(resumeService.getXMLProcessingService()).thenReturn(mock(com.resumebuilder.service.XMLProcessingService.class));
        when(resumeService.updateResume(eq(1L), any())).thenReturn(testResume);
        
        mockMvc.perform(post("/template/apply").session(session)
                .param("resumeId", "1")
                .param("templateType", "MODERN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/resume/1"))
                .andExpect(flash().attributeExists("successMessage"));
        
        verify(resumeService).isResumeOwnedByUser(1L, 1L);
        verify(resumeService).getResumeById(1L);
    }
    
    @Test
    void applyTemplate_WithoutOwnership_ShouldRedirectToDashboard() throws Exception {
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(false);
        
        mockMvc.perform(post("/template/apply").session(session)
                .param("resumeId", "1")
                .param("templateType", "MODERN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/dashboard"));
        
        verify(resumeService, never()).getResumeById(anyLong());
    }
    
    @Test
    void getTemplatePreview_WithValidTemplate_ShouldReturnTemplateMetadata() throws Exception {
        Map<String, Object> metadata = createTemplateMetadata("Modern", "Contemporary design");
        
        when(templateService.isTemplateSupported(TemplateType.MODERN)).thenReturn(true);
        when(templateService.getTemplateMetadata(TemplateType.MODERN)).thenReturn(metadata);
        when(templateService.previewTemplate(TemplateType.MODERN)).thenReturn("Modern template preview");
        
        mockMvc.perform(get("/template/api/preview/MODERN"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Modern"))
                .andExpect(jsonPath("$.description").value("Contemporary design"))
                .andExpect(jsonPath("$.preview").value("Modern template preview"));
        
        verify(templateService).isTemplateSupported(TemplateType.MODERN);
        verify(templateService).getTemplateMetadata(TemplateType.MODERN);
        verify(templateService).previewTemplate(TemplateType.MODERN);
    }
    
    @Test
    void getTemplatePreview_WithUnsupportedTemplate_ShouldReturnBadRequest() throws Exception {
        when(templateService.isTemplateSupported(any())).thenReturn(false);
        
        mockMvc.perform(get("/template/api/preview/INVALID"))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void downloadPDF_WithValidResumeAndOwnership_ShouldReturnPDFFile() throws Exception {
        byte[] pdfBytes = "PDF content".getBytes();
        
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(true);
        when(resumeService.getResumeById(1L)).thenReturn(testResume);
        when(resumeService.generatePDF(1L)).thenReturn(pdfBytes);
        
        mockMvc.perform(get("/template/1/download/pdf").session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(header().string("Content-Disposition", "form-data; name=\"attachment\"; filename=\"Test_Resume.pdf\""))
                .andExpect(content().bytes(pdfBytes));
        
        verify(resumeService).isResumeOwnedByUser(1L, 1L);
        verify(resumeService).generatePDF(1L);
    }
    
    @Test
    void downloadPDF_WithSpecificTemplate_ShouldGeneratePDFWithTemplate() throws Exception {
        byte[] pdfBytes = "PDF content".getBytes();
        
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(true);
        when(resumeService.getResumeById(1L)).thenReturn(testResume);
        when(resumeService.generatePDF(1L, TemplateType.MODERN)).thenReturn(pdfBytes);
        
        mockMvc.perform(get("/template/1/download/pdf")
                .param("templateType", "MODERN")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF));
        
        verify(resumeService).generatePDF(1L, TemplateType.MODERN);
    }
    
    @Test
    void downloadPDF_WithoutAuthentication_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/template/1/download/pdf"))
                .andExpect(status().isUnauthorized());
        
        verify(resumeService, never()).isResumeOwnedByUser(anyLong(), anyLong());
    }
    
    @Test
    void downloadPDF_WithoutOwnership_ShouldReturnForbidden() throws Exception {
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(false);
        
        mockMvc.perform(get("/template/1/download/pdf").session(session))
                .andExpect(status().isForbidden());
        
        verify(resumeService, never()).generatePDF(anyLong());
    }
    
    @Test
    void downloadPDF_WithPDFGenerationException_ShouldReturnInternalServerError() throws Exception {
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(true);
        when(resumeService.getResumeById(1L)).thenReturn(testResume);
        when(resumeService.generatePDF(1L)).thenThrow(new PDFGenerationException("PDF generation failed"));
        
        mockMvc.perform(get("/template/1/download/pdf").session(session))
                .andExpect(status().isInternalServerError());
    }
    
    @Test
    void downloadXML_WithValidResumeAndOwnership_ShouldReturnXMLFile() throws Exception {
        String xmlContent = "<resume><title>Test Resume</title></resume>";
        
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(true);
        when(resumeService.getResumeById(1L)).thenReturn(testResume);
        when(resumeService.generateXML(1L)).thenReturn(xmlContent);
        
        mockMvc.perform(get("/template/1/download/xml").session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(header().string("Content-Disposition", "form-data; name=\"attachment\"; filename=\"Test_Resume.xml\""))
                .andExpect(content().string(xmlContent));
        
        verify(resumeService).isResumeOwnedByUser(1L, 1L);
        verify(resumeService).generateXML(1L);
    }
    
    @Test
    void showGenerationPage_WithValidResumeAndOwnership_ShouldReturnGenerationView() throws Exception {
        List<TemplateType> templates = Arrays.asList(TemplateType.values());
        
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(true);
        when(resumeService.getResumeById(1L)).thenReturn(testResume);
        when(templateService.getAvailableTemplates()).thenReturn(templates);
        
        mockMvc.perform(get("/template/1/generate").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("template/generate"))
                .andExpect(model().attributeExists("resume", "templates", "currentTemplate"))
                .andExpect(model().attribute("currentTemplate", TemplateType.CLASSIC));
        
        verify(resumeService).isResumeOwnedByUser(1L, 1L);
        verify(resumeService).getResumeById(1L);
        verify(templateService).getAvailableTemplates();
    }
    
    @Test
    void createResumeVersion_WithValidResumeAndOwnership_ShouldCreateNewVersion() throws Exception {
        Resume newResume = new Resume();
        newResume.setId(2L);
        newResume.setTitle("Test Resume (Copy)");
        
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(true);
        when(resumeService.createResumeVersion(1L)).thenReturn(newResume);
        when(resumeService.generateXML(2L)).thenReturn("<resume><title>Test</title></resume>");
        when(resumeService.getXMLProcessingService()).thenReturn(mock(com.resumebuilder.service.XMLProcessingService.class));
        when(resumeService.updateResume(eq(2L), any())).thenReturn(newResume);
        
        mockMvc.perform(post("/template/1/create-version").session(session)
                .param("templateType", "MODERN"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/resume/2"))
                .andExpect(flash().attributeExists("successMessage"));
        
        verify(resumeService).createResumeVersion(1L);
    }
    
    @Test
    void validateTemplate_WithValidResumeAndTemplate_ShouldReturnValidationResult() throws Exception {
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(true);
        when(templateService.isTemplateSupported(TemplateType.MODERN)).thenReturn(true);
        when(resumeService.getResumeById(1L)).thenReturn(testResume);
        
        mockMvc.perform(get("/template/api/1/validate/MODERN").session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.valid").value(true))
                .andExpect(jsonPath("$.templateType").value("MODERN"))
                .andExpect(jsonPath("$.currentTemplate").value("CLASSIC"))
                .andExpect(jsonPath("$.message").value("Template is compatible"));
        
        verify(resumeService).isResumeOwnedByUser(1L, 1L);
        verify(templateService).isTemplateSupported(TemplateType.MODERN);
        verify(resumeService).getResumeById(1L);
    }
    
    private Map<String, Object> createTemplateMetadata(String name, String description) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("name", name);
        metadata.put("description", description);
        metadata.put("primaryColor", "#2C3E50");
        metadata.put("fontFamily", "Arial");
        metadata.put("layout", "single-column");
        metadata.put("features", Arrays.asList("Feature 1", "Feature 2"));
        return metadata;
    }
}