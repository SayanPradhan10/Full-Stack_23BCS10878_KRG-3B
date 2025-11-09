package com.resumebuilder.entity;

import javax.persistence.*;
import com.resumebuilder.validation.ValidDateRange;
import com.resumebuilder.validation.ValidGPA;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Entity
@Table(name = "education")
@ValidDateRange(message = "Education start date must be before end date")
public class Education {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;
    
    @Column(nullable = false)
    @NotNull(message = "Institution name is required")
    @Size(min = 1, max = 255, message = "Institution name must be between 1 and 255 characters")
    private String institution;
    
    @Column(nullable = false)
    @NotNull(message = "Degree is required")
    @Size(min = 1, max = 255, message = "Degree must be between 1 and 255 characters")
    private String degree;
    
    @Column(name = "field_of_study")
    @Size(max = 255, message = "Field of study must not exceed 255 characters")
    private String fieldOfStudy;
    
    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    
    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    
    @ValidGPA
    private Double gpa;
    
    // Constructors
    public Education() {}
    
    public Education(String institution, String degree) {
        this.institution = institution;
        this.degree = degree;
    }
    
    public Education(String institution, String degree, String fieldOfStudy) {
        this(institution, degree);
        this.fieldOfStudy = fieldOfStudy;
    }
    
    public Education(String institution, String degree, String fieldOfStudy, LocalDate startDate, LocalDate endDate) {
        this(institution, degree, fieldOfStudy);
        this.startDate = startDate;
        this.endDate = endDate;
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
    
    public String getInstitution() {
        return institution;
    }
    
    public void setInstitution(String institution) {
        this.institution = institution;
    }
    
    public String getDegree() {
        return degree;
    }
    
    public void setDegree(String degree) {
        this.degree = degree;
    }
    
    public String getFieldOfStudy() {
        return fieldOfStudy;
    }
    
    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
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
    
    public Double getGpa() {
        return gpa;
    }
    
    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }
    
    @Override
    public String toString() {
        return "Education{" +
                "id=" + id +
                ", institution='" + institution + '\'' +
                ", degree='" + degree + '\'' +
                ", fieldOfStudy='" + fieldOfStudy + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", gpa=" + gpa +
                '}';
    }
}