package com.resumebuilder.controller;

import com.resumebuilder.dto.ResumeData;
import com.resumebuilder.entity.Resume;
import com.resumebuilder.entity.TemplateType;
import com.resumebuilder.entity.User;
import com.resumebuilder.exception.ResumeNotFoundException;
import com.resumebuilder.service.ResumeService;
import com.resumebuilder.service.XMLProcessingService;
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
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for ResumeController endpoints.
 * Tests resume CRUD operations, form handling, and validation.
 */
@WebMvcTest(ResumeController.class)
class ResumeControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ResumeService resumeService;
    
    @MockBean
    private XMLProcessingService xmlProcessingService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private User testUser;
    private Resume testResume;
    private ResumeData testResumeData;
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
        testResume.setXmlContent("<resume><title>Test Resume</title></resume>");
        
        testResumeData = new ResumeData();
        testResumeData.setId(1L);
        testResumeData.setTitle("Test Resume");
        testResumeData.setTemplateType(TemplateType.CLASSIC);
        testResumeData.setPersonalInfo(new ResumeData.PersonalInfo("Test User", "test@example.com", "123-456-7890", "123 Test St"));
        testResumeData.setEducations(new ArrayList<>());
        testResumeData.setWorkExperiences(new ArrayList<>());
        testResumeData.setSkills(new ArrayList<>());
        
        session = new MockHttpSession();
        session.setAttribute("userId", 1L);
        session.setAttribute("currentUser", testUser);
    }
    
    @Test
    void showCreateResumeForm_WithAuthenticatedUser_ShouldReturnCreateView() throws Exception {
        mockMvc.perform(get("/resume/create").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("resume/create"))
                .andExpect(model().attributeExists("resumeData", "templateTypes"));
    }
    
    @Test
    void showCreateResumeForm_WithoutAuthentication_ShouldRedirectToLogin() throws Exception {
        mockMvc.perform(get("/resume/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }
    
    @Test
    void createResume_WithValidData_ShouldRedirectToResumeView() throws Exception {
        when(resumeService.createResume(eq(1L), any(ResumeData.class))).thenReturn(testResume);
        
        mockMvc.perform(post("/resume/create").session(session)
                .param("title", "Test Resume")
                .param("templateType", "CLASSIC")
                .param("personalInfo.name", "Test User")
                .param("personalInfo.email", "test@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/resume/1"))
                .andExpect(flash().attributeExists("successMessage"));
        
        verify(resumeService).createResume(eq(1L), any(ResumeData.class));
    }
    
    @Test
    void viewResume_WithValidResumeAndOwnership_ShouldReturnResumeView() throws Exception {
        when(resumeService.getResumeById(1L)).thenReturn(testResume);
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(true);
        when(xmlProcessingService.parseFromXML(anyString())).thenReturn(testResumeData);
        
        mockMvc.perform(get("/resume/1").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("resume/view"))
                .andExpect(model().attributeExists("resume", "resumeData"));
        
        verify(resumeService).getResumeById(1L);
        verify(resumeService).isResumeOwnedByUser(1L, 1L);
    }
    
    @Test
    void viewResume_WithoutOwnership_ShouldRedirectToDashboard() throws Exception {
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(false);
        
        mockMvc.perform(get("/resume/1").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/dashboard"));
        
        verify(resumeService, never()).getResumeById(anyLong());
    }
    
    @Test
    void viewResume_WithNonExistentResume_ShouldRedirectToDashboard() throws Exception {
        when(resumeService.isResumeOwnedByUser(1L, 999L)).thenReturn(true);
        when(resumeService.getResumeById(999L)).thenThrow(new ResumeNotFoundException(999L));
        
        mockMvc.perform(get("/resume/999").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/dashboard"));
    }
    
    @Test
    void showEditResumeForm_WithValidResumeAndOwnership_ShouldReturnEditView() throws Exception {
        when(resumeService.getResumeById(1L)).thenReturn(testResume);
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(true);
        when(xmlProcessingService.parseFromXML(anyString())).thenReturn(testResumeData);
        
        mockMvc.perform(get("/resume/1/edit").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("resume/edit"))
                .andExpect(model().attributeExists("resume", "resumeData", "templateTypes"));
        
        verify(resumeService).getResumeById(1L);
        verify(resumeService).isResumeOwnedByUser(1L, 1L);
    }
    
    @Test
    void updateResume_WithValidData_ShouldRedirectToResumeView() throws Exception {
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(true);
        when(resumeService.updateResume(eq(1L), any(ResumeData.class))).thenReturn(testResume);
        
        mockMvc.perform(post("/resume/1/edit").session(session)
                .param("title", "Updated Resume")
                .param("templateType", "MODERN")
                .param("personalInfo.name", "Updated User")
                .param("personalInfo.email", "updated@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/resume/1"))
                .andExpect(flash().attributeExists("successMessage"));
        
        verify(resumeService).updateResume(eq(1L), any(ResumeData.class));
    }
    
    @Test
    void deleteResume_WithValidResumeAndOwnership_ShouldRedirectToDashboard() throws Exception {
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(true);
        doNothing().when(resumeService).deleteResume(1L);
        
        mockMvc.perform(post("/resume/1/delete").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/dashboard"))
                .andExpect(flash().attributeExists("successMessage"));
        
        verify(resumeService).deleteResume(1L);
    }
    
    @Test
    void duplicateResume_WithValidResumeAndOwnership_ShouldRedirectToNewResumeEdit() throws Exception {
        Resume newResume = new Resume();
        newResume.setId(2L);
        newResume.setTitle("Test Resume (Copy)");
        
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(true);
        when(resumeService.createResumeVersion(1L)).thenReturn(newResume);
        
        mockMvc.perform(post("/resume/1/duplicate").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/resume/2/edit"))
                .andExpect(flash().attributeExists("successMessage"));
        
        verify(resumeService).createResumeVersion(1L);
    }
    
    @Test
    void showPersonalInfoForm_ShouldReturnPersonalInfoView() throws Exception {
        mockMvc.perform(get("/resume/personal-info").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("resume/personal-info"))
                .andExpect(model().attributeExists("personalInfo"));
    }
    
    @Test
    void showPersonalInfoForm_WithExistingResume_ShouldLoadExistingData() throws Exception {
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(true);
        when(resumeService.getResumeById(1L)).thenReturn(testResume);
        when(xmlProcessingService.parseFromXML(anyString())).thenReturn(testResumeData);
        
        mockMvc.perform(get("/resume/personal-info")
                .param("resumeId", "1")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("resume/personal-info"))
                .andExpect(model().attributeExists("personalInfo", "resumeId"));
    }
    
    @Test
    void showEducationForm_ShouldReturnEducationView() throws Exception {
        mockMvc.perform(get("/resume/education").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("resume/education"))
                .andExpect(model().attributeExists("educations"));
    }
    
    @Test
    void showExperienceForm_ShouldReturnExperienceView() throws Exception {
        mockMvc.perform(get("/resume/experience").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("resume/experience"))
                .andExpect(model().attributeExists("workExperiences"));
    }
    
    @Test
    void showSkillsForm_ShouldReturnSkillsView() throws Exception {
        mockMvc.perform(get("/resume/skills").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("resume/skills"))
                .andExpect(model().attributeExists("skills", "skillTypes"));
    }
    
    @Test
    void getResumeData_WithValidResumeAndOwnership_ShouldReturnResumeDataJson() throws Exception {
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(true);
        when(resumeService.getResumeById(1L)).thenReturn(testResume);
        when(xmlProcessingService.parseFromXML(anyString())).thenReturn(testResumeData);
        
        mockMvc.perform(get("/resume/1/api/data").session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Resume"));
        
        verify(resumeService).isResumeOwnedByUser(1L, 1L);
        verify(resumeService).getResumeById(1L);
    }
    
    @Test
    void getResumeData_WithoutOwnership_ShouldReturnNull() throws Exception {
        when(resumeService.isResumeOwnedByUser(1L, 1L)).thenReturn(false);
        
        mockMvc.perform(get("/resume/1/api/data").session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        
        verify(resumeService, never()).getResumeById(anyLong());
    }
    
    @Test
    void getResumeData_WithoutAuthentication_ShouldReturnNull() throws Exception {
        mockMvc.perform(get("/resume/1/api/data"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        
        verify(resumeService, never()).isResumeOwnedByUser(anyLong(), anyLong());
    }
}