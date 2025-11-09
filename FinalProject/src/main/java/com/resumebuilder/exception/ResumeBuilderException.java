package com.resumebuilder.exception;

/**
 * Base exception class for Resume Builder application.
 * All custom exceptions in the application should extend this class.
 */
public class ResumeBuilderException extends RuntimeException {
    
    public ResumeBuilderException(String message) {
        super(message);
    }
    
    public ResumeBuilderException(String message, Throwable cause) {
        super(message, cause);
    }
}