package com.resumebuilder.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resumebuilder.dto.ResumeData;
import com.resumebuilder.entity.*;
import com.resumebuilder.repository.ResumeRepository;
import com.resumebuilder.repository.UserRepository;
import com.resumebuilder.service.ResumeService;
import com.resumebuilder.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for complete resume creation workflows.
 * Tests the entire flow from user registration to PDF generation.
 * Requirements: 1.1, 4.1, 5.1, 7.1
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ResumeWorkflowIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ResumeService resumeService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private User testUser;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
        
        // Create test user
        testUser = new User();
        testUser.setName("John Doe");
        testUser.setEmail("john.doe@example.com");
        testUser.setPhone("555-1234");
        testUser.setAddress("123 Main St, City, State");
        testUser = userService.createUser(testUser);
        
        // Create authenticated session
        session = new MockHttpSession();
        session.setAttribute("userId", testUser.getId());
        session.setAttribute("currentUser", testUser);
    }

    /**
     * Test complete resume creation workflow from input to PDF generation.
     * Requirements: 1.1, 2.1, 5.1
     */
    @Test
    void testCompleteResumeCreationWorkflow() throws Exception {
        // Step 1: Create resume with personal information
        ResumeData resumeData = createSampleResumeData();
        
        MvcResult createResult = mockMvc.perform(post("/resume/create")
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", resumeData.getTitle())
                .param("templateType", resumeData.getTemplateType().name())
                .param("personalInfo.name", resumeData.getPersonalInfo().getName())
                .param("personalInfo.email", resumeData.getPersonalInfo().getEmail())
                .param("personalInfo.phone", resumeData.getPersonalInfo().getPhone())
                .param("personalInfo.address", resumeData.getPersonalInfo().getAddress()))
                .andExpect(status().is3xxRedirection())
                .andReturn();
        
        // Extract resume ID from redirect URL
        String redirectUrl = createResult.getResponse().getRedirectedUrl();
        assertThat(redirectUrl).contains("/resume/");
        Long resumeId = extractResumeIdFromUrl(redirectUrl);
        
        // Verify resume was created in database
        Resume createdResume = resumeRepository.findById(resumeId).orElse(null);
        assertThat(createdResume).isNotNull();
        assertThat(createdResume.getTitle()).isEqualTo(resumeData.getTitle());
        assertThat(createdResume.getTemplateType()).isEqualTo(resumeData.getTemplateType());
        assertThat(createdResume.getUser().getId()).isEqualTo(testUser.getId());
        
        // Step 2: View the created resume
        mockMvc.perform(get("/resume/" + resumeId)
                .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("resume/view"))
                .andExpect(model().attributeExists("resume", "resumeData"));
        
        // Step 3: Edit resume to add education and experience
        mockMvc.perform(get("/resume/" + resumeId + "/edit")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("resume/edit"));
        
        // Update resume with additional data
        mockMvc.perform(post("/resume/" + resumeId + "/edit")
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "Updated " + resumeData.getTitle())
                .param("templateType", TemplateType.MODERN.name())
                .param("personalInfo.name", resumeData.getPersonalInfo().getName())
                .param("personalInfo.email", resumeData.getPersonalInfo().getEmail())
                .param("educations[0].institution", "University of Technology")
                .param("educations[0].degree", "Bachelor of Science")
                .param("educations[0].fieldOfStudy", "Computer Science")
                .param("workExperiences[0].company", "Tech Corp")
                .param("workExperiences[0].position", "Software Developer")
                .param("skills[0].name", "Java")
                .param("skills[0].type", "TECHNICAL"))
                .andExpect(status().is3xxRedirection());
        
        // Step 4: Generate XML
        MvcResult xmlResult = mockMvc.perform(get("/template/" + resumeId + "/download/xml")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/xml"))
                .andReturn();
        
        String xmlContent = xmlResult.getResponse().getContentAsString();
        assertThat(xmlContent).contains("<resume>");
        assertThat(xmlContent).contains(resumeData.getPersonalInfo().getName());
        
        // Step 5: Generate PDF
        long pdfStartTime = System.currentTimeMillis();
        
        MvcResult pdfResult = mockMvc.perform(get("/template/" + resumeId + "/download/pdf")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/pdf"))
                .andReturn();
        
        long pdfGenerationTime = System.currentTimeMillis() - pdfStartTime;
        
        // Verify PDF generation time meets requirement (10 seconds)
        assertThat(pdfGenerationTime).isLessThan(10000);
        
        byte[] pdfContent = pdfResult.getResponse().getContentAsByteArray();
        assertThat(pdfContent).isNotEmpty();
        assertThat(pdfContent.length).isGreaterThan(1000); // PDF should have reasonable size
        
        // Verify PDF headers
        assertThat(pdfResult.getResponse().getHeader("Content-Disposition"))
                .contains("attachment")
                .contains(".pdf");
    }

    /**
     * Test template switching and version management functionality.
     * Requirements: 4.1, 6.1
     */
    @Test
    void testTemplateSwitchingAndVersionManagement() throws Exception {
        // Create initial resume
        ResumeData resumeData = createSampleResumeData();
        Resume resume = resumeService.createResume(testUser.getId(), resumeData);
        
        // Test template selection page
        mockMvc.perform(get("/template/select")
                .param("resumeId", resume.getId().toString())
                .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("template/select"))
                .andExpect(model().attributeExists("templates", "resumeId"));
        
        // Test template preview API
        mockMvc.perform(get("/template/api/preview/MODERN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Modern"))
                .andExpect(jsonPath("$.description").exists());
        
        // Apply different template
        mockMvc.perform(post("/template/apply")
                .session(session)
                .param("resumeId", resume.getId().toString())
                .param("templateType", "CREATIVE"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/resume/" + resume.getId()));
        
        // Verify template was changed
        Resume updatedResume = resumeRepository.findById(resume.getId()).orElse(null);
        assertThat(updatedResume).isNotNull();
        assertThat(updatedResume.getTemplateType()).isEqualTo(TemplateType.CREATIVE);
        
        // Create resume version with different template
        mockMvc.perform(post("/template/" + resume.getId() + "/create-version")
                .session(session)
                .param("templateType", "MODERN"))
                .andExpect(status().is3xxRedirection());
        
        // Verify new version was created
        List<Resume> userResumes = resumeRepository.findByUserIdOrderByCreatedDateDesc(testUser.getId());
        assertThat(userResumes).hasSize(2);
        
        Resume newVersion = userResumes.stream()
                .filter(r -> !r.getId().equals(resume.getId()))
                .findFirst()
                .orElse(null);
        assertThat(newVersion).isNotNull();
        assertThat(newVersion.getTemplateType()).isEqualTo(TemplateType.MODERN);
        assertThat(newVersion.getTitle()).contains("Copy");
        
        // Test duplicate resume functionality
        mockMvc.perform(post("/resume/" + resume.getId() + "/duplicate")
                .session(session))
                .andExpect(status().is3xxRedirection());
        
        // Verify duplicate was created
        List<Resume> allUserResumes = resumeRepository.findByUserIdOrderByCreatedDateDesc(testUser.getId());
        assertThat(allUserResumes).hasSize(3);
    }

    /**
     * Test dashboard loading and resume management operations.
     * Requirements: 7.1, 7.4
     */
    @Test
    void testDashboardLoadingAndResumeManagement() throws Exception {
        // Create multiple resumes for testing
        createMultipleTestResumes();
        
        // Test dashboard loading performance
        long dashboardStartTime = System.currentTimeMillis();
        
        MvcResult dashboardResult = mockMvc.perform(get("/user/dashboard")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("user/dashboard"))
                .andExpect(model().attributeExists("user", "resumes", "resumeCount"))
                .andReturn();
        
        long dashboardLoadTime = System.currentTimeMillis() - dashboardStartTime;
        
        // Verify dashboard loads within 3 seconds (requirement 7.4)
        assertThat(dashboardLoadTime).isLessThan(3000);
        
        // Verify dashboard data
        List<Resume> dashboardResumes = (List<Resume>) dashboardResult.getModelAndView()
                .getModel().get("resumes");
        assertThat(dashboardResumes).hasSize(3);
        
        Integer resumeCount = (Integer) dashboardResult.getModelAndView()
                .getModel().get("resumeCount");
        assertThat(resumeCount).isEqualTo(3);
        
        // Test resume management operations
        Resume testResume = dashboardResumes.get(0);
        
        // Test resume deletion
        mockMvc.perform(post("/resume/" + testResume.getId() + "/delete")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/dashboard"));
        
        // Verify resume was deleted
        assertThat(resumeRepository.findById(testResume.getId())).isEmpty();
        
        // Verify dashboard reflects the change
        mockMvc.perform(get("/user/dashboard")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(model().attribute("resumeCount", 2));
    }

    /**
     * Test user authentication and session management.
     * Requirements: 7.1
     */
    @Test
    void testUserAuthenticationFlow() throws Exception {
        // Test login page
        mockMvc.perform(get("/user/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/login"));
        
        // Test successful login
        MvcResult loginResult = mockMvc.perform(post("/user/login")
                .param("email", testUser.getEmail()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/dashboard"))
                .andReturn();
        
        MockHttpSession loginSession = (MockHttpSession) loginResult.getRequest().getSession();
        assertThat(loginSession.getAttribute("userId")).isEqualTo(testUser.getId());
        assertThat(loginSession.getAttribute("currentUser")).isNotNull();
        
        // Test protected resource access with valid session
        mockMvc.perform(get("/user/dashboard")
                .session(loginSession))
                .andExpect(status().isOk());
        
        // Test protected resource access without session
        mockMvc.perform(get("/user/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
        
        // Test logout
        mockMvc.perform(post("/user/logout")
                .session(loginSession))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
    }

    /**
     * Test error handling and validation scenarios.
     * Requirements: 1.2, 6.2
     */
    @Test
    void testErrorHandlingAndValidation() throws Exception {
        // Test resume creation with invalid data
        mockMvc.perform(post("/resume/create")
                .session(session)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "") // Empty title should fail validation
                .param("personalInfo.name", "")
                .param("personalInfo.email", "invalid-email"))
                .andExpect(status().isOk())
                .andExpect(view().name("resume/create"))
                .andExpect(model().hasErrors());
        
        // Test accessing non-existent resume
        mockMvc.perform(get("/resume/99999")
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/dashboard"));
        
        // Test accessing resume owned by different user
        User otherUser = new User();
        otherUser.setName("Jane Smith");
        otherUser.setEmail("jane.smith@example.com");
        otherUser = userService.createUser(otherUser);
        
        ResumeData otherUserResumeData = createSampleResumeData();
        Resume otherUserResume = resumeService.createResume(otherUser.getId(), otherUserResumeData);
        
        mockMvc.perform(get("/resume/" + otherUserResume.getId())
                .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/dashboard"));
    }

    /**
     * Test API endpoints functionality.
     * Requirements: 5.1, 7.1
     */
    @Test
    void testAPIEndpoints() throws Exception {
        // Create test resume
        ResumeData resumeData = createSampleResumeData();
        Resume resume = resumeService.createResume(testUser.getId(), resumeData);
        
        // Test current user API
        mockMvc.perform(get("/user/api/current")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testUser.getId()))
                .andExpect(jsonPath("$.name").value(testUser.getName()))
                .andExpect(jsonPath("$.email").value(testUser.getEmail()));
        
        // Test resume data API
        mockMvc.perform(get("/resume/" + resume.getId() + "/api/data")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(resumeData.getTitle()))
                .andExpect(jsonPath("$.personalInfo.name").value(resumeData.getPersonalInfo().getName()));
        
        // Test template validation API
        mockMvc.perform(get("/template/api/" + resume.getId() + "/validate/MODERN")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valid").value(true))
                .andExpect(jsonPath("$.templateType").value("MODERN"));
    }

    private ResumeData createSampleResumeData() {
        ResumeData resumeData = new ResumeData();
        resumeData.setTitle("Software Developer Resume");
        resumeData.setTemplateType(TemplateType.CLASSIC);
        
        // Personal Information
        ResumeData.PersonalInfo personalInfo = new ResumeData.PersonalInfo();
        personalInfo.setName("John Doe");
        personalInfo.setEmail("john.doe@example.com");
        personalInfo.setPhone("555-1234");
        personalInfo.setAddress("123 Main St, City, State");
        resumeData.setPersonalInfo(personalInfo);
        
        // Education
        List<ResumeData.EducationData> educations = new ArrayList<>();
        ResumeData.EducationData education = new ResumeData.EducationData();
        education.setInstitution("University of Technology");
        education.setDegree("Bachelor of Science");
        education.setFieldOfStudy("Computer Science");
        education.setStartDate(LocalDate.of(2018, 9, 1));
        education.setEndDate(LocalDate.of(2022, 5, 15));
        education.setGpa(3.8);
        educations.add(education);
        resumeData.setEducations(educations);
        
        // Work Experience
        List<ResumeData.WorkExperienceData> workExperiences = new ArrayList<>();
        ResumeData.WorkExperienceData work = new ResumeData.WorkExperienceData();
        work.setCompany("Tech Corp");
        work.setPosition("Software Developer");
        work.setStartDate(LocalDate.of(2022, 6, 1));
        work.setEndDate(LocalDate.of(2024, 10, 1));
        work.setDescription("Developed web applications using Java and Spring Boot");
        work.setIsInternship(false);
        workExperiences.add(work);
        resumeData.setWorkExperiences(workExperiences);
        
        // Skills
        List<ResumeData.SkillData> skills = new ArrayList<>();
        ResumeData.SkillData skill1 = new ResumeData.SkillData();
        skill1.setName("Java");
        skill1.setType("TECHNICAL");
        skill1.setProficiencyLevel("Advanced");
        skills.add(skill1);
        
        ResumeData.SkillData skill2 = new ResumeData.SkillData();
        skill2.setName("Communication");
        skill2.setType("SOFT_SKILL");
        skill2.setProficiencyLevel("Expert");
        skills.add(skill2);
        
        resumeData.setSkills(skills);
        
        return resumeData;
    }
    
    private void createMultipleTestResumes() {
        for (int i = 1; i <= 3; i++) {
            ResumeData resumeData = createSampleResumeData();
            resumeData.setTitle("Test Resume " + i);
            resumeData.setTemplateType(TemplateType.values()[i % 3]);
            resumeService.createResume(testUser.getId(), resumeData);
        }
    }
    
    private Long extractResumeIdFromUrl(String url) {
        // Extract ID from URL like "/resume/123"
        String[] parts = url.split("/");
        return Long.parseLong(parts[parts.length - 1]);
    }
}