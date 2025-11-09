package com.resumebuilder.controller;

import com.resumebuilder.entity.Resume;
import com.resumebuilder.entity.TemplateType;
import com.resumebuilder.exception.PDFGenerationException;
import com.resumebuilder.exception.ResumeNotFoundException;
import com.resumebuilder.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Controller for handling PDF generation and download requests.
 */
@Controller
@RequestMapping("/pdf")
public class PDFController {
    
    private final ResumeService resumeService;
    
    @Autowired
    public PDFController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }
    
    /**
     * Generates and downloads resume as PDF.
     * 
     * @param resumeId the ID of the resume to generate
     * @param templateType the template type to use (optional)
     * @param session HTTP session to get current user
     * @return PDF file as ResponseEntity or error response
     */
    @GetMapping("/generate/{resumeId}")
    public ResponseEntity<byte[]> generatePDF(@PathVariable Long resumeId,
                                             @RequestParam(required = false) TemplateType templateType,
                                             HttpSession session) {
        
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        // Verify user owns the resume
        if (!resumeService.isResumeOwnedByUser(userId, resumeId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        try {
            byte[] pdfBytes;
            Resume resume = resumeService.getResumeById(resumeId);
            
            if (templateType != null) {
                // Generate PDF with specified template
                pdfBytes = resumeService.generatePDF(resumeId, templateType);
            } else {
                // Generate PDF with resume's current template
                pdfBytes = resumeService.generatePDF(resumeId);
            }
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", 
                sanitizeFilename(resume.getTitle()) + ".pdf");
            headers.setContentLength(pdfBytes.length);
            
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
            
        } catch (ResumeNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (PDFGenerationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * API endpoint to check PDF generation status.
     * 
     * @param resumeId the ID of the resume
     * @param session HTTP session to get current user
     * @return status information as JSON
     */
    @GetMapping("/status/{resumeId}")
    @ResponseBody
    public ResponseEntity<Object> getPDFStatus(@PathVariable Long resumeId,
                                              HttpSession session) {
        
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        // Verify user owns the resume
        if (!resumeService.isResumeOwnedByUser(userId, resumeId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        try {
            Resume resume = resumeService.getResumeById(resumeId);
            
            return ResponseEntity.ok(new Object() {
                public final String status = "ready";
                public final String resumeTitle = resume.getTitle();
                public final String templateType = resume.getTemplateType().name();
                public final boolean canGenerate = true;
            });
            
        } catch (ResumeNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Helper method to get current user ID from session.
     * 
     * @param session HTTP session
     * @return user ID or null if not authenticated
     */
    private Long getCurrentUserId(HttpSession session) {
        Object userId = session.getAttribute("userId");
        return userId instanceof Long ? (Long) userId : null;
    }
    
    /**
     * Helper method to sanitize filename for download.
     * 
     * @param filename the original filename
     * @return sanitized filename safe for download
     */
    private String sanitizeFilename(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return "resume";
        }
        
        // Remove or replace invalid characters for filenames
        return filename.replaceAll("[^a-zA-Z0-9._-]", "_").trim();
    }
}