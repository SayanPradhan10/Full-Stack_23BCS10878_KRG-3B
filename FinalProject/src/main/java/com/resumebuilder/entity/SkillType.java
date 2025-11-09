package com.resumebuilder.entity;

public enum SkillType {
    TECHNICAL("Technical Skills"),
    SOFT_SKILL("Soft Skills"),
    LANGUAGE("Languages");
    
    private final String displayName;
    
    SkillType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}