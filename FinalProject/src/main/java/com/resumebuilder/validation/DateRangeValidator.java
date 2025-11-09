package com.resumebuilder.validation;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * Validator implementation for ValidDateRange annotation.
 * Validates that start date is before end date in objects with date ranges.
 */
public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {
    
    private String startDateField;
    private String endDateField;
    private boolean allowNullEndDate;
    
    @Override
    public void initialize(ValidDateRange constraintAnnotation) {
        this.startDateField = constraintAnnotation.startDateField();
        this.endDateField = constraintAnnotation.endDateField();
        this.allowNullEndDate = constraintAnnotation.allowNullEndDate();
    }
    
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle null validation
        }
        
        try {
            BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);
            
            LocalDate startDate = (LocalDate) beanWrapper.getPropertyValue(startDateField);
            LocalDate endDate = (LocalDate) beanWrapper.getPropertyValue(endDateField);
            
            // If start date is null, we can't validate the range
            if (startDate == null) {
                return true;
            }
            
            // If end date is null and we allow it (for current positions), it's valid
            if (endDate == null && allowNullEndDate) {
                return true;
            }
            
            // If end date is null but we don't allow it, it's invalid
            if (endDate == null && !allowNullEndDate) {
                return false;
            }
            
            // Both dates are present, check if start is before end
            return startDate.isBefore(endDate) || startDate.isEqual(endDate);
            
        } catch (Exception e) {
            // If we can't access the properties, assume it's valid
            // and let other validators handle the specific field validation
            return true;
        }
    }
}