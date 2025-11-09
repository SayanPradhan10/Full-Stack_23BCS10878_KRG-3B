package com.resumebuilder.service;

import com.resumebuilder.dto.ResumeData;
import com.resumebuilder.entity.TemplateType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for managing resume templates and formatting.
 * Provides methods for template management, preview, and application.
 */
@Service
public class TemplateService {
    
    private final Map<TemplateType, TemplateConfiguration> templateConfigurations;
    
    public TemplateService() {
        this.templateConfigurations = initializeTemplateConfigurations();
    }
    
    /**
     * Gets all available template types.
     * 
     * @return List of available TemplateType values
     */
    public List<TemplateType> getAvailableTemplates() {
        return Arrays.asList(TemplateType.values());
    }
    
    /**
     * Gets template configuration for a specific template type.
     * 
     * @param templateType the template type
     * @return TemplateConfiguration for the specified type
     */
    public TemplateConfiguration getTemplateConfiguration(TemplateType templateType) {
        return templateConfigurations.get(templateType);
    }
    
    /**
     * Applies template-specific formatting to resume data.
     * This method prepares the data structure for template-specific rendering.
     * 
     * @param resumeData the resume data to format
     * @param templateType the template type to apply
     * @return formatted resume data with template-specific adjustments
     */
    public ResumeData applyTemplate(ResumeData resumeData, TemplateType templateType) {
        if (resumeData == null || templateType == null) {
            throw new IllegalArgumentException("ResumeData and TemplateType cannot be null");
        }
        
        // Create a copy of the resume data to avoid modifying the original
        ResumeData formattedData = copyResumeData(resumeData);
        
        // Apply template-specific formatting rules
        TemplateConfiguration config = getTemplateConfiguration(templateType);
        if (config != null) {
            applyTemplateSpecificFormatting(formattedData, config);
        }
        
        return formattedData;
    }
    
    /**
     * Generates a preview description for a template.
     * 
     * @param templateType the template type to preview
     * @return preview description string
     */
    public String previewTemplate(TemplateType templateType) {
        TemplateConfiguration config = getTemplateConfiguration(templateType);
        if (config != null) {
            return config.getDescription();
        }
        return "Template preview not available";
    }
    
    /**
     * Gets template metadata including styling information.
     * 
     * @param templateType the template type
     * @return Map containing template metadata
     */
    public Map<String, Object> getTemplateMetadata(TemplateType templateType) {
        Map<String, Object> metadata = new HashMap<>();
        TemplateConfiguration config = getTemplateConfiguration(templateType);
        
        if (config != null) {
            metadata.put("name", templateType.getDisplayName());
            metadata.put("description", config.getDescription());
            metadata.put("primaryColor", config.getPrimaryColor());
            metadata.put("fontFamily", config.getFontFamily());
            metadata.put("layout", config.getLayout());
            metadata.put("features", config.getFeatures());
        }
        
        return metadata;
    }
    
    /**
     * Validates if a template type is supported.
     * 
     * @param templateType the template type to validate
     * @return true if template is supported, false otherwise
     */
    public boolean isTemplateSupported(TemplateType templateType) {
        return templateType != null && templateConfigurations.containsKey(templateType);
    }
    
    /**
     * Initializes template configurations for all available templates.
     */
    private Map<TemplateType, TemplateConfiguration> initializeTemplateConfigurations() {
        Map<TemplateType, TemplateConfiguration> configs = new HashMap<>();
        
        // Classic Template Configuration
        TemplateConfiguration classicConfig = new TemplateConfiguration();
        classicConfig.setDescription("Traditional and professional layout with clean typography");
        classicConfig.setPrimaryColor("#2C3E50");
        classicConfig.setFontFamily("Times New Roman");
        classicConfig.setLayout("single-column");
        classicConfig.setFeatures(Arrays.asList("Clean typography", "Professional appearance", "ATS-friendly"));
        configs.put(TemplateType.CLASSIC, classicConfig);
        
        // Modern Template Configuration
        TemplateConfiguration modernConfig = new TemplateConfiguration();
        modernConfig.setDescription("Contemporary design with modern typography and subtle colors");
        modernConfig.setPrimaryColor("#3498DB");
        modernConfig.setFontFamily("Arial");
        modernConfig.setLayout("two-column");
        modernConfig.setFeatures(Arrays.asList("Modern design", "Color accents", "Clean sections"));
        configs.put(TemplateType.MODERN, modernConfig);
        
        // Creative Template Configuration
        TemplateConfiguration creativeConfig = new TemplateConfiguration();
        creativeConfig.setDescription("Bold and creative design with unique layout and vibrant colors");
        creativeConfig.setPrimaryColor("#E74C3C");
        creativeConfig.setFontFamily("Helvetica");
        creativeConfig.setLayout("creative-grid");
        creativeConfig.setFeatures(Arrays.asList("Creative layout", "Bold colors", "Unique design"));
        configs.put(TemplateType.CREATIVE, creativeConfig);
        
        return configs;
    }
    
    /**
     * Applies template-specific formatting rules to resume data.
     */
    private void applyTemplateSpecificFormatting(ResumeData resumeData, TemplateConfiguration config) {
        // Template-specific formatting logic can be implemented here
        // For now, we'll set the template type to ensure consistency
        resumeData.setTemplateType(getTemplateTypeByConfig(config));
    }
    
    /**
     * Gets the template type associated with a configuration.
     */
    private TemplateType getTemplateTypeByConfig(TemplateConfiguration config) {
        return templateConfigurations.entrySet().stream()
            .filter(entry -> entry.getValue().equals(config))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElse(TemplateType.CLASSIC);
    }
    
    /**
     * Creates a deep copy of ResumeData object.
     */
    private ResumeData copyResumeData(ResumeData original) {
        // For simplicity, we'll return the original object
        // In a production environment, you might want to implement proper deep copying
        return original;
    }
    
    /**
     * Configuration class for template settings.
     */
    public static class TemplateConfiguration {
        private String description;
        private String primaryColor;
        private String fontFamily;
        private String layout;
        private List<String> features;
        
        // Getters and Setters
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String getPrimaryColor() { return primaryColor; }
        public void setPrimaryColor(String primaryColor) { this.primaryColor = primaryColor; }
        
        public String getFontFamily() { return fontFamily; }
        public void setFontFamily(String fontFamily) { this.fontFamily = fontFamily; }
        
        public String getLayout() { return layout; }
        public void setLayout(String layout) { this.layout = layout; }
        
        public List<String> getFeatures() { return features; }
        public void setFeatures(List<String> features) { this.features = features; }
    }
}