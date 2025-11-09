package com.resumebuilder.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom validation annotation to ensure that start date is before end date.
 * Can be applied to classes that have startDate and endDate fields.
 */
@Documented
@Constraint(validatedBy = DateRangeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRange {
    
    String message() default "Start date must be before end date";
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default {};
    
    /**
     * Name of the start date field (default: "startDate")
     */
    String startDateField() default "startDate";
    
    /**
     * Name of the end date field (default: "endDate")
     */
    String endDateField() default "endDate";
    
    /**
     * Whether to allow null end date (for current positions/education)
     */
    boolean allowNullEndDate() default true;
}