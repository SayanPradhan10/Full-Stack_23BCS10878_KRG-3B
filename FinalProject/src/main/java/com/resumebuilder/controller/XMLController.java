package com.resumebuilder.controller;

import com.resumebuilder.entity.Resume;
import com.resumebuilder.exception.ResumeNotFoundException;
import com.resumebuilder.exception.XMLProcessingException;
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
 * Controller for handling XML export and processing requests.
 */
@Controller
@RequestMapping("/xml")
public class XMLController {
    
    private final ResumeService resumeService;
    
    @Autowired
    public XMLController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }
    
    /**
     * Exports resume as XML file.
     * 
     * @param resumeId the ID of the resume to export
     * @param session HTTP session to get current user
     * @return XML file as ResponseEntity or error response
     */
    @GetMapping("/export/{resumeId}")
    public ResponseEntity<String> exportXML(@PathVariable Long resumeId,
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
            String xmlContent = resumeService.generateXML(resumeId);
            Resume resume = resumeService.getResumeById(resumeId);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            headers.setContentDispositionFormData("attachment", 
                sanitizeFilename(resume.getTitle()) + ".xml");
            headers.setContentLength(xmlContent.getBytes().length);
            
            return new ResponseEntity<>(xmlContent, headers, HttpStatus.OK);
            
        } catch (ResumeNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (XMLProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * API endpoint to get XML content for preview.
     * 
     * @param resumeId the ID of the resume
     * @param session HTTP session to get current user
     * @return XML content as JSON response
     */
    @GetMapping("/preview/{resumeId}")
    @ResponseBody
    public ResponseEntity<Object> previewXML(@PathVariable Long resumeId,
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
            String xmlContent = resumeService.generateXML(resumeId);
            Resume resume = resumeService.getResumeById(resumeId);
            
            final String finalXmlContent = xmlContent;
            return ResponseEntity.ok(new Object() {
                public final String xmlContent = finalXmlContent;
                public final String resumeTitle = resume.getTitle();
                public final String templateType = resume.getTemplateType().name();
                public final int contentLength = finalXmlContent.length();
            });
            
        } catch (ResumeNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (XMLProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * API endpoint to validate XML structure.
     * 
     * @param resumeId the ID of the resume
     * @param session HTTP session to get current user
     * @return validation result as JSON
     */
    @GetMapping("/validate/{resumeId}")
    @ResponseBody
    public ResponseEntity<Object> validateXML(@PathVariable Long resumeId,
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
            String xmlContent = resumeService.generateXML(resumeId);
            
            // Basic validation - check if XML is well-formed
            boolean isValid = xmlContent != null && 
                             xmlContent.trim().startsWith("<?xml") && 
                             xmlContent.contains("<resume>") && 
                             xmlContent.contains("</resume>");
            
            final String finalXmlContent = xmlContent;
            final boolean finalIsValid = isValid;
            return ResponseEntity.ok(new Object() {
                public final boolean valid = finalIsValid;
                public final String message = finalIsValid ? "XML is well-formed" : "XML validation failed";
                public final int contentLength = finalXmlContent != null ? finalXmlContent.length() : 0;
            });
            
        } catch (ResumeNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (XMLProcessingException e) {
            return ResponseEntity.ok(new Object() {
                public final boolean valid = false;
                public final String message = "XML processing error: " + e.getMessage();
                public final int contentLength = 0;
            });
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