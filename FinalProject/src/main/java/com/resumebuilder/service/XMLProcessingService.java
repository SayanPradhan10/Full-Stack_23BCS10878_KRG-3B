package com.resumebuilder.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.resumebuilder.dto.ResumeData;
import com.resumebuilder.entity.*;
import com.resumebuilder.exception.XMLProcessingException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for handling XML processing operations.
 * Provides methods for converting ResumeData to XML format and parsing XML back to objects.
 */
@Service
public class XMLProcessingService {
    
    private final XmlMapper xmlMapper;
    
    public XMLProcessingService() {
        this.xmlMapper = new XmlMapper();
        this.xmlMapper.registerModule(new JavaTimeModule());
        this.xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    /**
     * Converts a Resume entity to XML format.
     * 
     * @param resume the Resume entity to convert
     * @return XML string representation of the resume data
     * @throws XMLProcessingException if XML conversion fails
     */
    public String convertToXML(Resume resume) {
        if (resume == null) {
            throw new XMLProcessingException("Resume cannot be null");
        }
        
        try {
            ResumeData resumeData = convertResumeToResumeData(resume);
            return xmlMapper.writeValueAsString(resumeData);
        } catch (JsonProcessingException e) {
            throw new XMLProcessingException("Failed to convert resume to XML", e);
        }
    }
    
    /**
     * Converts ResumeData object to XML format.
     * 
     * @param resumeData the ResumeData object to convert
     * @return XML string representation of the resume data
     * @throws XMLProcessingException if XML conversion fails
     */
    public String convertToXML(ResumeData resumeData) {
        if (resumeData == null) {
            throw new XMLProcessingException("ResumeData cannot be null");
        }
        
        try {
            return xmlMapper.writeValueAsString(resumeData);
        } catch (JsonProcessingException e) {
            throw new XMLProcessingException("Failed to convert ResumeData to XML", e);
        }
    }
    
    /**
     * Parses XML content back to ResumeData object.
     * 
     * @param xmlContent the XML string to parse
     * @return ResumeData object parsed from XML
     * @throws XMLProcessingException if XML parsing fails
     */
    public ResumeData parseFromXML(String xmlContent) {
        if (xmlContent == null || xmlContent.trim().isEmpty()) {
            throw new XMLProcessingException("XML content cannot be null or empty");
        }
        
        try {
            return xmlMapper.readValue(xmlContent, ResumeData.class);
        } catch (IOException e) {
            throw new XMLProcessingException("Failed to parse XML content", e);
        }
    }
    
    /**
     * Validates XML structure by attempting to parse it.
     * 
     * @param xmlContent the XML string to validate
     * @return true if XML is valid, false otherwise
     */
    public boolean validateXMLStructure(String xmlContent) {
        try {
            parseFromXML(xmlContent);
            return true;
        } catch (XMLProcessingException e) {
            return false;
        }
    }
    
    /**
     * Converts a Resume entity to ResumeData DTO.
     * 
     * @param resume the Resume entity to convert
     * @return ResumeData DTO
     */
    private ResumeData convertResumeToResumeData(Resume resume) {
        ResumeData resumeData = new ResumeData();
        
        // Basic resume information
        resumeData.setId(resume.getId());
        resumeData.setTitle(resume.getTitle());
        resumeData.setTemplateType(resume.getTemplateType());
        resumeData.setCreatedDate(resume.getCreatedDate());
        resumeData.setLastModified(resume.getLastModified());
        
        // Personal information from User entity
        if (resume.getUser() != null) {
            User user = resume.getUser();
            ResumeData.PersonalInfo personalInfo = new ResumeData.PersonalInfo(
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress()
            );
            resumeData.setPersonalInfo(personalInfo);
        }
        
        // Education information
        if (resume.getEducations() != null) {
            List<ResumeData.EducationData> educationDataList = resume.getEducations().stream()
                .map(this::convertEducationToEducationData)
                .collect(Collectors.toList());
            resumeData.setEducations(educationDataList);
        }
        
        // Work experience information
        if (resume.getWorkExperiences() != null) {
            List<ResumeData.WorkExperienceData> workExperienceDataList = resume.getWorkExperiences().stream()
                .map(this::convertWorkExperienceToWorkExperienceData)
                .collect(Collectors.toList());
            resumeData.setWorkExperiences(workExperienceDataList);
        }
        
        // Skills information
        if (resume.getSkills() != null) {
            List<ResumeData.SkillData> skillDataList = resume.getSkills().stream()
                .map(this::convertSkillToSkillData)
                .collect(Collectors.toList());
            resumeData.setSkills(skillDataList);
        }
        
        return resumeData;
    }
    
    /**
     * Converts Education entity to EducationData DTO.
     */
    private ResumeData.EducationData convertEducationToEducationData(Education education) {
        ResumeData.EducationData educationData = new ResumeData.EducationData();
        educationData.setInstitution(education.getInstitution());
        educationData.setDegree(education.getDegree());
        educationData.setFieldOfStudy(education.getFieldOfStudy());
        educationData.setStartDate(education.getStartDate());
        educationData.setEndDate(education.getEndDate());
        educationData.setGpa(education.getGpa());
        return educationData;
    }
    
    /**
     * Converts WorkExperience entity to WorkExperienceData DTO.
     */
    private ResumeData.WorkExperienceData convertWorkExperienceToWorkExperienceData(WorkExperience workExperience) {
        ResumeData.WorkExperienceData workExperienceData = new ResumeData.WorkExperienceData();
        workExperienceData.setCompany(workExperience.getCompany());
        workExperienceData.setPosition(workExperience.getPosition());
        workExperienceData.setStartDate(workExperience.getStartDate());
        workExperienceData.setEndDate(workExperience.getEndDate());
        workExperienceData.setDescription(workExperience.getDescription());
        workExperienceData.setIsInternship(workExperience.getIsInternship());
        return workExperienceData;
    }
    
    /**
     * Converts Skill entity to SkillData DTO.
     */
    private ResumeData.SkillData convertSkillToSkillData(Skill skill) {
        ResumeData.SkillData skillData = new ResumeData.SkillData();
        skillData.setName(skill.getName());
        skillData.setType(skill.getType() != null ? skill.getType().name() : null);
        skillData.setProficiencyLevel(skill.getProficiencyLevel());
        return skillData;
    }
}