package com.resumebuilder.service;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.resumebuilder.dto.ResumeData;
import com.resumebuilder.entity.TemplateType;
import com.resumebuilder.exception.PDFGenerationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Service for generating PDF documents from resume data.
 * Supports multiple template styles with proper error handling and performance requirements.
 */
@Service
public class PDFGenerationService {
    
    private final TemplateService templateService;
    private static final int PDF_GENERATION_TIMEOUT_SECONDS = 10;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM yyyy");
    
    @Autowired
    public PDFGenerationService(TemplateService templateService) {
        this.templateService = templateService;
    }
    
    /**
     * Generates a PDF document from resume data using the specified template.
     * Ensures generation completes within 10 seconds as per requirements.
     * 
     * @param resumeData the resume data to convert to PDF
     * @param templateType the template style to apply
     * @return byte array containing the generated PDF
     * @throws PDFGenerationException if PDF generation fails or times out
     */
    public byte[] generatePDF(ResumeData resumeData, TemplateType templateType) {
        if (resumeData == null) {
            throw new PDFGenerationException("Resume data cannot be null");
        }
        
        if (templateType == null) {
            throw new PDFGenerationException("Template type cannot be null");
        }
        
        try {
            // Use CompletableFuture to enforce timeout requirement
            CompletableFuture<byte[]> pdfGeneration = CompletableFuture.supplyAsync(() -> {
                try {
                    return generatePDFInternal(resumeData, templateType);
                } catch (Exception e) {
                    throw new RuntimeException("PDF generation failed", e);
                }
            });
            
            return pdfGeneration.get(PDF_GENERATION_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            
        } catch (TimeoutException e) {
            throw new PDFGenerationException("PDF generation timed out after " + PDF_GENERATION_TIMEOUT_SECONDS + " seconds");
        } catch (Exception e) {
            throw new PDFGenerationException("Failed to generate PDF", e);
        }
    }
    
    /**
     * Internal method for PDF generation logic.
     */
    private byte[] generatePDFInternal(ResumeData resumeData, TemplateType templateType) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);
            
            // Apply template-specific formatting
            applyTemplateFormatting(document, templateType);
            
            // Generate content based on template type
            switch (templateType) {
                case CLASSIC:
                    generateClassicTemplate(document, resumeData);
                    break;
                case MODERN:
                    generateModernTemplate(document, resumeData);
                    break;
                case CREATIVE:
                    generateCreativeTemplate(document, resumeData);
                    break;
                default:
                    generateClassicTemplate(document, resumeData);
            }
            
            document.close();
            return baos.toByteArray();
            
        } catch (Exception e) {
            throw new PDFGenerationException("Error during PDF generation", e);
        }
    }
    
    /**
     * Applies template-specific formatting to the PDF document.
     */
    private void applyTemplateFormatting(Document document, TemplateType templateType) {
        TemplateService.TemplateConfiguration config = templateService.getTemplateConfiguration(templateType);
        
        if (config != null) {
            // Set margins and spacing based on template
            switch (templateType) {
                case CLASSIC:
                    document.setMargins(50, 50, 50, 50);
                    break;
                case MODERN:
                    document.setMargins(40, 40, 40, 40);
                    break;
                case CREATIVE:
                    document.setMargins(30, 30, 30, 30);
                    break;
            }
        }
    }
    
    /**
     * Generates PDF content using Classic template style.
     */
    private void generateClassicTemplate(Document document, ResumeData resumeData) {
        // Header with personal information
        if (resumeData.getPersonalInfo() != null) {
            ResumeData.PersonalInfo personalInfo = resumeData.getPersonalInfo();
            
            // Name as title
            Paragraph name = new Paragraph(personalInfo.getName())
                .setFontSize(20)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(5);
            document.add(name);
            
            // Contact information
            StringBuilder contactInfo = new StringBuilder();
            if (personalInfo.getEmail() != null) {
                contactInfo.append(personalInfo.getEmail());
            }
            if (personalInfo.getPhone() != null) {
                if (contactInfo.length() > 0) contactInfo.append(" | ");
                contactInfo.append(personalInfo.getPhone());
            }
            if (personalInfo.getAddress() != null) {
                if (contactInfo.length() > 0) contactInfo.append(" | ");
                contactInfo.append(personalInfo.getAddress());
            }
            
            Paragraph contact = new Paragraph(contactInfo.toString())
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
            document.add(contact);
        }
        
        // Add separator line
        document.add(new LineSeparator(new SolidLine()).setMarginBottom(15));
        
        // Education section
        addEducationSection(document, resumeData, TemplateType.CLASSIC);
        
        // Work Experience section
        addWorkExperienceSection(document, resumeData, TemplateType.CLASSIC);
        
        // Skills section
        addSkillsSection(document, resumeData, TemplateType.CLASSIC);
    }
    
    /**
     * Generates PDF content using Modern template style.
     */
    private void generateModernTemplate(Document document, ResumeData resumeData) {
        Color modernBlue = new DeviceRgb(52, 152, 219);
        
        // Header with personal information
        if (resumeData.getPersonalInfo() != null) {
            ResumeData.PersonalInfo personalInfo = resumeData.getPersonalInfo();
            
            // Name with modern styling
            Paragraph name = new Paragraph(personalInfo.getName())
                .setFontSize(24)
                .setBold()
                .setFontColor(modernBlue)
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginBottom(5);
            document.add(name);
            
            // Contact information with modern layout
            if (personalInfo.getEmail() != null) {
                document.add(new Paragraph("Email: " + personalInfo.getEmail()).setFontSize(10));
            }
            if (personalInfo.getPhone() != null) {
                document.add(new Paragraph("Phone: " + personalInfo.getPhone()).setFontSize(10));
            }
            if (personalInfo.getAddress() != null) {
                document.add(new Paragraph("Address: " + personalInfo.getAddress()).setFontSize(10));
            }
            
            document.add(new Paragraph().setMarginBottom(15));
        }
        
        // Education section with modern styling
        addEducationSection(document, resumeData, TemplateType.MODERN);
        
        // Work Experience section with modern styling
        addWorkExperienceSection(document, resumeData, TemplateType.MODERN);
        
        // Skills section with modern styling
        addSkillsSection(document, resumeData, TemplateType.MODERN);
    }
    
    /**
     * Generates PDF content using Creative template style.
     */
    private void generateCreativeTemplate(Document document, ResumeData resumeData) {
        Color creativeRed = new DeviceRgb(231, 76, 60);
        
        // Header with creative styling
        if (resumeData.getPersonalInfo() != null) {
            ResumeData.PersonalInfo personalInfo = resumeData.getPersonalInfo();
            
            // Name with creative styling
            Paragraph name = new Paragraph(personalInfo.getName())
                .setFontSize(28)
                .setBold()
                .setFontColor(creativeRed)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);
            document.add(name);
            
            // Creative contact layout
            StringBuilder contactInfo = new StringBuilder();
            if (personalInfo.getEmail() != null) {
                contactInfo.append("✉ ").append(personalInfo.getEmail());
            }
            if (personalInfo.getPhone() != null) {
                if (contactInfo.length() > 0) contactInfo.append("  ");
                contactInfo.append("📞 ").append(personalInfo.getPhone());
            }
            
            Paragraph contact = new Paragraph(contactInfo.toString())
                .setFontSize(11)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
            document.add(contact);
        }
        
        // Education section with creative styling
        addEducationSection(document, resumeData, TemplateType.CREATIVE);
        
        // Work Experience section with creative styling
        addWorkExperienceSection(document, resumeData, TemplateType.CREATIVE);
        
        // Skills section with creative styling
        addSkillsSection(document, resumeData, TemplateType.CREATIVE);
    }
    
    /**
     * Adds education section to the document.
     */
    private void addEducationSection(Document document, ResumeData resumeData, TemplateType templateType) {
        if (resumeData.getEducations() == null || resumeData.getEducations().isEmpty()) {
            return;
        }
        
        Color sectionColor = getSectionColor(templateType);
        
        // Section header
        Paragraph educationHeader = new Paragraph("EDUCATION")
            .setFontSize(14)
            .setBold()
            .setFontColor(sectionColor)
            .setMarginBottom(10);
        document.add(educationHeader);
        
        // Education entries
        for (ResumeData.EducationData education : resumeData.getEducations()) {
            Paragraph institution = new Paragraph()
                .add(new Text(education.getInstitution()).setBold())
                .add(new Text(" - " + education.getDegree()))
                .setMarginBottom(2);
            document.add(institution);
            
            if (education.getFieldOfStudy() != null) {
                document.add(new Paragraph("Field of Study: " + education.getFieldOfStudy())
                    .setFontSize(10)
                    .setMarginBottom(2));
            }
            
            if (education.getGpa() != null) {
                document.add(new Paragraph("GPA: " + education.getGpa())
                    .setFontSize(10)
                    .setMarginBottom(8));
            }
        }
        
        document.add(new Paragraph().setMarginBottom(10));
    }
    
    /**
     * Adds work experience section to the document.
     */
    private void addWorkExperienceSection(Document document, ResumeData resumeData, TemplateType templateType) {
        if (resumeData.getWorkExperiences() == null || resumeData.getWorkExperiences().isEmpty()) {
            return;
        }
        
        Color sectionColor = getSectionColor(templateType);
        
        // Section header
        Paragraph workHeader = new Paragraph("WORK EXPERIENCE")
            .setFontSize(14)
            .setBold()
            .setFontColor(sectionColor)
            .setMarginBottom(10);
        document.add(workHeader);
        
        // Work experience entries
        for (ResumeData.WorkExperienceData work : resumeData.getWorkExperiences()) {
            Paragraph position = new Paragraph()
                .add(new Text(work.getPosition()).setBold())
                .add(new Text(" at " + work.getCompany()))
                .setMarginBottom(2);
            document.add(position);
            
            if (work.getDescription() != null) {
                document.add(new Paragraph(work.getDescription())
                    .setFontSize(10)
                    .setMarginBottom(8));
            }
        }
        
        document.add(new Paragraph().setMarginBottom(10));
    }
    
    /**
     * Adds skills section to the document.
     */
    private void addSkillsSection(Document document, ResumeData resumeData, TemplateType templateType) {
        if (resumeData.getSkills() == null || resumeData.getSkills().isEmpty()) {
            return;
        }
        
        Color sectionColor = getSectionColor(templateType);
        
        // Section header
        Paragraph skillsHeader = new Paragraph("SKILLS")
            .setFontSize(14)
            .setBold()
            .setFontColor(sectionColor)
            .setMarginBottom(10);
        document.add(skillsHeader);
        
        // Skills entries
        StringBuilder skillsList = new StringBuilder();
        for (int i = 0; i < resumeData.getSkills().size(); i++) {
            ResumeData.SkillData skill = resumeData.getSkills().get(i);
            skillsList.append(skill.getName());
            if (i < resumeData.getSkills().size() - 1) {
                skillsList.append(", ");
            }
        }
        
        document.add(new Paragraph(skillsList.toString())
            .setFontSize(11)
            .setMarginBottom(10));
    }
    
    /**
     * Gets the appropriate section color for the template type.
     */
    private Color getSectionColor(TemplateType templateType) {
        switch (templateType) {
            case MODERN:
                return new DeviceRgb(52, 152, 219);
            case CREATIVE:
                return new DeviceRgb(231, 76, 60);
            case CLASSIC:
            default:
                return new DeviceRgb(44, 62, 80);
        }
    }
}