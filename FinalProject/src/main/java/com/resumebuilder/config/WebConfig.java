package com.resumebuilder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter;

/**
 * Web configuration for handling date formatting and conversion.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Autowired
    private DateConverter dateConverter;
    
    /**
     * Configure formatters for date/time conversion.
     * This handles the conversion between String and LocalDate/LocalDateTime.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // Register custom date converter
        registry.addConverter(dateConverter);
        
        // Register standard date/time formatters
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        registrar.registerFormatters(registry);
    }
}