package com.resumebuilder.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom validation annotation for GPA values.
 * Validates that GPA is within acceptable range and has proper decimal places.
 */
@Documented
@Constraint(validatedBy = GPAValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGPA {
    
    String message() default "GPA must be between 0.0 and 4.0 with at most 2 decimal places";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
    /**
     * Minimum GPA value (default: 0.0)
     */
    double min() default 0.0;
    
    /**
     * Maximum GPA value (default: 4.0)
     */
    double max() default 4.0;
    
    /**
     * Maximum number of decimal places (default: 2)
     */
    int decimalPlaces() default 2;
}