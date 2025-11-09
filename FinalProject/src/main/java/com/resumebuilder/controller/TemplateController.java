package com.resumebuilder.controller;

import com.resumebuilder.entity.Resume;
import com.resumebuilder.entity.TemplateType;
import com.resumebuilder.exception.PDFGenerationException;
import com.resumebuilder.exception.ResumeNotFoundException;
import com.resumebuilder.exception.XMLProcessingException;
import com.resumebuilder.service.ResumeService;
import com.resumebuilder.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Controller for handling template selection, preview, and resume generation endpoints.
 * Manages template-related operations including XML and PDF generation with download functionality.
 */
@Controller
@RequestMapping("/template")
public class TemplateController {
    
    private final TemplateService templateService;
    private final ResumeService resumeService;
    
    @Autowired
    public TemplateController(TemplateService templateService, ResumeService resumeService) {
        this.templateService = templateService;
        this.resumeService = resumeService;
    }
    
    /**
     * Displays the template selection page with previews.
     * 
     * @param resumeId the ID of the resume to apply template to (optional)
     * @param session HTTP session to get current user
     * @param model the model for the view
     * @return the template selection view name or redirect to login
     */
    @GetMapping("/select")
    public String showTemplateSelection(@RequestParam(required = false) Long resumeId,
                                       HttpSession session,
                                       Model model) {
        
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        // If resumeId is provided, verify user owns the resume
        if (resumeId != null && !resumeService.isResumeOwnedByUser(userId, resumeId)) {
            return "redirect:/user/dashboard";
        }
        
        List<TemplateType> availableTemplates = templateService.getAvailableTemplates();
        
        model.addAttribute("templates", availableTemplates);
        model.addAttribute("resumeId", resumeId);
        
        // Add template metadata for each template
        for (TemplateType template : availableTemplates) {
            Map<String, Object> metadata = templateService.getTemplateMetadata(template);
            model.addAttribute(template.name().toLowerCase() + "Metadata", metadata);
        }
        
        return "template/select-simple";
    }
    
    /**
     * Handles template selection and application to a resume.
     * 
     * @param resumeId the ID of the resume to apply template to
     * @param templateType the selected template type
     * @param session HTTP session to get current user
     * @param redirectAttributes attributes for redirect
     * @return redirect to resume view or template selection
     */
    @PostMapping("/apply")
    public String applyTemplate(@RequestParam Long resumeId,
                               @RequestParam TemplateType templateType,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        // Verify user owns the resume
        if (!resumeService.isResumeOwnedByUser(userId, resumeId)) {
            return "redirect:/user/dashboard";
        }
        
        try {
            Resume resume = resumeService.getResumeById(resumeId);
            
            // Update resume with new template type
            resume.setTemplateType(templateType);
            
            // Create ResumeData from existing resume and update with new template
            String xmlContent = resumeService.generateXML(resumeId);
            com.resumebuilder.dto.ResumeData resumeData = 
                resumeService.getXMLProcessingService().parseFromXML(xmlContent);
            resumeData.setTemplateType(templateType);
            
            resumeService.updateResume(resumeId, resumeData);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Template applied successfully!");
            return "redirect:/resume/" + resumeId;
            
        } catch (ResumeNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Resume not found.");
            return "redirect:/user/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Failed to apply template: " + e.getMessage());
            return "redirect:/template/select?resumeId=" + resumeId;
        }
    }
    
    /**
     * API endpoint to get template preview information.
     * 
     * @param templateType the template type to preview
     * @return template metadata as JSON
     */
    @GetMapping("/api/preview/{templateType}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getTemplatePreview(@PathVariable TemplateType templateType) {
        
        if (!templateService.isTemplateSupported(templateType)) {
            return ResponseEntity.badRequest().build();
        }
        
        Map<String, Object> metadata = templateService.getTemplateMetadata(templateType);
        String preview = templateService.previewTemplate(templateType);
        metadata.put("preview", preview);
        
        return ResponseEntity.ok(metadata);
    }
    
    /**
     * Generates and downloads resume as PDF with specified template.
     * 
     * @param resumeId the ID of the resume to generate
     * @param templateType the template type to use (optional, uses resume's current template if not specified)
     * @param session HTTP session to get current user
     * @return PDF file as ResponseEntity or error response
     */
    @GetMapping("/{resumeId}/download/pdf")
    public ResponseEntity<byte[]> downloadPDF(@PathVariable Long resumeId,
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
     * Generates and downloads resume as XML.
     * 
     * @param resumeId the ID of the resume to generate
     * @param session HTTP session to get current user
     * @return XML file as ResponseEntity or error response
     */
    @GetMapping("/{resumeId}/download/xml")
    public ResponseEntity<String> downloadXML(@PathVariable Long resumeId,
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
     * Displays the resume generation page with download options.
     * 
     * @param resumeId the ID of the resume to generate
     * @param session HTTP session to get current user
     * @param model the model for the view
     * @return the generation view name or redirect to dashboard
     */
    @GetMapping("/{resumeId}/generate")
    public String showGenerationPage(@PathVariable Long resumeId,
                                    HttpSession session,
                                    Model model) {
        
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        // Verify user owns the resume
        if (!resumeService.isResumeOwnedByUser(userId, resumeId)) {
            return "redirect:/user/dashboard";
        }
        
        try {
            Resume resume = resumeService.getResumeById(resumeId);
            List<TemplateType> availableTemplates = templateService.getAvailableTemplates();
            
            model.addAttribute("resume", resume);
            model.addAttribute("templates", availableTemplates);
            model.addAttribute("currentTemplate", resume.getTemplateType());
            
            return "template/generate";
            
        } catch (ResumeNotFoundException e) {
            return "redirect:/user/dashboard";
        }
    }
    
    /**
     * Handles resume version creation with template change.
     * 
     * @param resumeId the ID of the original resume
     * @param templateType the template type for the new version
     * @param session HTTP session to get current user
     * @param redirectAttributes attributes for redirect
     * @return redirect to new resume or error page
     */
    @PostMapping("/{resumeId}/create-version")
    public String createResumeVersion(@PathVariable Long resumeId,
                                     @RequestParam TemplateType templateType,
                                     HttpSession session,
                                     RedirectAttributes redirectAttributes) {
        
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        // Verify user owns the resume
        if (!resumeService.isResumeOwnedByUser(userId, resumeId)) {
            return "redirect:/user/dashboard";
        }
        
        try {
            // Create new version of the resume
            Resume newResume = resumeService.createResumeVersion(resumeId);
            
            // Apply new template to the version
            String xmlContent = resumeService.generateXML(newResume.getId());
            com.resumebuilder.dto.ResumeData resumeData = 
                resumeService.getXMLProcessingService().parseFromXML(xmlContent);
            resumeData.setTemplateType(templateType);
            
            resumeService.updateResume(newResume.getId(), resumeData);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "New resume version created with " + templateType.getDisplayName() + " template!");
            return "redirect:/resume/" + newResume.getId();
            
        } catch (ResumeNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Original resume not found.");
            return "redirect:/user/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Failed to create resume version: " + e.getMessage());
            return "redirect:/resume/" + resumeId;
        }
    }
    
    /**
     * API endpoint to validate template compatibility with resume data.
     * 
     * @param resumeId the ID of the resume
     * @param templateType the template type to validate
     * @param session HTTP session to get current user
     * @return validation result as JSON
     */
    @GetMapping("/api/{resumeId}/validate/{templateType}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> validateTemplate(@PathVariable Long resumeId,
                                                               @PathVariable TemplateType templateType,
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
            boolean isSupported = templateService.isTemplateSupported(templateType);
            Resume resume = resumeService.getResumeById(resumeId);
            
            Map<String, Object> result = Map.of(
                "valid", isSupported,
                "templateType", templateType.name(),
                "currentTemplate", resume.getTemplateType().name(),
                "message", isSupported ? "Template is compatible" : "Template not supported"
            );
            
            return ResponseEntity.ok(result);
            
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