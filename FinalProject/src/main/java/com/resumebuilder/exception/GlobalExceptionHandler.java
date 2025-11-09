package com.resumebuilder.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Global exception handler for the Resume Builder application.
 * Provides centralized error handling and user-friendly error pages.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * Handle ResumeNotFoundException - when a requested resume cannot be found.
     */
    @ExceptionHandler(ResumeNotFoundException.class)
    public ModelAndView handleResumeNotFound(ResumeNotFoundException ex, HttpServletRequest request) {
        logger.warn("Resume not found: {} - URL: {}", ex.getMessage(), request.getRequestURL());
        
        ModelAndView modelAndView = new ModelAndView("error/resume-not-found");
        modelAndView.addObject("errorMessage", ex.getMessage());
        modelAndView.addObject("errorCode", "RESUME_NOT_FOUND");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        
        return modelAndView;
    }
    
    /**
     * Handle PDFGenerationException - when PDF generation fails.
     */
    @ExceptionHandler(PDFGenerationException.class)
    public ModelAndView handlePDFGeneration(PDFGenerationException ex, HttpServletRequest request) {
        logger.error("PDF generation failed: {} - URL: {}", ex.getMessage(), request.getRequestURL(), ex);
        
        ModelAndView modelAndView = new ModelAndView("error/pdf-generation-error");
        modelAndView.addObject("errorMessage", "We encountered an issue generating your PDF. Please try again.");
        modelAndView.addObject("errorCode", "PDF_GENERATION_FAILED");
        modelAndView.addObject("technicalDetails", ex.getMessage());
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        
        return modelAndView;
    }
    
    /**
     * Handle XMLProcessingException - when XML processing fails.
     */
    @ExceptionHandler(XMLProcessingException.class)
    public ModelAndView handleXMLProcessing(XMLProcessingException ex, HttpServletRequest request) {
        logger.error("XML processing failed: {} - URL: {}", ex.getMessage(), request.getRequestURL(), ex);
        
        ModelAndView modelAndView = new ModelAndView("error/xml-processing-error");
        modelAndView.addObject("errorMessage", "We encountered an issue processing your resume data. Please try again.");
        modelAndView.addObject("errorCode", "XML_PROCESSING_FAILED");
        modelAndView.addObject("technicalDetails", ex.getMessage());
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        
        return modelAndView;
    }
    
    /**
     * Handle ValidationException - when service-layer validation fails.
     */
    @ExceptionHandler(ValidationException.class)
    public ModelAndView handleValidationException(ValidationException ex, HttpServletRequest request) {
        logger.warn("Validation exception: {} - URL: {}", ex.getMessage(), request.getRequestURL());
        
        ModelAndView modelAndView = new ModelAndView("error/validation-error");
        modelAndView.addObject("errorMessage", "Please correct the validation errors and try again.");
        modelAndView.addObject("errorCode", "VALIDATION_FAILED");
        modelAndView.addObject("fieldErrors", ex.getFieldErrors());
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        
        return modelAndView;
    }
    
    /**
     * Handle general ResumeBuilderException - catch-all for custom exceptions.
     */
    @ExceptionHandler(ResumeBuilderException.class)
    public ModelAndView handleResumeBuilderException(ResumeBuilderException ex, HttpServletRequest request) {
        logger.error("Resume builder exception: {} - URL: {}", ex.getMessage(), request.getRequestURL(), ex);
        
        ModelAndView modelAndView = new ModelAndView("error/general-error");
        modelAndView.addObject("errorMessage", "An error occurred while processing your request. Please try again.");
        modelAndView.addObject("errorCode", "GENERAL_ERROR");
        modelAndView.addObject("technicalDetails", ex.getMessage());
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        
        return modelAndView;
    }
    
    /**
     * Handle unexpected exceptions - catch-all for any unhandled exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneral(Exception ex, HttpServletRequest request) {
        logger.error("Unexpected error: {} - URL: {}", ex.getMessage(), request.getRequestURL(), ex);
        
        ModelAndView modelAndView = new ModelAndView("error/unexpected-error");
        modelAndView.addObject("errorMessage", "An unexpected error occurred. Our team has been notified.");
        modelAndView.addObject("errorCode", "UNEXPECTED_ERROR");
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        
        return modelAndView;
    }
}