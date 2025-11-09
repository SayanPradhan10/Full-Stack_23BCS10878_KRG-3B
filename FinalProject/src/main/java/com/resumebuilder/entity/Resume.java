package com.resumebuilder.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "resumes")
public class Resume {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    @NotNull(message = "Resume title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "template_type", nullable = false)
    @NotNull(message = "Template type is required")
    private TemplateType templateType;
    
    @Column(name = "xml_content", columnDefinition = "TEXT")
    private String xmlContent;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @Column(name = "last_modified")
    private LocalDateTime lastModified;
    
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Education> educations = new ArrayList<>();
    
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkExperience> workExperiences = new ArrayList<>();
    
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Skill> skills = new ArrayList<>();
    
    // Constructors
    public Resume() {
        this.createdDate = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
    }
    
    public Resume(String title, TemplateType templateType) {
        this();
        this.title = title;
        this.templateType = templateType;
    }
    
    public Resume(User user, String title, TemplateType templateType) {
        this(title, templateType);
        this.user = user;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public TemplateType getTemplateType() {
        return templateType;
    }
    
    public void setTemplateType(TemplateType templateType) {
        this.templateType = templateType;
    }
    
    public String getXmlContent() {
        return xmlContent;
    }
    
    public void setXmlContent(String xmlContent) {
        this.xmlContent = xmlContent;
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    
    public LocalDateTime getLastModified() {
        return lastModified;
    }
    
    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }
    
    public List<Education> getEducations() {
        return educations;
    }
    
    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }
    
    public List<WorkExperience> getWorkExperiences() {
        return workExperiences;
    }
    
    public void setWorkExperiences(List<WorkExperience> workExperiences) {
        this.workExperiences = workExperiences;
    }
    
    public List<Skill> getSkills() {
        return skills;
    }
    
    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
    
    // Helper methods for managing relationships
    public void addEducation(Education education) {
        educations.add(education);
        education.setResume(this);
    }
    
    public void removeEducation(Education education) {
        educations.remove(education);
        education.setResume(null);
    }
    
    public void addWorkExperience(WorkExperience workExperience) {
        workExperiences.add(workExperience);
        workExperience.setResume(this);
    }
    
    public void removeWorkExperience(WorkExperience workExperience) {
        workExperiences.remove(workExperience);
        workExperience.setResume(null);
    }
    
    public void addSkill(Skill skill) {
        skills.add(skill);
        skill.setResume(this);
    }
    
    public void removeSkill(Skill skill) {
        skills.remove(skill);
        skill.setResume(null);
    }
    
    // Update last modified timestamp
    @PreUpdate
    public void updateLastModified() {
        this.lastModified = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "Resume{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", templateType=" + templateType +
                ", createdDate=" + createdDate +
                ", lastModified=" + lastModified +
                '}';
    }
}