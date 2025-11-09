package com.resumebuilder.entity;

import javax.persistence.*;
import com.resumebuilder.validation.ValidDateRange;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Entity
@Table(name = "work_experience")
@ValidDateRange(message = "Work experience start date must be before end date")
public class WorkExperience {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;
    
    @Column(nullable = false)
    @NotNull(message = "Company name is required")
    @Size(min = 1, max = 255, message = "Company name must be between 1 and 255 characters")
    private String company;
    
    @Column(nullable = false)
    @NotNull(message = "Position is required")
    @Size(min = 1, max = 255, message = "Position must be between 1 and 255 characters")
    private String position;
    
    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    
    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    
    @Column(columnDefinition = "TEXT")
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;
    
    @Column(name = "is_internship")
    private Boolean isInternship = false;
    
    // Constructors
    public WorkExperience() {}
    
    public WorkExperience(String company, String position) {
        this.company = company;
        this.position = position;
    }
    
    public WorkExperience(String company, String position, LocalDate startDate, LocalDate endDate) {
        this(company, position);
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public WorkExperience(String company, String position, LocalDate startDate, LocalDate endDate, String description) {
        this(company, position, startDate, endDate);
        this.description = description;
    }
    
    public WorkExperience(String company, String position, LocalDate startDate, LocalDate endDate, String description, Boolean isInternship) {
        this(company, position, startDate, endDate, description);
        this.isInternship = isInternship;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Resume getResume() {
        return resume;
    }
    
    public void setResume(Resume resume) {
        this.resume = resume;
    }
    
    public String getCompany() {
        return company;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Boolean getIsInternship() {
        return isInternship;
    }
    
    public void setIsInternship(Boolean isInternship) {
        this.isInternship = isInternship;
    }
    
    // Helper method to check if this is currently active (no end date)
    public boolean isCurrentPosition() {
        return endDate == null;
    }
    
    @Override
    public String toString() {
        return "WorkExperience{" +
                "id=" + id +
                ", company='" + company + '\'' +
                ", position='" + position + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                ", isInternship=" + isInternship +
                '}';
    }
}