package com.resumebuilder.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;
    
    @Column(nullable = false)
    @NotNull(message = "Name is required")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String name;
    
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phone;
    
    @Size(max = 500, message = "Address must not exceed 500 characters")
    private String address;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Resume> resumes = new ArrayList<>();
    
    // Constructors
    public User() {
        this.createdDate = LocalDateTime.now();
    }
    
    public User(String email, String name) {
        this();
        this.email = email;
        this.name = name;
    }
    
    public User(String email, String name, String phone, String address) {
        this(email, name);
        this.phone = phone;
        this.address = address;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
    
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
    
    public List<Resume> getResumes() {
        return resumes;
    }
    
    public void setResumes(List<Resume> resumes) {
        this.resumes = resumes;
    }
    
    // Helper methods for managing relationships
    public void addResume(Resume resume) {
        resumes.add(resume);
        resume.setUser(this);
    }
    
    public void removeResume(Resume resume) {
        resumes.remove(resume);
        resume.setUser(null);
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}