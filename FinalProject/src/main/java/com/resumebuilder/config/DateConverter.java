package com.resumebuilder.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Custom converter to handle String to LocalDate conversion.
 * Supports multiple date formats commonly used in web forms.
 */
@Component
public class DateConverter implements Converter<String, LocalDate> {
    
    private static final DateTimeFormatter[] FORMATTERS = {
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ofPattern("MM/dd/yyyy"),
        DateTimeFormatter.ofPattern("dd/MM/yyyy"),
        DateTimeFormatter.ofPattern("yyyy/MM/dd")
    };
    
    @Override
    public LocalDate convert(String source) {
        if (source == null || source.trim().isEmpty()) {
            return null;
        }
        
        source = source.trim();
        
        // Try each formatter until one works
        for (DateTimeFormatter formatter : FORMATTERS) {
            try {
                return LocalDate.parse(source, formatter);
            } catch (DateTimeParseException e) {
                // Continue to next formatter
            }
        }
        
        // If no formatter worked, throw an exception with helpful message
        throw new IllegalArgumentException(
            "Unable to parse date: '" + source + "'. " +
            "Supported formats: yyyy-MM-dd, MM/dd/yyyy, dd/MM/yyyy, yyyy/MM/dd"
        );
    }
}