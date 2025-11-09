/**
 * Template Selection JavaScript functionality
 */

// Global variables
let selectedTemplate = null;
let resumeId = null;
let previewModal = null;

// Initialize template selection when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    initializeTemplateSelection();
});

/**
 * Initialize template selection functionality
 */
function initializeTemplateSelection() {
    // Get resume ID from URL or data attribute
    resumeId = getResumeIdFromUrl() || document.body.dataset.resumeId;
    
    // Initialize template cards
    initializeTemplateCards();
    
    // Initialize preview functionality
    initializePreviewFunctionality();
    
    // Initialize template application
    initializeTemplateApplication();
    
    // Initialize real-time preview
    initializeRealTimePreview();
}

/**
 * Initialize template cards interaction
 */
function initializeTemplateCards() {
    const templateCards = document.querySelectorAll('.template-card');
    
    templateCards.forEach(card => {
        card.addEventListener('click', function() {
            selectTemplate(this);
        });
        
        // Initialize preview buttons
        const previewBtn = card.querySelector('.btn-preview');
        if (previewBtn) {
            previewBtn.addEventListener('click', function(e) {
                e.stopPropagation();
                const templateType = this.dataset.template;
                showTemplatePreview(templateType);
            });
        }
        
        // Initialize apply buttons
        const applyBtn = card.querySelector('.btn-apply');
        if (applyBtn) {
            applyBtn.addEventListener('click', function(e) {
                e.stopPropagation();
                const templateType = this.dataset.template;
                applyTemplate(templateType);
            });
        }
    });
}

/**
 * Select a template
 */
function selectTemplate(card) {
    // Remove selected class from all cards
    document.querySelectorAll('.template-card').forEach(c => {
        c.classList.remove('selected');
    });
    
    // Add selected class to clicked card
    card.classList.add('selected');
    
    // Update selected template
    selectedTemplate = card.dataset.template;
    
    // Update form input if exists
    const templateInput = document.querySelector('input[name="templateType"]');
    if (templateInput) {
        templateInput.value = selectedTemplate;
    }
    
    // Update radio button if exists
    const radioButton = document.querySelector(`input[value="${selectedTemplate}"]`);
    if (radioButton) {
        radioButton.checked = true;
    }
    
    // Enable apply button
    updateApplyButton();
    
    // Show real-time preview if available
    if (resumeId) {
        showRealTimePreview(selectedTemplate);
    }
}

/**
 * Initialize preview functionality
 */
function initializePreviewFunctionality() {
    // Close preview modal when clicking outside
    document.addEventListener('click', function(e) {
        if (e.target.classList.contains('preview-modal-overlay')) {
            closePreviewModal();
        }
    });
    
    // Close preview modal with Escape key
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape' && previewModal) {
            closePreviewModal();
        }
    });
}

/**
 * Show template preview modal
 */
function showTemplatePreview(templateType) {
    // Create preview modal
    const modalHtml = `
        <div class="preview-modal-overlay" id="preview-modal">
            <div class="preview-modal">
                <div class="preview-modal-header">
                    <h3>${getTemplateDisplayName(templateType)} Template Preview</h3>
                    <button type="button" class="modal-close" onclick="closePreviewModal()">&times;</button>
                </div>
                <div class="preview-modal-body">
                    <div class="preview-loading">
                        <div class="loading"></div>
                        <p>Loading preview...</p>
                    </div>
                    <div class="preview-content" style="display: none;">
                        <div class="template-preview-large" id="template-preview-${templateType}">
                            ${getTemplatePreviewContent(templateType)}
                        </div>
                    </div>
                </div>
                <div class="preview-modal-footer">
                    <button type="button" class="btn btn-secondary" onclick="closePreviewModal()">Close</button>
                    ${resumeId ? `<button type="button" class="btn btn-primary" onclick="applyTemplate('${templateType}')">Apply Template</button>` : ''}
                </div>
            </div>
        </div>
    `;
    
    document.body.insertAdjacentHTML('beforeend', modalHtml);
    previewModal = document.getElementById('preview-modal');
    
    // Load preview content
    loadTemplatePreview(templateType);
}

/**
 * Close preview modal
 */
function closePreviewModal() {
    if (previewModal) {
        previewModal.remove();
        previewModal = null;
    }
}

/**
 * Load template preview content
 */
function loadTemplatePreview(templateType) {
    // Simulate loading delay
    setTimeout(() => {
        const loadingDiv = document.querySelector('.preview-loading');
        const contentDiv = document.querySelector('.preview-content');
        
        if (loadingDiv && contentDiv) {
            loadingDiv.style.display = 'none';
            contentDiv.style.display = 'block';
        }
        
        // If we have a resume ID, load actual preview
        if (resumeId) {
            loadActualPreview(templateType);
        }
    }, 1000);
}

/**
 * Load actual resume preview with template
 */
function loadActualPreview(templateType) {
    fetch(`${getContextPath()}/template/api/preview/${templateType}?resumeId=${resumeId}`)
        .then(response => response.json())
        .then(data => {
            const previewContainer = document.getElementById(`template-preview-${templateType}`);
            if (previewContainer && data.preview) {
                previewContainer.innerHTML = data.preview;
            }
        })
        .catch(error => {
            console.error('Error loading preview:', error);
            const previewContainer = document.getElementById(`template-preview-${templateType}`);
            if (previewContainer) {
                previewContainer.innerHTML = '<p class="text-danger">Failed to load preview</p>';
            }
        });
}

/**
 * Initialize template application
 */
function initializeTemplateApplication() {
    const applyButton = document.getElementById('apply-template-btn');
    if (applyButton) {
        applyButton.addEventListener('click', function() {
            if (selectedTemplate) {
                applyTemplate(selectedTemplate);
            } else {
                showAlert('Please select a template first', 'error');
            }
        });
    }
}

/**
 * Apply template to resume
 */
function applyTemplate(templateType) {
    if (!resumeId) {
        showAlert('No resume selected', 'error');
        return;
    }
    
    // Show loading state
    const applyBtn = document.querySelector(`[onclick="applyTemplate('${templateType}')"]`) || 
                     document.getElementById('apply-template-btn');
    
    if (applyBtn) {
        showButtonLoading(applyBtn);
    }
    
    // Create form and submit
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = `${getContextPath()}/template/apply`;
    
    // Add CSRF token
    const csrfToken = document.querySelector('meta[name="_csrf"]');
    if (csrfToken) {
        const csrfInput = document.createElement('input');
        csrfInput.type = 'hidden';
        csrfInput.name = '_token';
        csrfInput.value = csrfToken.getAttribute('content');
        form.appendChild(csrfInput);
    }
    
    // Add resume ID
    const resumeIdInput = document.createElement('input');
    resumeIdInput.type = 'hidden';
    resumeIdInput.name = 'resumeId';
    resumeIdInput.value = resumeId;
    form.appendChild(resumeIdInput);
    
    // Add template type
    const templateInput = document.createElement('input');
    templateInput.type = 'hidden';
    templateInput.name = 'templateType';
    templateInput.value = templateType;
    form.appendChild(templateInput);
    
    document.body.appendChild(form);
    form.submit();
}

/**
 * Initialize real-time preview
 */
function initializeRealTimePreview() {
    const previewContainer = document.getElementById('realtime-preview');
    if (previewContainer && resumeId) {
        // Load initial preview
        const currentTemplate = document.querySelector('.template-card.selected');
        if (currentTemplate) {
            showRealTimePreview(currentTemplate.dataset.template);
        }
    }
}

/**
 * Show real-time preview
 */
function showRealTimePreview(templateType) {
    const previewContainer = document.getElementById('realtime-preview');
    if (!previewContainer) return;
    
    // Show loading state
    previewContainer.innerHTML = `
        <div class="preview-loading">
            <div class="loading"></div>
            <p>Updating preview...</p>
        </div>
    `;
    
    // Load preview
    fetch(`${getContextPath()}/template/api/preview/${templateType}?resumeId=${resumeId}`)
        .then(response => response.json())
        .then(data => {
            previewContainer.innerHTML = `
                <div class="preview-header">
                    <h4>${getTemplateDisplayName(templateType)} Preview</h4>
                </div>
                <div class="preview-content">
                    ${data.preview || getTemplatePreviewContent(templateType)}
                </div>
            `;
        })
        .catch(error => {
            console.error('Error loading real-time preview:', error);
            previewContainer.innerHTML = `
                <div class="preview-error">
                    <p>Failed to load preview</p>
                    <button type="button" class="btn btn-sm btn-secondary" onclick="showRealTimePreview('${templateType}')">
                        Retry
                    </button>
                </div>
            `;
        });
}

/**
 * Update apply button state
 */
function updateApplyButton() {
    const applyButton = document.getElementById('apply-template-btn');
    if (applyButton) {
        applyButton.disabled = !selectedTemplate;
        applyButton.textContent = selectedTemplate ? 
            `Apply ${getTemplateDisplayName(selectedTemplate)} Template` : 
            'Select a Template';
    }
}

/**
 * Get template display name
 */
function getTemplateDisplayName(templateType) {
    const names = {
        'CLASSIC': 'Classic',
        'MODERN': 'Modern',
        'CREATIVE': 'Creative'
    };
    return names[templateType] || templateType;
}

/**
 * Get template preview content (fallback)
 */
function getTemplatePreviewContent(templateType) {
    const previews = {
        'CLASSIC': `
            <div class="template-preview-classic">
                <div class="preview-header">
                    <h2>John Doe</h2>
                    <p>john.doe@email.com | (555) 123-4567</p>
                </div>
                <div class="preview-section">
                    <h3>Education</h3>
                    <div class="preview-item">
                        <strong>Bachelor of Science in Computer Science</strong><br>
                        University of Technology, 2020-2024
                    </div>
                </div>
                <div class="preview-section">
                    <h3>Experience</h3>
                    <div class="preview-item">
                        <strong>Software Developer Intern</strong><br>
                        Tech Company Inc., Summer 2023
                    </div>
                </div>
                <div class="preview-section">
                    <h3>Skills</h3>
                    <div class="preview-item">
                        Java, Python, JavaScript, React
                    </div>
                </div>
            </div>
        `,
        'MODERN': `
            <div class="template-preview-modern">
                <div class="preview-sidebar">
                    <div class="preview-profile">
                        <div class="preview-avatar"></div>
                        <h2>John Doe</h2>
                        <p>Software Developer</p>
                    </div>
                    <div class="preview-contact">
                        <h4>Contact</h4>
                        <p>john.doe@email.com</p>
                        <p>(555) 123-4567</p>
                    </div>
                    <div class="preview-skills">
                        <h4>Skills</h4>
                        <div class="skill-bar">Java</div>
                        <div class="skill-bar">Python</div>
                        <div class="skill-bar">React</div>
                    </div>
                </div>
                <div class="preview-main">
                    <div class="preview-section">
                        <h3>Education</h3>
                        <div class="preview-item">
                            <strong>Bachelor of Science</strong><br>
                            University of Technology
                        </div>
                    </div>
                    <div class="preview-section">
                        <h3>Experience</h3>
                        <div class="preview-item">
                            <strong>Software Developer Intern</strong><br>
                            Tech Company Inc.
                        </div>
                    </div>
                </div>
            </div>
        `,
        'CREATIVE': `
            <div class="template-preview-creative">
                <div class="preview-header-creative">
                    <div class="preview-name-creative">
                        <h1>John Doe</h1>
                        <h2>Creative Developer</h2>
                    </div>
                    <div class="preview-contact-creative">
                        <p>📧 john.doe@email.com</p>
                        <p>📱 (555) 123-4567</p>
                    </div>
                </div>
                <div class="preview-content-creative">
                    <div class="preview-column">
                        <div class="preview-section-creative">
                            <h3>🎓 Education</h3>
                            <div class="preview-item-creative">
                                <strong>Computer Science Degree</strong><br>
                                University of Technology
                            </div>
                        </div>
                        <div class="preview-section-creative">
                            <h3>🛠️ Skills</h3>
                            <div class="preview-tags">
                                <span class="tag">Java</span>
                                <span class="tag">Python</span>
                                <span class="tag">React</span>
                            </div>
                        </div>
                    </div>
                    <div class="preview-column">
                        <div class="preview-section-creative">
                            <h3>💼 Experience</h3>
                            <div class="preview-item-creative">
                                <strong>Software Developer Intern</strong><br>
                                Tech Company Inc.
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `
    };
    
    return previews[templateType] || '<p>Template preview not available</p>';
}

/**
 * Get resume ID from URL
 */
function getResumeIdFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('resumeId');
}

/**
 * Get context path
 */
function getContextPath() {
    return window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1)) || '';
}

/**
 * Show loading state for buttons
 */
function showButtonLoading(button) {
    const originalText = button.textContent;
    button.dataset.originalText = originalText;
    button.innerHTML = '<span class="loading"></span> Applying...';
    button.disabled = true;
}

/**
 * Hide loading state for buttons
 */
function hideButtonLoading(button) {
    const originalText = button.dataset.originalText;
    if (originalText) {
        button.textContent = originalText;
        button.disabled = false;
    }
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

/**
 * Template comparison functionality
 */
function compareTemplates() {
    const selectedCards = document.querySelectorAll('.template-card.selected');
    if (selectedCards.length < 2) {
        showAlert('Please select at least 2 templates to compare', 'info');
        return;
    }
    
    const templates = Array.from(selectedCards).map(card => card.dataset.template);
    showTemplateComparison(templates);
}

/**
 * Show template comparison modal
 */
function showTemplateComparison(templates) {
    const modalHtml = `
        <div class="preview-modal-overlay" id="comparison-modal">
            <div class="comparison-modal">
                <div class="preview-modal-header">
                    <h3>Template Comparison</h3>
                    <button type="button" class="modal-close" onclick="closeComparisonModal()">&times;</button>
                </div>
                <div class="comparison-content">
                    ${templates.map(template => `
                        <div class="comparison-column">
                            <h4>${getTemplateDisplayName(template)}</h4>
                            <div class="template-preview-small">
                                ${getTemplatePreviewContent(template)}
                            </div>
                            <div class="template-features">
                                <h5>Features:</h5>
                                <ul>
                                    ${getTemplateFeatures(template).map(feature => `<li>${feature}</li>`).join('')}
                                </ul>
                            </div>
                            <button type="button" class="btn btn-primary btn-sm" onclick="applyTemplate('${template}')">
                                Choose This Template
                            </button>
                        </div>
                    `).join('')}
                </div>
            </div>
        </div>
    `;
    
    document.body.insertAdjacentHTML('beforeend', modalHtml);
}

/**
 * Close comparison modal
 */
function closeComparisonModal() {
    const modal = document.getElementById('comparison-modal');
    if (modal) {
        modal.remove();
    }
}

/**
 * Get template features
 */
function getTemplateFeatures(templateType) {
    const features = {
        'CLASSIC': [
            'Clean and professional layout',
            'Traditional format',
            'ATS-friendly design',
            'Easy to read typography',
            'Suitable for all industries'
        ],
        'MODERN': [
            'Contemporary design',
            'Sidebar layout',
            'Visual skill indicators',
            'Color accents',
            'Great for tech roles'
        ],
        'CREATIVE': [
            'Eye-catching design',
            'Colorful and vibrant',
            'Icon integration',
            'Unique layout',
            'Perfect for creative fields'
        ]
    };
    
    return features[templateType] || [];
}