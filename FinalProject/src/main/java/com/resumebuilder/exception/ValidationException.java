package com.resumebuilder.exception;

import java.util.Map;
import java.util.HashMap;

/**
 * Exception thrown when validation fails in service layer.
 * Can contain multiple field-specific error messages.
 */
public class ValidationException extends ResumeBuilderException {
    
    private final Map<String, String> fieldErrors;
    
    public ValidationException(String message) {
        super(message);
        this.fieldErrors = new HashMap<>();
    }
    
    public ValidationException(String message, Map<String, String> fieldErrors) {
        super(message);
        this.fieldErrors = fieldErrors != null ? fieldErrors : new HashMap<>();
    }
    
    public ValidationException(String field, String errorMessage) {
        super("Validation failed for field: " + field);
        this.fieldErrors = new HashMap<>();
        this.fieldErrors.put(field, errorMessage);
    }
    
    /**
     * Get all field-specific error messages.
     * 
     * @return Map of field names to error messages
     */
    public Map<String, String> getFieldErrors() {
        return new HashMap<>(fieldErrors);
    }
    
    /**
     * Check if there are any field-specific errors.
     * 
     * @return true if there are field errors, false otherwise
     */
    public boolean hasFieldErrors() {
        return !fieldErrors.isEmpty();
    }
    
    /**
     * Get error message for a specific field.
     * 
     * @param fieldName the name of the field
     * @return error message or null if no error for this field
     */
    public String getFieldError(String fieldName) {
        return fieldErrors.get(fieldName);
    }
    
    /**
     * Add a field-specific error message.
     * 
     * @param fieldName the name of the field
     * @param errorMessage the error message
     */
    public void addFieldError(String fieldName, String errorMessage) {
        this.fieldErrors.put(fieldName, errorMessage);
    }
}