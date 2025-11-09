package com.resumebuilder.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "skills")
public class Skill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;
    
    @Column(nullable = false)
    @NotNull(message = "Skill name is required")
    @Size(min = 1, max = 255, message = "Skill name must be between 1 and 255 characters")
    private String name;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Skill type is required")
    private SkillType type;
    
    @Size(max = 50, message = "Proficiency level must not exceed 50 characters")
    private String proficiencyLevel;
    
    // Constructors
    public Skill() {}
    
    public Skill(String name, SkillType type) {
        this.name = name;
        this.type = type;
    }
    
    public Skill(String name, SkillType type, String proficiencyLevel) {
        this(name, type);
        this.proficiencyLevel = proficiencyLevel;
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
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public SkillType getType() {
        return type;
    }
    
    public void setType(SkillType type) {
        this.type = type;
    }
    
    public String getProficiencyLevel() {
        return proficiencyLevel;
    }
    
    public void setProficiencyLevel(String proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }
    
    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", proficiencyLevel='" + proficiencyLevel + '\'' +
                '}';
    }
}