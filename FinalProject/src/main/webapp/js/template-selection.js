// Template Selection JavaScript

document.addEventListener('DOMContentLoaded', function() {
    initializeTemplateSelection();
    initializePreviewButtons();
    initializeApplyButtons();
    initializeRealtimePreview();
});

// Initialize template selection functionality
function initializeTemplateSelection() {
    const templateCards = document.querySelectorAll('.template-card');
    const applyBtn = document.getElementById('apply-template-btn');
    let selectedTemplate = null;
    
    templateCards.forEach(card => {
        card.addEventListener('click', function() {
            // Remove selection from all cards
            templateCards.forEach(c => c.classList.remove('selected'));
            
            // Add selection to clicked card
            this.classList.add('selected');
            
            // Update selected template
            selectedTemplate = this.dataset.template;
            
            // Enable apply button if resume ID exists
            if (applyBtn && selectedTemplate) {
                applyBtn.disabled = false;
                applyBtn.textContent = `Apply ${selectedTemplate} Template`;
                applyBtn.onclick = () => applyTemplate(selectedTemplate);
            }
            
            // Update realtime preview
            updateRealtimePreview(selectedTemplate);
        });
    });
}

// Initialize preview buttons
function initializePreviewButtons() {
    const previewButtons = document.querySelectorAll('.btn-preview');
    
    previewButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.stopPropagation();
            const templateType = this.dataset.template;
            showTemplatePreview(templateType);
        });
    });
}

// Initialize apply buttons
function initializeApplyButtons() {
    const applyButtons = document.querySelectorAll('.btn-apply');
    
    applyButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.stopPropagation();
            const templateType = this.dataset.template;
            applyTemplate(templateType);
        });
    });
}

// Show template preview in modal
function showTemplatePreview(templateType) {
    showLoadingState('Loading template preview...');
    
    fetch(`/template/api/preview/${templateType}`, {
        method: 'GET',
        headers: {
            'X-Requested-With': 'XMLHttpRequest'
        }
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        }
        throw new Error('Failed to load preview');
    })
    .then(data => {
        hideLoadingState();
        showPreviewModal(templateType, data);
    })
    .catch(error => {
        console.error('Error loading preview:', error);
        hideLoadingState();
        showMessage('Failed to load template preview', 'error');
    });
}

// Show preview modal
function showPreviewModal(templateType, data) {
    // Create modal if it doesn't exist
    let modal = document.getElementById('preview-modal');
    if (!modal) {
        modal = createPreviewModal();
        document.body.appendChild(modal);
    }
    
    // Update modal content
    const modalTitle = modal.querySelector('.modal-title');
    const modalBody = modal.querySelector('.modal-body');
    
    modalTitle.textContent = `${templateType} Template Preview`;
    
    modalBody.innerHTML = `
        <div class="template-preview-details">
            <div class="preview-image">
                <div class="template-preview-placeholder" style="font-size: 4rem; text-align: center; padding: 2rem; background: #f8f9fa; border-radius: 8px;">
                    ${getTemplateIcon(templateType)}
                </div>
            </div>
            <div class="preview-info">
                <h4>${data.name || templateType}</h4>
                <p>${data.description || getTemplateDescription(templateType)}</p>
                <div class="preview-features">
                    <h5>Features:</h5>
                    <ul>
                        ${(data.features || getTemplateFeatures(templateType)).map(feature => `<li>${feature}</li>`).join('')}
                    </ul>
                </div>
            </div>
        </div>
    `;
    
    // Show modal
    modal.style.display = 'block';
}

// Create preview modal
function createPreviewModal() {
    const modal = document.createElement('div');
    modal.id = 'preview-modal';
    modal.className = 'modal';
    modal.innerHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title">Template Preview</h3>
                <button type="button" class="modal-close">&times;</button>
            </div>
            <div class="modal-body">
                <!-- Content will be inserted here -->
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary modal-close">Close</button>
            </div>
        </div>
    `;
    
    // Add modal styles
    const style = document.createElement('style');
    style.textContent = `
        .modal {
            display: none;
            position: fixed;
            z-index: 10000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.5);
        }
        .modal-content {
            background-color: white;
            margin: 5% auto;
            padding: 0;
            border-radius: 8px;
            width: 80%;
            max-width: 600px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.3);
        }
        .modal-header {
            padding: 1rem 1.5rem;
            border-bottom: 1px solid #dee2e6;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .modal-body {
            padding: 1.5rem;
        }
        .modal-footer {
            padding: 1rem 1.5rem;
            border-top: 1px solid #dee2e6;
            text-align: right;
        }
        .modal-close {
            background: none;
            border: none;
            font-size: 1.5rem;
            cursor: pointer;
            color: #6c757d;
        }
        .template-preview-details {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1.5rem;
        }
        @media (max-width: 768px) {
            .template-preview-details {
                grid-template-columns: 1fr;
            }
        }
    `;
    document.head.appendChild(style);
    
    // Add close functionality
    modal.addEventListener('click', function(e) {
        if (e.target === modal || e.target.classList.contains('modal-close')) {
            modal.style.display = 'none';
        }
    });
    
    return modal;
}

// Apply template to resume
function applyTemplate(templateType) {
    const resumeId = document.body.dataset.resumeId;
    
    if (!resumeId) {
        showMessage('No resume selected', 'error');
        return;
    }
    
    if (confirm(`Are you sure you want to apply the ${templateType} template to your resume?`)) {
        showLoadingState('Applying template...');
        
        const formData = new FormData();
        formData.append('resumeId', resumeId);
        formData.append('templateType', templateType);
        formData.append(getCsrfTokenName(), getCsrfTokenValue());
        
        fetch('/template/apply', {
            method: 'POST',
            body: formData,
            headers: {
                'X-Requested-With': 'XMLHttpRequest'
            }
        })
        .then(response => {
            if (response.ok) {
                showMessage('Template applied successfully!', 'success');
                setTimeout(() => {
                    window.location.href = `/resume/${resumeId}`;
                }, 1000);
            } else {
                throw new Error('Failed to apply template');
            }
        })
        .catch(error => {
            console.error('Error applying template:', error);
            hideLoadingState();
            showMessage('Failed to apply template. Please try again.', 'error');
        });
    }
}

// Initialize realtime preview
function initializeRealtimePreview() {
    const previewContainer = document.getElementById('realtime-preview');
    if (!previewContainer) return;
    
    const resumeId = document.body.dataset.resumeId;
    if (!resumeId) return;
    
    // Load initial resume data
    loadResumeData(resumeId);
}

// Update realtime preview
function updateRealtimePreview(templateType) {
    const previewContainer = document.getElementById('realtime-preview');
    if (!previewContainer) return;
    
    const resumeId = document.body.dataset.resumeId;
    if (!resumeId) return;
    
    const previewContent = previewContainer.querySelector('.preview-content');
    if (!previewContent) return;
    
    previewContent.innerHTML = `
        <div style="display: flex; align-items: center; justify-content: center; height: 200px; color: #6c757d;">
            <p>Loading ${templateType} preview...</p>
        </div>
    `;
    
    // Simulate template preview (in a real app, this would fetch actual preview)
    setTimeout(() => {
        previewContent.innerHTML = `
            <div class="template-preview-${templateType.toLowerCase()}" style="padding: 1rem; border: 1px solid #dee2e6; border-radius: 4px; background: white;">
                <div style="text-align: center; color: #2c3e50;">
                    <div style="font-size: 2rem; margin-bottom: 0.5rem;">${getTemplateIcon(templateType)}</div>
                    <h4>${templateType} Template</h4>
                    <p style="color: #6c757d; font-size: 0.9rem;">Your resume will look like this</p>
                </div>
            </div>
        `;
    }, 500);
}

// Load resume data for preview
function loadResumeData(resumeId) {
    fetch(`/resume/${resumeId}/api/data`, {
        method: 'GET',
        headers: {
            'X-Requested-With': 'XMLHttpRequest'
        }
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        }
        return null;
    })
    .then(data => {
        if (data) {
            // Store resume data for preview generation
            window.resumeData = data;
        }
    })
    .catch(error => {
        console.error('Error loading resume data:', error);
    });
}

// Helper functions
function getTemplateIcon(templateType) {
    switch (templateType) {
        case 'CLASSIC': return '📄';
        case 'MODERN': return '🎨';
        case 'CREATIVE': return '✨';
        default: return '📄';
    }
}

function getTemplateDescription(templateType) {
    switch (templateType) {
        case 'CLASSIC': 
            return 'Clean and professional layout perfect for traditional industries. ATS-friendly design ensures your resume passes automated screening systems.';
        case 'MODERN': 
            return 'Contemporary design with sidebar layout and visual elements. Great for tech professionals and modern companies.';
        case 'CREATIVE': 
            return 'Eye-catching design with colors and icons. Perfect for creative fields like design, marketing, and media.';
        default: 
            return 'Professional resume template';
    }
}

function getTemplateFeatures(templateType) {
    switch (templateType) {
        case 'CLASSIC': 
            return ['ATS-friendly format', 'Traditional layout', 'Professional typography', 'Industry standard'];
        case 'MODERN': 
            return ['Sidebar design', 'Visual skill bars', 'Modern typography', 'Color accents'];
        case 'CREATIVE': 
            return ['Colorful design', 'Icon integration', 'Unique layout', 'Creative elements'];
        default: 
            return ['Professional design', 'Clean layout', 'Easy to read'];
    }
}

// Utility functions
function showLoadingState(message) {
    let overlay = document.getElementById('loading-overlay');
    if (!overlay) {
        overlay = document.createElement('div');
        overlay.id = 'loading-overlay';
        overlay.style.cssText = `
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.5);
            display: flex;
            align-items: center;
            justify-content: center;
            z-index: 9999;
            color: white;
            font-size: 1.2rem;
        `;
        document.body.appendChild(overlay);
    }
    overlay.textContent = message;
    overlay.style.display = 'flex';
}

function hideLoadingState() {
    const overlay = document.getElementById('loading-overlay');
    if (overlay) {
        overlay.style.display = 'none';
    }
}

function showMessage(message, type) {
    const messageEl = document.createElement('div');
    messageEl.className = `alert alert-${type}`;
    messageEl.textContent = message;
    messageEl.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        z-index: 10000;
        max-width: 300px;
        animation: slideIn 0.3s ease-out;
    `;
    
    const style = document.createElement('style');
    style.textContent = `
        @keyframes slideIn {
            from { transform: translateX(100%); opacity: 0; }
            to { transform: translateX(0); opacity: 1; }
        }
    `;
    document.head.appendChild(style);
    
    document.body.appendChild(messageEl);
    
    setTimeout(() => {
        if (messageEl.parentNode) {
            messageEl.parentNode.removeChild(messageEl);
        }
    }, 5000);
}

function getCsrfTokenName() {
    const meta = document.querySelector('meta[name="_csrf_header"]');
    return meta ? meta.getAttribute('content') : '_csrf';
}

function getCsrfTokenValue() {
    const meta = document.querySelector('meta[name="_csrf"]');
    return meta ? meta.getAttribute('content') : '';
}