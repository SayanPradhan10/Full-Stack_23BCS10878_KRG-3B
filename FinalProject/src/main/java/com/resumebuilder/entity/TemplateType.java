package com.resumebuilder.entity;

public enum TemplateType {
    CLASSIC("Classic"),
    MODERN("Modern"),
    CREATIVE("Creative");
    
    private final String displayName;
    
    TemplateType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}