package com.resumebuilder.exception;

/**
 * Exception thrown when PDF generation operations fail.
 * This includes PDF creation, formatting, and file writing errors.
 */
public class PDFGenerationException extends ResumeBuilderException {
    
    public PDFGenerationException(String message) {
        super(message);
    }
    
    public PDFGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}