package com.resumebuilder.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Validator implementation for ValidGPA annotation.
 * Validates GPA values for range and decimal precision.
 */
public class GPAValidator implements ConstraintValidator<ValidGPA, Double> {
    
    private double min;
    private double max;
    private int decimalPlaces;
    
    @Override
    public void initialize(ValidGPA constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.decimalPlaces = constraintAnnotation.decimalPlaces();
    }
    
    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle null validation if required
        }
        
        // Check range
        if (value < min || value > max) {
            return false;
        }
        
        // Check decimal places
        BigDecimal bd = BigDecimal.valueOf(value);
        int actualDecimalPlaces = bd.scale();
        
        // If the number has more decimal places than allowed, check if it can be rounded
        if (actualDecimalPlaces > decimalPlaces) {
            BigDecimal rounded = bd.setScale(decimalPlaces, RoundingMode.HALF_UP);
            return rounded.doubleValue() == value;
        }
        
        return true;
    }
}