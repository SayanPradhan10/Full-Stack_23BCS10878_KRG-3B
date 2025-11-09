package com.resumebuilder.service;

import com.resumebuilder.dto.ResumeData;
import com.resumebuilder.entity.Resume;
import com.resumebuilder.entity.TemplateType;
import com.resumebuilder.entity.User;
import com.resumebuilder.exception.PDFGenerationException;
import com.resumebuilder.exception.ResumeNotFoundException;
import com.resumebuilder.exception.XMLProcessingException;
import com.resumebuilder.repository.ResumeRepository;
import com.resumebuilder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing resume operations including CRUD, versioning, and generation.
 * Integrates with XMLProcessingService and PDFGenerationService for content generation.
 */
@Service
@Transactional
public class ResumeService {
    
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final XMLProcessingService xmlProcessingService;
    private final PDFGenerationService pdfGenerationService;
    private final TemplateService templateService;
    
    @Autowired
    public ResumeService(ResumeRepository resumeRepository,
                        UserRepository userRepository,
                        XMLProcessingService xmlProcessingService,
                        PDFGenerationService pdfGenerationService,
                        TemplateService templateService) {
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
        this.xmlProcessingService = xmlProcessingService;
        this.pdfGenerationService = pdfGenerationService;
        this.templateService = templateService;
    }
    
    /**
     * Creates a new resume for a user.
     * 
     * @param userId the ID of the user creating the resume
     * @param resumeData the resume data to create
     * @return the created Resume entity
     * @throws IllegalArgumentException if user not found or data is invalid
     */
    public Resume createResume(Long userId, ResumeData resumeData) {
        if (userId == null || resumeData == null) {
            throw new IllegalArgumentException("User ID and resume data cannot be null");
        }
        
        // Validate user exists
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        
        // Validate template type
        if (resumeData.getTemplateType() == null) {
            resumeData.setTemplateType(TemplateType.CLASSIC);
        }
        
        if (!templateService.isTemplateSupported(resumeData.getTemplateType())) {
            throw new IllegalArgumentException("Unsupported template type: " + resumeData.getTemplateType());
        }
        
        // Create resume entity
        Resume resume = new Resume();
        resume.setUser(user);
        resume.setTitle(resumeData.getTitle() != null ? resumeData.getTitle() : "My Resume");
        resume.setTemplateType(resumeData.getTemplateType());
        resume.setCreatedDate(LocalDateTime.now());
        resume.setLastModified(LocalDateTime.now());
        
        // Generate and store XML content
        try {
            String xmlContent = xmlProcessingService.convertToXML(resumeData);
            resume.setXmlContent(xmlContent);
        } catch (XMLProcessingException e) {
            throw new IllegalArgumentException("Failed to process resume data", e);
        }
        
        // Save and return the resume
        return resumeRepository.save(resume);
    }
    
    /**
     * Updates an existing resume.
     * 
     * @param resumeId the ID of the resume to update
     * @param resumeData the updated resume data
     * @return the updated Resume entity
     * @throws ResumeNotFoundException if resume not found
     */
    public Resume updateResume(Long resumeId, ResumeData resumeData) {
        if (resumeId == null || resumeData == null) {
            throw new IllegalArgumentException("Resume ID and resume data cannot be null");
        }
        
        Resume resume = resumeRepository.findById(resumeId)
            .orElseThrow(() -> new ResumeNotFoundException(resumeId));
        
        // Update basic information
        if (resumeData.getTitle() != null) {
            resume.setTitle(resumeData.getTitle());
        }
        
        if (resumeData.getTemplateType() != null) {
            if (!templateService.isTemplateSupported(resumeData.getTemplateType())) {
                throw new IllegalArgumentException("Unsupported template type: " + resumeData.getTemplateType());
            }
            resume.setTemplateType(resumeData.getTemplateType());
        }
        
        // Update XML content
        try {
            String xmlContent = xmlProcessingService.convertToXML(resumeData);
            resume.setXmlContent(xmlContent);
        } catch (XMLProcessingException e) {
            throw new IllegalArgumentException("Failed to process updated resume data", e);
        }
        
        // Update timestamp
        resume.setLastModified(LocalDateTime.now());
        
        return resumeRepository.save(resume);
    }
    
    /**
     * Retrieves a resume by ID.
     * 
     * @param resumeId the ID of the resume to retrieve
     * @return the Resume entity
     * @throws ResumeNotFoundException if resume not found
     */
    @Transactional(readOnly = true)
    public Resume getResumeById(Long resumeId) {
        if (resumeId == null) {
            throw new IllegalArgumentException("Resume ID cannot be null");
        }
        
        return resumeRepository.findById(resumeId)
            .orElseThrow(() -> new ResumeNotFoundException(resumeId));
    }
    
    /**
     * Retrieves all resumes for a user.
     * 
     * @param userId the ID of the user
     * @return list of resumes ordered by creation date (newest first)
     */
    @Transactional(readOnly = true)
    public List<Resume> getResumesByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        
        return resumeRepository.findByUserIdOrderByCreatedDateDesc(userId);
    }
    
    /**
     * Deletes a resume.
     * 
     * @param resumeId the ID of the resume to delete
     * @throws ResumeNotFoundException if resume not found
     */
    public void deleteResume(Long resumeId) {
        if (resumeId == null) {
            throw new IllegalArgumentException("Resume ID cannot be null");
        }
        
        Resume resume = resumeRepository.findById(resumeId)
            .orElseThrow(() -> new ResumeNotFoundException(resumeId));
        
        resumeRepository.delete(resume);
    }
    
    /**
     * Creates a new version of an existing resume.
     * 
     * @param resumeId the ID of the original resume
     * @return the new resume version
     * @throws ResumeNotFoundException if original resume not found
     */
    public Resume createResumeVersion(Long resumeId) {
        if (resumeId == null) {
            throw new IllegalArgumentException("Resume ID cannot be null");
        }
        
        Resume originalResume = resumeRepository.findById(resumeId)
            .orElseThrow(() -> new ResumeNotFoundException(resumeId));
        
        // Create new resume as a copy
        Resume newVersion = new Resume();
        newVersion.setUser(originalResume.getUser());
        newVersion.setTitle(originalResume.getTitle() + " (Copy)");
        newVersion.setTemplateType(originalResume.getTemplateType());
        newVersion.setXmlContent(originalResume.getXmlContent());
        newVersion.setCreatedDate(LocalDateTime.now());
        newVersion.setLastModified(LocalDateTime.now());
        
        return resumeRepository.save(newVersion);
    }
    
    /**
     * Generates PDF for a resume.
     * 
     * @param resumeId the ID of the resume
     * @return byte array containing the PDF
     * @throws ResumeNotFoundException if resume not found
     * @throws PDFGenerationException if PDF generation fails
     */
    @Transactional(readOnly = true)
    public byte[] generatePDF(Long resumeId) {
        if (resumeId == null) {
            throw new IllegalArgumentException("Resume ID cannot be null");
        }
        
        Resume resume = resumeRepository.findById(resumeId)
            .orElseThrow(() -> new ResumeNotFoundException(resumeId));
        
        return generatePDF(resumeId, resume.getTemplateType());
    }
    
    /**
     * Generates PDF for a resume with a specific template.
     * 
     * @param resumeId the ID of the resume
     * @param templateType the template type to use
     * @return byte array containing the PDF
     * @throws ResumeNotFoundException if resume not found
     * @throws PDFGenerationException if PDF generation fails
     */
    @Transactional(readOnly = true)
    public byte[] generatePDF(Long resumeId, TemplateType templateType) {
        if (resumeId == null || templateType == null) {
            throw new IllegalArgumentException("Resume ID and template type cannot be null");
        }
        
        Resume resume = resumeRepository.findById(resumeId)
            .orElseThrow(() -> new ResumeNotFoundException(resumeId));
        
        if (!templateService.isTemplateSupported(templateType)) {
            throw new IllegalArgumentException("Unsupported template type: " + templateType);
        }
        
        try {
            // Parse XML content to ResumeData
            ResumeData resumeData;
            if (resume.getXmlContent() != null && !resume.getXmlContent().trim().isEmpty()) {
                resumeData = xmlProcessingService.parseFromXML(resume.getXmlContent());
            } else {
                // Create ResumeData from entity if no XML content exists
                resumeData = xmlProcessingService.parseFromXML(xmlProcessingService.convertToXML(resume));
            }
            
            // Apply template and generate PDF
            ResumeData formattedData = templateService.applyTemplate(resumeData, templateType);
            return pdfGenerationService.generatePDF(formattedData, templateType);
            
        } catch (XMLProcessingException e) {
            throw new PDFGenerationException("Failed to process resume XML content", e);
        }
    }
    
    /**
     * Generates XML for a resume.
     * 
     * @param resumeId the ID of the resume
     * @return XML string representation of the resume
     * @throws ResumeNotFoundException if resume not found
     * @throws XMLProcessingException if XML generation fails
     */
    @Transactional(readOnly = true)
    public String generateXML(Long resumeId) {
        if (resumeId == null) {
            throw new IllegalArgumentException("Resume ID cannot be null");
        }
        
        Resume resume = resumeRepository.findById(resumeId)
            .orElseThrow(() -> new ResumeNotFoundException(resumeId));
        
        // Return existing XML content if available, otherwise generate new
        if (resume.getXmlContent() != null && !resume.getXmlContent().trim().isEmpty()) {
            return resume.getXmlContent();
        }
        
        try {
            return xmlProcessingService.convertToXML(resume);
        } catch (XMLProcessingException e) {
            throw new XMLProcessingException("Failed to generate XML for resume", e);
        }
    }
    
    /**
     * Validates resume data for business rules.
     * 
     * @param resumeData the resume data to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateResumeData(ResumeData resumeData) {
        if (resumeData.getTitle() == null || resumeData.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Resume title is required");
        }
        
        if (resumeData.getTitle().length() > 255) {
            throw new IllegalArgumentException("Resume title cannot exceed 255 characters");
        }
        
        if (resumeData.getPersonalInfo() == null) {
            throw new IllegalArgumentException("Personal information is required");
        }
        
        ResumeData.PersonalInfo personalInfo = resumeData.getPersonalInfo();
        if (personalInfo.getName() == null || personalInfo.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required in personal information");
        }
        
        if (personalInfo.getEmail() == null || personalInfo.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required in personal information");
        }
    }
    
    /**
     * Checks if a user owns a specific resume.
     * 
     * @param userId the user ID
     * @param resumeId the resume ID
     * @return true if user owns the resume, false otherwise
     */
    @Transactional(readOnly = true)
    public boolean isResumeOwnedByUser(Long userId, Long resumeId) {
        if (userId == null || resumeId == null) {
            return false;
        }
        
        Optional<Resume> resume = resumeRepository.findById(resumeId);
        return resume.isPresent() && resume.get().getUser().getId().equals(userId);
    }
    
    /**
     * Gets the XMLProcessingService instance for use by controllers.
     * 
     * @return the XMLProcessingService instance
     */
    public XMLProcessingService getXMLProcessingService() {
        return xmlProcessingService;
    }
}