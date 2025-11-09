/**
 * Client-side validation helper functions for Resume Builder forms.
 * Provides real-time validation feedback and form submission handling.
 */

// Validation utility object
const ValidationUtils = {
    
    /**
     * Initialize validation for a form
     * @param {string} formSelector - CSS selector for the form
     */
    initializeForm: function(formSelector) {
        const form = document.querySelector(formSelector);
        if (!form) return;
        
        // Add real-time validation to all input fields
        const inputs = form.querySelectorAll('input, textarea, select');
        inputs.forEach(input => {
            this.addFieldValidation(input);
        });
        
        // Handle form submission
        form.addEventListener('submit', (e) => {
            if (!this.validateForm(form)) {
                e.preventDefault();
                this.showFormError('Please correct the errors below before submitting.');
            }
        });
    },
    
    /**
     * Add real-time validation to a specific field
     * @param {HTMLElement} field - The input field element
     */
    addFieldValidation: function(field) {
        // Validate on blur (when user leaves the field)
        field.addEventListener('blur', () => {
            this.validateField(field);
        });
        
        // Clear validation state on input (when user starts typing)
        field.addEventListener('input', () => {
            this.clearFieldError(field);
        });
    },
    
    /**
     * Validate a single field
     * @param {HTMLElement} field - The input field element
     * @returns {boolean} - True if valid, false otherwise
     */
    validateField: function(field) {
        const value = field.value.trim();
        const fieldName = field.name;
        let isValid = true;
        let errorMessage = '';
        
        // Check required fields
        if (field.hasAttribute('required') && !value) {
            isValid = false;
            errorMessage = 'This field is required';
        }
        
        // Email validation
        else if (field.type === 'email' && value) {
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(value)) {
                isValid = false;
                errorMessage = 'Please enter a valid email address';
            }
        }
        
        // Length validation
        else if (field.hasAttribute('maxlength')) {
            const maxLength = parseInt(field.getAttribute('maxlength'));
            if (value.length > maxLength) {
                isValid = false;
                errorMessage = `Maximum ${maxLength} characters allowed`;
            }
        }
        
        // GPA validation
        else if (fieldName === 'gpa' && value) {
            const gpa = parseFloat(value);
            if (isNaN(gpa) || gpa < 0 || gpa > 4.0) {
                isValid = false;
                errorMessage = 'GPA must be between 0.0 and 4.0';
            }
        }
        
        // Date range validation for education and work experience
        else if (fieldName === 'endDate') {
            const startDateField = field.form.querySelector('[name="startDate"]');
            if (startDateField && startDateField.value && value) {
                const startDate = new Date(startDateField.value);
                const endDate = new Date(value);
                if (endDate < startDate) {
                    isValid = false;
                    errorMessage = 'End date must be after start date';
                }
            }
        }
        
        // Show or hide error message
        if (isValid) {
            this.clearFieldError(field);
        } else {
            this.showFieldError(field, errorMessage);
        }
        
        return isValid;
    },
    
    /**
     * Validate entire form
     * @param {HTMLElement} form - The form element
     * @returns {boolean} - True if all fields are valid, false otherwise
     */
    validateForm: function(form) {
        const fields = form.querySelectorAll('input, textarea, select');
        let isFormValid = true;
        
        fields.forEach(field => {
            if (!this.validateField(field)) {
                isFormValid = false;
            }
        });
        
        return isFormValid;
    },
    
    /**
     * Show error message for a field
     * @param {HTMLElement} field - The input field element
     * @param {string} message - Error message to display
     */
    showFieldError: function(field, message) {
        // Add error class to field
        field.classList.add('is-invalid');
        field.classList.remove('is-valid');
        
        // Find or create error message element
        let errorElement = field.parentNode.querySelector('.field-error');
        if (!errorElement) {
            errorElement = document.createElement('div');
            errorElement.className = 'field-error invalid-feedback';
            field.parentNode.appendChild(errorElement);
        }
        
        errorElement.innerHTML = `<i class="fas fa-exclamation-circle me-1"></i>${message}`;
        errorElement.style.display = 'block';
    },
    
    /**
     * Clear error message for a field
     * @param {HTMLElement} field - The input field element
     */
    clearFieldError: function(field) {
        field.classList.remove('is-invalid');
        
        const errorElement = field.parentNode.querySelector('.field-error');
        if (errorElement) {
            errorElement.style.display = 'none';
        }
        
        // Add valid class if field has value and is not required or has value
        if (field.value.trim() || !field.hasAttribute('required')) {
            field.classList.add('is-valid');
        }
    },
    
    /**
     * Show form-level error message
     * @param {string} message - Error message to display
     */
    showFormError: function(message) {
        // Find or create form error container
        let errorContainer = document.querySelector('.form-error-container');
        if (!errorContainer) {
            errorContainer = document.createElement('div');
            errorContainer.className = 'form-error-container';
            
            // Insert at the top of the form
            const form = document.querySelector('form');
            if (form) {
                form.insertBefore(errorContainer, form.firstChild);
            }
        }
        
        errorContainer.innerHTML = `
            <div class="alert alert-danger" role="alert">
                <i class="fas fa-exclamation-triangle me-2"></i>
                ${message}
            </div>
        `;
        
        // Scroll to error message
        errorContainer.scrollIntoView({ behavior: 'smooth', block: 'center' });
    },
    
    /**
     * Clear all form errors
     */
    clearFormErrors: function() {
        const errorContainer = document.querySelector('.form-error-container');
        if (errorContainer) {
            errorContainer.innerHTML = '';
        }
        
        // Clear all field errors
        const fields = document.querySelectorAll('.is-invalid');
        fields.forEach(field => {
            this.clearFieldError(field);
        });
    }
};

// Auto-initialize validation when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    // Initialize validation for common forms
    ValidationUtils.initializeForm('#userForm');
    ValidationUtils.initializeForm('#resumeForm');
    ValidationUtils.initializeForm('#personalInfoForm');
    ValidationUtils.initializeForm('#educationForm');
    ValidationUtils.initializeForm('#experienceForm');
    ValidationUtils.initializeForm('#skillsForm');
});

// Export for use in other scripts
window.ValidationUtils = ValidationUtils;