package com.resumebuilder.exception;

/**
 * Exception thrown when XML processing operations fail.
 * This includes XML serialization, deserialization, and validation errors.
 */
public class XMLProcessingException extends ResumeBuilderException {
    
    public XMLProcessingException(String message) {
        super(message);
    }
    
    public XMLProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}