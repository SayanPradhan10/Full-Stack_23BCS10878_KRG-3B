/**
 * Resume Forms JavaScript functionality
 */

// Initialize form functionality when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    initializeForms();
});

/**
 * Initialize all form functionality
 */
function initializeForms() {
    // Initialize dynamic form sections
    initializeDynamicSections();
    
    // Initialize form validation
    initializeValidation();
    
    // Initialize auto-save functionality
    initializeAutoSave();
    
    // Initialize form navigation
    initializeFormNavigation();
    
    // Initialize date pickers
    initializeDatePickers();
}

/**
 * Initialize dynamic sections (add/remove entries)
 */
function initializeDynamicSections() {
    // Education section
    initializeEducationSection();
    
    // Work experience section
    initializeWorkExperienceSection();
    
    // Skills section
    initializeSkillsSection();
}

/**
 * Initialize education section functionality
 */
function initializeEducationSection() {
    const addEducationBtn = document.getElementById('add-education');
    const educationContainer = document.getElementById('education-container');
    
    if (addEducationBtn && educationContainer) {
        addEducationBtn.addEventListener('click', function() {
            addEducationEntry();
        });
        
        // Initialize remove buttons for existing entries
        updateEducationRemoveButtons();
    }
}

/**
 * Initialize work experience section functionality
 */
function initializeWorkExperienceSection() {
    const addExperienceBtn = document.getElementById('add-experience');
    const experienceContainer = document.getElementById('experience-container');
    
    if (addExperienceBtn && experienceContainer) {
        addExperienceBtn.addEventListener('click', function() {
            addWorkExperienceEntry();
        });
        
        // Initialize remove buttons for existing entries
        updateExperienceRemoveButtons();
    }
}

/**
 * Initialize skills section functionality
 */
function initializeSkillsSection() {
    const addSkillBtn = document.getElementById('add-skill');
    const skillsContainer = document.getElementById('skills-container');
    
    if (addSkillBtn && skillsContainer) {
        addSkillBtn.addEventListener('click', function() {
            addSkillEntry();
        });
        
        // Initialize remove buttons for existing entries
        updateSkillRemoveButtons();
    }
}

/**
 * Add new education entry
 */
function addEducationEntry() {
    const container = document.getElementById('education-container');
    const entries = container.querySelectorAll('.education-entry');
    const index = entries.length;
    
    const entryHtml = `
        <div class="education-entry form-section">
            <div class="section-header">
                <h4>Education ${index + 1}</h4>
                <button type="button" class="btn btn-danger btn-sm remove-education" onclick="removeEducationEntry(this)">
                    Remove
                </button>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="educations[${index}].institution">Institution *</label>
                    <input type="text" 
                           id="educations[${index}].institution"
                           name="educations[${index}].institution" 
                           class="form-control" 
                           required>
                </div>
                
                <div class="form-group">
                    <label for="educations[${index}].degree">Degree *</label>
                    <input type="text" 
                           id="educations[${index}].degree"
                           name="educations[${index}].degree" 
                           class="form-control" 
                           required>
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="educations[${index}].fieldOfStudy">Field of Study</label>
                    <input type="text" 
                           id="educations[${index}].fieldOfStudy"
                           name="educations[${index}].fieldOfStudy" 
                           class="form-control">
                </div>
                
                <div class="form-group">
                    <label for="educations[${index}].gpa">GPA</label>
                    <input type="number" 
                           id="educations[${index}].gpa"
                           name="educations[${index}].gpa" 
                           class="form-control" 
                           step="0.01" 
                           min="0" 
                           max="4.0">
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="educations[${index}].startDate">Start Date</label>
                    <input type="date" 
                           id="educations[${index}].startDate"
                           name="educations[${index}].startDate" 
                           class="form-control">
                </div>
                
                <div class="form-group">
                    <label for="educations[${index}].endDate">End Date</label>
                    <input type="date" 
                           id="educations[${index}].endDate"
                           name="educations[${index}].endDate" 
                           class="form-control">
                </div>
            </div>
        </div>
    `;
    
    container.insertAdjacentHTML('beforeend', entryHtml);
    updateEducationRemoveButtons();
}

/**
 * Add new work experience entry
 */
function addWorkExperienceEntry() {
    const container = document.getElementById('experience-container');
    const entries = container.querySelectorAll('.experience-entry');
    const index = entries.length;
    
    const entryHtml = `
        <div class="experience-entry form-section">
            <div class="section-header">
                <h4>Work Experience ${index + 1}</h4>
                <button type="button" class="btn btn-danger btn-sm remove-experience" onclick="removeExperienceEntry(this)">
                    Remove
                </button>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="workExperiences[${index}].company">Company *</label>
                    <input type="text" 
                           id="workExperiences[${index}].company"
                           name="workExperiences[${index}].company" 
                           class="form-control" 
                           required>
                </div>
                
                <div class="form-group">
                    <label for="workExperiences[${index}].position">Position *</label>
                    <input type="text" 
                           id="workExperiences[${index}].position"
                           name="workExperiences[${index}].position" 
                           class="form-control" 
                           required>
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="workExperiences[${index}].startDate">Start Date</label>
                    <input type="date" 
                           id="workExperiences[${index}].startDate"
                           name="workExperiences[${index}].startDate" 
                           class="form-control">
                </div>
                
                <div class="form-group">
                    <label for="workExperiences[${index}].endDate">End Date</label>
                    <input type="date" 
                           id="workExperiences[${index}].endDate"
                           name="workExperiences[${index}].endDate" 
                           class="form-control">
                </div>
            </div>
            
            <div class="form-group">
                <label for="workExperiences[${index}].description">Job Description</label>
                <textarea id="workExperiences[${index}].description"
                          name="workExperiences[${index}].description" 
                          class="form-control" 
                          rows="4"
                          placeholder="Describe your responsibilities and achievements..."></textarea>
            </div>
            
            <div class="form-group">
                <label class="checkbox-label">
                    <input type="checkbox" 
                           id="workExperiences[${index}].isInternship"
                           name="workExperiences[${index}].isInternship" 
                           value="true">
                    This was an internship
                </label>
            </div>
        </div>
    `;
    
    container.insertAdjacentHTML('beforeend', entryHtml);
    updateExperienceRemoveButtons();
}

/**
 * Add new skill entry
 */
function addSkillEntry() {
    const container = document.getElementById('skills-container');
    const entries = container.querySelectorAll('.skill-entry');
    const index = entries.length;
    
    const entryHtml = `
        <div class="skill-entry form-section">
            <div class="section-header">
                <h4>Skill ${index + 1}</h4>
                <button type="button" class="btn btn-danger btn-sm remove-skill" onclick="removeSkillEntry(this)">
                    Remove
                </button>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="skills[${index}].name">Skill Name *</label>
                    <input type="text" 
                           id="skills[${index}].name"
                           name="skills[${index}].name" 
                           class="form-control" 
                           required>
                </div>
                
                <div class="form-group">
                    <label for="skills[${index}].type">Skill Type *</label>
                    <select id="skills[${index}].type"
                            name="skills[${index}].type" 
                            class="form-control" 
                            required>
                        <option value="">Select Type</option>
                        <option value="TECHNICAL">Technical</option>
                        <option value="SOFT_SKILL">Soft Skill</option>
                        <option value="LANGUAGE">Language</option>
                    </select>
                </div>
            </div>
            
            <div class="form-group">
                <label for="skills[${index}].proficiencyLevel">Proficiency Level</label>
                <select id="skills[${index}].proficiencyLevel"
                        name="skills[${index}].proficiencyLevel" 
                        class="form-control">
                    <option value="">Select Level</option>
                    <option value="Beginner">Beginner</option>
                    <option value="Intermediate">Intermediate</option>
                    <option value="Advanced">Advanced</option>
                    <option value="Expert">Expert</option>
                </select>
            </div>
        </div>
    `;
    
    container.insertAdjacentHTML('beforeend', entryHtml);
    updateSkillRemoveButtons();
}

/**
 * Remove education entry
 */
function removeEducationEntry(button) {
    const entry = button.closest('.education-entry');
    entry.remove();
    updateEducationRemoveButtons();
    reindexEducationEntries();
}

/**
 * Remove work experience entry
 */
function removeExperienceEntry(button) {
    const entry = button.closest('.experience-entry');
    entry.remove();
    updateExperienceRemoveButtons();
    reindexExperienceEntries();
}

/**
 * Remove skill entry
 */
function removeSkillEntry(button) {
    const entry = button.closest('.skill-entry');
    entry.remove();
    updateSkillRemoveButtons();
    reindexSkillEntries();
}

/**
 * Update education remove buttons visibility
 */
function updateEducationRemoveButtons() {
    const entries = document.querySelectorAll('.education-entry');
    entries.forEach((entry, index) => {
        const removeBtn = entry.querySelector('.remove-education');
        if (removeBtn) {
            removeBtn.style.display = entries.length > 1 ? 'inline-block' : 'none';
        }
    });
}

/**
 * Update experience remove buttons visibility
 */
function updateExperienceRemoveButtons() {
    const entries = document.querySelectorAll('.experience-entry');
    entries.forEach((entry, index) => {
        const removeBtn = entry.querySelector('.remove-experience');
        if (removeBtn) {
            removeBtn.style.display = entries.length > 1 ? 'inline-block' : 'none';
        }
    });
}

/**
 * Update skill remove buttons visibility
 */
function updateSkillRemoveButtons() {
    const entries = document.querySelectorAll('.skill-entry');
    entries.forEach((entry, index) => {
        const removeBtn = entry.querySelector('.remove-skill');
        if (removeBtn) {
            removeBtn.style.display = entries.length > 1 ? 'inline-block' : 'none';
        }
    });
}

/**
 * Reindex education entries after removal
 */
function reindexEducationEntries() {
    const entries = document.querySelectorAll('.education-entry');
    entries.forEach((entry, index) => {
        // Update header
        const header = entry.querySelector('h4');
        if (header) {
            header.textContent = `Education ${index + 1}`;
        }
        
        // Update form field names and IDs
        const fields = entry.querySelectorAll('input, select, textarea');
        fields.forEach(field => {
            const name = field.getAttribute('name');
            const id = field.getAttribute('id');
            
            if (name && name.startsWith('educations[')) {
                field.setAttribute('name', name.replace(/educations\[\d+\]/, `educations[${index}]`));
            }
            if (id && id.startsWith('educations[')) {
                field.setAttribute('id', id.replace(/educations\[\d+\]/, `educations[${index}]`));
            }
        });
        
        // Update labels
        const labels = entry.querySelectorAll('label');
        labels.forEach(label => {
            const forAttr = label.getAttribute('for');
            if (forAttr && forAttr.startsWith('educations[')) {
                label.setAttribute('for', forAttr.replace(/educations\[\d+\]/, `educations[${index}]`));
            }
        });
    });
}

/**
 * Reindex experience entries after removal
 */
function reindexExperienceEntries() {
    const entries = document.querySelectorAll('.experience-entry');
    entries.forEach((entry, index) => {
        // Update header
        const header = entry.querySelector('h4');
        if (header) {
            header.textContent = `Work Experience ${index + 1}`;
        }
        
        // Update form field names and IDs
        const fields = entry.querySelectorAll('input, select, textarea');
        fields.forEach(field => {
            const name = field.getAttribute('name');
            const id = field.getAttribute('id');
            
            if (name && name.startsWith('workExperiences[')) {
                field.setAttribute('name', name.replace(/workExperiences\[\d+\]/, `workExperiences[${index}]`));
            }
            if (id && id.startsWith('workExperiences[')) {
                field.setAttribute('id', id.replace(/workExperiences\[\d+\]/, `workExperiences[${index}]`));
            }
        });
        
        // Update labels
        const labels = entry.querySelectorAll('label');
        labels.forEach(label => {
            const forAttr = label.getAttribute('for');
            if (forAttr && forAttr.startsWith('workExperiences[')) {
                label.setAttribute('for', forAttr.replace(/workExperiences\[\d+\]/, `workExperiences[${index}]`));
            }
        });
    });
}

/**
 * Reindex skill entries after removal
 */
function reindexSkillEntries() {
    const entries = document.querySelectorAll('.skill-entry');
    entries.forEach((entry, index) => {
        // Update header
        const header = entry.querySelector('h4');
        if (header) {
            header.textContent = `Skill ${index + 1}`;
        }
        
        // Update form field names and IDs
        const fields = entry.querySelectorAll('input, select, textarea');
        fields.forEach(field => {
            const name = field.getAttribute('name');
            const id = field.getAttribute('id');
            
            if (name && name.startsWith('skills[')) {
                field.setAttribute('name', name.replace(/skills\[\d+\]/, `skills[${index}]`));
            }
            if (id && id.startsWith('skills[')) {
                field.setAttribute('id', id.replace(/skills\[\d+\]/, `skills[${index}]`));
            }
        });
        
        // Update labels
        const labels = entry.querySelectorAll('label');
        labels.forEach(label => {
            const forAttr = label.getAttribute('for');
            if (forAttr && forAttr.startsWith('skills[')) {
                label.setAttribute('for', forAttr.replace(/skills\[\d+\]/, `skills[${index}]`));
            }
        });
    });
}

/**
 * Initialize form validation
 */
function initializeValidation() {
    const forms = document.querySelectorAll('form');
    
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            if (!validateForm(this)) {
                e.preventDefault();
                showValidationErrors();
            }
        });
        
        // Real-time validation
        const inputs = form.querySelectorAll('input, select, textarea');
        inputs.forEach(input => {
            input.addEventListener('blur', function() {
                validateField(this);
            });
        });
    });
}

/**
 * Validate entire form
 */
function validateForm(form) {
    let isValid = true;
    const inputs = form.querySelectorAll('input[required], select[required], textarea[required]');
    
    inputs.forEach(input => {
        if (!validateField(input)) {
            isValid = false;
        }
    });
    
    return isValid;
}

/**
 * Validate individual field
 */
function validateField(field) {
    const value = field.value.trim();
    let isValid = true;
    let errorMessage = '';
    
    // Required field validation
    if (field.hasAttribute('required') && !value) {
        isValid = false;
        errorMessage = 'This field is required';
    }
    
    // Email validation
    if (field.type === 'email' && value) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(value)) {
            isValid = false;
            errorMessage = 'Please enter a valid email address';
        }
    }
    
    // Phone validation
    if (field.type === 'tel' && value) {
        const phoneRegex = /^[\+]?[1-9][\d]{0,15}$/;
        if (!phoneRegex.test(value.replace(/[\s\-\(\)]/g, ''))) {
            isValid = false;
            errorMessage = 'Please enter a valid phone number';
        }
    }
    
    // GPA validation
    if (field.name && field.name.includes('gpa') && value) {
        const gpa = parseFloat(value);
        if (isNaN(gpa) || gpa < 0 || gpa > 4.0) {
            isValid = false;
            errorMessage = 'GPA must be between 0.0 and 4.0';
        }
    }
    
    // Date validation
    if (field.type === 'date' && value) {
        const date = new Date(value);
        const now = new Date();
        
        if (field.name && field.name.includes('startDate')) {
            // Start date shouldn't be in the far future
            if (date > now) {
                const endDateField = findRelatedEndDate(field);
                if (!endDateField || !endDateField.value) {
                    // Only warn if no end date is set (might be current position)
                }
            }
        }
        
        if (field.name && field.name.includes('endDate')) {
            const startDateField = findRelatedStartDate(field);
            if (startDateField && startDateField.value) {
                const startDate = new Date(startDateField.value);
                if (date < startDate) {
                    isValid = false;
                    errorMessage = 'End date must be after start date';
                }
            }
        }
    }
    
    // Update field appearance
    updateFieldValidation(field, isValid, errorMessage);
    
    return isValid;
}

/**
 * Find related end date field for start date validation
 */
function findRelatedEndDate(startDateField) {
    const name = startDateField.name;
    const endDateName = name.replace('startDate', 'endDate');
    return document.querySelector(`[name="${endDateName}"]`);
}

/**
 * Find related start date field for end date validation
 */
function findRelatedStartDate(endDateField) {
    const name = endDateField.name;
    const startDateName = name.replace('endDate', 'startDate');
    return document.querySelector(`[name="${startDateName}"]`);
}

/**
 * Update field validation appearance
 */
function updateFieldValidation(field, isValid, errorMessage) {
    // Remove existing validation classes
    field.classList.remove('field-valid', 'field-invalid');
    
    // Remove existing error message
    const existingError = field.parentNode.querySelector('.field-error');
    if (existingError) {
        existingError.remove();
    }
    
    if (field.value.trim()) {
        if (isValid) {
            field.classList.add('field-valid');
        } else {
            field.classList.add('field-invalid');
            
            // Add error message
            const errorDiv = document.createElement('div');
            errorDiv.className = 'field-error';
            errorDiv.textContent = errorMessage;
            field.parentNode.appendChild(errorDiv);
        }
    }
}

/**
 * Show validation errors summary
 */
function showValidationErrors() {
    const invalidFields = document.querySelectorAll('.field-invalid');
    if (invalidFields.length > 0) {
        // Scroll to first invalid field
        invalidFields[0].scrollIntoView({ behavior: 'smooth', block: 'center' });
        invalidFields[0].focus();
        
        // Show alert
        showAlert(`Please fix ${invalidFields.length} validation error(s) before submitting.`, 'error');
    }
}

/**
 * Initialize auto-save functionality
 */
function initializeAutoSave() {
    let autoSaveTimeout;
    const forms = document.querySelectorAll('form[data-autosave="true"]');
    
    forms.forEach(form => {
        const inputs = form.querySelectorAll('input, select, textarea');
        
        inputs.forEach(input => {
            input.addEventListener('input', function() {
                clearTimeout(autoSaveTimeout);
                autoSaveTimeout = setTimeout(() => {
                    autoSaveForm(form);
                }, 2000); // Auto-save after 2 seconds of inactivity
            });
        });
    });
}

/**
 * Auto-save form data to localStorage
 */
function autoSaveForm(form) {
    const formData = new FormData(form);
    const data = {};
    
    for (let [key, value] of formData.entries()) {
        data[key] = value;
    }
    
    const formId = form.id || 'resume-form';
    localStorage.setItem(`autosave-${formId}`, JSON.stringify(data));
    
    // Show auto-save indicator
    showAutoSaveIndicator();
}

/**
 * Show auto-save indicator
 */
function showAutoSaveIndicator() {
    const indicator = document.getElementById('autosave-indicator');
    if (indicator) {
        indicator.textContent = 'Draft saved';
        indicator.style.opacity = '1';
        
        setTimeout(() => {
            indicator.style.opacity = '0';
        }, 2000);
    }
}

/**
 * Initialize form navigation
 */
function initializeFormNavigation() {
    const nextButtons = document.querySelectorAll('.btn-next');
    const prevButtons = document.querySelectorAll('.btn-prev');
    
    nextButtons.forEach(button => {
        button.addEventListener('click', function() {
            const currentSection = this.closest('.form-section');
            const nextSection = currentSection.nextElementSibling;
            
            if (nextSection && validateSection(currentSection)) {
                showSection(nextSection);
                hideSection(currentSection);
            }
        });
    });
    
    prevButtons.forEach(button => {
        button.addEventListener('click', function() {
            const currentSection = this.closest('.form-section');
            const prevSection = currentSection.previousElementSibling;
            
            if (prevSection) {
                showSection(prevSection);
                hideSection(currentSection);
            }
        });
    });
}

/**
 * Validate form section
 */
function validateSection(section) {
    const requiredFields = section.querySelectorAll('input[required], select[required], textarea[required]');
    let isValid = true;
    
    requiredFields.forEach(field => {
        if (!validateField(field)) {
            isValid = false;
        }
    });
    
    return isValid;
}

/**
 * Show form section
 */
function showSection(section) {
    section.style.display = 'block';
    section.scrollIntoView({ behavior: 'smooth' });
}

/**
 * Hide form section
 */
function hideSection(section) {
    section.style.display = 'none';
}

/**
 * Initialize date pickers with constraints
 */
function initializeDatePickers() {
    const dateInputs = document.querySelectorAll('input[type="date"]');
    
    dateInputs.forEach(input => {
        // Set reasonable date constraints
        if (input.name && input.name.includes('startDate')) {
            // Start dates can be up to 50 years ago
            const minDate = new Date();
            minDate.setFullYear(minDate.getFullYear() - 50);
            input.min = minDate.toISOString().split('T')[0];
            
            // Start dates can be up to 1 year in the future (for planned positions)
            const maxDate = new Date();
            maxDate.setFullYear(maxDate.getFullYear() + 1);
            input.max = maxDate.toISOString().split('T')[0];
        }
        
        if (input.name && input.name.includes('endDate')) {
            // End dates can be up to 50 years ago
            const minDate = new Date();
            minDate.setFullYear(minDate.getFullYear() - 50);
            input.min = minDate.toISOString().split('T')[0];
            
            // End dates can be up to 5 years in the future
            const maxDate = new Date();
            maxDate.setFullYear(maxDate.getFullYear() + 5);
            input.max = maxDate.toISOString().split('T')[0];
        }
    });
}

/**
 * Show alert message
 */
function showAlert(message, type) {
    const alertHtml = `
        <div class="alert alert-${type}">
            ${escapeHtml(message)}
        </div>
    `;
    
    const mainContent = document.querySelector('.main-content');
    if (mainContent) {
        mainContent.insertAdjacentHTML('afterbegin', alertHtml);
        
        // Auto-hide after 5 seconds
        setTimeout(() => {
            const alert = mainContent.querySelector('.alert');
            if (alert) {
                alert.style.opacity = '0';
                setTimeout(() => alert.remove(), 300);
            }
        }, 5000);
    }
}

/**
 * Escape HTML to prevent XSS
 */
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}