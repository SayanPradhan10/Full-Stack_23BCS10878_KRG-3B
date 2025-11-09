package com.resumebuilder.controller;

import com.resumebuilder.dto.ResumeData;
import com.resumebuilder.entity.Resume;
import com.resumebuilder.entity.TemplateType;
import com.resumebuilder.exception.ResumeNotFoundException;
import com.resumebuilder.service.ResumeService;
import com.resumebuilder.service.XMLProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controller for handling resume-related web requests including CRUD operations,
 * personal information input, education and experience management, and skills input.
 */
@Controller
@RequestMapping("/resume")
public class ResumeController {
    
    private final ResumeService resumeService;
    private final XMLProcessingService xmlProcessingService;
    
    @Autowired
    public ResumeController(ResumeService resumeService, XMLProcessingService xmlProcessingService) {
        this.resumeService = resumeService;
        this.xmlProcessingService = xmlProcessingService;
    }
    
    /**
     * Initialize data binder to handle empty strings for numeric fields.
     * This prevents NumberFormatException when form fields are left empty.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Allow empty strings to be converted to null for Double fields
        binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
    }
    
    /**
     * Displays the resume creation form with personal information input.
     * 
     * @param session HTTP session to get current user
     * @param model the model for the view
     * @return the resume creation view name or redirect to login
     */
    @GetMapping("/create")
    public String showCreateResumeForm(HttpSession session, Model model) {
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        ResumeData resumeData = new ResumeData();
        resumeData.setPersonalInfo(new ResumeData.PersonalInfo());
        resumeData.setEducations(new ArrayList<>());
        resumeData.setWorkExperiences(new ArrayList<>());
        resumeData.setSkills(new ArrayList<>());
        resumeData.setProjects(new ArrayList<>());
        resumeData.setCertifications(new ArrayList<>());
        resumeData.setInterests(new ArrayList<>());
        resumeData.setTemplateType(TemplateType.CLASSIC);
        
        model.addAttribute("resumeData", resumeData);
        model.addAttribute("templateTypes", TemplateType.values());
        return "resume/create";
    }
    
    /**
     * Processes resume creation form submission.
     * 
     * @param request the HTTP request containing form data
     * @param session HTTP session to get current user
     * @param redirectAttributes attributes for redirect
     * @param model the model for the view
     * @return redirect to resume list or back to creation form
     */
    @PostMapping("/create")
    public String createResume(HttpServletRequest request,
                              HttpSession session,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        try {
            // Manually parse form data to avoid array indexing issues
            ResumeData resumeData = parseFormData(request);
            
            // Basic validation
            if (resumeData.getTitle() == null || resumeData.getTitle().trim().isEmpty()) {
                model.addAttribute("errorMessage", "Resume title is required");
                model.addAttribute("resumeData", resumeData);
                model.addAttribute("templateTypes", TemplateType.values());
                return "resume/create";
            }
            
            if (resumeData.getPersonalInfo() == null || 
                resumeData.getPersonalInfo().getName() == null || 
                resumeData.getPersonalInfo().getName().trim().isEmpty()) {
                model.addAttribute("errorMessage", "Name is required");
                model.addAttribute("resumeData", resumeData);
                model.addAttribute("templateTypes", TemplateType.values());
                return "resume/create";
            }
            
            if (resumeData.getPersonalInfo().getEmail() == null || 
                resumeData.getPersonalInfo().getEmail().trim().isEmpty()) {
                model.addAttribute("errorMessage", "Email is required");
                model.addAttribute("resumeData", resumeData);
                model.addAttribute("templateTypes", TemplateType.values());
                return "resume/create";
            }
            
            Resume resume = resumeService.createResume(userId, resumeData);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Resume created successfully!");
            return "redirect:/resume/" + resume.getId();
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error creating resume: " + e.getMessage());
            ResumeData resumeData = new ResumeData();
            resumeData.setPersonalInfo(new ResumeData.PersonalInfo());
            resumeData.setEducations(new ArrayList<>());
            resumeData.setWorkExperiences(new ArrayList<>());
            resumeData.setSkills(new ArrayList<>());
            resumeData.setProjects(new ArrayList<>());
            resumeData.setCertifications(new ArrayList<>());
            resumeData.setInterests(new ArrayList<>());
            resumeData.setTemplateType(TemplateType.CLASSIC);
            model.addAttribute("resumeData", resumeData);
            model.addAttribute("templateTypes", TemplateType.values());
            return "resume/create";
        }
    }
    
    /**
     * Displays a specific resume for viewing.
     * 
     * @param resumeId the ID of the resume to view
     * @param session HTTP session to get current user
     * @param model the model for the view
     * @return the resume view name or redirect to dashboard
     */
    @GetMapping("/{resumeId}")
    public String viewResume(@PathVariable Long resumeId,
                            HttpSession session,
                            Model model) {
        
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        try {
            Resume resume = resumeService.getResumeById(resumeId);
            
            // Check if user owns this resume
            if (!resumeService.isResumeOwnedByUser(userId, resumeId)) {
                return "redirect:/user/dashboard";
            }
            
            // Parse XML content to ResumeData for display
            ResumeData resumeData;
            if (resume.getXmlContent() != null && !resume.getXmlContent().trim().isEmpty()) {
                resumeData = xmlProcessingService.parseFromXML(resume.getXmlContent());
            } else {
                resumeData = new ResumeData();
                resumeData.setTitle(resume.getTitle());
                resumeData.setTemplateType(resume.getTemplateType());
            }
            
            model.addAttribute("resume", resume);
            model.addAttribute("resumeData", resumeData);
            return "resume/view";
        } catch (ResumeNotFoundException e) {
            return "redirect:/user/dashboard";
        }
    }
    
    /**
     * Displays the resume editing form.
     * 
     * @param resumeId the ID of the resume to edit
     * @param session HTTP session to get current user
     * @param model the model for the view
     * @return the resume edit view name or redirect to dashboard
     */
    @GetMapping("/{resumeId}/edit")
    public String showEditResumeForm(@PathVariable Long resumeId,
                                    HttpSession session,
                                    Model model) {
        
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        try {
            Resume resume = resumeService.getResumeById(resumeId);
            
            // Check if user owns this resume
            if (!resumeService.isResumeOwnedByUser(userId, resumeId)) {
                return "redirect:/user/dashboard";
            }
            
            // Parse XML content to ResumeData for editing
            ResumeData resumeData;
            if (resume.getXmlContent() != null && !resume.getXmlContent().trim().isEmpty()) {
                resumeData = xmlProcessingService.parseFromXML(resume.getXmlContent());
            } else {
                resumeData = new ResumeData();
                resumeData.setTitle(resume.getTitle());
                resumeData.setTemplateType(resume.getTemplateType());
                resumeData.setPersonalInfo(new ResumeData.PersonalInfo());
                resumeData.setEducations(new ArrayList<>());
                resumeData.setWorkExperiences(new ArrayList<>());
                resumeData.setSkills(new ArrayList<>());
                resumeData.setProjects(new ArrayList<>());
                resumeData.setCertifications(new ArrayList<>());
                resumeData.setInterests(new ArrayList<>());
            }
            
            model.addAttribute("resume", resume);
            model.addAttribute("resumeData", resumeData);
            model.addAttribute("templateTypes", TemplateType.values());
            return "resume/edit-simple";
        } catch (ResumeNotFoundException e) {
            return "redirect:/user/dashboard";
        }
    }
    
    /**
     * Processes resume update form submission.
     * 
     * @param resumeId the ID of the resume to update
     * @param request the HTTP request containing form data
     * @param session HTTP session to get current user
     * @param redirectAttributes attributes for redirect
     * @param model the model for the view
     * @return redirect to resume view or back to edit form
     */
    @PostMapping("/{resumeId}/edit")
    public String updateResume(@PathVariable Long resumeId,
                              HttpServletRequest request,
                              HttpSession session,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        // Check if user owns this resume
        if (!resumeService.isResumeOwnedByUser(userId, resumeId)) {
            return "redirect:/user/dashboard";
        }
        
        try {
            // Manually parse form data to avoid array indexing issues
            ResumeData resumeData = parseFormData(request);
            
            // Basic validation
            if (resumeData.getTitle() == null || resumeData.getTitle().trim().isEmpty()) {
                model.addAttribute("errorMessage", "Resume title is required");
                model.addAttribute("resumeData", resumeData);
                model.addAttribute("templateTypes", TemplateType.values());
                return "resume/edit-simple";
            }
            
            if (resumeData.getPersonalInfo() == null || 
                resumeData.getPersonalInfo().getName() == null || 
                resumeData.getPersonalInfo().getName().trim().isEmpty()) {
                model.addAttribute("errorMessage", "Name is required");
                model.addAttribute("resumeData", resumeData);
                model.addAttribute("templateTypes", TemplateType.values());
                return "resume/edit-simple";
            }
            
            if (resumeData.getPersonalInfo().getEmail() == null || 
                resumeData.getPersonalInfo().getEmail().trim().isEmpty()) {
                model.addAttribute("errorMessage", "Email is required");
                model.addAttribute("resumeData", resumeData);
                model.addAttribute("templateTypes", TemplateType.values());
                return "resume/edit-simple";
            }
            
            resumeService.updateResume(resumeId, resumeData);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Resume updated successfully!");
            return "redirect:/resume/" + resumeId;
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating resume: " + e.getMessage());
            
            // Load existing resume data for the form
            try {
                Resume resume = resumeService.getResumeById(resumeId);
                ResumeData resumeData;
                if (resume.getXmlContent() != null && !resume.getXmlContent().trim().isEmpty()) {
                    resumeData = xmlProcessingService.parseFromXML(resume.getXmlContent());
                } else {
                    resumeData = new ResumeData();
                    resumeData.setTitle(resume.getTitle());
                    resumeData.setTemplateType(resume.getTemplateType());
                    resumeData.setPersonalInfo(new ResumeData.PersonalInfo());
                    resumeData.setEducations(new ArrayList<>());
                    resumeData.setWorkExperiences(new ArrayList<>());
                    resumeData.setSkills(new ArrayList<>());
                    resumeData.setProjects(new ArrayList<>());
                    resumeData.setCertifications(new ArrayList<>());
                    resumeData.setInterests(new ArrayList<>());
                }
                model.addAttribute("resume", resume);
                model.addAttribute("resumeData", resumeData);
                model.addAttribute("templateTypes", TemplateType.values());
            } catch (ResumeNotFoundException ex) {
                return "redirect:/user/dashboard";
            }
            
            return "resume/edit-simple";
        }
    }
    
    /**
     * Handles resume deletion.
     * 
     * @param resumeId the ID of the resume to delete
     * @param session HTTP session to get current user
     * @param redirectAttributes attributes for redirect
     * @return redirect to dashboard
     */
    @PostMapping("/{resumeId}/delete")
    public String deleteResume(@PathVariable Long resumeId,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        // Check if user owns this resume
        if (!resumeService.isResumeOwnedByUser(userId, resumeId)) {
            return "redirect:/user/dashboard";
        }
        
        try {
            resumeService.deleteResume(resumeId);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Resume deleted successfully!");
        } catch (ResumeNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Resume not found or could not be deleted.");
        }
        
        return "redirect:/user/dashboard";
    }
    
    /**
     * Creates a new version of an existing resume.
     * 
     * @param resumeId the ID of the original resume
     * @param session HTTP session to get current user
     * @param redirectAttributes attributes for redirect
     * @return redirect to new resume edit form
     */
    @PostMapping("/{resumeId}/duplicate")
    public String duplicateResume(@PathVariable Long resumeId,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        // Check if user owns this resume
        if (!resumeService.isResumeOwnedByUser(userId, resumeId)) {
            return "redirect:/user/dashboard";
        }
        
        try {
            Resume newResume = resumeService.createResumeVersion(resumeId);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Resume duplicated successfully!");
            return "redirect:/resume/" + newResume.getId() + "/edit";
        } catch (ResumeNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Original resume not found.");
            return "redirect:/user/dashboard";
        }
    }
    
    /**
     * Displays the personal information input form.
     * 
     * @param resumeId the ID of the resume (optional, for editing existing resume)
     * @param session HTTP session to get current user
     * @param model the model for the view
     * @return the personal info form view name
     */
    @GetMapping("/personal-info")
    public String showPersonalInfoForm(@RequestParam(required = false) Long resumeId,
                                      HttpSession session,
                                      Model model) {
        
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        ResumeData.PersonalInfo personalInfo = new ResumeData.PersonalInfo();
        
        // If editing existing resume, load current personal info
        if (resumeId != null && resumeService.isResumeOwnedByUser(userId, resumeId)) {
            try {
                Resume resume = resumeService.getResumeById(resumeId);
                if (resume.getXmlContent() != null) {
                    ResumeData resumeData = xmlProcessingService.parseFromXML(resume.getXmlContent());
                    personalInfo = resumeData.getPersonalInfo();
                }
            } catch (ResumeNotFoundException e) {
                // Use empty personal info if resume not found
            }
        }
        
        model.addAttribute("personalInfo", personalInfo);
        model.addAttribute("resumeId", resumeId);
        return "resume/personal-info";
    }
    
    /**
     * Displays the education input form.
     * 
     * @param resumeId the ID of the resume (optional, for editing existing resume)
     * @param session HTTP session to get current user
     * @param model the model for the view
     * @return the education form view name
     */
    @GetMapping("/education")
    public String showEducationForm(@RequestParam(required = false) Long resumeId,
                                   HttpSession session,
                                   Model model) {
        
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        List<ResumeData.EducationData> educations = new ArrayList<>();
        
        // If editing existing resume, load current education data
        if (resumeId != null && resumeService.isResumeOwnedByUser(userId, resumeId)) {
            try {
                Resume resume = resumeService.getResumeById(resumeId);
                if (resume.getXmlContent() != null) {
                    ResumeData resumeData = xmlProcessingService.parseFromXML(resume.getXmlContent());
                    educations = resumeData.getEducations() != null ? resumeData.getEducations() : new ArrayList<>();
                }
            } catch (ResumeNotFoundException e) {
                // Use empty list if resume not found
            }
        }
        
        // Add empty education entry if list is empty
        if (educations.isEmpty()) {
            educations.add(new ResumeData.EducationData());
        }
        
        model.addAttribute("educations", educations);
        model.addAttribute("resumeId", resumeId);
        return "resume/education";
    }
    
    /**
     * Displays the work experience input form.
     * 
     * @param resumeId the ID of the resume (optional, for editing existing resume)
     * @param session HTTP session to get current user
     * @param model the model for the view
     * @return the work experience form view name
     */
    @GetMapping("/experience")
    public String showExperienceForm(@RequestParam(required = false) Long resumeId,
                                    HttpSession session,
                                    Model model) {
        
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        List<ResumeData.WorkExperienceData> workExperiences = new ArrayList<>();
        
        // If editing existing resume, load current work experience data
        if (resumeId != null && resumeService.isResumeOwnedByUser(userId, resumeId)) {
            try {
                Resume resume = resumeService.getResumeById(resumeId);
                if (resume.getXmlContent() != null) {
                    ResumeData resumeData = xmlProcessingService.parseFromXML(resume.getXmlContent());
                    workExperiences = resumeData.getWorkExperiences() != null ? 
                        resumeData.getWorkExperiences() : new ArrayList<>();
                }
            } catch (ResumeNotFoundException e) {
                // Use empty list if resume not found
            }
        }
        
        // Add empty work experience entry if list is empty
        if (workExperiences.isEmpty()) {
            workExperiences.add(new ResumeData.WorkExperienceData());
        }
        
        model.addAttribute("workExperiences", workExperiences);
        model.addAttribute("resumeId", resumeId);
        return "resume/experience";
    }
    
    /**
     * Displays the skills and achievements input form.
     * 
     * @param resumeId the ID of the resume (optional, for editing existing resume)
     * @param session HTTP session to get current user
     * @param model the model for the view
     * @return the skills form view name
     */
    @GetMapping("/skills")
    public String showSkillsForm(@RequestParam(required = false) Long resumeId,
                                HttpSession session,
                                Model model) {
        
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        List<ResumeData.SkillData> skills = new ArrayList<>();
        
        // If editing existing resume, load current skills data
        if (resumeId != null && resumeService.isResumeOwnedByUser(userId, resumeId)) {
            try {
                Resume resume = resumeService.getResumeById(resumeId);
                if (resume.getXmlContent() != null) {
                    ResumeData resumeData = xmlProcessingService.parseFromXML(resume.getXmlContent());
                    skills = resumeData.getSkills() != null ? resumeData.getSkills() : new ArrayList<>();
                }
            } catch (ResumeNotFoundException e) {
                // Use empty list if resume not found
            }
        }
        
        // Add empty skill entries if list is empty
        if (skills.isEmpty()) {
            skills.add(new ResumeData.SkillData());
        }
        
        model.addAttribute("skills", skills);
        model.addAttribute("resumeId", resumeId);
        model.addAttribute("skillTypes", new String[]{"TECHNICAL", "SOFT_SKILL", "LANGUAGE"});
        return "resume/skills";
    }
    
    /**
     * API endpoint to get resume data as JSON (for AJAX requests).
     * 
     * @param resumeId the ID of the resume
     * @param session HTTP session to get current user
     * @return ResumeData as JSON or null if not found/unauthorized
     */
    @GetMapping(value = "/{resumeId}/api/data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResumeData getResumeData(@PathVariable Long resumeId, HttpSession session) {
        Long userId = getCurrentUserId(session);
        if (userId == null || !resumeService.isResumeOwnedByUser(userId, resumeId)) {
            return null;
        }
        
        try {
            Resume resume = resumeService.getResumeById(resumeId);
            if (resume.getXmlContent() != null && !resume.getXmlContent().trim().isEmpty()) {
                return xmlProcessingService.parseFromXML(resume.getXmlContent());
            }
        } catch (ResumeNotFoundException e) {
            return null;
        }
        
        return null;
    }
    
    /**
     * Helper method to get current user ID from session.
     * 
     * @param session HTTP session
     * @return user ID or null if not authenticated
     */
    private Long getCurrentUserId(HttpSession session) {
        Object userId = session.getAttribute("userId");
        return userId instanceof Long ? (Long) userId : null;
    }
    
    /**
     * Manually parse form data to avoid array indexing issues.
     * 
     * @param request HTTP request containing form parameters
     * @return parsed ResumeData object
     */
    private ResumeData parseFormData(HttpServletRequest request) {
        ResumeData resumeData = new ResumeData();
        
        // Parse basic info
        resumeData.setTitle(request.getParameter("title"));
        String templateTypeStr = request.getParameter("templateType");
        if (templateTypeStr != null && !templateTypeStr.isEmpty()) {
            try {
                resumeData.setTemplateType(TemplateType.valueOf(templateTypeStr));
            } catch (IllegalArgumentException e) {
                resumeData.setTemplateType(TemplateType.CLASSIC);
            }
        } else {
            resumeData.setTemplateType(TemplateType.CLASSIC);
        }
        
        // Parse personal info
        ResumeData.PersonalInfo personalInfo = new ResumeData.PersonalInfo();
        personalInfo.setName(request.getParameter("personalInfo.name"));
        personalInfo.setEmail(request.getParameter("personalInfo.email"));
        personalInfo.setPhone(request.getParameter("personalInfo.phone"));
        personalInfo.setAddress(request.getParameter("personalInfo.address"));
        resumeData.setPersonalInfo(personalInfo);
        
        // Parse professional summary
        resumeData.setProfessionalSummary(request.getParameter("professionalSummary"));
        
        // Parse educations
        List<ResumeData.EducationData> educations = new ArrayList<>();
        Map<String, String[]> paramMap = request.getParameterMap();
        
        for (int i = 0; i < 10; i++) { // Check up to 10 education entries
            String degree = request.getParameter("educations[" + i + "].degree");
            String institution = request.getParameter("educations[" + i + "].institution");
            
            if ((degree != null && !degree.trim().isEmpty()) || 
                (institution != null && !institution.trim().isEmpty())) {
                
                ResumeData.EducationData education = new ResumeData.EducationData();
                education.setDegree(degree);
                education.setInstitution(institution);
                education.setFieldOfStudy(request.getParameter("educations[" + i + "].fieldOfStudy"));
                
                // Parse GPA safely
                String gpaStr = request.getParameter("educations[" + i + "].gpa");
                if (gpaStr != null && !gpaStr.trim().isEmpty()) {
                    try {
                        education.setGpa(Double.parseDouble(gpaStr));
                    } catch (NumberFormatException e) {
                        // Ignore invalid GPA
                    }
                }
                
                // Parse dates safely
                String startDateStr = request.getParameter("educations[" + i + "].startDate");
                if (startDateStr != null && !startDateStr.trim().isEmpty()) {
                    try {
                        education.setStartDate(LocalDate.parse(startDateStr));
                    } catch (DateTimeParseException e) {
                        // Ignore invalid date
                    }
                }
                
                String endDateStr = request.getParameter("educations[" + i + "].endDate");
                if (endDateStr != null && !endDateStr.trim().isEmpty()) {
                    try {
                        education.setEndDate(LocalDate.parse(endDateStr));
                    } catch (DateTimeParseException e) {
                        // Ignore invalid date
                    }
                }
                
                educations.add(education);
            }
        }
        resumeData.setEducations(educations);
        
        // Parse work experiences
        List<ResumeData.WorkExperienceData> workExperiences = new ArrayList<>();
        
        for (int i = 0; i < 10; i++) { // Check up to 10 work experience entries
            String company = request.getParameter("workExperiences[" + i + "].company");
            String position = request.getParameter("workExperiences[" + i + "].position");
            
            if ((company != null && !company.trim().isEmpty()) || 
                (position != null && !position.trim().isEmpty())) {
                
                ResumeData.WorkExperienceData workExp = new ResumeData.WorkExperienceData();
                workExp.setCompany(company);
                workExp.setPosition(position);
                workExp.setDescription(request.getParameter("workExperiences[" + i + "].description"));
                
                // Parse dates safely
                String startDateStr = request.getParameter("workExperiences[" + i + "].startDate");
                if (startDateStr != null && !startDateStr.trim().isEmpty()) {
                    try {
                        workExp.setStartDate(LocalDate.parse(startDateStr));
                    } catch (DateTimeParseException e) {
                        // Ignore invalid date
                    }
                }
                
                String endDateStr = request.getParameter("workExperiences[" + i + "].endDate");
                if (endDateStr != null && !endDateStr.trim().isEmpty()) {
                    try {
                        workExp.setEndDate(LocalDate.parse(endDateStr));
                    } catch (DateTimeParseException e) {
                        // Ignore invalid date
                    }
                }
                
                workExperiences.add(workExp);
            }
        }
        resumeData.setWorkExperiences(workExperiences);
        
        // Parse skills
        List<ResumeData.SkillData> skills = new ArrayList<>();
        
        for (int i = 0; i < 20; i++) { // Check up to 20 skill entries
            String skillName = request.getParameter("skills[" + i + "].name");
            
            if (skillName != null && !skillName.trim().isEmpty()) {
                ResumeData.SkillData skill = new ResumeData.SkillData();
                skill.setName(skillName);
                
                String skillType = request.getParameter("skills[" + i + "].type");
                if (skillType != null && !skillType.trim().isEmpty()) {
                    skill.setType(skillType);
                } else {
                    skill.setType("TECHNICAL"); // Default type
                }
                
                skill.setProficiencyLevel(request.getParameter("skills[" + i + "].proficiencyLevel"));
                skills.add(skill);
            }
        }
        resumeData.setSkills(skills);
        
        // Initialize empty lists for other sections
        resumeData.setProjects(new ArrayList<>());
        resumeData.setCertifications(new ArrayList<>());
        resumeData.setInterests(new ArrayList<>());
        
        return resumeData;
    }
}