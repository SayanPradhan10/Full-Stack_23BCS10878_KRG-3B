// Dashboard JavaScript

document.addEventListener('DOMContentLoaded', function() {
    // Initialize dashboard functionality
    initializeSearch();
    initializeSort();
    initializeDuplicateButtons();
    initializeDeleteButtons();
});

// Search functionality
function initializeSearch() {
    const searchInput = document.getElementById('resume-search');
    const resumeCards = document.querySelectorAll('.resume-card');
    
    if (searchInput && resumeCards.length > 0) {
        searchInput.addEventListener('input', function() {
            const searchTerm = this.value.toLowerCase();
            
            resumeCards.forEach(card => {
                const title = card.querySelector('.resume-title').textContent.toLowerCase();
                const template = card.querySelector('.template-badge').textContent.toLowerCase();
                
                if (title.includes(searchTerm) || template.includes(searchTerm)) {
                    card.style.display = 'block';
                } else {
                    card.style.display = 'none';
                }
            });
        });
    }
}

// Sort functionality
function initializeSort() {
    const sortSelect = document.getElementById('resume-sort');
    const resumeGrid = document.querySelector('.resume-grid');
    
    if (sortSelect && resumeGrid) {
        sortSelect.addEventListener('change', function() {
            const sortBy = this.value;
            const resumeCards = Array.from(resumeGrid.querySelectorAll('.resume-card'));
            
            resumeCards.sort((a, b) => {
                switch (sortBy) {
                    case 'title':
                        const titleA = a.querySelector('.resume-title').textContent;
                        const titleB = b.querySelector('.resume-title').textContent;
                        return titleA.localeCompare(titleB);
                    
                    case 'template':
                        const templateA = a.querySelector('.template-badge').textContent;
                        const templateB = b.querySelector('.template-badge').textContent;
                        return templateA.localeCompare(templateB);
                    
                    case 'date':
                    default:
                        const dateA = new Date(a.getAttribute('data-created-date'));
                        const dateB = new Date(b.getAttribute('data-created-date'));
                        return dateB - dateA; // Newest first
                }
            });
            
            // Re-append sorted cards
            resumeCards.forEach(card => resumeGrid.appendChild(card));
        });
    }
}

// Duplicate resume functionality
function initializeDuplicateButtons() {
    const duplicateButtons = document.querySelectorAll('.btn-duplicate-resume');
    
    duplicateButtons.forEach(button => {
        button.addEventListener('click', function() {
            const resumeId = this.getAttribute('data-resume-id');
            const resumeTitle = this.getAttribute('data-resume-title');
            
            if (confirm(`Are you sure you want to duplicate "${resumeTitle}"?`)) {
                duplicateResume(resumeId);
            }
        });
    });
}

// Delete resume functionality
function initializeDeleteButtons() {
    const deleteButtons = document.querySelectorAll('.btn-delete-resume');
    
    deleteButtons.forEach(button => {
        button.addEventListener('click', function() {
            const resumeId = this.getAttribute('data-resume-id');
            const resumeTitle = this.getAttribute('data-resume-title');
            
            if (confirm(`Are you sure you want to delete "${resumeTitle}"? This action cannot be undone.`)) {
                deleteResume(resumeId);
            }
        });
    });
}

// Duplicate resume function
function duplicateResume(resumeId) {
    showLoadingState('Duplicating resume...');
    
    fetch(`/resume/${resumeId}/duplicate`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'X-Requested-With': 'XMLHttpRequest'
        },
        body: getCsrfToken()
    })
    .then(response => {
        if (response.ok) {
            showMessage('Resume duplicated successfully!', 'success');
            setTimeout(() => {
                location.reload();
            }, 1000);
        } else {
            throw new Error('Duplication failed');
        }
    })
    .catch(error => {
        console.error('Error duplicating resume:', error);
        hideLoadingState();
        showMessage('Failed to duplicate resume. Please try again.', 'error');
    });
}

// Delete resume function
function deleteResume(resumeId) {
    showLoadingState('Deleting resume...');
    
    fetch(`/resume/${resumeId}/delete`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'X-Requested-With': 'XMLHttpRequest'
        },
        body: getCsrfToken()
    })
    .then(response => {
        if (response.ok) {
            showMessage('Resume deleted successfully!', 'success');
            setTimeout(() => {
                location.reload();
            }, 1000);
        } else {
            throw new Error('Deletion failed');
        }
    })
    .catch(error => {
        console.error('Error deleting resume:', error);
        hideLoadingState();
        showMessage('Failed to delete resume. Please try again.', 'error');
    });
}

// Utility Functions
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

function getCsrfToken() {
    const token = document.querySelector('meta[name="_csrf"]');
    const header = document.querySelector('meta[name="_csrf_header"]');
    
    if (token && header) {
        return `${header.getAttribute('content')}=${token.getAttribute('content')}`;
    }
    return '';
}
//
 Template Selection Enhancement
function initializeTemplateButtons() {
    const templateButtons = document.querySelectorAll('a[href*="/template/select"]');
    
    templateButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            const resumeId = this.href.match(/resumeId=(\d+)/)?.[1];
            if (resumeId) {
                // Add loading state
                this.innerHTML = '🎨 Loading...';
                this.style.pointerEvents = 'none';
                
                // Reset after a delay if page doesn't load
                setTimeout(() => {
                    this.innerHTML = '🎨 Template';
                    this.style.pointerEvents = 'auto';
                }, 5000);
            }
        });
    });
}

// Initialize template buttons when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    initializeTemplateButtons();
});

// Add template preview functionality
function showTemplatePreview(resumeId, templateType) {
    showLoadingState('Loading template preview...');
    
    fetch(`/template/api/preview/${templateType}?resumeId=${resumeId}`, {
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
        showTemplatePreviewModal(templateType, data, resumeId);
    })
    .catch(error => {
        console.error('Error loading template preview:', error);
        hideLoadingState();
        showMessage('Failed to load template preview', 'error');
    });
}

// Show template preview modal
function showTemplatePreviewModal(templateType, data, resumeId) {
    // Create modal if it doesn't exist
    let modal = document.getElementById('template-preview-modal');
    if (!modal) {
        modal = createTemplatePreviewModal();
        document.body.appendChild(modal);
    }
    
    // Update modal content
    const modalTitle = modal.querySelector('.modal-title');
    const modalBody = modal.querySelector('.modal-body');
    const modalFooter = modal.querySelector('.modal-footer');
    
    modalTitle.textContent = `${templateType} Template Preview`;
    
    modalBody.innerHTML = `
        <div class="template-preview-info">
            <div class="text-center mb-3">
                <div style="font-size: 3rem; margin-bottom: 1rem;">
                    ${getTemplateIcon(templateType)}
                </div>
                <h4>${templateType} Template</h4>
                <p class="text-muted">${getTemplateDescription(templateType)}</p>
            </div>
            <div class="template-features">
                <h5>Key Features:</h5>
                <ul class="list-unstyled">
                    ${getTemplateFeatures(templateType).map(feature => `<li>✓ ${feature}</li>`).join('')}
                </ul>
            </div>
        </div>
    `;
    
    modalFooter.innerHTML = `
        <button type="button" class="btn btn-secondary" onclick="closeTemplatePreviewModal()">Close</button>
        <a href="/template/select?resumeId=${resumeId}" class="btn btn-primary">Select This Template</a>
    `;
    
    // Show modal
    modal.style.display = 'block';
}

// Create template preview modal
function createTemplatePreviewModal() {
    const modal = document.createElement('div');
    modal.id = 'template-preview-modal';
    modal.className = 'modal';
    modal.innerHTML = `
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title">Template Preview</h3>
                <button type="button" class="modal-close" onclick="closeTemplatePreviewModal()">&times;</button>
            </div>
            <div class="modal-body">
                <!-- Content will be inserted here -->
            </div>
            <div class="modal-footer">
                <!-- Footer will be inserted here -->
            </div>
        </div>
    `;
    
    // Add modal styles if not already added
    if (!document.getElementById('template-modal-styles')) {
        const style = document.createElement('style');
        style.id = 'template-modal-styles';
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
                max-width: 500px;
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
        `;
        document.head.appendChild(style);
    }
    
    return modal;
}

// Close template preview modal
function closeTemplatePreviewModal() {
    const modal = document.getElementById('template-preview-modal');
    if (modal) {
        modal.style.display = 'none';
    }
}

// Helper functions for template information
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