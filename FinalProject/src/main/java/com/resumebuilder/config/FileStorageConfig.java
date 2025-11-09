package com.resumebuilder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Configuration class for file storage settings.
 * Manages directories for PDF, XML, and temporary file storage.
 */
@Configuration
@ConfigurationProperties(prefix = "app.file")
public class FileStorageConfig {

    private String uploadDir = "./uploads";
    private String pdfDir = "./uploads/pdfs";
    private String xmlDir = "./uploads/xml";
    private String tempDir = "./uploads/temp";

    /**
     * Initialize storage directories on application startup.
     * Creates directories if they don't exist.
     */
    @PostConstruct
    public void init() {
        createDirectoryIfNotExists(uploadDir);
        createDirectoryIfNotExists(pdfDir);
        createDirectoryIfNotExists(xmlDir);
        createDirectoryIfNotExists(tempDir);
    }

    private void createDirectoryIfNotExists(String directory) {
        try {
            Path path = Paths.get(directory);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                System.out.println("Created directory: " + path.toAbsolutePath());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create directory: " + directory, e);
        }
    }

    // Getters and Setters
    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getPdfDir() {
        return pdfDir;
    }

    public void setPdfDir(String pdfDir) {
        this.pdfDir = pdfDir;
    }

    public String getXmlDir() {
        return xmlDir;
    }

    public void setXmlDir(String xmlDir) {
        this.xmlDir = xmlDir;
    }

    public String getTempDir() {
        return tempDir;
    }

    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }

    /**
     * Get the absolute path for the upload directory.
     */
    public Path getUploadPath() {
        return Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    /**
     * Get the absolute path for the PDF directory.
     */
    public Path getPdfPath() {
        return Paths.get(pdfDir).toAbsolutePath().normalize();
    }

    /**
     * Get the absolute path for the XML directory.
     */
    public Path getXmlPath() {
        return Paths.get(xmlDir).toAbsolutePath().normalize();
    }

    /**
     * Get the absolute path for the temporary directory.
     */
    public Path getTempPath() {
        return Paths.get(tempDir).toAbsolutePath().normalize();
    }
}