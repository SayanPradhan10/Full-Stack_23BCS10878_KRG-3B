package com.resumebuilder.exception;

/**
 * Exception thrown when a requested resume cannot be found.
 */
public class ResumeNotFoundException extends ResumeBuilderException {
    
    public ResumeNotFoundException(String message) {
        super(message);
    }
    
    public ResumeNotFoundException(Long resumeId) {
        super("Resume not found with ID: " + resumeId);
    }
}