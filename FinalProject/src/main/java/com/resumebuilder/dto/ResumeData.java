package com.resumebuilder.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.resumebuilder.entity.TemplateType;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for Resume data used in XML processing and PDF generation.
 * This class represents the complete resume data structure for serialization.
 */
@JacksonXmlRootElement(localName = "resume")
public class ResumeData {
    
    @JacksonXmlProperty(localName = "id")
    private Long id;
    
    @JacksonXmlProperty(localName = "title")
    @NotNull(message = "Resume title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;
    
    @JacksonXmlProperty(localName = "templateType")
    @NotNull(message = "Template type is required")
    private TemplateType templateType;
    
    @JacksonXmlProperty(localName = "createdDate")
    private LocalDateTime createdDate;
    
    @JacksonXmlProperty(localName = "lastModified")
    private LocalDateTime lastModified;
    
    @JacksonXmlProperty(localName = "personalInfo")
    @NotNull(message = "Personal information is required")
    @Valid
    private PersonalInfo personalInfo;
    
    @JacksonXmlElementWrapper(localName = "educations")
    @JacksonXmlProperty(localName = "education")
    @Valid
    private List<EducationData> educations;
    
    @JacksonXmlElementWrapper(localName = "workExperiences")
    @JacksonXmlProperty(localName = "workExperience")
    @Valid
    private List<WorkExperienceData> workExperiences;
    
    @JacksonXmlElementWrapper(localName = "skills")
    @JacksonXmlProperty(localName = "skill")
    @Valid
    private List<SkillData> skills;
    
    @JacksonXmlProperty(localName = "professionalSummary")
    @Size(max = 1000, message = "Professional summary must not exceed 1000 characters")
    private String professionalSummary;
    
    @JacksonXmlElementWrapper(localName = "projects")
    @JacksonXmlProperty(localName = "project")
    @Valid
    private List<ProjectData> projects;
    
    @JacksonXmlElementWrapper(localName = "certifications")
    @JacksonXmlProperty(localName = "certification")
    @Valid
    private List<CertificationData> certifications;
    
    @JacksonXmlElementWrapper(localName = "interests")
    @JacksonXmlProperty(localName = "interest")
    @Valid
    private List<InterestData> interests;
    
    // Constructors
    public ResumeData() {}
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public TemplateType getTemplateType() { return templateType; }
    public void setTemplateType(TemplateType templateType) { this.templateType = templateType; }
    
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    
    public LocalDateTime getLastModified() { return lastModified; }
    public void setLastModified(LocalDateTime lastModified) { this.lastModified = lastModified; }
    
    public PersonalInfo getPersonalInfo() { return personalInfo; }
    public void setPersonalInfo(PersonalInfo personalInfo) { this.personalInfo = personalInfo; }
    
    public List<EducationData> getEducations() { return educations; }
    public void setEducations(List<EducationData> educations) { this.educations = educations; }
    
    public List<WorkExperienceData> getWorkExperiences() { return workExperiences; }
    public void setWorkExperiences(List<WorkExperienceData> workExperiences) { this.workExperiences = workExperiences; }
    
    public List<SkillData> getSkills() { return skills; }
    public void setSkills(List<SkillData> skills) { this.skills = skills; }
    
    public String getProfessionalSummary() { return professionalSummary; }
    public void setProfessionalSummary(String professionalSummary) { this.professionalSummary = professionalSummary; }
    
    public List<ProjectData> getProjects() { return projects; }
    public void setProjects(List<ProjectData> projects) { this.projects = projects; }
    
    public List<CertificationData> getCertifications() { return certifications; }
    public void setCertifications(List<CertificationData> certifications) { this.certifications = certifications; }
    
    public List<InterestData> getInterests() { return interests; }
    public void setInterests(List<InterestData> interests) { this.interests = interests; }
    
    // Nested classes for structured data
    public static class PersonalInfo {
        @JacksonXmlProperty(localName = "name")
        @NotNull(message = "Name is required")
        @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
        private String name;
        
        @JacksonXmlProperty(localName = "email")
        @NotNull(message = "Email is required")
        @Email(message = "Email should be valid")
        @Size(max = 255, message = "Email must not exceed 255 characters")
        private String email;
        
        @JacksonXmlProperty(localName = "phone")
        @Size(max = 20, message = "Phone number must not exceed 20 characters")
        private String phone;
        
        @JacksonXmlProperty(localName = "address")
        @Size(max = 500, message = "Address must not exceed 500 characters")
        private String address;
        
        // Constructors
        public PersonalInfo() {}
        
        public PersonalInfo(String name, String email, String phone, String address) {
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.address = address;
        }
        
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
    }
    
    public static class EducationData {
        @JacksonXmlProperty(localName = "institution")
        @NotNull(message = "Institution name is required")
        @Size(min = 1, max = 255, message = "Institution name must be between 1 and 255 characters")
        private String institution;
        
        @JacksonXmlProperty(localName = "degree")
        @NotNull(message = "Degree is required")
        @Size(min = 1, max = 255, message = "Degree must be between 1 and 255 characters")
        private String degree;
        
        @JacksonXmlProperty(localName = "fieldOfStudy")
        @Size(max = 255, message = "Field of study must not exceed 255 characters")
        private String fieldOfStudy;
        
        @JacksonXmlProperty(localName = "startDate")
        private LocalDate startDate;
        
        @JacksonXmlProperty(localName = "endDate")
        private LocalDate endDate;
        
        @JacksonXmlProperty(localName = "gpa")
        @DecimalMin(value = "0.0", message = "GPA must be at least 0.0")
        @DecimalMax(value = "4.0", message = "GPA must not exceed 4.0")
        private Double gpa;
        
        // Constructors
        public EducationData() {}
        
        // Getters and Setters
        public String getInstitution() { return institution; }
        public void setInstitution(String institution) { this.institution = institution; }
        
        public String getDegree() { return degree; }
        public void setDegree(String degree) { this.degree = degree; }
        
        public String getFieldOfStudy() { return fieldOfStudy; }
        public void setFieldOfStudy(String fieldOfStudy) { this.fieldOfStudy = fieldOfStudy; }
        
        public LocalDate getStartDate() { return startDate; }
        public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
        
        public LocalDate getEndDate() { return endDate; }
        public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
        
        public Double getGpa() { return gpa; }
        public void setGpa(Double gpa) { this.gpa = gpa; }
    }
    
    public static class WorkExperienceData {
        @JacksonXmlProperty(localName = "company")
        @NotNull(message = "Company name is required")
        @Size(min = 1, max = 255, message = "Company name must be between 1 and 255 characters")
        private String company;
        
        @JacksonXmlProperty(localName = "position")
        @NotNull(message = "Position is required")
        @Size(min = 1, max = 255, message = "Position must be between 1 and 255 characters")
        private String position;
        
        @JacksonXmlProperty(localName = "startDate")
        private LocalDate startDate;
        
        @JacksonXmlProperty(localName = "endDate")
        private LocalDate endDate;
        
        @JacksonXmlProperty(localName = "description")
        @Size(max = 2000, message = "Description must not exceed 2000 characters")
        private String description;
        
        @JacksonXmlProperty(localName = "isInternship")
        private Boolean isInternship;
        
        // Constructors
        public WorkExperienceData() {}
        
        // Getters and Setters
        public String getCompany() { return company; }
        public void setCompany(String company) { this.company = company; }
        
        public String getPosition() { return position; }
        public void setPosition(String position) { this.position = position; }
        
        public LocalDate getStartDate() { return startDate; }
        public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
        
        public LocalDate getEndDate() { return endDate; }
        public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public Boolean getIsInternship() { return isInternship; }
        public void setIsInternship(Boolean isInternship) { this.isInternship = isInternship; }
    }
    
    public static class SkillData {
        @JacksonXmlProperty(localName = "name")
        @NotNull(message = "Skill name is required")
        @Size(min = 1, max = 255, message = "Skill name must be between 1 and 255 characters")
        private String name;
        
        @JacksonXmlProperty(localName = "type")
        @NotNull(message = "Skill type is required")
        private String type;
        
        @JacksonXmlProperty(localName = "proficiencyLevel")
        @Size(max = 50, message = "Proficiency level must not exceed 50 characters")
        private String proficiencyLevel;
        
        // Constructors
        public SkillData() {}
        
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public String getProficiencyLevel() { return proficiencyLevel; }
        public void setProficiencyLevel(String proficiencyLevel) { this.proficiencyLevel = proficiencyLevel; }
    }
    
    /**
     * Data class for project information.
     */
    public static class ProjectData {
        @JacksonXmlProperty(localName = "name")
        @NotNull(message = "Project name is required")
        @Size(min = 1, max = 255, message = "Project name must be between 1 and 255 characters")
        private String name;
        
        @JacksonXmlProperty(localName = "description")
        @Size(max = 1000, message = "Project description must not exceed 1000 characters")
        private String description;
        
        @JacksonXmlProperty(localName = "technologies")
        @Size(max = 500, message = "Technologies must not exceed 500 characters")
        private String technologies;
        
        @JacksonXmlProperty(localName = "startDate")
        private LocalDate startDate;
        
        @JacksonXmlProperty(localName = "endDate")
        private LocalDate endDate;
        
        @JacksonXmlProperty(localName = "projectUrl")
        @Size(max = 500, message = "Project URL must not exceed 500 characters")
        private String projectUrl;
        
        @JacksonXmlProperty(localName = "githubUrl")
        @Size(max = 500, message = "GitHub URL must not exceed 500 characters")
        private String githubUrl;
        
        // Constructors
        public ProjectData() {}
        
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getTechnologies() { return technologies; }
        public void setTechnologies(String technologies) { this.technologies = technologies; }
        
        public LocalDate getStartDate() { return startDate; }
        public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
        
        public LocalDate getEndDate() { return endDate; }
        public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
        
        public String getProjectUrl() { return projectUrl; }
        public void setProjectUrl(String projectUrl) { this.projectUrl = projectUrl; }
        
        public String getGithubUrl() { return githubUrl; }
        public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }
    }
    
    /**
     * Data class for certification information.
     */
    public static class CertificationData {
        @JacksonXmlProperty(localName = "name")
        @NotNull(message = "Certification name is required")
        @Size(min = 1, max = 255, message = "Certification name must be between 1 and 255 characters")
        private String name;
        
        @JacksonXmlProperty(localName = "issuingOrganization")
        @NotNull(message = "Issuing organization is required")
        @Size(min = 1, max = 255, message = "Issuing organization must be between 1 and 255 characters")
        private String issuingOrganization;
        
        @JacksonXmlProperty(localName = "issueDate")
        private LocalDate issueDate;
        
        @JacksonXmlProperty(localName = "expirationDate")
        private LocalDate expirationDate;
        
        @JacksonXmlProperty(localName = "credentialId")
        @Size(max = 255, message = "Credential ID must not exceed 255 characters")
        private String credentialId;
        
        @JacksonXmlProperty(localName = "credentialUrl")
        @Size(max = 500, message = "Credential URL must not exceed 500 characters")
        private String credentialUrl;
        
        // Constructors
        public CertificationData() {}
        
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getIssuingOrganization() { return issuingOrganization; }
        public void setIssuingOrganization(String issuingOrganization) { this.issuingOrganization = issuingOrganization; }
        
        public LocalDate getIssueDate() { return issueDate; }
        public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
        
        public LocalDate getExpirationDate() { return expirationDate; }
        public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }
        
        public String getCredentialId() { return credentialId; }
        public void setCredentialId(String credentialId) { this.credentialId = credentialId; }
        
        public String getCredentialUrl() { return credentialUrl; }
        public void setCredentialUrl(String credentialUrl) { this.credentialUrl = credentialUrl; }
    }
    
    /**
     * Data class for interests and hobbies information.
     */
    public static class InterestData {
        @JacksonXmlProperty(localName = "name")
        @NotNull(message = "Interest name is required")
        @Size(min = 1, max = 255, message = "Interest name must be between 1 and 255 characters")
        private String name;
        
        @JacksonXmlProperty(localName = "category")
        @Size(max = 100, message = "Interest category must not exceed 100 characters")
        private String category;
        
        @JacksonXmlProperty(localName = "description")
        @Size(max = 500, message = "Interest description must not exceed 500 characters")
        private String description;
        
        // Constructors
        public InterestData() {}
        
        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}