package com.resumebuilder.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.concurrent.Executor;

/**
 * Main application configuration class.
 * Enables configuration properties and sets up application-wide beans.
 */
@Configuration
@EnableConfigurationProperties({FileStorageConfig.class, PDFConfig.class})
@EnableAsync
public class ApplicationConfig {

    /**
     * Initialize application directories and settings on startup.
     */
    @PostConstruct
    public void init() {
        // Create logs directory
        createDirectoryIfNotExists("./logs");
        
        System.out.println("Resume Builder Application Configuration Initialized");
    }

    /**
     * Task executor for asynchronous operations like PDF generation.
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("ResumeBuilder-");
        executor.initialize();
        return executor;
    }

    /**
     * Development-specific task executor with more verbose logging.
     */
    @Bean(name = "devTaskExecutor")
    @Profile("dev")
    public Executor devTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("ResumeBuilder-Dev-");
        executor.initialize();
        return executor;
    }

    /**
     * Production-specific task executor with higher capacity.
     */
    @Bean(name = "prodTaskExecutor")
    @Profile("prod")
    public Executor prodTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("ResumeBuilder-Prod-");
        executor.initialize();
        return executor;
    }

    private void createDirectoryIfNotExists(String directory) {
        File dir = new File(directory);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (created) {
                System.out.println("Created directory: " + dir.getAbsolutePath());
            }
        }
    }
}